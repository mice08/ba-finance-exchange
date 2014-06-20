package com.dianping.ba.finance.exchange.siteweb.beans;

import java.math.BigDecimal;

/**
 * Created by Eric on 2014/6/18.
 */
public class ReceiveOrderBean {

    private int roId;
    private String businessType;
    private String customerName;
    private String receiveAmount;
    private String bankReceiveTime;
    private String receiveTime;
    private String receiveType;
    private String bizContent;
    private String payChannel;
    private String receiveNotifyId;
    private String payerName;
    private String status;
    private String memo;

    public int getRoId() {
        return roId;
    }

    public void setRoId(int roId) {
        this.roId = roId;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(String receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public String getBankReceiveTime() {
        return bankReceiveTime;
    }

    public void setBankReceiveTime(String bankReceiveTime) {
        this.bankReceiveTime = bankReceiveTime;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(String receiveType) {
        this.receiveType = receiveType;
    }

    public String getBizContent() {
        return bizContent;
    }

    public void setBizContent(String bizContent) {
        this.bizContent = bizContent;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getReceiveNotifyId() {
        return receiveNotifyId;
    }

    public void setReceiveNotifyId(String receiveNotifyId) {
        this.receiveNotifyId = receiveNotifyId;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
