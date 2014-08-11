package com.dianping.ba.finance.exchange.paymonitor.api.enums;

/**
 * Created by adam.huang on 2014/8/7.
 */
public enum MonitorExceptionType {
    DEFAULT(0),
    PP_IN_SUBMISSION_TIMEOUT(1),
    PP_IN_PAYMENT_TIMEOUT(2),
    PP_IN_PAYMENT_INVALID_STATE(3),
    PP_PAY_SUCCESS_INVALID_STATE(4),
    PP_REFUND_INVALID_STATE(5),
    PP_PAY_FAILED(6),
    PP_PAY_SEQUENCE_NOT_FOUND(7),
    PP_PAY_ORDER_NOT_FOUND(8);


    private int type;

    private MonitorExceptionType(int type) {
        this.type = type;
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
        switch(monitorExceptionType){
            case PP_IN_SUBMISSION_TIMEOUT:
                return "付款计划状态一直为提交中";
            case PP_IN_PAYMENT_TIMEOUT:
                return "付款计划状态一直为支付中";
            case PP_IN_PAYMENT_INVALID_STATE:
                return "付款计划状态无法与付款单状态对应，当前付款计划为支付中";
            case PP_PAY_SUCCESS_INVALID_STATE:
                return "付款计划状态无法与付款单状态对应，当前付款计划为支付成功";
            case PP_REFUND_INVALID_STATE:
                return "付款计划状态无法与付款单状态对应，当前付款计划为退票";
            case PP_PAY_FAILED:
                return "付款计划支付失败";
            case PP_PAY_SEQUENCE_NOT_FOUND:
                return  "付款计划找不到对应的付款序列号";
            case PP_PAY_ORDER_NOT_FOUND:
                return "付款计划找不到对应的付款单记录";
            default:
                return "未知错误";
        }

    }

    public int value(){
        return this.type;
    }

}
