package com.dianping.ba.finance.exchange.siteweb.action.ajax;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.PayOrderService;
import com.dianping.ba.finance.exchange.api.beans.PayOrderSearchBean;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.ba.finance.exchange.api.enums.PayOrderStatus;
import com.dianping.ba.finance.exchange.siteweb.beans.PayOrderBean;
import com.dianping.ba.finance.exchange.siteweb.util.DateUtil;
import com.dianping.core.type.PageModel;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Eric on 2014/6/11.
 */
public class PayOrderAjaxAction extends AjaxBaseAction {

    /**
     * 记录需要监控的业务日志
     */
    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.web.monitor.PayOrderAjaxAction");
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
    private BigDecimal totalAmount = BigDecimal.ZERO;

    private String payCode;

    private String addDate;

    private PayOrderService payOrderService;

    @Override
    protected void jsonExecute() throws Exception {
        if (businessType == BusinessType.DEFAULT.value()) {
            totalAmount = BigDecimal.ZERO;
            return;
        }
        try {
            PayOrderSearchBean payOrderSearchBean = buildPayOrderSearchBean();
            payOrderModel = payOrderService.paginatePayOrderList(payOrderSearchBean, page, pageSize);
            payOrderModel.setRecords(buildPayOrderBeans((List<PayOrderData>) payOrderModel.getRecords()));
            totalAmount = payOrderService.findPayOrderTotalAmount(payOrderSearchBean);
        } catch (Exception e) {
            MONITOR_LOGGER.error("severity=[1] PayOrderAjaxAction.jsonExecute error!", e);
        }
    }

    private PayOrderSearchBean buildPayOrderSearchBean() throws ParseException {

        PayOrderSearchBean payOrderSearchBean=new PayOrderSearchBean();
        Date beginTime = DateUtil.isValidDate(addBeginTime) ? DateUtil.formatDate(addBeginTime, false) : null;
        Date endTime = DateUtil.isValidDate(addEndTime) ? DateUtil.formatDate(addEndTime, true) : null;
        payOrderSearchBean.setStatus(status);
        payOrderSearchBean.setBusinessType(businessType);
        payOrderSearchBean.setBeginTime(beginTime);
        payOrderSearchBean.setEndTime(endTime);
        if (StringUtils.isNotBlank(payCode)) {
            payOrderSearchBean.setPayCode(payCode);
        }
        return payOrderSearchBean;
    }

    private List<PayOrderBean> buildPayOrderBeans(List<PayOrderData> payOrderDataList) {
        List<PayOrderBean> payOrderBeans = new ArrayList<PayOrderBean>();
        if (payOrderDataList == null) {
            return payOrderBeans;
        }
        for (PayOrderData payOrderData : payOrderDataList) {
            payOrderBeans.add(convertPODataToPOBean(payOrderData));
        }
        return payOrderBeans;
    }

    private PayOrderBean convertPODataToPOBean(PayOrderData payOrderData){
        PayOrderBean payOrderBean=new PayOrderBean();
        payOrderBean.setPayCode(payOrderData.getPayCode());
        payOrderBean.setAddTime(DateUtil.formatDateToString(payOrderData.getAddTime(), "yyyy-MM-dd"));
        payOrderBean.setBankAccountName(payOrderData.getBankAccountName());
        payOrderBean.setBankAccountNo(payOrderData.getBankAccountNo());
        payOrderBean.setBankFullBranchName(payOrderData.getBankFullBranchName());
        payOrderBean.setCustomerName(getCustomerNameById(payOrderData.getCustomerId()));
        payOrderBean.setMemo(payOrderData.getMemo());
        payOrderBean.setPaidDate(DateUtil.formatDateToString(payOrderData.getPaidDate(), "yyyy-MM-dd"));
        payOrderBean.setPayAmount(payOrderData.getPayAmount());
        payOrderBean.setPoId(payOrderData.getPoId());
        payOrderBean.setStatus(PayOrderStatus.valueOf(payOrderData.getStatus()).toString());
        return payOrderBean;
    }

    private String getCustomerNameById(int customerId){
        return "";
    }

    @Override
    public int getCode() {
        return 0;
    }

    @Override
    public Map<String, Object> getMsg() {
        return null;
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

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
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

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }
}
