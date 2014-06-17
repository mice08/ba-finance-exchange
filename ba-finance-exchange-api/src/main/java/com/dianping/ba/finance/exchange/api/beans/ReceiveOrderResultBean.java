package com.dianping.ba.finance.exchange.api.beans;

import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.ba.finance.exchange.api.enums.ReceiveOrderPayChannel;
import com.dianping.ba.finance.exchange.api.enums.ReceiveType;

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
public class ReceiveOrderResultBean implements Serializable {

    private static final long serialVersionUID = -1L;

    private int roId;
    private int customerId;
    private int shopId;
    private BusinessType businessType;
    private int loginId;
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
    private ReceiveOrderPayChannel payChannel;
    private ReceiveType receiveType;
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

    public int getRoId() {
        return roId;
    }

    public void setRoId(int roId) {
        this.roId = roId;
    }

    public BusinessType getBusinessType() {
        return businessType;
    }

    public void setBusinessType(BusinessType businessType) {
        this.businessType = businessType;
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

    public int getLoginId() {
        return loginId;
    }

    public void setLoginId(int loginId) {
        this.loginId = loginId;
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

    public ReceiveOrderPayChannel getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(ReceiveOrderPayChannel payChannel) {
        this.payChannel = payChannel;
    }

    public ReceiveType getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(ReceiveType receiveType) {
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
        return "ReceiveOrderResultBean{" +
                "roId=" + roId +
                ", customerId=" + customerId +
                ", shopId=" + shopId +
                ", businessType=" + businessType +
                ", loginId=" + loginId +
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
