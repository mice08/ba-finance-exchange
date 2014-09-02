package com.dianping.ba.finance.exchange.api.datas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付中心收款请求表
 */
public class PayCentreReceiveRequestData implements Serializable {

    private int requestId;

    private String tradeNo;

    private int tradeType;

    private int bankId;

    private String bizContent;

    private Date receiveDate;

    private BigDecimal receiveAmount;

    private int businessType;

    private int payMethod;

    private int payChannel;

    private Date addTime;

    private String oriTradeNo;

    private String memo;

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public int getTradeType() {
        return tradeType;
    }

    public void setTradeType(int tradeType) {
        this.tradeType = tradeType;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public String getBizContent() {
        return bizContent;
    }

    public void setBizContent(String bizContent) {
        this.bizContent = bizContent;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public BigDecimal getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(BigDecimal receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public int getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(int payMethod) {
        this.payMethod = payMethod;
    }

    public int getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(int payChannel) {
        this.payChannel = payChannel;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getOriTradeNo() {
        return oriTradeNo;
    }

    public void setOriTradeNo(String oriTradeNo) {
        this.oriTradeNo = oriTradeNo;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "PayCentreReceiveRequestData{" +
                "requestId=" + requestId +
                ", tradeNo='" + tradeNo + '\'' +
                ", tradeType=" + tradeType +
                ", bankId=" + bankId +
                ", bizContent='" + bizContent + '\'' +
                ", receiveDate=" + receiveDate +
                ", receiveAmount=" + receiveAmount +
                ", businessType=" + businessType +
                ", payMethod=" + payMethod +
                ", payChannel=" + payChannel +
                ", addTime=" + addTime +
                ", oriTradeNo='" + oriTradeNo + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }
}
