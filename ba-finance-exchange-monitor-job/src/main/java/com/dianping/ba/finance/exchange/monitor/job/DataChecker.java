package com.dianping.ba.finance.exchange.monitor.job;

import java.util.Date;

public abstract class DataChecker {

    private Date monitorStartDate;

    public Date getMonitorStartDate() {
        return monitorStartDate;
    }

    public void setMonitorStartDate(Date monitorStartDate) {
        this.monitorStartDate = monitorStartDate;
    }

    abstract public boolean run();

}
