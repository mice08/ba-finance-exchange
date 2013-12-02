package com.dianping.ba.finance.fundaccount.api.enums;

/**
 * 交易类型
 * @author junjie.mao
 *
 */
public enum TradeTypeEnum {

	/**
	 * 默认
	 */
	Default(0),
	/**
	 * 充值
	 */
	RECHARGE(200),
    /**
     * 退款
     */
    REFUND(201),
	/**
	 * 消耗
	 */
	CONSUME(101);

	
	private int tradeType;

	private TradeTypeEnum(int tradeType) {
		this.setTradeType(tradeType);
	}

	@Override
	public String toString() {
		switch (tradeType) {
			case 200:
				return "收款";
			case 101:
                return "消耗";
            case 201:
                return "退款";
			default:
				return "默认";
		}
	}

	public  String toString(int tradeType) {
		this.setTradeType(tradeType);
		return this.toString();
	}

	public void setTradeType(int tradeType) {
		this.tradeType = tradeType;
	}

	public int getTradeType() {
		return tradeType;
	}
}
