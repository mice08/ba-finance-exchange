package com.dianping.ba.finance.exchange.siteweb.enums;

public enum SubmitAction {
    DEFAULT(-1,  "默认错误"),

    PAY_REQUEST(1, "提交支付"),
    PAY_ORDER(2, "支付确认");

    int code;
    String message;

    SubmitAction(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static SubmitAction getByCode(int code) {
        for (SubmitAction action : SubmitAction.values()) {
            if (action.getCode() == code) {
                return action;
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