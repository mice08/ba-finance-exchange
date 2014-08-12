package com.dianping.ba.finance.exchange.paymonitor.api.enums;

/**
 * Created by adam.huang on 2014/8/7.
 */
public enum PayOrderStatus {
    /**
     * 异常
     */
    DEFAULT(0, "异常"),
    /**
     * 初始
     */
    INIT(1, "初始"),
    /**
     * 2-支付中
     */
    EXPORT_PAYING(2, "支付中"),
    /**
     * 3-支付成功
     */
    PAY_SUCCESS(3, "支付成功"),
    /**
     * 4-退票
     */
    REFUND(4, "退票");

    private int payOrderStatus;
    private String description;

    private PayOrderStatus(int payOrderStatus, String description) {
        this.payOrderStatus = payOrderStatus;
        this.description = description;
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
            default:
                return DEFAULT;
        }
    }

    @Override
    public String toString() {
        return this.description;
    }

    public int value(){
        return payOrderStatus;
    }
}

