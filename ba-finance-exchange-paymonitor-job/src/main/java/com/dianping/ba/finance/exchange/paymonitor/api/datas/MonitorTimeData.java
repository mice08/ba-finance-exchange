package com.dianping.ba.finance.exchange.paymonitor.api.datas;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by adam.huang on 2014/8/7.
 */
public class MonitorTimeData implements Serializable{
    private int monitorId;
    private Date monitorTime;

    public int getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(int monitorId) {
        this.monitorId = monitorId;
    }

    public Date getMonitorTime() {
        return monitorTime;
    }

    public void setMonitorTime(Date monitorTime) {
        this.monitorTime = monitorTime;
    }

    @Override
    public String toString() {
        return "MonitorTimeData{" +
                "monitorId=" + monitorId +
                ", monitorTime=" + monitorTime +
                '}';
    }
}
