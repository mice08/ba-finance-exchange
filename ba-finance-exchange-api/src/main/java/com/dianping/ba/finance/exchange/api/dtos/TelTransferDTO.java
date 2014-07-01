package com.dianping.ba.finance.exchange.api.dtos;

import java.io.Serializable;

/**
 *
 */
public class TelTransferDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String bankFlowId;
    private String bankReceiveDate;
    private String bankTradeType;
    private String amount;
    private String payerAccountName;
    private String payerAccountNo;
    private String payerBankName;
    private String memo;

    public TelTransferDTO() {
    }

    public String getBankFlowId() {
        return bankFlowId;
    }

    public void setBankFlowId(String bankFlowId) {
        this.bankFlowId = bankFlowId;
    }

    public String getBankReceiveDate() {
        return bankReceiveDate;
    }

    public void setBankReceiveDate(String bankReceiveDate) {
        this.bankReceiveDate = bankReceiveDate;
    }

    public String getBankTradeType() {
        return bankTradeType;
    }

    public void setBankTradeType(String bankTradeType) {
        this.bankTradeType = bankTradeType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPayerAccountName() {
        return payerAccountName;
    }

    public void setPayerAccountName(String payerAccountName) {
        this.payerAccountName = payerAccountName;
    }

    public String getPayerAccountNo() {
        return payerAccountNo;
    }

    public void setPayerAccountNo(String payerAccountNo) {
        this.payerAccountNo = payerAccountNo;
    }

    public String getPayerBankName() {
        return payerBankName;
    }

    public void setPayerBankName(String payerBankName) {
        this.payerBankName = payerBankName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "TelTransferDTO{" +
                "bankFlowId='" + bankFlowId + '\'' +
                ", bankReceiveDate='" + bankReceiveDate + '\'' +
                ", bankTradeType='" + bankTradeType + '\'' +
                ", amount=" + amount +
                ", payerAccountName='" + payerAccountName + '\'' +
                ", payerAccountNo='" + payerAccountNo + '\'' +
                ", payerBankName='" + payerBankName + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }
}
