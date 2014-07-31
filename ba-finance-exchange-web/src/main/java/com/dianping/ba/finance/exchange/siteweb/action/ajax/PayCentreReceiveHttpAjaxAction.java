package com.dianping.ba.finance.exchange.siteweb.action.ajax;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.PayCentreReceiveRequestHandleService;
import com.dianping.ba.finance.exchange.api.dtos.PayCentreReceiveRequestDTO;
import com.dianping.ba.finance.exchange.siteweb.util.DateUtil;
import com.dianping.swallow.consumer.BackoutMessageException;
import com.google.common.collect.Maps;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: yaxiong.cheng
 * Date: 2014/7/31
 * Time: 9:59
 * To change this template use File | Settings | File Templates.
 * 支付中心请求http接口
 */
public class PayCentreReceiveHttpAjaxAction extends AjaxBaseAction{
	/**
	 * 记录需要监控的业务日志
	 */
	private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger
			("com.dianping.ba.finance.exchange.web.monitor.PayCentreAjaxAction");

	private int code;
	private Map<String, Object> msg = Maps.newHashMap();

	//PayCentreReceiveRequestDTO的字段
	/**
	 * 支付中心唯一交易流水号
	 */
	private String tradeNo;
	/**
	 * 交易类型：1收款；2冲销
	 */
	private int tradeType;
	/**
	 * 我司收款银行   汉涛上海：1    汉海上海：8   汉海北京：7   汉海广州：11
	 */
	private int bankId;
	private String receiveAmount;
	/**
	 * 收款日期
	 */
	private String receiveDate;
	/**
	 * 1, "团购" 2, "预订" 6, "广告" 7, "结婚"  8,"储值卡"
	 */
	private int businessType;
	/**
	 * 对方付款渠道：10是快钱渠道
	 */
	private int payChannel;
	/**
	 * 对方付款方式：5是POS机
	 */
	private int payMethod;
	/**
	 * 业务文本（广告：合同号）
	 */
	private String bizContent;

	/**
	 * 原始交易号 冲销必须有
	 */
	private String oriTradeNo;
	private String memo;

	private PayCentreReceiveRequestHandleService payCentreReceiveRequestHandleService;

	@Override
	protected void jsonExecute() throws Exception {
		try {
			PayCentreReceiveRequestDTO payCentreReceiveRequestDTO = buildPayCentreReceiveRequestDTO();
			payCentreReceiveRequestHandleService.handleReceiveRequest(payCentreReceiveRequestDTO);
			code = SUCCESS_CODE;
			msg.put("message","处理成功");
		} catch (Exception e) {
			MONITOR_LOGGER.error("severity=[1] PayCentreReceiveHttpAjaxAction.jsonExecute error!", e);
			code = ERROR_CODE;
			msg.put("message","处理失败");
		}
	}

	private PayCentreReceiveRequestDTO buildPayCentreReceiveRequestDTO() throws ParseException {
        PayCentreReceiveRequestDTO dtoBean = new PayCentreReceiveRequestDTO();
		dtoBean.setMemo(memo);
		if (receiveAmount == null || receiveAmount.equals("")) {
			dtoBean.setReceiveAmount(BigDecimal.ZERO);
		} else {
			BigDecimal receiveAmountBig = new BigDecimal(receiveAmount);
			dtoBean.setReceiveAmount(receiveAmountBig);
		}
		dtoBean.setBizContent(bizContent);
		dtoBean.setBankId(bankId);
		dtoBean.setBusinessType(businessType);
		dtoBean.setOriTradeNo(oriTradeNo);
		dtoBean.setTradeNo(tradeNo);
		dtoBean.setTradeType(tradeType);
		dtoBean.setPayChannel(payChannel);
		dtoBean.setPayMethod(payMethod);
		dtoBean.setReceiveDate(DateUtil.isValidDate(receiveDate) ? DateUtil.formatDate(receiveDate, true) : null);
		return dtoBean;
	}

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public Map<String, Object> getMsg() {
		return msg;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public int getTradeType() {
		return tradeType;
	}

	public void setTradeType(int tradeType) {
		this.tradeType = tradeType;
	}

	public int getBankId() {
		return bankId;
	}

	public void setBankId(int bankId) {
		this.bankId = bankId;
	}

	public String getReceiveAmount() {
		return receiveAmount;
	}

	public void setReceiveAmount(String receiveAmount) {
		this.receiveAmount = receiveAmount;
	}

	public String getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}

	public int getBusinessType() {
		return businessType;
	}

	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}

	public int getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(int payChannel) {
		this.payChannel = payChannel;
	}

	public int getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(int payMethod) {
		this.payMethod = payMethod;
	}

	public String getBizContent() {
		return bizContent;
	}

	public void setBizContent(String bizContent) {
		this.bizContent = bizContent;
	}

	public String getOriTradeNo() {
		return oriTradeNo;
	}

	public void setOriTradeNo(String oriTradeNo) {
		this.oriTradeNo = oriTradeNo;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setPayCentreReceiveRequestHandleService(PayCentreReceiveRequestHandleService payCentreReceiveRequestHandleService) {
		this.payCentreReceiveRequestHandleService = payCentreReceiveRequestHandleService;
	}
}
