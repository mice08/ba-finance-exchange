package com.dianping.ba.finance.exchange.monitor.api.enums;


public enum ExceptionType {

    DEFAULT(0),

    EO_INITANDPENDING_WITH_NO_FLOW(1),
    EO_INITANDPENDING_WITH_MORE_FLOW(2);
    private int type;

    private ExceptionType(int type) {
        this.type = type;
    }

    public static ExceptionType valueOf(int type) {
        switch (type) {
            case 1:
                return EO_INITANDPENDING_WITH_NO_FLOW;
            case 2:
                return EO_INITANDPENDING_WITH_MORE_FLOW;
            default:
                return DEFAULT;
        }
    }

    public static String toString(ExceptionType exType) {
        switch (exType) {
            case EO_INITANDPENDING_WITH_NO_FLOW:
                return "EO状态为初始或者支付中，ShopFundAccountFlow不存在对应的Flow";
            case EO_INITANDPENDING_WITH_MORE_FLOW:
                return "EO状态为初始或者支付中，ShopFundAccountFlow存在多条对应的Flow";
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
