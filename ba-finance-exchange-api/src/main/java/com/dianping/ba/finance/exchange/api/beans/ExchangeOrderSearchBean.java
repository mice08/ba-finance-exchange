package com.dianping.ba.finance.exchange.api.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: 遐
 * Date: 14-1-13
 * Time: 上午11:39
 * To change this template use File | Settings | File Templates.
 */
public class ExchangeOrderSearchBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int exchangeOrderId;
    private Date beginDate;
    private Date endDate;
    private int status;
    private int orderType;
    private String bizCode;
    private int businessType;
    private int shopId;

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getExchangeOrderId() {
        return exchangeOrderId;
    }

    public void setExchangeOrderId(int exchangeOrderId) {
        this.exchangeOrderId = exchangeOrderId;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    @Override
    public String toString() {
        return "ExchangeOrderSearchBean{" +
                "exchangeOrderId=" + exchangeOrderId +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", status=" + status +
                ", orderType=" + orderType +
                ", bizCode='" + bizCode + '\'' +
                ", businessType=" + businessType +
                ", shopId=" + shopId +
                '}';
    }
}
