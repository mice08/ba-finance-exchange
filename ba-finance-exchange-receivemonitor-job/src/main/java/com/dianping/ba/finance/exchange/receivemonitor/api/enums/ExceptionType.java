package com.dianping.ba.finance.exchange.receivemonitor.api.enums;


public enum ExceptionType {

    DEFAULT(0),

    RO_SUCCESS_WITHOUT_RC_TIMEOUT(1),

    RO_SUCCESS_WITH_WRONG_RC(2);

    private int type;

    private ExceptionType(int type) {
        this.type = type;
    }

    public static ExceptionType valueOf(int type) {
        switch (type) {
            case 1:
                return RO_SUCCESS_WITHOUT_RC_TIMEOUT;
            case 2:
                return RO_SUCCESS_WITH_WRONG_RC;
            default:
                return DEFAULT;
        }
    }

    public String toString() {
        switch (type) {
            case 1:
                return "收款单状态已确认，无对应收款确认且超时";
            case 2:
                return "收款单状态已确认，对应收款确认状态为确认失败";
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
