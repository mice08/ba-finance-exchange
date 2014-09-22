package com.dianping.ba.finance.exchange.api.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Eric on 2014/6/11.
 */
public class PayOrderSearchBean implements Serializable {

    private int businessType;
    private Date beginTime;
    private Date endTime;
    private int status;
    private List<Integer> poIdList;

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Integer> getPoIdList() {
        return poIdList;
    }

    public void setPoIdList(List<Integer> poIdList) {
        this.poIdList = poIdList;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "PayOrderSearchBean{" +
                "businessType=" + businessType +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", status=" + status +
                ", poIdList=" + poIdList +
                '}';
    }
}
