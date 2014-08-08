package com.dianping.ba.finance.exchange.siteweb.beans;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by noahshen on 14-7-28.
 */
public class ReceiveInfoBean implements Serializable {

    private int customerId;

    private String customerName;

    private int businessType;

    private BigDecimal receiveAmount;

    private int payChannel;

    private String payTime;

    private String bizContent;

    private int bankId;

    public ReceiveInfoBean() {
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

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getBizContent() {
        return bizContent;
    }

    public void setBizContent(String bizContent) {
        this.bizContent = bizContent;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    @Override
    public String toString() {
        return "ReceiveInfoBean{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", businessType=" + businessType +
                ", receiveAmount=" + receiveAmount +
                ", payChannel=" + payChannel +
                ", payTime=" + payTime +
                ", bizContent='" + bizContent + '\'' +
                ", bankId=" + bankId +
                '}';
    }
}
