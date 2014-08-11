package com.dianping.ba.finance.exchange.paymonitor.api.enums;

/**
 * Created by adam.huang on 2014/8/7.
 */
public enum PayPlanStatus {

    /**
     * 异常
     */
    DEFAULT(0),
    /**
     * 初始
     */
    INIT(1),
    /**
     * 2-提交中
     */
    IN_SUBMISSION(2),
    /**
     * 3-无有效的银行账号
     */
    INVALID_BANK_ACCOUNT(3),
    /**
     * 4-支付中
     */
    IN_PAYMENT(4),
    /**
     * 5-支付成功
     */
    PAY_SUCCESS(5),
    /**
     * 6-退票
     */
    REFUND(6),
    /**
     * 7-暂停
     */
    SUSPEND(7),

    /**
     * 8-支付失败
     */
    PAY_FAILED(8);

    private int payPlanStatus;

    private PayPlanStatus(int paymentPlanStatus) {
        this.setPayPlanStatus(paymentPlanStatus);
    }

    public static PayPlanStatus valueOf(int value) {
        switch (value) {
            case 1:
                return INIT;
            case 2:
                return IN_SUBMISSION;
            case 3:
                return INVALID_BANK_ACCOUNT;
            case 4:
                return IN_PAYMENT;
            case 5:
                return PAY_SUCCESS;
            case 6:
                return REFUND;
            case 7:
                return SUSPEND;
            case 8:
                return PAY_FAILED;
            default:
                return DEFAULT;
        }
    }

    @Override
    public String toString() {
        switch (payPlanStatus) {
            case 1:
                return "初始";
            case 2:
                return "提交中";
            case 3:
                return "无有效银行账号";
            case 4:
                return "支付中";
            case 5:
                return "支付成功";
            case 6:
                return "退票";
            case 7:
                return "暂停";
            case 8:
                return "支付失败";
            default:
                return "错误";
        }
    }

    private void setPayPlanStatus(int payPlanStatus) {
        this.payPlanStatus = payPlanStatus;
    }

    public int value(){
        return payPlanStatus;
    }
}
