package com.dianping.ba.finance.exchange.siteweb.beans;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by noahshen on 14-6-24.
 */
public class CommonTemplateBean implements Serializable {

    private int formType = 2;

    private String payCode;

    private String customerId = "2200099797";

    private int reservationFlag = 0;

    private String payerAccountNo = "601244708";

    private BigDecimal payAmount;

    private String bankAccountNo;

    private String bankAccountName;

    private int bankAccountType;

    private String subClientNo;

    private String subAccountNo;

    private String subAccountName;

    private String subAccountBankName;

    private String usage;

    private int remitChannel = 7;

    private int notifyReceiver = 0;

    private String phone;

    private String email;

    private String bankCodeAndFullBranchName;

    public CommonTemplateBean() {
    }

    public int getFormType() {
        return formType;
    }

    public void setFormType(int formType) {
        this.formType = formType;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getReservationFlag() {
        return reservationFlag;
    }

    public void setReservationFlag(int reservationFlag) {
        this.reservationFlag = reservationFlag;
    }

    public String getPayerAccountNo() {
        return payerAccountNo;
    }

    public void setPayerAccountNo(String payerAccountNo) {
        this.payerAccountNo = payerAccountNo;
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

    public int getBankAccountType() {
        return bankAccountType;
    }

    public void setBankAccountType(int bankAccountType) {
        this.bankAccountType = bankAccountType;
    }

    public String getSubClientNo() {
        return subClientNo;
    }

    public void setSubClientNo(String subClientNo) {
        this.subClientNo = subClientNo;
    }

    public String getSubAccountNo() {
        return subAccountNo;
    }

    public void setSubAccountNo(String subAccountNo) {
        this.subAccountNo = subAccountNo;
    }

    public String getSubAccountName() {
        return subAccountName;
    }

    public void setSubAccountName(String subAccountName) {
        this.subAccountName = subAccountName;
    }

    public String getSubAccountBankName() {
        return subAccountBankName;
    }

    public void setSubAccountBankName(String subAccountBankName) {
        this.subAccountBankName = subAccountBankName;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public int getRemitChannel() {
        return remitChannel;
    }

    public void setRemitChannel(int remitChannel) {
        this.remitChannel = remitChannel;
    }

    public int getNotifyReceiver() {
        return notifyReceiver;
    }

    public void setNotifyReceiver(int notifyReceiver) {
        this.notifyReceiver = notifyReceiver;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBankCodeAndFullBranchName() {
        return bankCodeAndFullBranchName;
    }

    public void setBankCodeAndFullBranchName(String bankCodeAndFullBranchName) {
        this.bankCodeAndFullBranchName = bankCodeAndFullBranchName;
    }

    @Override
    public String toString() {
        return "CommonTemplateBean{" +
                "formType=" + formType +
                ", payCode='" + payCode + '\'' +
                ", customerId='" + customerId + '\'' +
                ", reservationFlag=" + reservationFlag +
                ", payerAccountNo='" + payerAccountNo + '\'' +
                ", payAmount=" + payAmount +
                ", bankAccountNo='" + bankAccountNo + '\'' +
                ", bankAccountName='" + bankAccountName + '\'' +
                ", bankAccountType=" + bankAccountType +
                ", subClientNo='" + subClientNo + '\'' +
                ", subAccountNo='" + subAccountNo + '\'' +
                ", subAccountName='" + subAccountName + '\'' +
                ", subAccountBankName='" + subAccountBankName + '\'' +
                ", usage='" + usage + '\'' +
                ", remitChannel=" + remitChannel +
                ", notifyReceiver=" + notifyReceiver +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", bankCodeAndFullBranchName='" + bankCodeAndFullBranchName + '\'' +
                '}';
    }
}
