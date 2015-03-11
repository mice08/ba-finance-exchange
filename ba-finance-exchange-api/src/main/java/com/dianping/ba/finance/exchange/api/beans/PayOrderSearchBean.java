package com.dianping.ba.finance.exchange.api.beans;

import java.io.Serializable;
import java.math.BigDecimal;
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
    private BigDecimal startAmount;
    private BigDecimal endAmount;
    private int payType;

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

    public BigDecimal getStartAmount() {
        return startAmount;
    }

    public void setStartAmount(BigDecimal startAmount) {
        this.startAmount = startAmount;
    }

    public BigDecimal getEndAmount() {
        return endAmount;
    }

    public void setEndAmount(BigDecimal endAmount) {
        this.endAmount = endAmount;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    @Override
    public String toString() {
        return "PayOrderSearchBean{" +
                "businessType=" + businessType +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", status=" + status +
                ", poIdList=" + poIdList +
                ", startAmount=" + startAmount +
                ", endAmount=" + endAmount +
                ", payType=" + payType +
                '}';
    }
}
