package com.dianping.ba.finance.exchange.api.dtos;

import com.dianping.ba.finance.exchange.api.beans.GenericResult;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: huajiao.zou
 * Date: 14-2-19
 * Time: 上午9:54
 * To change this template use File | Settings | File Templates.
 */
public class RefundResultDTO implements Serializable{
    private static final long serialVersionUID = 1L;

    private Map<String,String> refundFailedMap;
    private BigDecimal refundTotalAmount;

    public RefundResultDTO() {
        this.refundFailedMap = new HashMap<String, String>();
        this.refundTotalAmount = BigDecimal.ZERO;
    }

    public Map<String, String> getRefundFailedMap() {
        return refundFailedMap;
    }

    public void setRefundFailedMap(Map<String, String> refundFailedMap) {
        this.refundFailedMap = refundFailedMap;
    }

    public BigDecimal getRefundTotalAmount() {
        return refundTotalAmount;
    }

    public void setRefundTotalAmount(BigDecimal refundTotalAmount) {
        this.refundTotalAmount = refundTotalAmount;
    }
}