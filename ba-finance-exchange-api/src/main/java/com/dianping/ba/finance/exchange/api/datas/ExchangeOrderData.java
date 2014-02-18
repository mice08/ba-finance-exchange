package com.dianping.ba.finance.exchange.api.datas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jinyu.yin
 * Date: 13-12-12
 * Time: 下午6:01
 * To change this template use File | Settings | File Templates.
 */
public class ExchangeOrderData implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private int exchangeOrderId;
    /**
     * 金额（都是正数）
     */
    private BigDecimal orderAmount;
    /**
     * 1:付款 2:收款
     */
    private int orderType;
    /**
     * bankAccountNo
     */
    private String bankAccountNo;
    /**
     * BankAccountName
     */
    private String bankAccountName;
    /**
     * BankName
     */
    private String bankName;
    /**
     * 交易状态；1初始；2支付中; 3成功；4失败
     */
    private int status;
    /**
     * 添加时间
     */
    private Date addDate;
    /**
     * 交易成功时间
     */
    private Date orderDate;
    /**
     * 更新时间
     */
    private Date lastUpdateDate;
    /**
     * 备注
     */
    private String memo;

    private String bankCity;

    private String bankProvince;

    private String bizCode;

    private int addLoginId;
    private int lastUpdateLoginId;

    public int getAddLoginId() {
        return addLoginId;
    }

    public void setAddLoginId(int addLoginId) {
        this.addLoginId = addLoginId;
    }

    public int getLastUpdateLoginId() {
        return lastUpdateLoginId;
    }

    public void setLastUpdateLoginId(int lastUpdateLoginId) {
        this.lastUpdateLoginId = lastUpdateLoginId;
    }

    public int getExchangeOrderId() {
        return exchangeOrderId;
    }

    public void setExchangeOrderId(int exchangeOrderId) {
        this.exchangeOrderId = exchangeOrderId;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getBankProvince() {
        return bankProvince;
    }

    public void setBankProvince(String bankProvince) {
        this.bankProvince = bankProvince;
    }

    public String getBankCity() {
        return bankCity;
    }

    public void setBankCity(String bankCity) {
        this.bankCity = bankCity;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }
}
