package com.dianping.ba.finance.exchange.api.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 */
public class ReceiveVoucherNotifyDTO implements Serializable{

    private String bizId;

    private int customerId;

    private int shopId;

    private int cityId;

    private int companyId;

    private int bankId;

    private Date voucherDate;

    private int voucherType;

    private BigDecimal amount;

    private String bizInfo;

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public Date getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(Date voucherDate) {
        this.voucherDate = voucherDate;
    }

    public int getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(int voucherType) {
        this.voucherType = voucherType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBizInfo() {
        return bizInfo;
    }

    public void setBizInfo(String bizInfo) {
        this.bizInfo = bizInfo;
    }

    @Override
    public String toString() {
        return "ReceiveVoucherNotifyDTO{" +
                "bizId='" + bizId + '\'' +
                ", customerId=" + customerId +
                ", shopId=" + shopId +
                ", cityId=" + cityId +
                ", companyId=" + companyId +
                ", bankId=" + bankId +
                ", voucherDate=" + voucherDate +
                ", voucherType=" + voucherType +
                ", amount=" + amount +
                ", bizInfo='" + bizInfo + '\'' +
                '}';
    }
}
