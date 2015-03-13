package com.dianping.ba.finance.exchange.bankpaymonitor.api.datas;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by will on 15-3-13.
 */
public class BankPayMonitorExceptionData implements Serializable{
    private int id;
    private int poId;
    private int checkStatus;
    private String memo;
    private Date addTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoId() {
        return poId;
    }

    public void setPoId(int poId) {
        this.poId = poId;
    }

    public int getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(int checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}
