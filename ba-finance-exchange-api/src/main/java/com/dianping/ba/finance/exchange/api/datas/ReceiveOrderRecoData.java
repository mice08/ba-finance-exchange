package com.dianping.ba.finance.exchange.api.datas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by will on 14-8-18.
 */
public class ReceiveOrderRecoData implements Serializable {

    private int id;

    private String batchId;

    private int customerId;

    private int shopId;

    private int type;

    private BigDecimal receiveAmount;

    private Date receiveTime;

    private int payChannel;

    private int receiveType;

    private String bizContent;

    private String tradeNo;

    private int bankID;

    private Date addTime;

    private Date updateTime;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public BigDecimal getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(BigDecimal receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
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

    public String getBizContent() {
        return bizContent;
    }

    public void setBizContent(String bizContent) {
        this.bizContent = bizContent;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public int getBankID() {
        return bankID;
    }

    public void setBankID(int bankID) {
        this.bankID = bankID;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    @Override
    public String toString() {
        return "ReceiveOrderRecoData{" +
                "id=" + id +
                ", batchId='" + batchId + '\'' +
                ", customerId=" + customerId +
                ", shopId=" + shopId +
                ", type=" + type +
                ", receiveAmount=" + receiveAmount +
                ", receiveTime=" + receiveTime +
                ", payChannel=" + payChannel +
                ", receiveType=" + receiveType +
                ", bizContent='" + bizContent + '\'' +
                ", tradeNo='" + tradeNo + '\'' +
                ", bankID=" + bankID +
                ", addTime=" + addTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
