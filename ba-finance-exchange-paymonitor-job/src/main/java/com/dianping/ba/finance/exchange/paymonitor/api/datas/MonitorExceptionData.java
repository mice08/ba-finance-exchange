package com.dianping.ba.finance.exchange.paymonitor.api.datas;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by adam.huang on 2014/8/7.
 */
public class MonitorExceptionData implements Serializable{
    private int exceptionId;
    private int ppId;
    private int exceptionType;
    private Date addDate;
    private int status;

    public int getExceptionId() {
        return exceptionId;
    }

    public void setExceptionId(int exceptionId) {
        this.exceptionId = exceptionId;
    }

    public int getPpId() {
        return ppId;
    }

    public void setPpId(int ppId) {
        this.ppId = ppId;
    }

    public int getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(int exceptionType) {
        this.exceptionType = exceptionType;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MonitorExceptionData{" +
                "exceptionId=" + exceptionId +
                ", ppId=" + ppId +
                ", exceptionType=" + exceptionType +
                ", addDate=" + addDate +
                ", status=" + status +
                '}';
    }
}
