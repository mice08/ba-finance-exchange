package com.dianping.ba.finance.exchange.api.enums;

/**
 * 收款单状态
 */
public enum ReceiveOrderStatus {

	/**
	 * 0-默认错误
	 */
	DEFAULT(0, "错误"),
	/**
     * 1-初始
     */
    INIT(1, "初始"),

	/**
     * 2-待确认
     */
    UNCONFIRMED(2, "待确认"),
	/**
     * 3-已确认
     */
    CONFIRMED(3, "已确认");

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

    ReceiveOrderStatus(int status, String description) {
        this.status = status;
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }

	public static ReceiveOrderStatus valueOf(int value){
        for (ReceiveOrderStatus receiveOrderStatus : ReceiveOrderStatus.values()) {
            if (receiveOrderStatus.getStatus() == value) {
                return receiveOrderStatus;
            }
        }
        return DEFAULT;
	}

    public int value(){
        return status;
    }
}
