package com.dianping.ba.finance.exchange.api.enums;

/**
 * Created by will on 15-3-13.
 */
public enum BankPayResult {

    /**
     * 0-成功
     */
    SUCCESS(0, "成功"),

    /**
     * 口令验证失败
     */
    AUTH_FAIL(1, "口令验证失败"),

    /**
     * 口令验证失败
     */
    SYSTEM_ERROR(2, "提交系统报错");

    private int code;

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    BankPayResult(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }

    public static BankPayResult valueOf(int value){
        for (BankPayResult result : BankPayResult.values()) {
            if (result.getCode() == value) {
                return result;
            }
        }
        return SUCCESS;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
