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
    private BigDecimal payAmount;
    private String bankAccountNo;
    private String bankAccountName;
    private String bankFullBranchName;
    private String status;
    private String memo;
    private String paidDate;

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

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
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

    public String getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(String paidDate) {
        this.paidDate = paidDate;
    }
}
