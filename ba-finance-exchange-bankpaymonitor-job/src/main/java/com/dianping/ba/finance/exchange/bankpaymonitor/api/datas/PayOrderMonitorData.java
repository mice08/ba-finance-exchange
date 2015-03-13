package com.dianping.ba.finance.exchange.bankpaymonitor.api.datas;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by adam.huang on 2014/8/7.
 */
public class PayOrderMonitorData implements Serializable {
    private int poId;
    private int status;
    private BigDecimal payAmount;
    private String bankAccountNo;
    private String payBankAccountNo;
    private String payCode;


    public int getPoId() {
        return poId;
    }

    public void setPoId(int poId) {
        this.poId = poId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PayOrderMonitorData{" +
                "poId=" + poId +
                ", status=" + status +
                ", payAmount=" + payAmount +
                ", bankAccountNo=" + bankAccountNo +
                ", payBankAccountNo=" + payBankAccountNo +
                ", payCode=" + payCode +
                '}';
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

    public String getPayBankAccountNo() {
        return payBankAccountNo;
    }

    public void setPayBankAccountNo(String payBankAccountNo) {
        this.payBankAccountNo = payBankAccountNo;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }
}
