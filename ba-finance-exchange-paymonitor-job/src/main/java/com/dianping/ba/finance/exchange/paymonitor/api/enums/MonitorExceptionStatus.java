package com.dianping.ba.finance.exchange.paymonitor.api.enums;

/**
 * Created by adam.huang on 2014/8/7.
 */
public enum MonitorExceptionStatus {
    INIT(1),

    HANDLED(2);

    private int status;

    private MonitorExceptionStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int value(){
        return this.status;
    }
}
