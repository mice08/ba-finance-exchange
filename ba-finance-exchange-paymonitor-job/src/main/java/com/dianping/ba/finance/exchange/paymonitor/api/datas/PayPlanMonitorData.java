package com.dianping.ba.finance.exchange.paymonitor.api.datas;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by adam.huang on 2014/8/7.
 */
public class PayPlanMonitorData implements Serializable {
    private int ppId;

    private int status;

    private Date updateTime;

    public int getPpId() {
        return ppId;
    }

    public void setPpId(int ppId) {
        this.ppId = ppId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "PayPlanMonitorData{" +
                "ppId=" + ppId +
                ", status=" + status +
                ", updateTime=" + updateTime +
                '}';
    }
}
