package com.dianping.ba.finance.exchange.api.dtos;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 */
public class PayRequestResultDTO implements Serializable {

    private static final long serialVersionUID = -1L;

    private String paySequence;
    private int status;
    private int poId;
    private BigDecimal payAmount;
    private String memo;

    public String getPaySequence() {
        return paySequence;
    }

    public void setPaySequence(String paySequence) {
        this.paySequence = paySequence;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "PayRequestResultDTO{" +
                "paySequence='" + paySequence + '\'' +
                ", status=" + status +
                ", poId=" + poId +
                ", payAmount=" + payAmount +
                ", memo='" + memo + '\'' +
                '}';
    }
}
