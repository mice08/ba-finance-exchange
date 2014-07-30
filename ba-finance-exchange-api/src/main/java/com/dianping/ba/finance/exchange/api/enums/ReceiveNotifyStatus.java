package com.dianping.ba.finance.exchange.api.enums;

/**
 * 收款类型
 */
public enum ReceiveNotifyStatus {

	/**
	 * 0-异常
	 */
	DEFAULT(0, "异常"),

    INIT(1, "待确认"),

    // 2为处理失败，见ReceiveNotifyResultStatus

    REJECT(3, "驳回"),

    MATCHED(4, "已匹配"),

    CONFIRMED(5, "已确认");

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

    private ReceiveNotifyStatus(int status, String description) {
        this.status = status;
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }

	public static ReceiveNotifyStatus valueOf(int value) {
        for (ReceiveNotifyStatus status : ReceiveNotifyStatus.values()) {
            if (status.value() == value) {
                return status;
            }
        }
        return DEFAULT;
	}

    public int value(){
        return status;
    }
}
