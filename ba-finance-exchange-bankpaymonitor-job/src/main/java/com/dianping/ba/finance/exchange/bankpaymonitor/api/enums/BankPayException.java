package com.dianping.ba.finance.exchange.bankpaymonitor.api.enums;

/**
 * Created by will on 15-3-13.
 */
public enum  BankPayException {

    /**
     * 默认错误
     */
    DEFAULT(0, "默认错误"),
    /**
     * 状态不匹配
     */
    DIFF_STATUS(1, "状态不匹配"),
    /**
     * 1-正向
     */
    RECORD_NOT_FOUND(2, "请求记录未找到"),
    /**
     * 2-负向
     */
    MULTI_RECORDS(3, "多条有效请求");

    int code;
    String message;

    BankPayException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static BankPayException getByCode(int code) {
        for (BankPayException exception : BankPayException.values()) {
            if (exception.getCode() == code) {
                return exception;
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
