package com.dianping.ba.finance.exchange.siteweb.action.ajax;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.ReceiveOrderService;
import com.dianping.ba.finance.exchange.api.beans.ReceiveOrderSearchBean;
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.ba.finance.exchange.api.enums.ReceiveOrderPayChannel;
import com.dianping.ba.finance.exchange.api.enums.ReceiveOrderStatus;
import com.dianping.ba.finance.exchange.api.enums.ReceiveType;
import com.dianping.ba.finance.exchange.siteweb.beans.ReceiveOrderBean;
import com.dianping.ba.finance.exchange.siteweb.util.DateUtil;
import com.dianping.core.type.PageModel;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    private int bankId;

    private String memo;

    private int code;

    private String bankReceiveTimeBegin;

    private String bankReceiveTimeEnd;

    private String receiveTimeBegin;

    private String receiveTimeEnd;

    private int status;

    private String totalAmount;


    //查询结果，付款计划列表
    private PageModel receiveOrderModel = new PageModel();
    //第几页
    private Integer page = 1;
    //分页大小
    private Integer pageSize = 20;

    private Map<String, Object> msg = Maps.newHashMap();

    private ReceiveOrderService receiveOrderService;

    @Override
    protected void jsonExecute() {
        if (businessType == BusinessType.DEFAULT.value()) {
            totalAmount = new DecimalFormat("0.00").format(BigDecimal.ZERO);
            return;
        }

        try {
            ReceiveOrderSearchBean receiveOrderSearchBean = buildROSearchBean();
            receiveOrderModel = receiveOrderService.paginateReceiveOrderList(receiveOrderSearchBean, page, pageSize);
            receiveOrderModel.setRecords(buildReceiveOrderBeans((List<ReceiveOrderData>) receiveOrderModel.getRecords()));
            totalAmount=new DecimalFormat("0.00").format(receiveOrderService.loadReceiveOrderTotalAmountByCondition(receiveOrderSearchBean));
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

    private List<ReceiveOrderBean> buildReceiveOrderBeans(List<ReceiveOrderData> receiveOrderDataList) {
        List<ReceiveOrderBean> receiveOrderBeans = new ArrayList<ReceiveOrderBean>();
        if (receiveOrderDataList == null) {
            return receiveOrderBeans;
        }
        for (ReceiveOrderData receiveOrderData : receiveOrderDataList) {
            receiveOrderBeans.add(convertRODataToROBean(receiveOrderData));
        }
        return receiveOrderBeans;
    }

    private ReceiveOrderBean convertRODataToROBean(ReceiveOrderData receiveOrderData) {
        ReceiveOrderBean receiveOrderBean = new ReceiveOrderBean();
        receiveOrderBean.setBankReceiveTime(DateUtil.formatDateToString(receiveOrderData.getBankReceiveTime(), "yyyy-MM-dd"));
        receiveOrderBean.setBizContent(receiveOrderData.getBizContent());
        receiveOrderBean.setBusinessType(BusinessType.valueOf(receiveOrderData.getBusinessType()).toString());
        receiveOrderBean.setCustomerName(getCustomerNameById(receiveOrderData.getCustomerId()));
        receiveOrderBean.setMemo(receiveOrderData.getMemo());
        receiveOrderBean.setPayChannel(ReceiveOrderPayChannel.valueOf(receiveOrderData.getPayChannel()).toString());
        receiveOrderBean.setPayerName("");
        receiveOrderBean.setReceiveAmount(new DecimalFormat("0.00").format(receiveOrderData.getReceiveAmount()));
        receiveOrderBean.setReceiveTime(DateUtil.formatDateToString(receiveOrderData.getReceiveTime(), "yyyy-MM-dd"));
        receiveOrderBean.setReceiveType(ReceiveType.valueOf(receiveOrderData.getReceiveType()).toString());
        receiveOrderBean.setRoId(receiveOrderData.getRoId());
        receiveOrderBean.setStatus(ReceiveOrderStatus.valueOf(receiveOrderData.getStatus()).toString());
        return receiveOrderBean;
    }

    private String getCustomerNameById(int id){
        return "";
    }

    private ReceiveOrderSearchBean buildROSearchBean() throws ParseException {
        ReceiveOrderSearchBean receiveOrderSearchBean = new ReceiveOrderSearchBean();
        receiveOrderSearchBean.setStatus(status);
        receiveOrderSearchBean.setBusinessType(businessType);
        receiveOrderSearchBean.setBankReceiveTimeBegin(DateUtil.isValidDate(bankReceiveTimeBegin) ? DateUtil.formatDate(bankReceiveTimeBegin, false) : null);
        receiveOrderSearchBean.setBankReceiveTimeEnd(DateUtil.isValidDate(bankReceiveTimeEnd) ? DateUtil.formatDate(bankReceiveTimeEnd, false) : null);
        receiveOrderSearchBean.setCustomerId(customerId);
        receiveOrderSearchBean.setPayChannel(payChannel);
        receiveOrderSearchBean.setReceiveTimeBegin(DateUtil.isValidDate(receiveTimeBegin) ? DateUtil.formatDate(receiveTimeBegin, false) : null);
        receiveOrderSearchBean.setReceiveTimeEnd(DateUtil.isValidDate(receiveTimeEnd) ? DateUtil.formatDate(receiveTimeEnd, false) : null);
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
}
