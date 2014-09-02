package com.dianping.ba.finance.exchange.siteweb.beans;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by noahshen on 14-7-14.
 */
public class MerchantsTemplateBean implements Serializable {

    private int poId;

    private String payCode;
    //收款人编号，默认空
    private String debitSideId = "";
    //收款人帐号
    private String bankAccountNo;
    //收款人名称
    private String bankAccountName;
    //收方开户支行
    private String bankName;
    //收款人所在省
    private String bankProvince;
    //收款人所在市
    private String bankCity;
    //收方邮件地址，默认空
    private String debitSideEmail = "";
    //币种，默认“人民币”
    private String currency = "人民币";
    //付款分行，默认“上海”
    private String payerBranchBank = "上海";
    //结算方式，默认“普通”
    private String settleType = "普通";
    //付方帐号，默认“121909245310505”
    private String payerAccountNo = "";
    //期望日，默认当天
    private String expectedDate;
    //期望时间，默认空
    private String expectedTime = "";
    //用途，期望“合同号+商户名+大众点评”，暂时简单处理，默认“大众点评”
    private String use = "大众点评网";
    //金额
    private BigDecimal orderAmount;
    //收方联行号，默认空
    private String debitSideBankNo = "";
    //收方银行，默认空
    private String debitSideBankName = "";
    //业务摘要，默认‘大众点评网储值卡’,以后团购迁移过来后会改掉
    private String businessSummary = "";

    public MerchantsTemplateBean() {
    }

    public int getPoId() {
        return poId;
    }

    public void setPoId(int poId) {
        this.poId = poId;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getDebitSideId() {
        return debitSideId;
    }

    public void setDebitSideId(String debitSideId) {
        this.debitSideId = debitSideId;
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

    public String getDebitSideEmail() {
        return debitSideEmail;
    }

    public void setDebitSideEmail(String debitSideEmail) {
        this.debitSideEmail = debitSideEmail;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPayerBranchBank() {
        return payerBranchBank;
    }

    public void setPayerBranchBank(String payerBranchBank) {
        this.payerBranchBank = payerBranchBank;
    }

    public String getSettleType() {
        return settleType;
    }

    public void setSettleType(String settleType) {
        this.settleType = settleType;
    }

    public String getPayerAccountNo() {
        return payerAccountNo;
    }

    public void setPayerAccountNo(String payerAccountNo) {
        this.payerAccountNo = payerAccountNo;
    }

    public String getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(String expectedDate) {
        this.expectedDate = expectedDate;
    }

    public String getExpectedTime() {
        return expectedTime;
    }

    public void setExpectedTime(String expectedTime) {
        this.expectedTime = expectedTime;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getDebitSideBankNo() {
        return debitSideBankNo;
    }

    public void setDebitSideBankNo(String debitSideBankNo) {
        this.debitSideBankNo = debitSideBankNo;
    }

    public String getDebitSideBankName() {
        return debitSideBankName;
    }

    public void setDebitSideBankName(String debitSideBankName) {
        this.debitSideBankName = debitSideBankName;
    }

    public String getBusinessSummary() {
        return businessSummary;
    }

    public void setBusinessSummary(String businessSummary) {
        this.businessSummary = businessSummary;
    }

    @Override
    public String toString() {
        return "MerchantsTemplateBean{" +
                "poId=" + poId +
                ", payCode='" + payCode + '\'' +
                ", debitSideId='" + debitSideId + '\'' +
                ", bankAccountNo='" + bankAccountNo + '\'' +
                ", bankAccountName='" + bankAccountName + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankProvince='" + bankProvince + '\'' +
                ", bankCity='" + bankCity + '\'' +
                ", debitSideEmail='" + debitSideEmail + '\'' +
                ", currency='" + currency + '\'' +
                ", payerBranchBank='" + payerBranchBank + '\'' +
                ", settleType='" + settleType + '\'' +
                ", payerAccountNo='" + payerAccountNo + '\'' +
                ", expectedDate='" + expectedDate + '\'' +
                ", expectedTime='" + expectedTime + '\'' +
                ", use='" + use + '\'' +
                ", orderAmount=" + orderAmount +
                ", debitSideBankNo='" + debitSideBankNo + '\'' +
                ", debitSideBankName='" + debitSideBankName + '\'' +
                ", businessSummary='" + businessSummary + '\'' +
                '}';
    }
}
