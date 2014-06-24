package com.dianping.ba.finance.exchange.siteweb.beans;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by noahshen on 14-6-24.
 */
public class SameBankPersonalTemplateBean implements Serializable {

    private String bankAccountNo;

    private BigDecimal payAmount;

    private String bankAccountName;

    public SameBankPersonalTemplateBean() {
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    @Override
    public String toString() {
        return "SameBankPersonalTemplateBean{" +
                "bankAccountNo='" + bankAccountNo + '\'' +
                ", payAmount=" + payAmount +
                ", bankAccountName='" + bankAccountName + '\'' +
                '}';
    }
}
