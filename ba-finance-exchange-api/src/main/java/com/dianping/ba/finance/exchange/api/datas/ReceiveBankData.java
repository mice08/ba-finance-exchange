package com.dianping.ba.finance.exchange.api.datas;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by noahshen on 14-6-17.
 */
public class ReceiveBankData implements Serializable {

    private int bankId;

    private String bankName;

    private Date addTime;

    private int companyId;

    private int businessType;

    public ReceiveBankData() {
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    @Override
    public String toString() {
        return "ReceiveBankData{" +
                "bankId=" + bankId +
                ", bankName='" + bankName + '\'' +
                ", addTime=" + addTime +
                ", companyId=" + companyId +
                ", businessType=" + businessType +
                '}';
    }
}
