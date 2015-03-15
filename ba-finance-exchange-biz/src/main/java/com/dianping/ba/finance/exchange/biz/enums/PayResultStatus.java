package com.dianping.ba.finance.exchange.biz.enums;

public enum PayResultStatus {
    DEFAULT(0, "默认错误"),

    SYSTEM_ERROR(-1, "系统异常"),
    REQUEST_SUCCESS(1, "提交成功"),
    REQUEST_FAILED(2, "提交失败"),
    PAY_SUCCESS(3, "付款成功"),
    PAY_FAILED(4, "付款失败"),
    PAYING(5, "付款中"),
    ;

    int code;
    String message;

    PayResultStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static PayResultStatus getByCode(int code) {
        for (PayResultStatus error : PayResultStatus.values()) {
            if (error.getCode() == code) {
                return error;
            }
        }
        return DEFAULT;
    }

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}