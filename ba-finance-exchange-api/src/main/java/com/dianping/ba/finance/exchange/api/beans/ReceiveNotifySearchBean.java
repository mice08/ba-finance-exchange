package com.dianping.ba.finance.exchange.api.beans;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by adam.huang on 2014/7/29.
 */
public class ReceiveNotifySearchBean {
    private int businessType;
    private int customerId;
    private Date payTimeBegin;
    private Date payTimeEnd;
    private int payChannel;
    private BigDecimal receiveAmount;
    private int companyId;
    private int status;

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

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Date getPayTimeBegin() {
        return payTimeBegin;
    }

    public void setPayTimeBegin(Date payTimeBegin) {
        this.payTimeBegin = payTimeBegin;
    }

    public Date getPayTimeEnd() {
        return payTimeEnd;
    }

    public void setPayTimeEnd(Date payTimeEnd) {
        this.payTimeEnd = payTimeEnd;
    }

    public int getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(int payChannel) {
        this.payChannel = payChannel;
    }

    public BigDecimal getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(BigDecimal receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }


    @Override
    public String toString() {
        return "ReceiveNotifySearchBean{" +
                "businessType=" + businessType +
                ", customerId=" + customerId +
                ", payTimeBegin=" + payTimeBegin +
                ", payTimeEnd=" + payTimeEnd +
                ", payChannel=" + payChannel +
                ", receiveAmount=" + receiveAmount +
                ", companyId=" + companyId +
                ", status=" + status +
                '}';
    }
}
