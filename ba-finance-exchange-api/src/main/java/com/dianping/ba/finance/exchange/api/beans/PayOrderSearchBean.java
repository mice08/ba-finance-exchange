package com.dianping.ba.finance.exchange.api.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Eric on 2014/6/11.
 */
public class PayOrderSearchBean implements Serializable {

    private int businessType;
    private Date beginTime;
    private Date endTime;
    private int status;
    private String payCode;

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

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
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
                ", payCode='" + payCode + '\'' +
                '}';
    }
}
