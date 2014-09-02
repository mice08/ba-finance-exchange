package com.dianping.ba.finance.exchange.api.dtos;

import java.io.Serializable;

/**
 * Created by Administrator on 2014/7/23.
 */
public class ReceiveNotifyResultDTO implements Serializable {
    private String applicationId;
    private int receiveNotifyId;
    private int status;
    private String memo;

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public int getReceiveNotifyId() {
        return receiveNotifyId;
    }

    public void setReceiveNotifyId(int receiveNotifyId) {
        this.receiveNotifyId = receiveNotifyId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "ReceiveNotifyResultDTO{" +
                "applicationId='" + applicationId + '\'' +
                ", receiveNotifyId='" + receiveNotifyId + '\'' +
                ", status=" + status +
                ", memo='" + memo + '\'' +
                '}';
    }
}
