package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.PayCentreReceiveRequestHandleService;
import com.dianping.ba.finance.exchange.api.PayCentreReceiveRequestService;
import com.dianping.ba.finance.exchange.api.ReceiveOrderService;
import com.dianping.ba.finance.exchange.api.datas.PayCentreReceiveRequestData;
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;
import com.dianping.ba.finance.exchange.api.dtos.PayCentreReceiveRequestDTO;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.ba.finance.exchange.api.enums.ReceiveOrderPayChannel;
import com.dianping.ba.finance.exchange.api.enums.ReceiveOrderStatus;
import com.dianping.ba.finance.exchange.api.enums.ReceiveType;
import com.dianping.ba.finance.exchange.biz.service.BizInfoService;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.aop.annotation.ReturnDefault;
import com.dianping.finance.common.util.ConvertUtils;
import com.dianping.finance.common.util.DateUtils;
import com.dianping.midas.finance.api.dto.CorporationDTO;
import com.dianping.midas.finance.api.service.CorporationService;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;

/**
 * 处理支付中心收款请求的Service类
 */
public class PayCentreReceiveRequestHandleServiceObject implements PayCentreReceiveRequestHandleService {

	private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.biz.monitor.PayCentreReceiveRequestHandleServiceObject");

	private ReceiveOrderService receiveOrderService;

	private PayCentreReceiveRequestService payCentreReceiveRequestService;

	private ExecutorService executorService;

	private BizInfoService bizInfoService;
	@Log(logBefore = true, logAfter = true, severity = 1)
	@ReturnDefault
	@Override
	public boolean handleReceiveRequest(final PayCentreReceiveRequestDTO payCentreReceiveRequestDTO) {
		final PayCentreReceiveRequestData payCentreReceiveRequestData = buildPayCentreReceiveRequestData(payCentreReceiveRequestDTO);
		payCentreReceiveRequestService.insertPayCentreReceiveRequest(payCentreReceiveRequestData);
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				doHandle(payCentreReceiveRequestDTO);
			}
		});
		return true;
	}

	private PayCentreReceiveRequestData buildPayCentreReceiveRequestData(PayCentreReceiveRequestDTO payCentreReceiveRequestDTO) {
		try {
			PayCentreReceiveRequestData payCentreReceiveRequestData = ConvertUtils.copy(payCentreReceiveRequestDTO, PayCentreReceiveRequestData.class);
			payCentreReceiveRequestData.setAddTime(DateUtils.getCurrentTime());
			return payCentreReceiveRequestData;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 处理支付中心收款请求
	 *
	 * @param requestDTO
	 */
	private void doHandle(PayCentreReceiveRequestDTO requestDTO) {
		if (requestDTO.getTradeType() == 1) {
			doHandleReceive(requestDTO);
		} else {
			doHandleReverse(requestDTO);
		}
	}

	/**
	 * 处理支付中心收款请求-冲销 (可考虑将此逻辑移至ReceiveOrder)
	 *
	 * @param requestDTO
	 * @return
	 */
	private boolean doHandleReverse(PayCentreReceiveRequestDTO requestDTO) {
		ReceiveOrderData originReceiveOrder = receiveOrderService.loadReceiveOrderByTradeNo(requestDTO.getOriTradeNo());
		if (originReceiveOrder == null) {
			MONITOR_LOGGER.error("severity=[1] reverse not find origin receive order");
			return false;
		}

		if (ReceiveOrderStatus.CONFIRMED.value() == originReceiveOrder.getStatus()) {
			requestDTO.setReceiveAmount(BigDecimal.ZERO.subtract(requestDTO.getReceiveAmount()));
			ReceiveOrderData receiveOrderData = buildReceiveOrderData(requestDTO, originReceiveOrder.getReverseRoId());
			int reverseRoId = receiveOrderService.createReceiveOrder(receiveOrderData);
			receiveOrderService.updateReverseRoId(originReceiveOrder.getRoId(), reverseRoId);
		} else {
			receiveOrderService.dropReceiveOrder(originReceiveOrder.getRoId(), requestDTO.getTradeNo());
		}
		return true;
	}

	/**
	 * 处理支付中心收款请求-收款
	 *
	 * @param requestDTO
	 * @return
	 */
	private void doHandleReceive(PayCentreReceiveRequestDTO requestDTO) {
		ReceiveOrderData receiveOrderData = buildReceiveOrderData(requestDTO, 0);
		receiveOrderService.createReceiveOrder(receiveOrderData);
	}

	private ReceiveOrderData buildReceiveOrderData(PayCentreReceiveRequestDTO requestDTO, int reverseRoId) {
		ReceiveOrderData roData = new ReceiveOrderData();
		int customerId = getAdCustomerIdByBizContent(requestDTO);
		roData.setCustomerId(customerId);
		roData.setTradeNo(requestDTO.getTradeNo());
		roData.setBusinessType(BusinessType.valueOfPayCentre(requestDTO.getBusinessType()).value());
		roData.setReceiveAmount(requestDTO.getReceiveAmount());
		roData.setBankReceiveTime(requestDTO.getReceiveDate());
		roData.setPayChannel(ReceiveOrderPayChannel.valueOfPayCentre(requestDTO.getPayChannel(), requestDTO.getPayMethod()).value());
		roData.setReceiveType(ReceiveType.AD_FEE.value());
		roData.setBizContent(requestDTO.getBizContent());
		roData.setBankID(requestDTO.getBankId());
		roData.setMemo(requestDTO.getMemo());
		int status = (customerId > 0 || reverseRoId > 0) ? ReceiveOrderStatus.CONFIRMED.value() : ReceiveOrderStatus.UNCONFIRMED.value();
		roData.setStatus(status);
		roData.setAddLoginId(0);
		roData.setUpdateLoginId(0);
		roData.setReverseRoId(reverseRoId);
		return roData;
	}


	/**
	 * 根据bizContent去调推广（广告）接口查询客户ID
	 *
	 * @param requestDTO
	 * @return
	 */

	private int getAdCustomerIdByBizContent(PayCentreReceiveRequestDTO requestDTO) {
		return bizInfoService.getBizInfo(requestDTO);
	}

	public void setReceiveOrderService(ReceiveOrderService receiveOrderService) {
		this.receiveOrderService = receiveOrderService;
	}

	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}

	public void setPayCentreReceiveRequestService(PayCentreReceiveRequestService payCentreReceiveRequestService) {
		this.payCentreReceiveRequestService = payCentreReceiveRequestService;
	}

	public void setBizInfoService(BizInfoService bizInfoService) {
		this.bizInfoService = bizInfoService;
	}
}
