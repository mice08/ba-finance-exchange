package com.dianping.ba.finance.exchange.api.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: bingqiu.yuan
 * Date: 14-6-4
 * Time: 下午13:44
 * To change this template use File | Settings | File Templates.
 */
public class PayCentreReceiveRequestDTO implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 支付中心唯一交易流水号
     */
    private String tradeNo;
    /**
     * 交易类型：1收款；2冲销
     */
    private int tradeType;
    /**
     * 我司收款银行   汉涛上海：1    汉海上海：8   汉海北京：7   汉海广州：11
     */
    private int bankId;
    private BigDecimal receiveAmount;
    /**
     * 收款日期
     */
    private Date receiveDate;
    /**
     * 1, "团购" 2, "预订" 6, "广告" 7, "结婚"  8,"储值卡"
     */
    private int businessType;
    /**
     * 对方付款渠道：10是快钱渠道
     */
    private int payChannel;
    /**
     * 对方付款方式：5是POS机
     */
    private int payMethod;
    /**
     * 业务文本（广告：合同号）
     */
    private String bizContent;

    /**
     * 原始交易号 冲销必须有
     */
    private String oriTradeNo;
    private String memo;

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

    public BigDecimal getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(BigDecimal receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public int getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(int payChannel) {
        this.payChannel = payChannel;
    }

    public int getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(int payMethod) {
        this.payMethod = payMethod;
    }

    public String getBizContent() {
        return bizContent;
    }

    public void setBizContent(String bizContent) {
        this.bizContent = bizContent;
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
        return "PayCentreReceiveRequestDTO{" +
                "tradeNo='" + tradeNo + '\'' +
                ", tradeType=" + tradeType +
                ", bankId=" + bankId +
                ", receiveAmount=" + receiveAmount +
                ", receiveDate=" + receiveDate +
                ", businessType=" + businessType +
                ", payChannel=" + payChannel +
                ", payMethod=" + payMethod +
                ", bizContent='" + bizContent + '\'' +
                ", oriTradeNo='" + oriTradeNo + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }
}
