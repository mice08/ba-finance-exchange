package com.dianping.ba.finance.exchange.siteweb.action.ajax;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.ReceiveNotifyService;
import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.ba.finance.exchange.api.enums.ReceiveNotifyStatus;
import com.dianping.ba.finance.exchange.api.enums.ReceiveOrderPayChannel;
import com.dianping.ba.finance.exchange.siteweb.beans.CustomerInfoBean;
import com.dianping.ba.finance.exchange.siteweb.beans.ReceiveInfoBean;
import com.dianping.ba.finance.exchange.siteweb.beans.ReceiveNotifyConfirmBean;
import com.dianping.ba.finance.exchange.siteweb.services.CustomerNameService;
import com.dianping.ba.finance.exchange.siteweb.util.DateUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
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

    public String rornCancelLink(){
        try {
            receiveNotifyService.removeReceiveNotifyMatchRelation(rnId,roMatcherId);
            code = SUCCESS_CODE;
        } catch (Exception e){
            MONITOR_LOGGER.error("severity=[1] ReceiveOrderAjaxAction.rornCancelLink error!", e);
            code = ERROR_CODE;
        }
        return SUCCESS;
    }

    public String findNotifiesByROId(){
        try {
            List<ReceiveNotifyData> list = receiveNotifyService.findMatchedReceiveNotify(roMatcherId);

            records = buildReceiveNotifyConfirmList(list);
            code = SUCCESS_CODE;
        } catch (Exception e){
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

    private List<ReceiveNotifyConfirmBean> buildReceiveNotifyConfirmList(List<ReceiveNotifyData> list){
        Map<Integer, String> customerIdNameMap = customerNameService.getRORNCustomerName(list, getLoginId());
        List<ReceiveNotifyConfirmBean> beans = new ArrayList<ReceiveNotifyConfirmBean>();
        for (ReceiveNotifyData data : list){
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

    @Override
    protected void jsonExecute() throws Exception {

    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public Map<String, Object> getMsg() {
        return msg;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public void setReceiveNotifyService(ReceiveNotifyService receiveNotifyService) {
        this.receiveNotifyService = receiveNotifyService;
    }

    public void setCustomerNameService(CustomerNameService customerNameService) {
        this.customerNameService = customerNameService;
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
}
