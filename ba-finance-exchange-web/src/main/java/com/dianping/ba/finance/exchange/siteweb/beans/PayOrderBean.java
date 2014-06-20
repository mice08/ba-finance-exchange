package com.dianping.ba.finance.exchange.siteweb.beans;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Eric on 2014/6/11.
 */
public class PayOrderBean {

    private String payCode;
    private String customerName;
    private int poId;
    private String addTime;
    private String payAmount;
    private String bankAccountNo;
    private String bankAccountName;
    private String bankFullBranchName;
    private int status;
    private String statusDesc;
    private String sendBackTime;
    private String memo;
    private String paidDate;
    private int queryStatus;

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getPoId() {
        return poId;
    }

    public void setPoId(int poId) {
        this.poId = poId;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getBankFullBranchName() {
        return bankFullBranchName;
    }

    public void setBankFullBranchName(String bankFullBranchName) {
        this.bankFullBranchName = bankFullBranchName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(String paidDate) {
        this.paidDate = paidDate;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public int getQueryStatus() {
        return queryStatus;
    }

    public void setQueryStatus(int queryStatus) {
        this.queryStatus = queryStatus;
    }

    public String getSendBackTime() {
        return sendBackTime;
    }

    public void setSendBackTime(String sendBackTime) {
        this.sendBackTime = sendBackTime;
    }
}
