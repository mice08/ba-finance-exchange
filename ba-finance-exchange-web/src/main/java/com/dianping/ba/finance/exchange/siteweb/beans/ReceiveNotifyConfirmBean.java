package com.dianping.ba.finance.exchange.siteweb.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by will on 14-7-29.
 */
public class ReceiveNotifyConfirmBean implements Serializable {

    private int receiveNotifyId;
    private String applicationId;
    private String businessType;
    private BigDecimal receiveAmount;
    private String payChannel;
    private Date payTime;
    private String payerName;
    private String bizContent;
    private int customerId;
    private String customerName;
    private int bankId;
    private String attachment;
    private String status;
    private int roMatcherId;
    private String memo;

    public int getReceiveNotifyId() {
        return receiveNotifyId;
    }

    public void setReceiveNotifyId(int receiveNotifyId) {
        this.receiveNotifyId = receiveNotifyId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public BigDecimal getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(BigDecimal receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getBizContent() {
        return bizContent;
    }

    public void setBizContent(String bizContent) {
        this.bizContent = bizContent;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRoMatcherId() {
        return roMatcherId;
    }

    public void setRoMatcherId(int roMatcherId) {
        this.roMatcherId = roMatcherId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
