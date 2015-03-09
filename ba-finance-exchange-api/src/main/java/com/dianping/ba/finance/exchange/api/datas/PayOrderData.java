package com.dianping.ba.finance.exchange.api.datas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 付款单
 */
public class PayOrderData implements Serializable {

    private static final long serialVersionUID = -1L;
    /**
     * 主键
     */
    private int poId;
    /**
     * 付款金额
     */
    private BigDecimal payAmount;
    /**
     * 业务类型 1团购2：预定3：结婚4：储值卡
     */
    private int businessType;

    private int payType;

    /**
     * 交易状态；1初始；2导出支付中; 3成功；4失败
     */
    private int status;
    /**
     * 添加时间
     */
    private Date addTime;
    /**
     * 付款成功时间
     */
    private Date paidDate;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 备注
     */
    private String memo;

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

    private int addLoginId;

    private int updateLoginId;

    /**
     * 添加类型：1系统2人工
     */
    private int addType;

    private String payeeName;

    private String payBankName;

    private String payBankAccountNo;

    public int getPoId() {
        return poId;
    }

    public void setPoId(int poId) {
        this.poId = poId;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getBankCity() {
        return bankCity;
    }

    public void setBankCity(String bankCity) {
        this.bankCity = bankCity;
    }

    public String getBankProvince() {
        return bankProvince;
    }

    public void setBankProvince(String bankProvince) {
        this.bankProvince = bankProvince;
    }

    public String getPaySequence() {
        return paySequence;
    }

    public void setPaySequence(String paySequence) {
        this.paySequence = paySequence;
    }

    public int getAddLoginId() {
        return addLoginId;
    }

    public void setAddLoginId(int addLoginId) {
        this.addLoginId = addLoginId;
    }

    public int getUpdateLoginId() {
        return updateLoginId;
    }

    public void setUpdateLoginId(int updateLoginId) {
        this.updateLoginId = updateLoginId;
    }

    public int getAddType() {
        return addType;
    }

    public void setAddType(int addType) {
        this.addType = addType;
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

    public int getBankAccountType() {
        return bankAccountType;
    }

    public void setBankAccountType(int bankAccountType) {
        this.bankAccountType = bankAccountType;
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

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getPayBankName() {
        return payBankName;
    }

    public void setPayBankName(String payBankName) {
        this.payBankName = payBankName;
    }

    public String getPayBankAccountNo() {
        return payBankAccountNo;
    }

    public void setPayBankAccountNo(String payBankAccountNo) {
        this.payBankAccountNo = payBankAccountNo;
    }

    @Override
    public String toString() {
        return "PayOrderData{" +
                "poId=" + poId +
                ", payAmount=" + payAmount +
                ", businessType=" + businessType +
                ", payType=" + payType +
                ", status=" + status +
                ", addTime=" + addTime +
                ", paidDate=" + paidDate +
                ", updateTime=" + updateTime +
                ", memo='" + memo + '\'' +
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
                ", addLoginId=" + addLoginId +
                ", updateLoginId=" + updateLoginId +
                ", addType=" + addType +
                ", payeeName='" + payeeName + '\'' +
                ", payBankName='" + payBankName + '\'' +
                ", payBankAccountNo='" + payBankAccountNo + '\'' +
                '}';
    }
}
