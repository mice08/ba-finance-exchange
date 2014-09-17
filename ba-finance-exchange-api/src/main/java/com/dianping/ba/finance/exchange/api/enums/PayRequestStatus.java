package com.dianping.ba.finance.exchange.api.enums;

import java.io.Serializable;

/**
 * 付款请求记录的状态枚举
 */
public enum PayRequestStatus implements Serializable {


    DEFAULT(-1, "错误"),
	/**
	 * 初始
	 */
	INIT(1, "初始"),

	/**
	 * 处理成功
	 */
	SUCCESS(2, "处理成功"),

    /**
     * token不合法
     */
	INVALID_TOKEN(3, "token不合法"),

    /**
     * 请求超时
     */
    TIMEOUT(4, "请求超时"),

    /**
     * 处理请求失败
     */
    HANDLE_REQUEST_FAILED(5, "处理请求失败"),

    /**
     * 非法的金额
     */
    INVALID_AMOUNT(6, "非法的金额");

    private int status;

    private String description;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    PayRequestStatus(int status, String description) {
        this.status = status;
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }

    public static PayRequestStatus valueOf(int value) {
        for (PayRequestStatus status : PayRequestStatus.values()) {
            if (status.getStatus() == value) {
                return status;
            }
        }
        return DEFAULT;
    }

    public int value() {
        return status;
    }


}
