package com.dianping.ba.finance.exchange.api.enums;

/**
 * 收款类型
 */
public enum ReceiveType {

	/**
	 * 0-默认错误
	 */
	DEFAULT(0),
	/**
     * 1-推广-广告款
     */
    AD_FEE(1);

    private int receiveType;

    public int getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(int receiveType) {
        this.receiveType = receiveType;
    }

    private ReceiveType(int receiveType) {
        this.receiveType = receiveType;
    }

    @Override
    public String toString() {
        switch (receiveType) {
            case 1:
                return "推广-广告款";
            default:
                return "错误";
        }
    }

	public static ReceiveType valueOf(int value){
		switch (value){
			case 1:
				return AD_FEE;
			default:
				return DEFAULT;
		}
	}

    public int value(){
        return receiveType;
    }
}
