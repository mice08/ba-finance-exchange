package com.dianping.ba.finance.exchange.api.datas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by noahshen on 14-8-21.
 */
public class ReceiveCalResultData implements Serializable {

    private int customerId;

    private int shopId;

    private int businessType;

    private BigDecimal totalAmount;

    private int payChannel;

    private int receiveType;

    private int bankId;

    private Date voucherDate;

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

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(int payChannel) {
        this.payChannel = payChannel;
    }

    public int getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(int receiveType) {
        this.receiveType = receiveType;
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

    @Override
    public String toString() {
        return "ReceiveCalResultData{" +
                "customerId=" + customerId +
                ", shopId=" + shopId +
                ", businessType=" + businessType +
                ", totalAmount=" + totalAmount +
                ", payChannel=" + payChannel +
                ", receiveType=" + receiveType +
                ", bankId=" + bankId +
                ", voucherDate=" + voucherDate +
                '}';
    }
}
