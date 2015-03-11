package com.dianping.ba.finance.exchange.siteweb.beans;


import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by noahshen on 14-11-17.
 */
public class GuaranteeInfoBean implements Serializable {

    private int customerId;

    private int guaranteeId;

    private BigDecimal amount;

    private BigDecimal returnAmount;

    private BigDecimal leftAmount;

    private String guaranteeBillId;

    public GuaranteeInfoBean() {
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getGuaranteeId() {
        return guaranteeId;
    }

    public void setGuaranteeId(int guaranteeId) {
        this.guaranteeId = guaranteeId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(BigDecimal returnAmount) {
        this.returnAmount = returnAmount;
    }

    public BigDecimal getLeftAmount() {
        return leftAmount;
    }

    public void setLeftAmount(BigDecimal leftAmount) {
        this.leftAmount = leftAmount;
    }

    public String getGuaranteeBillId() {
        return guaranteeBillId;
    }

    public void setGuaranteeBillId(String guaranteeBillId) {
        this.guaranteeBillId = guaranteeBillId;
    }

    @Override
    public String toString() {
        return "GuaranteeInfoBean{" +
                "customerId=" + customerId +
                ", guaranteeId=" + guaranteeId +
                ", amount=" + amount +
                ", returnAmount=" + returnAmount +
                ", leftAmount=" + leftAmount +
                ", guaranteeBillId='" + guaranteeBillId + '\'' +
                '}';
    }
}
