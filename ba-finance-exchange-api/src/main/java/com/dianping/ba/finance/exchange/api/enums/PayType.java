package com.dianping.ba.finance.exchange.api.enums;

import java.io.Serializable;

/**
 * 支付类型
 */
public enum PayType implements Serializable {

	/**
	 * 错误
	 */
	DEFAULT(0),
	/**
	 * 团购结算款
	 */
	GROUPON_SETTLE(11),
    /**
     * 保底款（不区分团购还是闪惠）
     */
    GUARANTEE(1),
	/**
	 * 团购保证金
	 */
    GROUPON_BOND(13),
    /**
     * 闪惠结算款
     */
    SHAN_HUI_SETTLE(61),
    /**
     * 费用结算款
     */
    EXPENSE_SETTLE(71),

    /**
     * 闪付结算款
     */
    SHAN_FU_SETTLE(81),

    /**
     * 电影结算款
     */
    MOVIE_SETTLE(91);

    private int payType;

	private PayType(int payType) {
		this.setPayType(payType);
	}

	@Override
	public String toString() {
		switch (payType) {
			case 11:
				return "团购结算款";
			case 1:
                return "保底款";
            case 13:
                return "团购保证金";
            case 61:
                return "闪惠结算款";
            case 71:
                return "费用结算款";
            case 81:
                return "闪付结算款";
            case 91:
                return "电影结算款";
			default:
				return "默认";
		}
	}

	public static PayType valueOf(int value){
		switch (value){
			case 11:
				return GROUPON_SETTLE;
			case 1:
				return GUARANTEE;
			case 13:
				return GROUPON_BOND;
			case 61:
				return SHAN_HUI_SETTLE;
            case 71:
                return EXPENSE_SETTLE;
            case 81:
                return SHAN_FU_SETTLE;
            case 91:
                return MOVIE_SETTLE;
			default:
				return DEFAULT;
		}
	}

	public  String toString(int payType) {
		this.setPayType(payType);
		return this.toString();
	}

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int value() {
		return this.payType;
	}
}
