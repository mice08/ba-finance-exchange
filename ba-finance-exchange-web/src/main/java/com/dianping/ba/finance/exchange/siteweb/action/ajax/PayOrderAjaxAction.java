package com.dianping.ba.finance.exchange.siteweb.action.ajax;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.auditlog.api.enums.OperationType;
import com.dianping.ba.finance.auditlog.client.OperationLogger;
import com.dianping.ba.finance.exchange.api.PayOrderDomainService;
import com.dianping.ba.finance.exchange.api.PayOrderService;
import com.dianping.ba.finance.exchange.api.beans.PayOrderSearchBean;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.ba.finance.exchange.api.dtos.AuthMsgDTO;
import com.dianping.ba.finance.exchange.api.enums.*;
import com.dianping.ba.finance.exchange.siteweb.beans.PayOrderBean;
import com.dianping.ba.finance.exchange.siteweb.beans.PayOrderExportBean;
import com.dianping.ba.finance.exchange.siteweb.beans.PayRecordInfoBean;
import com.dianping.ba.finance.exchange.siteweb.enums.SubmitType;
import com.dianping.ba.finance.exchange.siteweb.services.CustomerNameService;
import com.dianping.ba.finance.exchange.siteweb.services.PayTemplateService;
import com.dianping.ba.finance.exchange.siteweb.util.DateUtil;
import com.dianping.ba.finance.paymentplatform.api.PaymentQueryService;
import com.dianping.ba.finance.paymentplatform.api.dtos.PaymentRecordDTO;
import com.dianping.ba.finance.paymentplatform.api.enums.PaymentRecordStatus;
import com.dianping.core.type.PageModel;
import com.dianping.finance.common.util.ConvertUtils;
import com.dianping.finance.common.util.LionConfigUtils;
import com.dianping.finance.common.util.ListUtils;
import com.google.common.collect.Sets;
import jodd.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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

    private static final OperationLogger OPERATION_LOGGER = new OperationLogger("Exchange", "PayOrder", LionConfigUtils.getProperty("ba-finance-exchange-web.auditlog.token"));

    private static final Set<Integer> ALLOWED_EXPORT_STATUS = Sets.newHashSet(PayOrderStatus.INIT.value(), PayOrderStatus.EXPORT_PAYING.value());

    private Comparator<PaymentRecordDTO> REQUEST_TIME_COMPARATOR = new Comparator<PaymentRecordDTO>() {
        @Override
        public int compare(PaymentRecordDTO o1, PaymentRecordDTO o2) {
            return o2.getAddTime().compareTo(o1.getAddTime());
        }
    };

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

    private int poId;

    private String addDate;

    private PayOrderService payOrderService;

    private Map<String, PayTemplateService> payTemplateServiceMap;

    private CustomerNameService customerNameService;

    private String startAmount;

    private String endAmount;

    private int payType;

    private int bankId;

    private String requestToken;

    private String rejectMemo;

    private List<PayRecordInfoBean> payRecordInfoBeanList;

    @Autowired
    private PayOrderDomainService payOrderDomainService;

    @Autowired
    private PaymentQueryService paymentQueryService;

    @Override
    protected void jsonExecute() throws Exception {
        if (businessType == BusinessType.DEFAULT.value()) {
            msg.put("totalAmount", new DecimalFormat("##,###,###,###,##0.00").format(BigDecimal.ZERO));
            code = ERROR_CODE;
            return;
        }
        try {
            PayOrderSearchBean payOrderSearchBean = buildPayOrderSearchBean();
            OPERATION_LOGGER.log(OperationType.QUERY, "查询付款单", payOrderSearchBean.toString(), String.valueOf(getLoginId()));
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
            OPERATION_LOGGER.log(OperationType.UPDATE, "导出支付", searchBean.toString(), String.valueOf(getLoginId()));
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

    private List<Integer> getSubmitOrderIdList(int submitAction) throws ParseException {
        List<Integer> orderIdList = StringUtil.isBlank(poIds) ? new ArrayList<Integer>() : com.dianping.finance.common.util.StringUtils.splitStringToList(poIds, ",");

        if (CollectionUtils.isEmpty(orderIdList)) {
            PayOrderSearchBean searchBean = buildPayOrderSearchBean();
            if (submitAction == SubmitType.PAY_ORDER.getCode()) {
                OPERATION_LOGGER.log(OperationType.UPDATE, "直联支付(全选)", searchBean.toString(), String.valueOf(getLoginId()));
            } else {
                OPERATION_LOGGER.log(OperationType.UPDATE, "提交付款单(全选)", searchBean.toString(), String.valueOf(getLoginId()));
            }
            orderIdList = payOrderService.findPayOrderIdList(searchBean);
        } else {
            if (submitAction == SubmitType.PAY_ORDER.getCode()) {
                OPERATION_LOGGER.log(OperationType.UPDATE, "直联支付(勾选)", "付款单ID: " + orderIdList, String.valueOf(getLoginId()));
            } else {
                OPERATION_LOGGER.log(OperationType.UPDATE, "提交付款单(勾选)", "付款单ID: " + orderIdList, String.valueOf(getLoginId()));
            }
        }
        return orderIdList;
    }

    public String payOrderBankPay() throws Exception {
        try {
            List<Integer> idList = getSubmitOrderIdList(SubmitType.PAY_ORDER.getCode());
            if (CollectionUtils.isEmpty(idList)) {
                MONITOR_LOGGER.info(String.format("severity=[2] PayOrderAjaxAction.payOrderBankPay No PayOrder found! " +
                        "businessType=[%d]&addBeginTime=[%s]&addEndTime=[%s]&poIds[%s]&status=[%d]", businessType, addBeginTime, addEndTime, poIds, status));
                code = SUCCESS_CODE;
                return SUCCESS;
            }
            int result = payOrderDomainService.payWithAuth(idList, getLoginId(), new AuthMsgDTO(getWorkNo(), requestToken));
            if(result == BankPayResult.AUTH_FAIL.getCode()) {
                code = NO_AUTH_CODE;
            } else if(result == BankPayResult.SYSTEM_ERROR.getCode()) {
                code = ERROR_CODE;
            } else {
                code = SUCCESS_CODE;
            }
            msg.put("submitCount", idList.size());
            return SUCCESS;
        } catch (Exception e) {
            MONITOR_LOGGER.error("severity=[1], PayOrderAjaxAction.payOrderBankPay fail!", e);
            code = ERROR_CODE;
            return ERROR;
        }
    }

    public String payOrderBankPayRequest() throws Exception {
        try {
            List<Integer> idList = getSubmitOrderIdList(SubmitType.PAY_REQUEST.getCode());
            if (CollectionUtils.isEmpty(idList)) {
                MONITOR_LOGGER.info(String.format("severity=[2] PayOrderAjaxAction.payOrderBankPay No PayOrder found! " +
                        "businessType=[%d]&addBeginTime=[%s]&addEndTime=[%s]&poIds[%s]&status=[%d]", businessType, addBeginTime, addEndTime, poIds, status));
                code = SUCCESS_CODE;
                return SUCCESS;
            }
            int result = payOrderService.submitBankPayPOs(idList, getLoginId(), new AuthMsgDTO(getWorkNo(), requestToken));
            if(result == BankPaySubmitResult.AUTH_FAIL.getCode()) {
                code = NO_AUTH_CODE;
            } else if(result == BankPaySubmitResult.SYSTEM_ERROR.getCode()) {
                code = ERROR_CODE;
            } else {
                code = SUCCESS_CODE;
            }
            msg.put("submitNum", idList.size());
            return SUCCESS;
        } catch (Exception e) {
            MONITOR_LOGGER.error("severity=[1], PayOrderAjaxAction.payOrderBankPayRequest fail!", e);
            code = ERROR_CODE;
            return ERROR;
        }
    }

    public String payOrderRejectRequest() throws Exception {
        try{
            if(StringUtils.isBlank(poIds)){
                MONITOR_LOGGER.warn("No pay order need to be rejected!");
                code = SUCCESS_CODE;
                return SUCCESS;
            }
            String[] orderIdList = poIds.trim().split(",");

            for(String orderId: orderIdList){
                int poId = NumberUtils.toInt(orderId);
                payOrderService.updatePayOrderStatus(poId, PayOrderStatus.SUBMIT_FOR_PAY.value(), PayOrderStatus.INIT.value(), rejectMemo);
            }
            code = SUCCESS_CODE;
            return SUCCESS;
        } catch (Exception e){
            MONITOR_LOGGER.error("severity=[1], PayOrderAjaxAction.payOrderRejectRequest fail!", e);
            code = ERROR_CODE;
            return ERROR;
        }
    }

    public String payOrderBankAccountInvalid() throws Exception {
        try {
            if (StringUtils.isBlank(poIds)) {
                MONITOR_LOGGER.warn("No pay order need to be mark as bank acccount invalid!");
                code = SUCCESS_CODE;
                return SUCCESS;
            }
            String[] orderIdList = poIds.trim().split(",");
            List<Integer> poIdList = ListUtils.convertStringArrayToIntegerList(orderIdList);
            int successCount = payOrderService.markPayOrderInvalid(poIdList, getLoginId());
            msg.put("successCount", successCount);
            msg.put("failedCount", poIdList.size() - successCount);
            code = SUCCESS_CODE;
            return SUCCESS;
        } catch (Exception e) {
            MONITOR_LOGGER.error("severity=[1], PayOrderAjaxAction.payOrderBankAccountInvalid fail!", e);
            code = ERROR_CODE;
            return ERROR;
        }

    }

    public String payOrderQueryPaymentRecord() throws Exception {
        try {
            payRecordInfoBeanList = new ArrayList<PayRecordInfoBean>();
            if(poId < 0){
                MONITOR_LOGGER.warn("No pay order request!");
                code = SUCCESS_CODE;
                return SUCCESS;
            }
            PayOrderData payOrderData = payOrderService.loadPayOrderDataByPOID(poId);
            if(payOrderData == null || StringUtils.isBlank(payOrderData.getPayCode())){
                MONITOR_LOGGER.warn(String.format("Pay order not found! poId=[%s]", poId));
                code = SUCCESS_CODE;
                return SUCCESS;
            }
            String payCode = payOrderData.getPayCode();
            String[] payCodeArr = payCode.split("\\|");
            List<PaymentRecordDTO> paymentRecordDTOList = paymentQueryService.queryPaymentRecordByInsIds(Arrays.asList(payCodeArr));
            if(CollectionUtils.isEmpty(paymentRecordDTOList)){
                MONITOR_LOGGER.warn(String.format("No payment record found! poId=[%s]&payCode=[%s]", poId, payCode));
                code = SUCCESS_CODE;
                return SUCCESS;
            }
            Collections.sort(paymentRecordDTOList, REQUEST_TIME_COMPARATOR);
            for(int i= 0; i< paymentRecordDTOList.size(); i++){
                payRecordInfoBeanList.add(buildPayRecordInfoBean(paymentRecordDTOList.get(i), i+1));
            }
            code = SUCCESS_CODE;
            return SUCCESS;
        } catch (Exception e) {
            MONITOR_LOGGER.error("severity=[1], PayOrderAjaxAction.payOrderQueryPaymentRecord fail!", e);
            code = ERROR_CODE;
            return ERROR;
        }
    }

    private PayRecordInfoBean buildPayRecordInfoBean(PaymentRecordDTO paymentRecordDTO, int index){
        PayRecordInfoBean infoBean = new PayRecordInfoBean();
        infoBean.setIndex(index);
        infoBean.setAmount(new DecimalFormat("##,###,###,###,##0.00").format(paymentRecordDTO.getAmount()));
        infoBean.setMemo(paymentRecordDTO.getMemo() == null ? "" : paymentRecordDTO.getMemo());
        infoBean.setRequestTime(DateUtil.formatDateToString(paymentRecordDTO.getAddTime(), "yyyy-MM-dd HH:mm:ss"));
        infoBean.setPayCode(paymentRecordDTO.getInsId());
        infoBean.setStatus(PaymentRecordStatus.getByCode(paymentRecordDTO.getStatus()).getMessage());
        return infoBean;
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
        String exportBank = selectExportBank(beanList.get(0));
        MONITOR_LOGGER.info(String.format("exportBank=%s", exportBank));
        PayTemplateService payTemplateService = payTemplateServiceMap.get(exportBank);
        if (payTemplateService == null) {
            throw new RuntimeException("不支持该银行的支付模板!exportBank=" + exportBank);
        }
        payTemplateService.createExcelAndDownload(response, "付款单", beanList, bankId);
	}

    private String selectExportBank(PayOrderExportBean payOrderExportBean) {
        String exportBank;
        if(payOrderExportBean.getBusinessType() == BusinessType.EXPENSE.value()) {
            exportBank = LionConfigUtils.getProperty("ba-finance-exchange-web.expense.exportBank", "Merchants");
        } else {
            exportBank = LionConfigUtils.getProperty("ba-finance-exchange-web.exportBank", "Minsheng");
        }
        return exportBank;
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
        payOrderSearchBean.setPayType(payType);
        payOrderSearchBean.setBeginTime(beginTime);
        payOrderSearchBean.setEndTime(endTime);
        payOrderSearchBean.setStartAmount(parseAmount(startAmount));
        payOrderSearchBean.setEndAmount(parseAmount(endAmount));
        if (StringUtils.isNotBlank(poIds)) {
            payOrderSearchBean.setPoIdList(com.dianping.finance.common.util.StringUtils.splitStringToList(poIds, ","));
        }
        return payOrderSearchBean;
    }

    private BigDecimal parseAmount(String amountStr) {
        if (StringUtils.isBlank(amountStr)) {
            return null;
        }
        try {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setGroupingSeparator(',');
            symbols.setDecimalSeparator('.');
            String pattern = "#,###,###,##0.0#";
            DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
            decimalFormat.setParseBigDecimal(true);
            return (BigDecimal) decimalFormat.parse(amountStr);
        } catch (Exception e) {
            MONITOR_LOGGER.error("severity=[1] PayOrderAjaxAction parse amount error!", e);
        }
        return null;
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
        payOrderBean.setCustomerName(getCustomerNameById(payOrderData.getCustomerId(), customerIdNameMap));
        payOrderBean.setMemo(payOrderData.getMemo() == null ? "" : payOrderData.getMemo());
        payOrderBean.setUseMemo(payOrderData.getUseMemo() == null ? "" : payOrderData.getUseMemo());
        payOrderBean.setPayType(PayType.valueOf(payOrderData.getPayType()).toString());
        payOrderBean.setPayTypeValue(payOrderData.getPayType());
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

    public void setStartAmount(String startAmount) {
        this.startAmount = startAmount;
    }

    public void setEndAmount(String endAmount) {
        this.endAmount = endAmount;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public String getRequestToken() {
        return requestToken;
    }

    public void setRequestToken(String requestToken) {
        this.requestToken = requestToken;
    }

    public void setPayOrderDomainService(PayOrderDomainService payOrderDomainService) {
        this.payOrderDomainService = payOrderDomainService;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getRejectMemo() {
        return rejectMemo;
    }

    public void setRejectMemo(String rejectMemo) {
        this.rejectMemo = rejectMemo;
    }

    public int getPoId() {
        return poId;
    }

    public void setPoId(int poId) {
        this.poId = poId;
    }

    public List<PayRecordInfoBean> getPayRecordInfoBeanList() {
        return payRecordInfoBeanList;
    }

    public void setPayRecordInfoBeanList(List<PayRecordInfoBean> payRecordInfoBeanList) {
        this.payRecordInfoBeanList = payRecordInfoBeanList;
    }

}
