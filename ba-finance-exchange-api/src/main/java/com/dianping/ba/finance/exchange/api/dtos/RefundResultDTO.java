package com.dianping.ba.finance.exchange.api.dtos;

import com.dianping.ba.finance.exchange.api.beans.GenericResult;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: huajiao.zou
 * Date: 14-2-19
 * Time: 上午9:54
 * To change this template use File | Settings | File Templates.
 */
public class RefundResultDTO extends GenericResult{
    private static final long serialVersionUID = 1L;

    private BigDecimal succeedTotalAmount;
    private BigDecimal failedTotalAmount;

    public BigDecimal getSucceedTotalAmount() {
        return succeedTotalAmount;
    }

    public void setSucceedTotalAmount(BigDecimal succeedTotalAmount) {
        this.succeedTotalAmount = succeedTotalAmount;
    }

    public BigDecimal getFailedTotalAmount() {
        return failedTotalAmount;
    }

    public void setFailedTotalAmount(BigDecimal failedTotalAmount) {
        this.failedTotalAmount = failedTotalAmount;
    }
}