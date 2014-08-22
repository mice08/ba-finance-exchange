package com.dianping.ba.finance.exchange.api.datas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by noahshen on 14-8-21.
 */
public class ReceiveVoucherData implements Serializable {

    private int voucherId;

    private int customerId;

    private int shopId;

    private int cityId;

    private int companyId;

    private Date voucherDate;

    private BigDecimal amount;

    private int bankId;

    private int voucherType;

    private Date addTime;

    private int addLoginId;

    private Date updateTime;

    private int updateLoginId;

    public int getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(int voucherId) {
        this.voucherId = voucherId;
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

    public Date getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(Date voucherDate) {
        this.voucherDate = voucherDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public int getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(int voucherType) {
        this.voucherType = voucherType;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public int getAddLoginId() {
        return addLoginId;
    }

    public void setAddLoginId(int addLoginId) {
        this.addLoginId = addLoginId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getUpdateLoginId() {
        return updateLoginId;
    }

    public void setUpdateLoginId(int updateLoginId) {
        this.updateLoginId = updateLoginId;
    }

    @Override
    public String toString() {
        return "ReceiveVoucherData{" +
                "voucherId=" + voucherId +
                ", customerId=" + customerId +
                ", shopId=" + shopId +
                ", cityId=" + cityId +
                ", companyId=" + companyId +
                ", voucherDate=" + voucherDate +
                ", amount=" + amount +
                ", bankId=" + bankId +
                ", voucherType=" + voucherType +
                ", addTime=" + addTime +
                ", addLoginId=" + addLoginId +
                ", updateTime=" + updateTime +
                ", updateLoginId=" + updateLoginId +
                '}';
    }
}
