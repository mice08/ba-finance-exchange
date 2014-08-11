package com.dianping.ba.finance.exchange.paymonitor.api.enums;

/**
 * Created by adam.huang on 2014/8/7.
 */
public enum MonitorExceptionType {
    /**
     * 异常
     */
    DEFAULT(0,"异常"),

    /**
     * 提交超时
     */
    PP_IN_SUBMISSION_TIMEOUT(1, "付款计划状态提交超时"),

    /**
     * 支付超时
     */
    PP_IN_PAYMENT_TIMEOUT(2, "付款计划状态支付超时"),

    /**
     * 支付中对应状态出错
     */
    PP_IN_PAYMENT_INVALID_STATE(3, "付款计划状态无法与付款单状态对应，当前付款计划为支付中"),

    /**
     * 支付成功对应状态出错
     */
    PP_PAY_SUCCESS_INVALID_STATE(4, "付款计划状态无法与付款单状态对应，当前付款计划为支付成功"),

    /**
     * 退票对应状态出错
     */
    PP_REFUND_INVALID_STATE(5, "付款计划状态无法与付款单状态对应，当前付款计划为退票"),

    /**
     * 支付失败
     */
    PP_PAY_FAILED(6, "付款计划支付失败"),

    /**
     * 找不到对应付款序列号
     */
    PP_PAY_SEQUENCE_NOT_FOUND(7, "付款计划找不到对应的付款序列号"),

    /**
     * 找不到对应付款单记录
     */
    PP_PAY_ORDER_NOT_FOUND(8, "付款计划找不到对应的付款单记录");


    private int type;
    private String description;

    private MonitorExceptionType(int type, String description) {
        this.type = type;
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static MonitorExceptionType valueOf(int type){
        switch(type) {
            case 1:
                return PP_IN_SUBMISSION_TIMEOUT;
            case 2:
                return PP_IN_PAYMENT_TIMEOUT;
            case 3:
                return PP_IN_PAYMENT_INVALID_STATE;
            case 4:
                return PP_PAY_SUCCESS_INVALID_STATE;
            case 5:
                return PP_REFUND_INVALID_STATE;
            case 6:
                return PP_PAY_FAILED;
            case 7:
                return PP_PAY_SEQUENCE_NOT_FOUND;
            case 8:
                return PP_PAY_ORDER_NOT_FOUND;
            default:
                return DEFAULT;
        }
    }

    public static String toString(MonitorExceptionType monitorExceptionType){
        return monitorExceptionType.description;

    }

    public int value(){
        return this.type;
    }

}
