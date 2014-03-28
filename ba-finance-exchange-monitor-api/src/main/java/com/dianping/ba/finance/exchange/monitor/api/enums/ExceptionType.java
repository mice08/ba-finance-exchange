package com.dianping.ba.finance.exchange.monitor.api.enums;


public enum ExceptionType {

    DEFAULT(0),

    EO_INIT_PENDING_WITHOUT_FLOW(1),

    EO_INIT_PENDING_WITH_MORE_FLOW(2),

    EO_SUCCESS_WITHOUT_OUT_FLOW(3),

    EO_SUCCESS_WITH_MORE_FLOW(4),

    EO_REFUND_WITHOUT_FLOW(5),

    EO_REFUND_WITH_WRONG_FLOW_COUNT(6);

    private int type;

    private ExceptionType(int type) {
        this.type = type;
    }

    public static ExceptionType valueOf(int type) {
        switch (type) {
            case 1:
                return EO_INIT_PENDING_WITHOUT_FLOW;
            case 2:
                return EO_INIT_PENDING_WITH_MORE_FLOW;
            case 3:
                return EO_SUCCESS_WITHOUT_OUT_FLOW;
            case 4:
                return EO_SUCCESS_WITH_MORE_FLOW;
            case 5:
                return EO_REFUND_WITHOUT_FLOW;
            case 6:
                return EO_REFUND_WITH_WRONG_FLOW_COUNT;
            default:
                return DEFAULT;
        }
    }

    public String toString() {
        switch (type) {
            case 1:
                return "付款单状态为初始或者支付中，无对应资金账户流水";
            case 2:
                return "付款单状态为初始或者支付中，存在对应多条资金账户流水";
            case 3:
                return "付款单状态为支付成功，无负向资金账户流水";
            case 4:
                return "付款单状态为支付成功，有多条资金账户流水";
            case 5:
                return "付款单状态为退票，无对应资金账户流水";
            case 6:
                return "付款单状态为退票，对应资金账户流水数量不对";
            default:
                return "未知错误";
        }

    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int value() {
        return this.type;
    }


}
