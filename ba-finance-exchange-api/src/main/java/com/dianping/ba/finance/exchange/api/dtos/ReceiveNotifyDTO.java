package com.dianping.ba.finance.exchange.api.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2014/7/23.
 */
public class ReceiveNotifyDTO implements Serializable{
    private String applicationId;
    private int businessType;
    private BigDecimal receiveAmount;
    private int customerId;
    private int payChannel;
    private int receiveType;
    private Date payTime;
    private String payerName;
    private String bizContent;
    private String attachment;
    private String memo;
    private Date requestTime;
    private String token;
    private int bankId;

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public BigDecimal getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(BigDecimal receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(int payChannel) {
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

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public int getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(int receiveType) {
        this.receiveType = receiveType;
    }

    @Override
    public String toString() {
        return "ReceiveNotifyDTO{" +
                "applicationId='" + applicationId + '\'' +
                ", businessType=" + businessType +
                ", receiveAmount=" + receiveAmount +
                ", customerId=" + customerId +
                ", payChannel=" + payChannel +
                ", receiveType=" + receiveType +
                ", payTime=" + payTime +
                ", payerName='" + payerName + '\'' +
                ", bizContent='" + bizContent + '\'' +
                ", attachment='" + attachment + '\'' +
                ", memo='" + memo + '\'' +
                ", requestTime=" + requestTime +
                ", token='" + token + '\'' +
                ", bankId=" + bankId +
                '}';
    }
}
