package com.dianping.ba.finance.exchange.siteweb.action.ajax;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.ReceiveOrderService;
import com.dianping.ba.finance.exchange.api.beans.ReceiveOrderSearchBean;
import com.dianping.ba.finance.exchange.api.beans.ReceiveOrderUpdateBean;
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.ba.finance.exchange.api.enums.ReceiveOrderPayChannel;
import com.dianping.ba.finance.exchange.api.enums.ReceiveOrderStatus;
import com.dianping.ba.finance.exchange.api.enums.ReceiveType;
import com.dianping.ba.finance.exchange.siteweb.beans.ReceiveOrderBean;
import com.dianping.ba.finance.exchange.siteweb.services.CustomerNameService;
import com.dianping.ba.finance.exchange.siteweb.util.DateUtil;
import com.dianping.core.type.PageModel;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;

/**
 *
 */
public class ReceiveOrderAjaxAction extends AjaxBaseAction {

    /**
     * 记录需要监控的业务日志
     */
    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.web.monitor.ReceiveOrderAjaxAction");

    private int customerId;

    private int businessType;

    private BigDecimal receiveAmount = BigDecimal.ZERO;

    private String bankReceiveTime;

    private int payChannel;

    private int receiveType;

    private String bizContent;

    private String receiveTime;

    private int bankId;

    private String memo;

    private int code;

    private String bankReceiveTimeBegin;

    private String bankReceiveTimeEnd;

    private String receiveTimeBegin;

    private String receiveTimeEnd;

    private int status;

    private String totalAmount;

    private ReceiveOrderBean receiveOrder;

    private ReceiveOrderData receiveOrderData;

    private int roId;


    //查询结果，付款计划列表
    private PageModel receiveOrderModel = new PageModel();
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
            ReceiveOrderSearchBean receiveOrderSearchBean = buildROSearchBean();
            receiveOrderModel = receiveOrderService.paginateReceiveOrderList(receiveOrderSearchBean, page, pageSize);
            receiveOrderModel.setRecords(buildReceiveOrderBeans((List<ReceiveOrderData>) receiveOrderModel.getRecords()));
            totalAmount=new DecimalFormat("##,###,###,###,##0.00").format(receiveOrderService.loadReceiveOrderTotalAmountByCondition(receiveOrderSearchBean));
            code=SUCCESS_CODE;
        } catch (Exception e) {
            MONITOR_LOGGER.error("severity=[1] ReceiveOrderAjaxAction.jsonExecute error!", e);
            code=ERROR_CODE;
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

    /**
     * 手工录入付款单
     *
     * @return
     * @throws Exception
     */
    public String createReceiveOrderManually() throws Exception {
        try {
            int loginId = getLoginId();
            StringBuilder sb = new StringBuilder();
            if (!isInputValid(sb)) {
                msg.put(msgKey, sb.toString());
                code = ERROR_CODE;
                return SUCCESS;
            }

            ReceiveOrderData receiveOrderData = buildReceiveOrderData(loginId);
            receiveOrderService.createReceiveOrder(receiveOrderData);
            code = SUCCESS_CODE;
            return SUCCESS;
        } catch (Exception e) {
            MONITOR_LOGGER.error("severity=[1] ReceiveOrderAjaxAction.createReceiveOrderManually error!", e);
            code = ERROR_CODE;
            return SUCCESS;
        }
    }

    public String loadReceiveOrderById(){
        try {
            receiveOrderData = receiveOrderService.loadReceiveOrderDataByRoId(roId);
            Map<Integer, String> customerIdNameMap = customerNameService.getROCustomerName(Arrays.asList(receiveOrderData), getLoginId());
            receiveOrder = convertRODataToROBean(receiveOrderData, customerIdNameMap);
        } catch (Exception e) {
            MONITOR_LOGGER.error("severity=[1] ReceiveOrderAjaxAction.getReveiveOrderById error!", e);
            code = ERROR_CODE;
            return SUCCESS;
        }
        code = SUCCESS_CODE;
        return SUCCESS;
    }

    public String updateReceiveOrder(){
        int result = -1;
        try {
            ReceiveOrderUpdateBean receiveOrderUpdateBean = buildUpdateReceiveOrder();
            result = receiveOrderService.updateReceiveOrderConfirm(receiveOrderUpdateBean);
        } catch (Exception e) {
            MONITOR_LOGGER.error("severity=[1] ReceiveOrderAjaxAction.getReveiveOrderById error!", e);
            code = ERROR_CODE;
            return SUCCESS;
        }
        if (result == -1){
            code = ERROR_CODE;
        } else {
            code = SUCCESS_CODE;
        }
        return SUCCESS;
    }

    private ReceiveOrderUpdateBean buildUpdateReceiveOrder() throws ParseException {
        ReceiveOrderUpdateBean receiveOrderUpdateBean=new ReceiveOrderUpdateBean();
        receiveOrderUpdateBean.setRoId(roId);
        Date receiveTimeDate = DateUtil.isValidDate(receiveTime) ? DateUtil.formatDate(receiveTime, false) : null;
        receiveOrderUpdateBean.setReceiveTime(receiveTimeDate);
        receiveOrderUpdateBean.setBizContent(bizContent);
        receiveOrderUpdateBean.setMemo(memo);
        receiveOrderUpdateBean.setCustomerId(customerId);
        receiveOrderUpdateBean.setStatus(status);
        receiveOrderUpdateBean.setReceiveType(ReceiveType.valueOf(receiveType));
        receiveOrderUpdateBean.setUpdateLoginId(getLoginId());
        return receiveOrderUpdateBean;
    }

    private List<ReceiveOrderBean> buildReceiveOrderBeans(List<ReceiveOrderData> receiveOrderDataList) {
        if (CollectionUtils.isEmpty(receiveOrderDataList)) {
            return Collections.emptyList();
        }

        Map<Integer, String> customerIdNameMap = customerNameService.getROCustomerName(receiveOrderDataList, getLoginId());

        List<ReceiveOrderBean> receiveOrderBeans = new ArrayList<ReceiveOrderBean>();
        for (ReceiveOrderData receiveOrderData : receiveOrderDataList) {
            receiveOrderBeans.add(convertRODataToROBean(receiveOrderData, customerIdNameMap));
        }
        return receiveOrderBeans;
    }

    private ReceiveOrderBean convertRODataToROBean(ReceiveOrderData receiveOrderData, Map<Integer, String> customerIdNameMap) {
        ReceiveOrderBean receiveOrderBean = new ReceiveOrderBean();
        if (receiveOrderData.getBankReceiveTime() != null) {
            receiveOrderBean.setBankReceiveTime(DateUtil.formatDateToString(receiveOrderData.getBankReceiveTime(), "yyyy-MM-dd"));
        }
        receiveOrderBean.setBizContent(receiveOrderData.getBizContent());
        receiveOrderBean.setBusinessType(BusinessType.valueOf(receiveOrderData.getBusinessType()).toString());
        receiveOrderBean.setCustomerName(getCustomerNameById(receiveOrderData.getCustomerId(), customerIdNameMap));
        receiveOrderBean.setCustomerId(receiveOrderData.getCustomerId());
        receiveOrderBean.setMemo(receiveOrderData.getMemo());
        receiveOrderBean.setPayChannel(ReceiveOrderPayChannel.valueOf(receiveOrderData.getPayChannel()).toString());
        receiveOrderBean.setBankID(receiveOrderData.getBankID());
        receiveOrderBean.setTradeNo(receiveOrderData.getTradeNo());
        receiveOrderBean.setPayerAccountName(receiveOrderData.getPayerAccountName());
        receiveOrderBean.setPayerAccountNo(receiveOrderData.getPayerAccountNo());
        receiveOrderBean.setPayerBankName(receiveOrderData.getPayerBankName());
        receiveOrderBean.setPayTime(DateUtil.formatDateToString(receiveOrderData.getPayTime(), "yyyy-MM-dd"));
        receiveOrderBean.setReverseRoId(receiveOrderData.getReverseRoId());
        receiveOrderBean.setShopId(receiveOrderData.getShopId());
        receiveOrderBean.setPayerName(receiveOrderData.getPayerAccountName());
        receiveOrderBean.setReceiveAmount(new DecimalFormat("##,###,###,###,##0.00").format(receiveOrderData.getReceiveAmount()));
        receiveOrderBean.setReceiveTime(DateUtil.formatDateToString(receiveOrderData.getReceiveTime(), "yyyy-MM-dd"));
        ReceiveType rt = ReceiveType.valueOf(receiveOrderData.getReceiveType());
        if (rt != ReceiveType.DEFAULT) {
            receiveOrderBean.setReceiveType(rt.toString());
        } else {
            // 未匹配的，暂时显示为空
            receiveOrderBean.setReceiveType("");
        }
        receiveOrderBean.setRoId(receiveOrderData.getRoId());
        receiveOrderBean.setStatus(ReceiveOrderStatus.valueOf(receiveOrderData.getStatus()).toString());
        return receiveOrderBean;
    }

    private String getCustomerNameById(int id, Map<Integer, String> customerIdNameMap){
        String customerName = customerIdNameMap.get(id);
        if (StringUtils.isNotEmpty(customerName)) {
            return customerName;
        }
        return "";
    }

    private ReceiveOrderSearchBean buildROSearchBean() throws ParseException {
        ReceiveOrderSearchBean receiveOrderSearchBean = new ReceiveOrderSearchBean();
        receiveOrderSearchBean.setStatus(status);
        receiveOrderSearchBean.setBusinessType(businessType);
        receiveOrderSearchBean.setBankReceiveTimeBegin(DateUtil.isValidDate(bankReceiveTimeBegin) ? DateUtil.formatDate(bankReceiveTimeBegin, false) : null);
        receiveOrderSearchBean.setBankReceiveTimeEnd(DateUtil.isValidDate(bankReceiveTimeEnd) ? DateUtil.formatDate(bankReceiveTimeEnd, true) : null);
        receiveOrderSearchBean.setCustomerId(customerId);
        receiveOrderSearchBean.setPayChannel(payChannel);
        receiveOrderSearchBean.setReceiveTimeBegin(DateUtil.isValidDate(receiveTimeBegin) ? DateUtil.formatDate(receiveTimeBegin, false) : null);
        receiveOrderSearchBean.setReceiveTimeEnd(DateUtil.isValidDate(receiveTimeEnd) ? DateUtil.formatDate(receiveTimeEnd, true) : null);
        receiveOrderSearchBean.setReceiveType(receiveType);
        receiveOrderSearchBean.setBankId(bankId);
        return receiveOrderSearchBean;
    }

    private boolean isInputValid(StringBuilder sb) {
        if (customerId == 0) {
            sb.append("请填写客户名");
            return false;
        }
        if (businessType == BusinessType.DEFAULT.value()) {
            sb.append("请选择产品线");
            return false;
        }
        if (receiveAmount == null || BigDecimal.ZERO.compareTo(receiveAmount) >= 0) {
            sb.append("请正确填写收款金额");
            return false;
        }
        if (bankReceiveTime != null && !DateUtil.isValidDate(bankReceiveTime)) {
            sb.append("请填写正确的收款日期");
            return false;
        }
        if (payChannel == ReceiveOrderPayChannel.DEFAULT.value()) {
            sb.append("请填写收款方式");
            return false;
        }
        if (receiveType == ReceiveType.DEFAULT.value()) {
            sb.append("请填写业务类型");
            return false;
        }
        if (StringUtils.isEmpty(bizContent)) {
            sb.append("请填写业务ID");
            return false;
        }
        return true;
    }


    public ReceiveOrderData buildReceiveOrderData(int loginId) throws ParseException {
        ReceiveOrderData roData = new ReceiveOrderData();
        roData.setCustomerId(customerId);
        roData.setBusinessType(businessType);
        roData.setReceiveAmount(receiveAmount);
        Date bankReceiveTimeDate = DateUtil.isValidDate(bankReceiveTime) ? DateUtil.formatDate(bankReceiveTime, false) : null;
        roData.setBankReceiveTime(bankReceiveTimeDate);
        roData.setPayChannel(payChannel);
        roData.setReceiveType(receiveType);
        roData.setBizContent(bizContent);
        roData.setBankID(bankId);
        roData.setMemo(memo);
        roData.setStatus(ReceiveOrderStatus.CONFIRMED.value());
        roData.setAddLoginId(loginId);
        roData.setUpdateLoginId(loginId);
		roData.setReverseRoId(0);
        return roData;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public void setReceiveAmount(BigDecimal receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public void setBankReceiveTime(String bankReceiveTime) {
        this.bankReceiveTime = bankReceiveTime;
    }

    public void setPayChannel(int payChannel) {
        this.payChannel = payChannel;
    }

    public void setReceiveType(int receiveType) {
        this.receiveType = receiveType;
    }

    public void setBizContent(String bizContent) {
        this.bizContent = bizContent;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setReceiveOrderService(ReceiveOrderService receiveOrderService) {
        this.receiveOrderService = receiveOrderService;
    }

    public PageModel getReceiveOrderModel() {
        return receiveOrderModel;
    }

    public void setReceiveOrderModel(PageModel receiveOrderModel) {
        this.receiveOrderModel = receiveOrderModel;
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

    public String getBankReceiveTimeBegin() {
        return bankReceiveTimeBegin;
    }

    public void setBankReceiveTimeBegin(String bankReceiveTimeBegin) {
        this.bankReceiveTimeBegin = bankReceiveTimeBegin;
    }

    public String getBankReceiveTimeEnd() {
        return bankReceiveTimeEnd;
    }

    public void setBankReceiveTimeEnd(String bankReceiveTimeEnd) {
        this.bankReceiveTimeEnd = bankReceiveTimeEnd;
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

    public int getRoId() {
        return roId;
    }

    public void setRoId(int roId) {
        this.roId = roId;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public ReceiveOrderBean getReceiveOrder() {
        return receiveOrder;
    }

    public void setReceiveOrder(ReceiveOrderBean receiveOrder) {
        this.receiveOrder = receiveOrder;
    }

    public ReceiveOrderData getReceiveOrderData() {
        return receiveOrderData;
    }

    public void setReceiveOrderData(ReceiveOrderData receiveOrderData) {
        this.receiveOrderData = receiveOrderData;
    }

    public void setCustomerNameService(CustomerNameService customerNameService) {
        this.customerNameService = customerNameService;
    }
}
