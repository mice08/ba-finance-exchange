package com.dianping.ba.finance.exchange.api.enums;

/**
 * 收款单状态
 */
public enum AccoutingVoucherType {

	/**
	 * 0-默认错误
	 */
	DEFAULT(0, "错误"),

	/**
     * 1-待确认
     */
    NONE_CONTRACT(1, "待确认"),
	/**
     * 2-已确认
     */
    CONFIRMED(2, "已确认"),
	/**
	 * 3-作废
	 */
	INVALID(3, "作废");

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

    AccoutingVoucherType(int status, String description) {
        this.status = status;
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }

	public static AccoutingVoucherType valueOf(int value){
        for (AccoutingVoucherType receiveOrderStatus : AccoutingVoucherType.values()) {
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
