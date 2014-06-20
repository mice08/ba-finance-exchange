package com.dianping.ba.finance.exchange.monitor.api.datas;

import java.util.Date;


public class ExceptionData {
    private int exceptionId;
    private int eoId;
    private int exceptionType;
    private Date addDate;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getExceptionId() {
        return exceptionId;
    }

    public void setExceptionId(int exceptionId) {
        this.exceptionId = exceptionId;
    }

    public int getEoId() {
        return eoId;
    }

    public void setEoId(int eoId) {
        this.eoId = eoId;
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


    @Override
    public String toString() {
        return "MonitorExceptionData{" +
                "exceptionId=" + exceptionId +
                ", eoId=" + eoId +
                ", exceptionType=" + exceptionType +
                ", addDate=" + addDate +
                ", status=" + status +
                '}';
    }
}
