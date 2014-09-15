package com.dianping.ba.finance.exchange.api.enums;

/**
 * Created by Eric on 2014/6/4.
 */
public enum PayOrderStatus {

    /**
     * 异常
     */
    DEFAULT(0),
    /**
     * 初始
     */
    INIT(1),
    /**
     * 2-支付中
     */
    EXPORT_PAYING(2),
    /**
     * 3-支付成功
     */
    PAY_SUCCESS(3),
    /**
     * 4-退票
     */
    REFUND(4),
    /**
     * 5-暂停
     */
    SUSPEND(5),
    /**
     * 6-作废
     */
    INVALID(6);

    private int payOrderStatus;

    private PayOrderStatus(int payOrderStatus) {
        this.setPayOrderStatus(payOrderStatus);
    }

    public static PayOrderStatus valueOf(int value) {
        switch (value) {
            case 1:
                return INIT;
            case 2:
                return EXPORT_PAYING;
            case 3:
                return PAY_SUCCESS;
            case 4:
                return REFUND;
            case 5:
                return SUSPEND;
            case 6:
                return INVALID;
            default:
                return DEFAULT;
        }
    }

    @Override
    public String toString() {
        switch (payOrderStatus) {
            case 1:
                return "初始";
            case 2:
                return "支付中";
            case 3:
                return "支付成功";
            case 4:
                return "退票";
            case 5:
                return "暂停";
            case 6:
                return "作废";
            default:
                return "错误";
        }
    }

    private void setPayOrderStatus(int payOrderStatus) {
        this.payOrderStatus = payOrderStatus;
    }

    public int value(){
        return payOrderStatus;
    }
}
