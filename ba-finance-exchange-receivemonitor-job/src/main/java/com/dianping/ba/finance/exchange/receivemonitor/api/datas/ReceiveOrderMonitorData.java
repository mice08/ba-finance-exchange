package com.dianping.ba.finance.exchange.receivemonitor.api.datas;

import java.util.Date;

/**
 * 需要监控的RO对象的一个封装
 */
public class ReceiveOrderMonitorData {

    private int roId;

    private int status;

    private Date addDate;

    private Date lastUpdateDate;


    public ReceiveOrderMonitorData() {
    }

	public int getRoId() {
		return roId;
	}

	public void setRoId(int roId) {
		this.roId = roId;
	}

	public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Override
    public String toString() {
        return "ReceiveOrderMonitorData{" +
                "roId=" + roId +
                ", status=" + status +
                ", addDate=" + addDate +
                ", lastUpdateDate=" + lastUpdateDate +
                '}';
    }
}
