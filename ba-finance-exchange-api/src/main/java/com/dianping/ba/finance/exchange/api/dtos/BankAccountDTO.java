package com.dianping.ba.finance.exchange.api.dtos;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by will on 15-3-9.
 */
public class BankAccountDTO implements Serializable {
    private int id;
    private String bankAccountNo;
    private String bankAccountName;
    private int bankAccountType;
    private int bankBranchId;
    private int accountId;
    private int paymentStatus;
    private int status;
    private Date addTime;
    private int addLoginId;
    private String memo;

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getAddLoginId() {
        return addLoginId;
    }

    public void setAddLoginId(int addLoginId) {
        this.addLoginId = addLoginId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getBankBranchId() {
        return bankBranchId;
    }

    public void setBankBranchId(int bankBranchId) {
        this.bankBranchId = bankBranchId;
    }

    public int getBankAccountType() {
        return bankAccountType;
    }

    public void setBankAccountType(int bankAccountType) {
        this.bankAccountType = bankAccountType;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
