package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.ExchangeOrderService;
import com.dianping.ba.finance.exchange.api.beans.GenericResult;
import com.dianping.ba.finance.exchange.api.datas.ExchangeOrderData;
import com.dianping.ba.finance.exchange.api.dtos.ExchangeOrderDTO;
import com.dianping.ba.finance.exchange.api.enums.ExchangeOrderStatusEnum;
import com.dianping.ba.finance.exchange.biz.dao.ExchangeOrderDao;
import com.dianping.ba.finance.exchange.biz.producer.ExchangeOrderStatusChangeNotify;
import com.dianping.ba.finance.exchange.biz.utils.BizUtils;
import com.dianping.core.type.PageModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 遐
 * Date: 13-12-13
 * Time: 上午10:28
 */

public class ExchangeOrderServiceObject implements ExchangeOrderService {

	private ExchangeOrderDao exchangeOrderDao;
	private ExchangeOrderStatusChangeNotify exchangeOrderStatusChangeNotify;

	private static final AvatarLogger monitorLogger = AvatarLoggerFactory.getLogger(ExchangeOrderServiceObject.class);

	@Override
	public int insertExchangeOrder(ExchangeOrderData exchangeOrderData) {
		//TODO: 增加唯一性校验
		return exchangeOrderDao.insertExchangeOrder(exchangeOrderData);
	}

	@Override
	public GenericResult<Integer> updateExchangeOrderToSuccess(List<Integer> orderIds) {
		Long startTime = Calendar.getInstance().getTimeInMillis();

		GenericResult genericResult = new GenericResult<Integer>();
		List successExchangeOrders = new ArrayList<Integer>();
		List failedExchangeOrders = new ArrayList<Integer>();
		int processExchangeOrderId = 0;
		try {
			genericResult.setSuccessList(successExchangeOrders);
			genericResult.setFailList(failedExchangeOrders);
			for (int orderId : orderIds) {
				processExchangeOrderId = orderId;
				boolean success = updateExchangeOrderToSuccess(orderId);
				if (success) {
					successExchangeOrders.add(orderId);
				} else {
					failedExchangeOrders.add(orderId);
				}
			}
		} catch (Exception e) {
			failedExchangeOrders.add(processExchangeOrderId);
		}
		if (failedExchangeOrders.size() > 0) {
			BizUtils.log(monitorLogger, startTime, "updateExchangeOrderToSuccess", "error", "Failed exchange order ids: " + failedExchangeOrders.toString(), null);
		}
		return genericResult;
	}

	@Override
	public PageModel paginateExchangeOrderList(int orderId, Date addDateBegin, Date addDateEnd, int page, int pageSize) {
		return exchangeOrderDao.paginateExchangeOrderList(orderId, addDateBegin, addDateEnd, page, pageSize);
	}

    private boolean isExchangeOrderValid(ExchangeOrderData exchangeOrderData){
        if(exchangeOrderData == null || exchangeOrderData.getStatus() != ExchangeOrderStatusEnum.PENDING.getExchangeOrderStatus()) {
            return false;
        }
        return true;
    }

	private boolean updateExchangeOrderToSuccess(int orderId) {
		if (orderId <= 0) {
			return false;
		}
		ExchangeOrderData exchangeOrderData = exchangeOrderDao.loadExchangeOrderByOrderId(orderId);
		if (isExchangeOrderValid(exchangeOrderData)) {
			Date orderDate = getCurrentTime();
			int affectedRows = exchangeOrderDao.updateExchangeOrderData(orderId, orderDate, ExchangeOrderStatusEnum.SUCCESS.getExchangeOrderStatus());
			if (affectedRows > 0) {
				ExchangeOrderDTO exchangeOrderDTO = new ExchangeOrderDTO();
				exchangeOrderDTO.setExchangeOrderId(orderId);
				exchangeOrderDTO.setStatus(ExchangeOrderStatusEnum.SUCCESS.ordinal());
				exchangeOrderDTO.setOrderType(exchangeOrderData.getOrderType());
				exchangeOrderStatusChangeNotify.exchangeOrderStatusChangeNotify(exchangeOrderDTO);
			} else {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	private Date getCurrentTime() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}

	public void setExchangeOrderDao(ExchangeOrderDao exchangeOrderDao) {
		this.exchangeOrderDao = exchangeOrderDao;
	}

	public void setExchangeOrderStatusChangeNotify(ExchangeOrderStatusChangeNotify exchangeOrderStatusChangeNotify) {
		this.exchangeOrderStatusChangeNotify = exchangeOrderStatusChangeNotify;
	}
}
