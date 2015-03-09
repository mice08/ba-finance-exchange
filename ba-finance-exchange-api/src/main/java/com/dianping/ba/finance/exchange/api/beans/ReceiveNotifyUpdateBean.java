package com.dianping.ba.finance.exchange.api.beans;

import java.io.Serializable;

/**
 * Created by adam.huang on 2014/9/23.
 */
public class ReceiveNotifyUpdateBean implements Serializable {
    private int rnId;

    private int preStatus;

    private int setStatus;

    private String memo;

    public int getRnId() {
        return rnId;
    }

    public void setRnId(int rnId) {
        this.rnId = rnId;
    }

    public int getPreStatus() {
        return preStatus;
    }

    public void setPreStatus(int preStatus) {
        this.preStatus = preStatus;
    }

    public int getSetStatus() {
        return setStatus;
    }

    public void setSetStatus(int setStatus) {
        this.setStatus = setStatus;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "ReceiveNotifyUpdateBean{" +
                "rnId=" + rnId +
                ", preStatus=" + preStatus +
                ", setStatus=" + setStatus +
                ", memo='" + memo + '\'' +
                '}';
    }
}
