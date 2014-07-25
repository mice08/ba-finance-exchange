package com.dianping.ba.finance.exchange.api.datas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2014/7/23.
 */
public class ReceiveNotifyData implements Serializable {
    private int receiveNotifyId;
    private String applicationId;
    private int businessType;
    private BigDecimal receiveAmount;
    private int payChannel;
    private Date payTime;
    private String payerName;
    private String bizContent;
    private int customerId;
    private int bankId;
    private String attachment;
    private int status;
    private int roMatcherId;
    private String memo;
    private Date addTime;
    private int addLoginId;
    private Date updateTime;
    private int updateLoginId;

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

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public int getAddLoginId() {
        return addLoginId;
    }

    public void setAddLoginId(int addLoginId) {
        this.addLoginId = addLoginId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getUpdateLoginId() {
        return updateLoginId;
    }

    public void setUpdateLoginId(int updateLoginId) {
        this.updateLoginId = updateLoginId;
    }

    @Override
    public String toString() {
        return "ReceiveNotifyData{" +
                "receiveNotifyId=" + receiveNotifyId +
                ", applicationId='" + applicationId + '\'' +
                ", businessType=" + businessType +
                ", receiveAmount=" + receiveAmount +
                ", payChannel=" + payChannel +
                ", payTime=" + payTime +
                ", payerName='" + payerName + '\'' +
                ", bizContent='" + bizContent + '\'' +
                ", customerId=" + customerId +
                ", bankId=" + bankId +
                ", attachment='" + attachment + '\'' +
                ", status=" + status +
                ", roMatcherId=" + roMatcherId +
                ", memo='" + memo + '\'' +
                ", addTime=" + addTime +
                ", addLoginId=" + addLoginId +
                ", updateTime=" + updateTime +
                ", updateLoginId=" + updateLoginId +
                '}';
    }
}
