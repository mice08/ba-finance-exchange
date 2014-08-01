package com.dianping.ba.finance.exchange.siteweb.action.ajax;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.ReceiveBankService;
import com.dianping.ba.finance.exchange.api.ReceiveNotifyService;
import com.dianping.ba.finance.exchange.api.beans.ReceiveNotifySearchBean;
import com.dianping.ba.finance.exchange.api.datas.ReceiveBankData;
import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData;
import com.dianping.ba.finance.exchange.api.enums.*;
import com.dianping.ba.finance.exchange.siteweb.beans.*;
import com.dianping.ba.finance.exchange.siteweb.services.CustomerNameService;
import com.dianping.ba.finance.exchange.siteweb.util.DateUtil;
import com.dianping.core.type.PageModel;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class ReceiveNotifyAjaxAction extends AjaxBaseAction {

	/**
	 * 记录需要监控的业务日志
	 */
	private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.web.monitor.ReceiveNotifyAjaxAction");

	private String applicationId;

	private int roMatcherId;

	private List<ReceiveNotifyConfirmBean> records;

	private int businessType;

	private int code;

	private int rnId;

	private Map<String, Object> msg = Maps.newHashMap();

	private ReceiveNotifyService receiveNotifyService;

	private CustomerNameService customerNameService;
	private int customerId;
	private String receiveTimeBegin;
	private String receiveTimeEnd;
	private String receiveAmount;
	private int payChannel;
	private int bankId;
	private int status;
	private String totalAmount;


	//查询结果，收款通知列表
	private PageModel receiveNotifyModel = new PageModel();
	//第几页
	private Integer page = 1;
	//分页大小
	private Integer pageSize = 20;


	private ReceiveBankService receiveBankService;


	@Override
	protected void jsonExecute() {
		if (businessType == BusinessType.DEFAULT.value()) {
			totalAmount = new DecimalFormat("##,###,###,###,##0.00").format(BigDecimal.ZERO);
			return;
		}
		try {
			ReceiveNotifySearchBean searchBean = buildRNSearchBean();
			receiveNotifyModel = receiveNotifyService.paginateReceiveNotifyList(searchBean, page, pageSize);
			receiveNotifyModel.setRecords(buildReceiveNotifyBeans((List<ReceiveNotifyData>) receiveNotifyModel.getRecords()));
			totalAmount = new DecimalFormat("##,###,###,###,##0.00").format(receiveNotifyService.loadTotalReceiveAmountByCondition(searchBean));
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

	private List<ReceiveNotifyBean> buildReceiveNotifyBeans(List<ReceiveNotifyData> records) {
		if (CollectionUtils.isEmpty(records)) {
			return Collections.emptyList();
		}
		//根据customerId获取customerName
		Map<Integer, String> customerIdNameMap = customerNameService.getRORNCustomerName(records, getLoginId());

		List<ReceiveNotifyBean> receiveNotifyBeans = new ArrayList<ReceiveNotifyBean>();
		for (ReceiveNotifyData receiveNotifyData : records) {
			receiveNotifyBeans.add(convertRNDataToRNBean(receiveNotifyData, customerIdNameMap));
		}
		return receiveNotifyBeans;
	}

	private ReceiveNotifyBean convertRNDataToRNBean(ReceiveNotifyData receiveNotifyData, Map<Integer, String> customerIdNameMap) {
		ReceiveNotifyBean bean = new ReceiveNotifyBean();
		bean.setReceiveNotifyId(receiveNotifyData.getApplicationId());
		if (receiveNotifyData.getAttachment().trim().equals("")) {
			bean.setAttachment("nourl");
		} else {
			bean.setAttachment(receiveNotifyData.getAttachment());
		}
		bean.setRoMatchId(receiveNotifyData.getRoMatcherId());
		bean.setBizContent(receiveNotifyData.getBizContent());
		bean.setMemo(receiveNotifyData.getMemo());
		bean.setPayerName(receiveNotifyData.getPayerName());
		bean.setReceiveAmount(receiveNotifyData.getReceiveAmount());
		bean.setPayTime(DateUtil.formatDateToString(receiveNotifyData.getPayTime(), "yyyy-MM-dd"));
		bean.setPayerName(receiveNotifyData.getPayerName());
		bean.setBusinessType(BusinessType.valueOf(receiveNotifyData.getBusinessType()).toString());
		bean.setPayChannel(ReceiveOrderPayChannel.valueOf(receiveNotifyData.getPayChannel()).toString());
		bean.setCustomerId(customerIdNameMap.get(receiveNotifyData.getCustomerId()));
		bean.setReceiveType(ReceiveType.valueOf(receiveNotifyData.getReceiveType()).toString());
		bean.setStatus(ReceiveNotifyStatus.valueOf(receiveNotifyData.getStatus()).toString());
		ReceiveBankData bankData = receiveBankService.loadReceiveBankByBankId(receiveNotifyData.getBankId());
		if (bankData != null) {
			bean.setBankId(CompanyIDName.valueOf(bankData.getCompanyId()).toString());
		}
		return bean;
	}


	private ReceiveNotifySearchBean buildRNSearchBean() throws ParseException {
		ReceiveNotifySearchBean receiveNotifySearchBean = new ReceiveNotifySearchBean();
		if (receiveAmount == null || receiveAmount.equals("")) {
			receiveNotifySearchBean.setReceiveAmount(BigDecimal.ZERO);
		} else {
			BigDecimal receiveAmountBig = new BigDecimal(receiveAmount);
			receiveNotifySearchBean.setReceiveAmount(receiveAmountBig);
		}
		receiveNotifySearchBean.setStatus(status);
		receiveNotifySearchBean.setBusinessType(businessType);
		receiveNotifySearchBean.setPayTimeBegin(DateUtil.isValidDate(receiveTimeBegin) ? DateUtil.formatDate(receiveTimeBegin, false) : null);
		receiveNotifySearchBean.setPayTimeEnd(DateUtil.isValidDate(receiveTimeEnd) ? DateUtil.formatDate(receiveTimeEnd, true) : null);
		receiveNotifySearchBean.setCustomerId(customerId);
		receiveNotifySearchBean.setPayChannel(payChannel);
		receiveNotifySearchBean.setBankId(bankId);
		return receiveNotifySearchBean;
	}


	public String rornCancelLink() {
		try {
			receiveNotifyService.removeReceiveNotifyMatchRelation(rnId, roMatcherId);
			code = SUCCESS_CODE;
		} catch (Exception e) {
			MONITOR_LOGGER.error("severity=[1] ReceiveOrderAjaxAction.rornCancelLink error!", e);
			code = ERROR_CODE;
		}
		return SUCCESS;
	}

	public String findNotifiesByROId() {
		try {
			List<ReceiveNotifyData> list = receiveNotifyService.findMatchedReceiveNotify(roMatcherId);

			records = buildReceiveNotifyConfirmList(list);
			code = SUCCESS_CODE;
		} catch (Exception e) {
			MONITOR_LOGGER.error("severity=[1] ReceiveOrderAjaxAction.findNotifiesByROId error!", e);
			code = ERROR_CODE;
		}
		return SUCCESS;
	}

	public String loadReceiveOrderInfo() {
		if (StringUtils.isBlank(applicationId)) {
			code = SUCCESS_CODE;
			msg.put(msgKey, "请输入收款通知ID！");

			return SUCCESS;
		}
		if (businessType == 0) {
			code = SUCCESS_CODE;
			msg.put(msgKey, "无效的产品线！");
			return SUCCESS;
		}

		try {
			ReceiveNotifyData rnData = receiveNotifyService.loadUnmatchedReceiveNotifyByApplicationId(ReceiveNotifyStatus.INIT, businessType, applicationId);
			if (rnData != null) {
				ReceiveInfoBean receiveInfoBean = buildReceiveInfoBean(rnData);
				msg.put("receiveInfo", receiveInfoBean);
			}
			code = SUCCESS_CODE;
		} catch (Exception e) {
			MONITOR_LOGGER.error("severity=[1] ReceiveOrderAjaxAction.jsonExecute error!", e);
			code = ERROR_CODE;
		}
		return SUCCESS;
	}

	private List<ReceiveNotifyConfirmBean> buildReceiveNotifyConfirmList(List<ReceiveNotifyData> list) {
		Map<Integer, String> customerIdNameMap = customerNameService.getRORNCustomerName(list, getLoginId());
		List<ReceiveNotifyConfirmBean> beans = new ArrayList<ReceiveNotifyConfirmBean>();
		for (ReceiveNotifyData data : list) {
			ReceiveNotifyConfirmBean bean = new ReceiveNotifyConfirmBean();
			bean.setReceiveAmount(data.getReceiveAmount());
			bean.setBizContent(data.getBizContent());
			bean.setApplicationId(data.getApplicationId());
			bean.setAttachment(data.getAttachment());
			bean.setBankId(data.getBankId());
			bean.setBusinessType(BusinessType.valueOf(data.getBusinessType()).toString());
			bean.setCustomerId(data.getCustomerId());
			bean.setCustomerName(customerIdNameMap.get(data.getCustomerId()));
			bean.setMemo(data.getMemo());
			bean.setPayChannel(ReceiveOrderPayChannel.valueOf(data.getPayChannel()).toString());
			bean.setPayerName(data.getPayerName());
			bean.setPayTime(DateUtil.formatDateToString(data.getPayTime(), "yyyy-MM-dd"));
			bean.setReceiveNotifyId(data.getReceiveNotifyId());
			bean.setRoMatcherId(data.getRoMatcherId());
			bean.setStatus(ReceiveNotifyStatus.valueOf(data.getStatus()).toString());
			beans.add(bean);
		}
		return beans;
	}

	private ReceiveInfoBean buildReceiveInfoBean(ReceiveNotifyData rnData) {
		ReceiveInfoBean receiveInfoBean = new ReceiveInfoBean();
		receiveInfoBean.setBankId(rnData.getBankId());
		receiveInfoBean.setBizContent(rnData.getBizContent());
		receiveInfoBean.setBusinessType(rnData.getBusinessType());
		receiveInfoBean.setCustomerId(rnData.getCustomerId());
		CustomerInfoBean customerInfoBean = customerNameService.getCustomerInfoById(rnData.getBusinessType(), rnData.getCustomerId(), getLoginId());
		if (customerInfoBean != null) {
			receiveInfoBean.setCustomerId(customerInfoBean.getCustomerId());
			receiveInfoBean.setCustomerName(customerInfoBean.getCustomerName());
		}
		receiveInfoBean.setPayChannel(rnData.getPayChannel());
		receiveInfoBean.setPayTime(rnData.getPayTime());
		receiveInfoBean.setReceiveAmount(rnData.getReceiveAmount());
		return receiveInfoBean;
	}


	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}


	public void setReceiveNotifyService(ReceiveNotifyService receiveNotifyService) {
		this.receiveNotifyService = receiveNotifyService;
	}

	public int getRoMatcherId() {
		return roMatcherId;
	}

	public void setRoMatcherId(int roMatcherId) {
		this.roMatcherId = roMatcherId;
	}

	public List<ReceiveNotifyConfirmBean> getRecords() {
		return records;
	}

	public void setRecords(List<ReceiveNotifyConfirmBean> records) {
		this.records = records;
	}

	public int getRnId() {
		return rnId;
	}

	public void setRnId(int rnId) {
		this.rnId = rnId;
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


	public void setReceiveAmount(String receiveAmount) {
		this.receiveAmount = receiveAmount;
	}

	public String getReceiveAmount() {
		return receiveAmount;
	}

	public void setReceiveBankService(ReceiveBankService receiveBankService) {
		this.receiveBankService = receiveBankService;
	}

	public void setCustomerNameService(CustomerNameService customerNameService) {
		this.customerNameService = customerNameService;
	}

}
