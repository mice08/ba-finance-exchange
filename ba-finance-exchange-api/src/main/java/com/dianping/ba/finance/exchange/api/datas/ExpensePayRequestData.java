package com.dianping.ba.finance.exchange.api.datas;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by adam.huang on 2014/9/29.
 */
public class ExpensePayRequestData implements Serializable {
    private String paySequence;
    private BigDecimal totalPayAmount;

    public String getPaySequence() {
        return paySequence;
    }

    public void setPaySequence(String paySequence) {
        this.paySequence = paySequence;
    }

    public BigDecimal getTotalPayAmount() {
        return totalPayAmount;
    }

    public void setTotalPayAmount(BigDecimal totalPayAmount) {
        this.totalPayAmount = totalPayAmount;
    }

    @Override
    public String toString() {
        return "ExpensePayOrderDTO{" +
                "paySequence='" + paySequence + '\'' +
                ", totalPayAmount=" + totalPayAmount +
                '}';
    }
}
