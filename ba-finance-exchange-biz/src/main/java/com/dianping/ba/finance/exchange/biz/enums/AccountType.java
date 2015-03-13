package com.dianping.ba.finance.exchange.biz.enums;

public enum AccountType {
    DEFAULT(0, "默认错误"),

    PUBLIC(1, "对公"),
    PRIVATE(2, "对私"),

    ;

    int code;
    String message;

    AccountType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static AccountType getByCode(int code) {
        for (AccountType type : AccountType.values()) {
            if (type.getCode() == code) {
                return type;
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