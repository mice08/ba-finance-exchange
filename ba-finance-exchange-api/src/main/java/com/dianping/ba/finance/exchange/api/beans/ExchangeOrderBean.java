package com.dianping.ba.finance.exchange.api.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: bingqiu.yuan
 * Date: 13-12-12
 * Time: 下午6:01
 * To change this template use File | Settings | File Templates.
 */
public class ExchangeOrderBean implements Serializable {
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
     * 交易状态；1初始；2支付中; 3成功；4失败
     */
    private int status;
    /**
     * 交易成功时间
     */
    private Date orderDate;

    /**
     * 备注
     */
    private String memo;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
