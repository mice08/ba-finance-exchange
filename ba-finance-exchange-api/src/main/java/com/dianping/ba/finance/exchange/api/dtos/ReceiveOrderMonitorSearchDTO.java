package com.dianping.ba.finance.exchange.api.dtos;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by adam.huang on 2014/8/21.
 */
public class ReceiveOrderMonitorSearchDTO implements Serializable {
    private Date startTime;
    private Date endTime;
    private int BusinessType;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getBusinessType() {
        return BusinessType;
    }

    public void setBusinessType(int businessType) {
        BusinessType = businessType;
    }

    @Override
    public String toString() {
        return "ReceiveOrderMonitorSearchDTO{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", BusinessType=" + BusinessType +
                '}';
    }
}
