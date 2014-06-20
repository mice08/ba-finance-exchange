package com.dianping.ba.finance.exchange.api.datas;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: 遐
 * Date: 14-2-24
 * Time: 下午2:03
 * To change this template use File | Settings | File Templates.
 */
public class ExchangeOrderSummaryData {

    private String bizCode;
    private BigDecimal orderAmount;
    private int status;
    private Date addDate;
    private int businessType;

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
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

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    @Override
    public String toString() {
        return "ExchangeOrderSummaryData{" +
                "bizCode='" + bizCode + '\'' +
                ", orderAmount=" + orderAmount +
                ", status=" + status +
                ", addDate=" + addDate +
                ", businessType=" + businessType +
                '}';
    }
}
