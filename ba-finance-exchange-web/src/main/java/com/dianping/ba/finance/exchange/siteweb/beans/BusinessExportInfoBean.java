package com.dianping.ba.finance.exchange.siteweb.beans;


import com.dianping.ba.finance.exchange.api.enums.BusinessType;

/**
 *
 */
public class BusinessExportInfoBean {

    // 业务类型
    private BusinessType businessType;
    //币种，默认“人民币”
    private String currency = "人民币";
    //付款分行，默认“上海”
    private String payerBranchBank = "上海";
    //结算方式，默认“普通”
    private String settleType = "普通";
    //付方帐号，默认“121909245310505” 团购
    private String payerAccountNo = "";
    //期望日，默认当天
    private String expectedDate;
    //期望时间，默认空
    private String expectedTime = "";
    //用途，期望“合同号+商户名+大众点评”，暂时简单处理，默认“大众点评”
    private String use = "";
    //收方联行号，默认空
    private String debitSideBankNo = "";
    //收方银行，默认空
    private String debitSideBankName = "";
    //业务摘要，默认‘大众点评网储值卡’,以后团购迁移过来后会改掉
    private String businessSummary = "";

    public BusinessExportInfoBean() {
    }

    public BusinessType getBusinessType() {
        return businessType;
    }

    public void setBusinessType(BusinessType businessType) {
        this.businessType = businessType;
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
        return "ExchangeOrderExportInfoBean{" +
                "businessType=" + businessType +
                ", currency='" + currency + '\'' +
                ", payerBranchBank='" + payerBranchBank + '\'' +
                ", settleType='" + settleType + '\'' +
                ", payerAccountNo='" + payerAccountNo + '\'' +
                ", expectedDate='" + expectedDate + '\'' +
                ", expectedTime='" + expectedTime + '\'' +
                ", use='" + use + '\'' +
                ", debitSideBankNo='" + debitSideBankNo + '\'' +
                ", debitSideBankName='" + debitSideBankName + '\'' +
                ", businessSummary='" + businessSummary + '\'' +
                '}';
    }
}
