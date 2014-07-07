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
    private String payerAccountName;
    private String payerAccountNo;
    private String payerBankName;
    private String tradeNo;
    private int bankID;
    private int reverseRoId;
    private int shopId;
    private String payTime;
    private String payChannel;
    private String receiveNotifyId;
    private String payerName;
    private int customerId;
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

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getPayerAccountName() {
        return payerAccountName;
    }

    public void setPayerAccountName(String payerAccountName) {
        this.payerAccountName = payerAccountName;
    }

    public String getPayerAccountNo() {
        return payerAccountNo;
    }

    public void setPayerAccountNo(String payerAccountNo) {
        this.payerAccountNo = payerAccountNo;
    }

    public String getPayerBankName() {
        return payerBankName;
    }

    public void setPayerBankName(String payerBankName) {
        this.payerBankName = payerBankName;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public int getBankID() {
        return bankID;
    }

    public void setBankID(int bankID) {
        this.bankID = bankID;
    }

    public int getReverseRoId() {
        return reverseRoId;
    }

    public void setReverseRoId(int reverseRoId) {
        this.reverseRoId = reverseRoId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }
}
