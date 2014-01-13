package com.dianping.ba.finance.exchange.api.datas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: noahshen
 * Date: 14-1-13
 * Time: 下午2:17
 * To change this template use File | Settings | File Templates.
 */
public class ExchangeOrderDisplayData implements Serializable {
    private static final long serialVersionUID = 1L;

    private int exchangeOrderId;

    private String customerGlobalId;

    private String companyGlobalId;

    private int shopId;

    private BigDecimal orderAmount;

    private String bankAccountNo;

    private String bankAccountName;

    private String bankName;

    private int status;

    private String memo;

    private Date orderDate;


    public ExchangeOrderDisplayData() {
    }

    public int getExchangeOrderId() {
        return exchangeOrderId;
    }

    public void setExchangeOrderId(int exchangeOrderId) {
        this.exchangeOrderId = exchangeOrderId;
    }

    public String getCustomerGlobalId() {
        return customerGlobalId;
    }

    public void setCustomerGlobalId(String customerGlobalId) {
        this.customerGlobalId = customerGlobalId;
    }

    public String getCompanyGlobalId() {
        return companyGlobalId;
    }

    public void setCompanyGlobalId(String companyGlobalId) {
        this.companyGlobalId = companyGlobalId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
