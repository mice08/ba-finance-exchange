package com.dianping.ba.finance.exchange.receivemonitor.api.enums;


public enum TodoStatus {

    INIT(1),

    HANDLED(2);

    private int status;

    private TodoStatus(int status) {
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
