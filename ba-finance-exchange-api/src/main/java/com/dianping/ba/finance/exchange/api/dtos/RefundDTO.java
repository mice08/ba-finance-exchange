package com.dianping.ba.finance.exchange.api.dtos;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: huajiao.zou
 * Date: 14-2-18
 * Time: 下午4:24
 * To change this template use File | Settings | File Templates.
 */
public class RefundDTO implements Serializable {

   private static final long serialVersionUID = 1L;
    private String refundId;
    private String refundReason;

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    @Override
    public String toString() {
        return "RefundDTO{" +
                "refundId='" + refundId + '\'' +
                ", refundReason='" + refundReason + '\'' +
                '}';
    }
}
