package com.dianping.ba.finance.exchange.receivemonitor.api.datas;

import java.util.Date;

/**
 * 需要监控的RO对象对应的RC的一个封装
 */
public class ReceiveConfirmMonitorData {

    private int rcId;

    private int status;

    private Date addDate;

    public ReceiveConfirmMonitorData() {
    }


	public int getRcId() {
		return rcId;
	}

	public void setRcId(int rcId) {
		this.rcId = rcId;
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

    @Override
    public String toString() {
        return "ReceiveConfirmMonitorData{" +
                "rcId=" + rcId +
                ", status=" + status +
                ", addDate=" + addDate +
                '}';
    }
}
