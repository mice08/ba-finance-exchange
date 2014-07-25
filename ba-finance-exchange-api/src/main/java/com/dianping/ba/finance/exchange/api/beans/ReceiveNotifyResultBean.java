package com.dianping.ba.finance.exchange.api.beans;

import com.dianping.ba.finance.exchange.api.enums.ReceiveNotifyResultStatus;

import java.io.Serializable;

/**
 * Created by Administrator on 2014/7/24.
 */
public class ReceiveNotifyResultBean implements Serializable{
    private String applicationId;
    private int receiveNotifyId;
    private ReceiveNotifyResultStatus status;
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

    public ReceiveNotifyResultStatus getStatus() {
        return status;
    }

    public void setStatus(ReceiveNotifyResultStatus status) {
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
        return "ReceiveNotifyResultBean{" +
                "applicationId='" + applicationId + '\'' +
                ", receiveNotifyId='" + receiveNotifyId + '\'' +
                ", status=" + status +
                ", memo='" + memo + '\'' +
                '}';
    }
}
