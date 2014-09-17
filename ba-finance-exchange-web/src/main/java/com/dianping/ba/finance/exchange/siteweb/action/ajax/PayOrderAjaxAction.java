package com.dianping.ba.finance.exchange.siteweb.action.ajax;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.PayOrderService;
import com.dianping.ba.finance.exchange.api.beans.PayOrderSearchBean;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.ba.finance.exchange.api.enums.PayOrderStatus;
import com.dianping.ba.finance.exchange.siteweb.beans.PayOrderBean;
import com.dianping.ba.finance.exchange.siteweb.beans.PayOrderExportBean;
import com.dianping.ba.finance.exchange.siteweb.services.CustomerNameService;
import com.dianping.ba.finance.exchange.siteweb.services.PayTemplateService;
import com.dianping.ba.finance.exchange.siteweb.util.DateUtil;
import com.dianping.core.type.PageModel;
import com.dianping.finance.common.util.ConvertUtils;
import com.dianping.finance.common.util.LionConfigUtils;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;

/**
 * Created by Eric on 2014/6/11.
 */
public class PayOrderAjaxAction extends AjaxBaseAction {

    /**
     * 记录需要监控的业务日志
     */
    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.web.monitor.PayOrderAjaxAction");

    private static final Set<Integer> ALLOWED_EXPORT_STATUS = Sets.newHashSet(PayOrderStatus.INIT.value(), PayOrderStatus.EXPORT_PAYING.value());

    //查询结果，付款计划列表
    private PageModel payOrderModel = new PageModel();
    //第几页
    private Integer page = 1;
    //分页大小
    private Integer pageSize = 20;
    //状态
    private int status = 1;

    //业务类型
    private int businessType;

    // 产生日期的起始查询日期
    private String addBeginTime;
    // 产生日期的结束查询日期
    private String addEndTime;

    //付款计划总金额
    private String totalAmount = new DecimalFormat("##,###,###,###,##0.00").format(BigDecimal.ZERO);

    private String poIds;

    private String addDate;

    private PayOrderService payOrderService;

    private Map<String, PayTemplateService> payTemplateServiceMap;

    private CustomerNameService customerNameService;
    @Override
    protected void jsonExecute() throws Exception {
        if (businessType == BusinessType.DEFAULT.value()) {
            msg.put("totalAmount", new DecimalFormat("##,###,###,###,##0.00").format(BigDecimal.ZERO));
            code = ERROR_CODE;
            return;
        }
        try {
            PayOrderSearchBean payOrderSearchBean = buildPayOrderSearchBean();
            payOrderModel = payOrderService.paginatePayOrderList(payOrderSearchBean, page, pageSize);
            payOrderModel.setRecords(buildPayOrderBeans((List<PayOrderData>) payOrderModel.getRecords()));
            totalAmount = new DecimalFormat("##,###,###,###,##0.00").format(payOrderService.findPayOrderTotalAmount(payOrderSearchBean));
            msg.put("payOrderModel", payOrderModel);
            msg.put("totalAmount", totalAmount);
            code = SUCCESS_CODE;
        } catch (Exception e) {
            code = ERROR_CODE;
            MONITOR_LOGGER.error("severity=[1] PayOrderAjaxAction.jsonExecute error!", e);
        }
    }

	public String payOrderExportForPay() throws Exception {
		try {
			PayOrderSearchBean searchBean = buildPayOrderSearchBean();
			List<PayOrderData> dataList = payOrderService.findPayOrderList(searchBean);
            if (CollectionUtils.isEmpty(dataList)) {
                MONITOR_LOGGER.info(String.format("severity=[2] PayOrderAjaxAction.payOrderExportForPay No PayOrder found! searchBean=%s", searchBean));
                return null;
            }
			List<PayOrderExportBean> beanList = buildPayOrderExportBeanList(dataList);
            if (CollectionUtils.isEmpty(beanList)) {
                MONITOR_LOGGER.info(String.format("severity=[2] PayOrderAjaxAction.payOrderExportForPay No matched PayOrderBean found! searchBean=%s", searchBean));
                return null;
            }
			updatePayOrderStatus(beanList, getLoginId());
			exportPayOrders(beanList);
			return null;
		} catch (Exception e) {
			MONITOR_LOGGER.error("severity=[1] PayOrderAjaxAction.payOrderExportForPay", e);
			return ERROR;
		}
	}

	private void updatePayOrderStatus(List<PayOrderExportBean> beanList, int loginId){
		if(!CollectionUtils.isEmpty(beanList)){
			List<Integer> orderIds = new ArrayList<Integer>();
			for (PayOrderExportBean bean : beanList){
				orderIds.add(bean.getPoId());
			}
			payOrderService.updatePayOrderToPaying(orderIds,loginId);
		}
	}

	private void exportPayOrders(List<PayOrderExportBean> beanList) throws Exception {
		HttpServletResponse response = getHttpServletResponse();
        String exportBank = LionConfigUtils.getProperty("ba-finance-exchange-web.exportBank", "Minsheng");
        MONITOR_LOGGER.info(String.format("exportBank=%s", exportBank));
        PayTemplateService payTemplateService = payTemplateServiceMap.get(exportBank);
        if (payTemplateService == null) {
            throw new RuntimeException("不支持该银行的支付模板!exportBank=" + exportBank);
        }
        payTemplateService.createExcelAndDownload(response, "付款单", beanList);
	}

	protected HttpServletResponse getHttpServletResponse() {
		return ServletActionContext.getResponse();
	}

	private List<PayOrderExportBean> buildPayOrderExportBeanList(List<PayOrderData> payOrderDataList) throws Exception {
		if (CollectionUtils.isEmpty(payOrderDataList)) {
			return Collections.emptyList();
		}
		List orderExportBeanList = new ArrayList<PayOrderExportBean>();
		for (PayOrderData order : payOrderDataList){
			if (orderCanExport(order)){
				PayOrderExportBean exportBean = buildPayOrderExportBean(order);
				orderExportBeanList.add(exportBean);
			}
		}

		return orderExportBeanList;
	}

	private boolean orderCanExport(PayOrderData order) {
		return order.getPayAmount().compareTo(BigDecimal.ZERO) > 0 &&
                ALLOWED_EXPORT_STATUS.contains(order.getStatus());
	}

	private PayOrderExportBean buildPayOrderExportBean(PayOrderData order) throws Exception {
		PayOrderExportBean exportBean = ConvertUtils.copy(order, PayOrderExportBean.class);
        exportBean.setPayCode(String.valueOf(order.getPoId()));
		return exportBean;
	}

	public PayOrderSearchBean buildPayOrderSearchBean() throws ParseException {

        PayOrderSearchBean payOrderSearchBean=new PayOrderSearchBean();
        Date beginTime = DateUtil.isValidDateTime(addBeginTime) ? DateUtil.parseDate(addBeginTime, "yyyy-MM-dd HH:mm") :
                (DateUtil.isValidDate(addBeginTime) ? DateUtil.formatDate(addBeginTime, false) : null);
        Date endTime = DateUtil.isValidDateTime(addEndTime) ? DateUtil.parseDate(addEndTime, "yyyy-MM-dd HH:mm") :
                (DateUtil.isValidDate(addEndTime) ? DateUtil.formatDate(addEndTime, true) : null);
        payOrderSearchBean.setStatus(status);
        payOrderSearchBean.setBusinessType(businessType);
        payOrderSearchBean.setBeginTime(beginTime);
        payOrderSearchBean.setEndTime(endTime);
        if (StringUtils.isNotBlank(poIds)) {
            payOrderSearchBean.setPoIdList(com.dianping.finance.common.util.StringUtils.splitStringToList(poIds, ","));
        }
        return payOrderSearchBean;
    }

    private List<PayOrderBean> buildPayOrderBeans(List<PayOrderData> payOrderDataList) {
        if (CollectionUtils.isEmpty(payOrderDataList)) {
            return Collections.emptyList();
        }
        List<PayOrderBean> payOrderBeans = new ArrayList<PayOrderBean>();

        Map<Integer, String> customerIdNameMap = customerNameService.getCustomerName(payOrderDataList, getLoginId());

        for (PayOrderData payOrderData : payOrderDataList) {
            payOrderBeans.add(convertPODataToPOBean(payOrderData, customerIdNameMap));
        }
        return payOrderBeans;
    }

    private PayOrderBean convertPODataToPOBean(PayOrderData payOrderData, Map<Integer, String> customerIdNameMap) {
        PayOrderBean payOrderBean = new PayOrderBean();
        payOrderBean.setPayCode(String.valueOf(payOrderData.getPoId()));
        payOrderBean.setAddTime(DateUtil.formatDateToString(payOrderData.getAddTime(), "yyyy-MM-dd HH:mm:ss"));
        if (payOrderData.getStatus() == PayOrderStatus.REFUND.value()) {
            payOrderBean.setSendBackTime(DateUtil.formatDateToString(payOrderData.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));
        }
        payOrderBean.setBankAccountName(payOrderData.getBankAccountName());
        payOrderBean.setBankAccountNo(payOrderData.getBankAccountNo());
        payOrderBean.setBankFullBranchName(payOrderData.getBankFullBranchName());
        if(payOrderData.getBusinessType() == BusinessType.EXPENSE.value()) {
            payOrderBean.setCustomerName(payOrderData.getPayeeName());
        } else {
            payOrderBean.setCustomerName(getCustomerNameById(payOrderData.getCustomerId(), customerIdNameMap));
        }
        payOrderBean.setMemo(payOrderData.getMemo());
        payOrderBean.setPaidDate(DateUtil.formatDateToString(payOrderData.getPaidDate(), "yyyy-MM-dd HH:mm:ss"));
        payOrderBean.setPayAmount(new DecimalFormat("##,###,###,###,##0.00").format(payOrderData.getPayAmount()));
        payOrderBean.setPoId(payOrderData.getPoId());
        payOrderBean.setStatusDesc(PayOrderStatus.valueOf(payOrderData.getStatus()).toString());
        payOrderBean.setStatus(payOrderData.getStatus());
        payOrderBean.setQueryStatus(status);
        return payOrderBean;
    }

    private String getCustomerNameById(int customerId, Map<Integer, String> customerIdNameMap){
        String customerName = customerIdNameMap.get(customerId);
        if (StringUtils.isNotEmpty(customerName)) {
            return customerName;
        }
        return "";
    }


    @Override
    public int getCode() {
        return code;
    }

    @Override
    public Map<String, Object> getMsg() {
        return msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public PageModel getPayOrderModel() {
        return payOrderModel;
    }

    public void setPayOrderModel(PageModel payOrderModel) {
        this.payOrderModel = payOrderModel;
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

    public void setPayOrderService(PayOrderService payOrderService) {
        this.payOrderService = payOrderService;
    }

    public void setPayTemplateServiceMap(Map<String, PayTemplateService> payTemplateServiceMap) {
        this.payTemplateServiceMap = payTemplateServiceMap;
    }

    public void setCustomerNameService(CustomerNameService customerNameService) {
        this.customerNameService = customerNameService;
    }

    public String getAddBeginTime() {
        return addBeginTime;
    }

    public void setAddBeginTime(String addBeginTime) {
        this.addBeginTime = addBeginTime;
    }

    public String getAddEndTime() {
        return addEndTime;
    }

    public void setAddEndTime(String addEndTime) {
        this.addEndTime = addEndTime;
    }

    public void setPoIds(String poIds) {
        this.poIds = poIds;
    }
}
