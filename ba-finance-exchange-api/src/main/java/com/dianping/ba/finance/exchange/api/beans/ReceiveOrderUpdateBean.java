package com.dianping.ba.finance.exchange.api.beans;

import com.dianping.ba.finance.exchange.api.enums.ReceiveType;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Eric on 2014/6/11.
 */
public class ReceiveOrderUpdateBean implements Serializable {

    private int roId;

	private int reverseRoId;

    private int status;

    private ReceiveType receiveType;

    private int customerId;

    private int shopId;

    private String bizContent;

    private String applicationId;

    private Date receiveTime;

	private int updateLoginId;

	private String memo;

    public ReceiveOrderUpdateBean() {
    }

    public int getRoId() {
        return roId;
    }

    public void setRoId(int roId) {
        this.roId = roId;
    }

    public int getReverseRoId() {
        return reverseRoId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public void setReverseRoId(int reverseRoId) {
        this.reverseRoId = reverseRoId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ReceiveType getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(ReceiveType receiveType) {
        this.receiveType = receiveType;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getBizContent() {
        return bizContent;
    }

    public void setBizContent(String bizContent) {
        this.bizContent = bizContent;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public int getUpdateLoginId() {
        return updateLoginId;
    }

    public void setUpdateLoginId(int updateLoginId) {
        this.updateLoginId = updateLoginId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    @Override
    public String toString() {
        return "ReceiveOrderUpdateBean{" +
                "roId=" + roId +
                ", reverseRoId=" + reverseRoId +
                ", status=" + status +
                ", receiveType=" + receiveType +
                ", customerId=" + customerId +
                ", shopId=" + shopId +
                ", bizContent='" + bizContent + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", receiveTime=" + receiveTime +
                ", updateLoginId=" + updateLoginId +
                ", memo='" + memo + '\'' +
                '}';
    }
}
