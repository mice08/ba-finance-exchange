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
    INVALID(6),
    /**
     * 7-银行支付中
     */
    BANK_PAYING(7),
    /**
     * 8-支付失败(银行帐号异常)
     */
    PAY_FAILED(8),
    /**
     * 9-提交支付中
     */
    SUBMIT_FOR_PAY(9),
    /**
     * 10-系统异常
     */
    SYSTEM_ERROR(10),
    /**
     * 11-提交失败
     */
    SUBMIT_FAILED(11),
    ;

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
            case 7:
                return BANK_PAYING;
            case 8:
                return PAY_FAILED;
            case 9:
                return SUBMIT_FOR_PAY;
            case 10:
                return SYSTEM_ERROR;
            case 11:
                return SUBMIT_FAILED;
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
            case 7:
                return "银行支付中";
            case 8:
                return "支付失败";
            case 9:
                return "提交支付中";
            case 10:
                return "系统异常";
            case 11:
                return "提交失败";
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
