package com.dianping.ba.finance.exchange.monitor.api.datas;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: noahshen
 * Date: 14-3-26
 * Time: 下午3:04
 * To change this template use File | Settings | File Templates.
 */
public class ExchangeOrderMonitorData {

    private int eoId;

    private int status;

    private Date addDate;

    private Date lastUpdateDate;


    public ExchangeOrderMonitorData() {
    }

    public int getEoId() {
        return eoId;
    }

    public void setEoId(int eoId) {
        this.eoId = eoId;
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
        return "ExchangeOrderMonitorData{" +
                "eoId=" + eoId +
                ", status=" + status +
                ", addDate=" + addDate +
                ", lastUpdateDate=" + lastUpdateDate +
                '}';
    }
}
