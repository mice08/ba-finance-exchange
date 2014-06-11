package com.dianping.ba.finance.exchange.api.dtos;

import com.dianping.ba.finance.exchange.api.enums.RefundFailedReason;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class RefundResultDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, RefundFailedReason> refundFailedMap;
    private BigDecimal refundTotalAmount;

    public RefundResultDTO() {
        this.refundFailedMap = new HashMap<String, RefundFailedReason>();
        this.refundTotalAmount = BigDecimal.ZERO;
    }

    public Map<String, RefundFailedReason> getRefundFailedMap() {
        return refundFailedMap;
    }

    public void setRefundFailedMap(Map<String, RefundFailedReason> refundFailedMap) {
        this.refundFailedMap = refundFailedMap;
    }

    public BigDecimal getRefundTotalAmount() {
        return refundTotalAmount;
    }

    public void setRefundTotalAmount(BigDecimal refundTotalAmount) {
        this.refundTotalAmount = refundTotalAmount;
    }

    public void addRefundAmount(BigDecimal refundAmount) {
        refundTotalAmount = refundTotalAmount.add(refundAmount);
    }

    public void addFailedRefund(String refundId, RefundFailedReason reason) {
        refundFailedMap.put(refundId, reason);
    }

    public void mergeFromOtherResult(RefundResultDTO refundResultDTO) {
        this.refundFailedMap.putAll(refundResultDTO.getRefundFailedMap());
        this.refundTotalAmount = refundTotalAmount.add(refundResultDTO.getRefundTotalAmount());
    }

    public boolean containFailedResult() {
        return !this.refundFailedMap.isEmpty();
    }

    @Override
    public String toString() {
        return "RefundResultDTO{" +
                "refundFailedMap=" + refundFailedMap +
                ", refundTotalAmount=" + refundTotalAmount +
                '}';
    }
}