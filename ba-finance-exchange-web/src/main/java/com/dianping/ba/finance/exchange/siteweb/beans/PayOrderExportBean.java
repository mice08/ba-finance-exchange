package com.dianping.ba.finance.exchange.siteweb.beans;

import java.math.BigDecimal;

/**
 * Description：
 * User: liufeng.ren
 * Date: 14-6-11
 * Time: 下午1:51
 */
public class PayOrderExportBean {

    private int poId;
    /**
     * 凭证号
     */
	private String payCode;

    private BigDecimal payAmount;

    private String bankAccountNo;

    private String bankAccountName;

    private String bankName;

    private String bankProvince;

    private String bankCity;

    private String bankBranchName;

    private String bankFullBranchName;

    private String bankCode;

    private int bankAccountType;

    /**
     * 业务类型
     */
    private int businessType;

    private String memo;

    public PayOrderExportBean() {
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

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "PayOrderExportBean{" +
                "poId=" + poId +
                ", payCode='" + payCode + '\'' +
                ", payAmount=" + payAmount +
                ", bankAccountNo='" + bankAccountNo + '\'' +
                ", bankAccountName='" + bankAccountName + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankProvince='" + bankProvince + '\'' +
                ", bankCity='" + bankCity + '\'' +
                ", bankBranchName='" + bankBranchName + '\'' +
                ", bankFullBranchName='" + bankFullBranchName + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", bankAccountType=" + bankAccountType +
                ", businessType=" + businessType +
                ", memo='" + memo + '\'' +
                '}';
    }
}
