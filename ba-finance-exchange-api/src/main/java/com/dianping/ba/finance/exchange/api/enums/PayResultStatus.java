package com.dianping.ba.finance.exchange.api.enums;

/**
 * Created with IntelliJ IDEA.
 * User: bingqiu.yuan
 * Date: 14-6-4
 * Time: 上午11:52
 * To change this template use File | Settings | File Templates.
 */
public enum PayResultStatus {

    /**
     * 异常
     */
    DEFAULT(0),
    /**
     * 2-接收申请失败
     */
    REQUEST_FAIL(2),
    /**
     * 11-生成付款单成功
     */
    PAY_ORDER_GENERATED(11),
    /**
     * 12-支付成功
     */
    PAY_SUCCESS(12),
    /**
     * 13-退票
     */
    PAY_REFUND(13);

    private int payResultStatus;

    private PayResultStatus(int payResultStatus) {
        this.setPayResultStatus(payResultStatus);
    }

    public static PayResultStatus valueOf(int value) {
        switch (value) {
            case 2:
                return REQUEST_FAIL;
            case 11:
                return PAY_ORDER_GENERATED;
            case 12:
                return PAY_SUCCESS;
            case 13:
                return PAY_REFUND;
            default:
                return DEFAULT;
        }
    }

    @Override
    public String toString() {
        switch (payResultStatus) {
            case 2:
                return "接收申请失败";
            case 11:
                return "生成付款单成功";
            case 12:
                return "支付成功";
            case 13:
                return "退票";
            default:
                return "错误";
        }
    }

    public int getPayResultStatus() {
        return payResultStatus;
    }

    public void setPayResultStatus(int payResultStatus) {
        this.payResultStatus = payResultStatus;
    }

    public int value(){
        return payResultStatus;
    }
}
