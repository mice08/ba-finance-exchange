package com.dianping.ba.finance.exchange.api.dtos;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: noahshen
 * Date: 14-3-27
 * Time: 上午9:57
 * To change this template use File | Settings | File Templates.
 */
public class EOMonitorDTO {

    private int exchangeOrderId;

    private String bizCode;

    private int status;

    private Date addDate;

    private Date lastUpdateDate;

    public EOMonitorDTO() {
    }

    public int getExchangeOrderId() {
        return exchangeOrderId;
    }

    public void setExchangeOrderId(int exchangeOrderId) {
        this.exchangeOrderId = exchangeOrderId;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
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

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Override
    public String toString() {
        return "EOMonitorDTO{" +
                "exchangeOrderId=" + exchangeOrderId +
                ", bizCode='" + bizCode + '\'' +
                ", status=" + status +
                ", addDate=" + addDate +
                ", lastUpdateDate=" + lastUpdateDate +
                '}';
    }
}
