package com.dianping.ba.finance.exchange.api.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Eric on 2014/6/11.
 */
public class ReceiveOrderSearchBean implements Serializable {

    private int businessType;

    private int customerId;

    private int receiveType;

    private Date receiveTimeBegin;

    private Date receiveTimeEnd;

    private int payChannel;

    private int status;

    private Date bankReceiveTimeBegin;

    private Date bankReceiveTimeEnd;

    public ReceiveOrderSearchBean() {
    }

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(int receiveType) {
        this.receiveType = receiveType;
    }

    public Date getReceiveTimeBegin() {
        return receiveTimeBegin;
    }

    public void setReceiveTimeBegin(Date receiveTimeBegin) {
        this.receiveTimeBegin = receiveTimeBegin;
    }

    public Date getReceiveTimeEnd() {
        return receiveTimeEnd;
    }

    public void setReceiveTimeEnd(Date receiveTimeEnd) {
        this.receiveTimeEnd = receiveTimeEnd;
    }

    public int getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(int payChannel) {
        this.payChannel = payChannel;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getBankReceiveTimeBegin() {
        return bankReceiveTimeBegin;
    }

    public void setBankReceiveTimeBegin(Date bankReceiveTimeBegin) {
        this.bankReceiveTimeBegin = bankReceiveTimeBegin;
    }

    public Date getBankReceiveTimeEnd() {
        return bankReceiveTimeEnd;
    }

    public void setBankReceiveTimeEnd(Date bankReceiveTimeEnd) {
        this.bankReceiveTimeEnd = bankReceiveTimeEnd;
    }

    @Override
    public String toString() {
        return "ReceiveOrderSearchBean{" +
                "businessType=" + businessType +
                ", customerId=" + customerId +
                ", receiveType=" + receiveType +
                ", receiveTimeBegin=" + receiveTimeBegin +
                ", receiveTimeEnd=" + receiveTimeEnd +
                ", payChannel=" + payChannel +
                ", status=" + status +
                ", bankReceiveTimeBegin=" + bankReceiveTimeBegin +
                ", bankReceiveTimeEnd=" + bankReceiveTimeEnd +
                '}';
    }
}
