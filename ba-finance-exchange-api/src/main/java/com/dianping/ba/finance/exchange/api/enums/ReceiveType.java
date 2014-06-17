package com.dianping.ba.finance.exchange.api.enums;

/**
 * 收款类型
 */
public enum ReceiveType {

	/**
	 * 0-默认错误
	 */
	DEFAULT(0, "默认错误"),
	/**
     * 1-推广-广告款
     */
    AD_FEE(1, "推广-广告款");

    private int receiveType;

    private String description;

    public int getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(int receiveType) {
        this.receiveType = receiveType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private ReceiveType(int receiveType, String description) {
        this.receiveType = receiveType;
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }

	public static ReceiveType valueOf(int value) {
        for (ReceiveType receiveType : ReceiveType.values()) {
            if (receiveType.getReceiveType() == value) {
                return receiveType;
            }
        }
        return DEFAULT;
	}

    public int value(){
        return receiveType;
    }
}
