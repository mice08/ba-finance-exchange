package com.dianping.ba.finance.exchange.api.datas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by noahshen on 14-6-16.
 */
public class ReceiveOrderData implements Serializable{

    private int roId;

    private int customerId;

    private int shopId;

    private int businessType;

    private BigDecimal receiveAmount;

    private Date receiveTime;

    private Date payTime;

    private Date bankReceiveTime;

    private int payChannel;

    private int receiveType;

    private String bizContent;

    private String tradeNo;

    private int bankID;

    private int status;

    private Date addTime;

    private int addLoginId;

    private Date updateTime;

	private int reverseRoId;

    private String applicationId;

    private int updateLoginId;

    private String memo;

    private String payerAccountName;

    private String payerAccountNo;

    private String payerBankName;

    public ReceiveOrderData() {
    }

    public int getRoId() {
        return roId;
    }

    public void setRoId(int roId) {
        this.roId = roId;
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

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
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

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getBankReceiveTime() {
        return bankReceiveTime;
    }

    public void setBankReceiveTime(Date bankReceiveTime) {
        this.bankReceiveTime = bankReceiveTime;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

	public int getReverseRoId() {
		return reverseRoId;
	}

	public void setReverseRoId(int reverseRoId) {
		this.reverseRoId = reverseRoId;
	}

    public String getPayerAccountName() {
        return payerAccountName;
    }

    public void setPayerAccountName(String payerAccountName) {
        this.payerAccountName = payerAccountName;
    }

    public String getPayerAccountNo() {
        return payerAccountNo;
    }

    public void setPayerAccountNo(String payerAccountNo) {
        this.payerAccountNo = payerAccountNo;
    }

    public String getPayerBankName() {
        return payerBankName;
    }

    public void setPayerBankName(String payerBankName) {
        this.payerBankName = payerBankName;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    @Override
    public String toString() {
        return "ReceiveOrderData{" +
                "roId=" + roId +
                ", customerId=" + customerId +
                ", shopId=" + shopId +
                ", businessType=" + businessType +
                ", receiveAmount=" + receiveAmount +
                ", receiveTime=" + receiveTime +
                ", payTime=" + payTime +
                ", bankReceiveTime=" + bankReceiveTime +
                ", payChannel=" + payChannel +
                ", receiveType=" + receiveType +
                ", bizContent='" + bizContent + '\'' +
                ", tradeNo='" + tradeNo + '\'' +
                ", bankID=" + bankID +
                ", status=" + status +
                ", addTime=" + addTime +
                ", addLoginId=" + addLoginId +
                ", updateTime=" + updateTime +
                ", reverseRoId=" + reverseRoId +
                ", applicationId='" + applicationId + '\'' +
                ", updateLoginId=" + updateLoginId +
                ", memo='" + memo + '\'' +
                ", payerAccountName='" + payerAccountName + '\'' +
                ", payerAccountNo='" + payerAccountNo + '\'' +
                ", payerBankName='" + payerBankName + '\'' +
                '}';
    }
}
