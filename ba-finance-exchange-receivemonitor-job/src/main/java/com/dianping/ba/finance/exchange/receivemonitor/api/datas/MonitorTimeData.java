package com.dianping.ba.finance.exchange.receivemonitor.api.datas;

import java.util.Date;

public class MonitorTimeData {
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
