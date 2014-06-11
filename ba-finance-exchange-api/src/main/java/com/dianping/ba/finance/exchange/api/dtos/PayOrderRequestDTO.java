package com.dianping.ba.finance.exchange.api.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 付款计划请求
 */
public class PayOrderRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String paySequence;

    private BigDecimal payAmount;

    private int loginId;

    private Date requestDate;

    private int businessType;

    private int customerBankId;

    private String bankAccountNo;

    private String bankAccountName;

    private String bankName;

    private String bankProvince;

    private String bankCity;

    private String bankBranchName;
    // 人行支付行全称
    private String bankFullBranchName;
    //联行号
    private String bankCode;

    private String memo;

    public String getPaySequence() {
        return paySequence;
    }

    public void setPaySequence(String paySequence) {
        this.paySequence = paySequence;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public int getLoginId() {
        return loginId;
    }

    public void setLoginId(int loginId) {
        this.loginId = loginId;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankProvince() {
        return bankProvince;
    }

    public void setBankProvince(String bankProvince) {
        this.bankProvince = bankProvince;
    }

    public String getBankCity() {
        return bankCity;
    }

    public void setBankCity(String bankCity) {
        this.bankCity = bankCity;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getCustomerBankId() {
        return customerBankId;
    }

    public void setCustomerBankId(int customerBankId) {
        this.customerBankId = customerBankId;
    }

    public String getBankBranchName() {
        return bankBranchName;
    }

    public void setBankBranchName(String bankBranchName) {
        this.bankBranchName = bankBranchName;
    }

    public String getBankFullBranchName() {
        return bankFullBranchName;
    }

    public void setBankFullBranchName(String bankFullBranchName) {
        this.bankFullBranchName = bankFullBranchName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    @Override
    public String toString() {
        return "PayOrderRequestDTO{" +
                "paySequence='" + paySequence + '\'' +
                ", payAmount=" + payAmount +
                ", requestDate=" + requestDate +
                ", businessType=" + businessType +
                ", customerBankId=" + customerBankId +
                ", bankAccountNo='" + bankAccountNo + '\'' +
                ", bankAccountName='" + bankAccountName + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankProvince='" + bankProvince + '\'' +
                ", bankCity='" + bankCity + '\'' +
                ", bankBranchName='" + bankBranchName + '\'' +
                ", bankFullBranchName='" + bankFullBranchName + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }
}
