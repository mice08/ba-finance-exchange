package com.dianping.ba.finance.exchange.paymonitor.api.enums;

/**
 * Created by adam.huang on 2014/8/7.
 */
public enum PayPlanStatus {

    /**
     * 异常
     */
    DEFAULT(0,"异常"),
    /**
     * 初始
     */
    INIT(1,"初始"),
    /**
     * 2-提交中
     */
    IN_SUBMISSION(2,"提交中"),
    /**
     * 3-无有效的银行账号
     */
    INVALID_BANK_ACCOUNT(3,"无有效的银行账号"),
    /**
     * 4-支付中
     */
    IN_PAYMENT(4,"支付中"),
    /**
     * 5-支付成功
     */
    PAY_SUCCESS(5,"支付成功"),
    /**
     * 6-退票
     */
    REFUND(6,"退票"),
    /**
     * 7-暂停
     */
    SUSPEND(7,"暂停"),
    /**
     * 8-支付失败
     */
    PAY_FAILED(8,"支付失败");

    private int payPlanStatus;
    private String description;

    private PayPlanStatus(int paymentPlanStatus, String description) {
        this.setPayPlanStatus(paymentPlanStatus);
        this.setDescription(description);
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
        return this.description;
    }

    private void setPayPlanStatus(int payPlanStatus) {
        this.payPlanStatus = payPlanStatus;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int value(){
        return payPlanStatus;
    }
}
