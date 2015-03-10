package com.dianping.ba.finance.exchange.api.datas;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by will on 15-3-10.
 */
public class AuthStatusInfoData implements Serializable {

    private int id;
    private int times;
    private int type;
    private int status;
    private String workNo;
    private Date addTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getWorkNo() {
        return workNo;
    }

    public void setWorkNo(String workNo) {
        this.workNo = workNo;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
