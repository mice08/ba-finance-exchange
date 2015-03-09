package com.dianping.ba.finance.exchange.api.dtos;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by 遐 on 2015/3/3.
 */
public class BankPayRequestDTO implements Serializable {

    private String insId;
    private String accountNo;
    private String accountName;
    private String accountToNo;
    private String accountToName;
    private int bankId;
    private int bankToId;
    private int accountType;
    private int accountToType;
    private String bankCode;
    private String bankBranchCode;
    private String bankName;
    private BigDecimal amount;
    private String description;

    public String getBankBranchCode() {
        return bankBranchCode;
    }

    public void setBankBranchCode(String bankBranchCode) {
        this.bankBranchCode = bankBranchCode;
    }

    public int getAccountToType() {
        return accountToType;
    }

    public void setAccountToType(int accountToType) {
        this.accountToType = accountToType;
    }
    public String getInsId() {
        return insId;
    }

    public void setInsId(String insId) {
        this.insId = insId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountToNo() {
        return accountToNo;
    }

    public void setAccountToNo(String accountToNo) {
        this.accountToNo = accountToNo;
    }

    public String getAccountToName() {
        return accountToName;
    }

    public void setAccountToName(String accountToName) {
        this.accountToName = accountToName;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public int getBankToId() {
        return bankToId;
    }

    public void setBankToId(int bankToId) {
        this.bankToId = bankToId;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
