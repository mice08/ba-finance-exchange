package com.dianping.ba.finance.exchange.siteweb.beans;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: yaxiong.cheng
 * Date: 2014/7/29
 * Time: 14:54
 * To change this template use File | Settings | File Templates.
 * 返回给前端页面的收款通知
 */
public class ReceiveNotifyBean implements Serializable{
	private int receiveNotifyId;
	private String applicationId;
	private String businessType;
	private String receiveAmount;
	private String payChannel;
	private String receiveType;
	private String payTime;
	private String payerName;
	private String bizContent;
	private String customerId;
	private String bankId;
	private String attachment;
	private String status;
	private String memo;
	private int roMatchId;

    public int getReceiveNotifyId() {
        return receiveNotifyId;
    }

    public void setReceiveNotifyId(int receiveNotifyId) {
        this.receiveNotifyId = receiveNotifyId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public int getRoMatchId() {
		return roMatchId;
	}

	public void setRoMatchId(int roMatchId) {
		this.roMatchId = roMatchId;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

    public String getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(String receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public String getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getBizContent() {
		return bizContent;
	}

	public void setBizContent(String bizContent) {
		this.bizContent = bizContent;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

    @Override
    public String toString() {
        return "ReceiveNotifyBean{" +
                "receiveNotifyId=" + receiveNotifyId +
                ", applicationId='" + applicationId + '\'' +
                ", businessType='" + businessType + '\'' +
                ", receiveAmount=" + receiveAmount +
                ", payChannel='" + payChannel + '\'' +
                ", receiveType='" + receiveType + '\'' +
                ", payTime='" + payTime + '\'' +
                ", payerName='" + payerName + '\'' +
                ", bizContent='" + bizContent + '\'' +
                ", customerId='" + customerId + '\'' +
                ", bankId='" + bankId + '\'' +
                ", attachment='" + attachment + '\'' +
                ", status='" + status + '\'' +
                ", memo='" + memo + '\'' +
                ", roMatchId=" + roMatchId +
                '}';
    }
}
