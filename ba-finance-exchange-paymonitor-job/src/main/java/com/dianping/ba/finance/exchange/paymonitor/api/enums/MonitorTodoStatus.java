package com.dianping.ba.finance.exchange.paymonitor.api.enums;

/**
 * Created by adam.huang on 2014/8/7.
 */
public enum MonitorTodoStatus {
    /**
     *初始
     */
    INIT(1, "初始"),

    /**
     * 已处理
     */
    HANDLED(2, "已处理");

    private int status;
    private String description;


    private MonitorTodoStatus(int status, String description) {
        this.status = status;
        this.description = description;
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
