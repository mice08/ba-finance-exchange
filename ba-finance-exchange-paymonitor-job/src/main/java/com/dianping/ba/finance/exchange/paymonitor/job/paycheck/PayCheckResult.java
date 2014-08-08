package com.dianping.ba.finance.exchange.paymonitor.job.paycheck;


import com.dianping.ba.finance.exchange.paymonitor.api.enums.MonitorExceptionType;

public class PayCheckResult {

    private boolean valided;

    private boolean timeout;

    private MonitorExceptionType monitorExceptionType;

    public PayCheckResult() {
    }

    public boolean isValided() {
        return valided;
    }

    public void setValided(boolean valided) {
        this.valided = valided;
    }

    public boolean isTimeout() {
        return timeout;
    }

    public void setTimeout(boolean timeout) {
        this.timeout = timeout;
    }

    public MonitorExceptionType getMonitorExceptionType() {
        return monitorExceptionType;
    }

    public void setMonitorExceptionType(MonitorExceptionType monitorExceptionType) {
        this.monitorExceptionType = monitorExceptionType;
    }

}
