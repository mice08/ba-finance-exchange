package com.dianping.ba.finance.exchange.api.enums;

/**
 * 收款单状态
 */
public enum AccountingVoucherType {

	/**
	 * 0-默认错误
	 */
	DEFAULT(0, "错误"),

	/**
     * AD表示推广，O表示其他凭证，NONE_CONTRACT_RECEIVE，无合同收款，最后表示GAAPType
     */
    AD_O_NONE_CONTRACT_RECEIVE_PRC(56032, "DP_pos无合同收款_推广_PRC"),

    AD_O_NONE_CONTRACT_RECEIVE_US(56055, "DP_pos无合同收款_推广_US");

    private int type;

    private String description;

    public int getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    AccountingVoucherType(int type, String description) {
        this.type = type;
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }

	public static AccountingVoucherType valueOf(int value){
        for (AccountingVoucherType type : AccountingVoucherType.values()) {
            if (type.getType() == value) {
                return type;
            }
        }
        return DEFAULT;
	}

    public int value(){
        return type;
    }
}
