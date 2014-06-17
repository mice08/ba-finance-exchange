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
public class ReceiveResultNotifyDTO implements Serializable {

    private static final long serialVersionUID = -1L;

    private String bizId;
    private int customerId;
    private int shopId;
    private BigDecimal receiveAmount;
    /**
     * 系统确认收款日期
     */
    private Date receiveTime;
    /**
     * 客户打款日期
     */
    private Date payTime;
    /**
     * 银行到账日期
     */
    private Date bankReceiveTime;
    private int payChannel;
    private int receiveType;
    /**
     * 业务文本（广告：合同号）
     */
    private String bizContent;
    /**
     * 我司收款银行
     */
    private int bankId;
    /**
     *交易流水号（from支付中心）
     */
    private String tradeNo;
    private String memo;

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public BigDecimal getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(BigDecimal receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getBankReceiveTime() {
        return bankReceiveTime;
    }

    public void setBankReceiveTime(Date bankReceiveTime) {
        this.bankReceiveTime = bankReceiveTime;
    }

    public int getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(int payChannel) {
        this.payChannel = payChannel;
    }

    public int getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(int receiveType) {
        this.receiveType = receiveType;
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

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "ReceiveResultNotifyDTO{" +
                "bizId='" + bizId + '\'' +
                ", customerId=" + customerId +
                ", shopId=" + shopId +
                ", receiveAmount=" + receiveAmount +
                ", receiveTime=" + receiveTime +
                ", payTime=" + payTime +
                ", bankReceiveTime=" + bankReceiveTime +
                ", payChannel=" + payChannel +
                ", receiveType=" + receiveType +
                ", bizContent='" + bizContent + '\'' +
                ", bankId=" + bankId +
                ", tradeNo='" + tradeNo + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }
}
