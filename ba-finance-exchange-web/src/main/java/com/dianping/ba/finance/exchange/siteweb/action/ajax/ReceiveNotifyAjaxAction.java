package com.dianping.ba.finance.exchange.siteweb.action.ajax;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.ReceiveOrderService;
import com.dianping.ba.finance.exchange.api.beans.ReceiveNotifySearchBean;
import com.dianping.ba.finance.exchange.api.beans.ReceiveOrderSearchBean;
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.ba.finance.exchange.siteweb.beans.ReceiveOrderBean;
import com.dianping.ba.finance.exchange.siteweb.services.CustomerNameService;
import com.dianping.ba.finance.exchange.siteweb.util.DateUtil;
import com.dianping.core.type.PageModel;
import com.google.common.collect.Maps;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 *
 */
public class ReceiveNotifyAjaxAction extends AjaxBaseAction {

	/**
	 * 记录需要监控的业务日志
	 */
	private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.web.monitor.ReceiveNotifyAjaxAction");

	private int customerId;
	private int businessType;
	private String receiveTimeBegin;
	private String receiveTimeEnd;
	private int payChannel;
	private int bankId;
	private int status;
	private String totalAmount;

	private int code;

	//查询结果，收款通知列表
	private PageModel receiveNotifyModel = new PageModel();
	//第几页
	private Integer page = 1;
	//分页大小
	private Integer pageSize = 20;

	private Map<String, Object> msg = Maps.newHashMap();

	private ReceiveOrderService receiveOrderService;

	private CustomerNameService customerNameService;

	@Override
	protected void jsonExecute() {
		if (businessType == BusinessType.DEFAULT.value()) {
			totalAmount = new DecimalFormat("##,###,###,###,##0.00").format(BigDecimal.ZERO);
			return;
		}

		try {
			code = SUCCESS_CODE;
		} catch (Exception e) {
			MONITOR_LOGGER.error("severity=[1] ReceiveOrderAjaxAction.jsonExecute error!", e);
			code = ERROR_CODE;
		}
	}

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public Map<String, Object> getMsg() {
		return msg;
	}

	private ReceiveNotifySearchBean buildRNSearchBean() throws ParseException {
		ReceiveNotifySearchBean receiveNotifySearchBean = new ReceiveNotifySearchBean();
		receiveNotifySearchBean.setStatus(status);
		receiveNotifySearchBean.setBusinessType(businessType);
		receiveNotifySearchBean.setPayTimeBegin(DateUtil.isValidDate(receiveTimeBegin) ? DateUtil.formatDate(receiveTimeBegin, false) : null);
		receiveNotifySearchBean.setPayTimeEnd(DateUtil.isValidDate(receiveTimeEnd) ? DateUtil.formatDate(receiveTimeEnd, true) : null);
		receiveNotifySearchBean.setCustomerId(customerId);
		receiveNotifySearchBean.setPayChannel(payChannel);
		//receiveNotifySearchBean.setBankId(bankId);
		return receiveNotifySearchBean;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getBusinessType() {
		return businessType;
	}

	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}

	public String getReceiveTimeBegin() {
		return receiveTimeBegin;
	}

	public void setReceiveTimeBegin(String receiveTimeBegin) {
		this.receiveTimeBegin = receiveTimeBegin;
	}

	public String getReceiveTimeEnd() {
		return receiveTimeEnd;
	}

	public void setReceiveTimeEnd(String receiveTimeEnd) {
		this.receiveTimeEnd = receiveTimeEnd;
	}

	public int getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(int payChannel) {
		this.payChannel = payChannel;
	}

	public int getBankId() {
		return bankId;
	}

	public void setBankId(int bankId) {
		this.bankId = bankId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public PageModel getReceiveNotifyModel() {
		return receiveNotifyModel;
	}

	public void setReceiveNotifyModel(PageModel receiveNotifyModel) {
		this.receiveNotifyModel = receiveNotifyModel;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public void setMsg(Map<String, Object> msg) {
		this.msg = msg;
	}

	public ReceiveOrderService getReceiveOrderService() {
		return receiveOrderService;
	}

	public void setReceiveOrderService(ReceiveOrderService receiveOrderService) {
		this.receiveOrderService = receiveOrderService;
	}

	public CustomerNameService getCustomerNameService() {
		return customerNameService;
	}

	public void setCustomerNameService(CustomerNameService customerNameService) {
		this.customerNameService = customerNameService;
	}
}
