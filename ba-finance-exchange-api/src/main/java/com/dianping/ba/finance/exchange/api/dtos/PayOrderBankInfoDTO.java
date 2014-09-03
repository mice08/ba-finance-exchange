package com.dianping.ba.finance.exchange.api.dtos;

import java.io.Serializable;

/**
 * Created by noahshen on 14-9-2.
 */
public class PayOrderBankInfoDTO implements Serializable {

    private int poId;

    private String paySequence;

    private String payCode;

    private int customerId;

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

    private int bankAccountType;

    public PayOrderBankInfoDTO() {
    }

    public int getPoId() {
        return poId;
    }

    public void setPoId(int poId) {
        this.poId = poId;
    }

    public String getPaySequence() {
        return paySequence;
    }

    public void setPaySequence(String paySequence) {
        this.paySequence = paySequence;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCustomerBankId() {
        return customerBankId;
    }

    public void setCustomerBankId(int customerBankId) {
        this.customerBankId = customerBankId;
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

    public int getBankAccountType() {
        return bankAccountType;
    }

    public void setBankAccountType(int bankAccountType) {
        this.bankAccountType = bankAccountType;
    }

    @Override
    public String toString() {
        return "PayOrderBankInfoDTO{" +
                "poId=" + poId +
                ", paySequence='" + paySequence + '\'' +
                ", payCode='" + payCode + '\'' +
                ", customerId=" + customerId +
                ", customerBankId=" + customerBankId +
                ", bankAccountNo='" + bankAccountNo + '\'' +
                ", bankAccountName='" + bankAccountName + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankProvince='" + bankProvince + '\'' +
                ", bankCity='" + bankCity + '\'' +
                ", bankBranchName='" + bankBranchName + '\'' +
                ", bankFullBranchName='" + bankFullBranchName + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", bankAccountType=" + bankAccountType +
                '}';
    }
}
