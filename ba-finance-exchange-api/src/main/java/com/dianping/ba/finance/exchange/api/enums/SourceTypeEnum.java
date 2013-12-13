package com.dianping.ba.finance.exchange.api.enums;

/**
 * 交易类型
 * @author junjie.mao
 *
 */
public enum SourceTypeEnum {

	/**
	 * 默认
	 */
	Default(0),
	/**
	 * 付款计划
	 */
	PaymentPlan(1),
    /**
     * 收款计划
     */
    ReceivablePlan(2),
    /**
     * 交易指令
     */
    ExchangeOrder(3);

    public int getSourceType() {
        return sourceType;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }

    private int sourceType;

	private SourceTypeEnum(int sourceType) {
		this.setSourceType(sourceType);
	}

	@Override
	public String toString() {
		switch (sourceType) {
			case 1:
				return "正向";
			case 2:
                return "负向";
			default:
				return "默认";
		}
	}

	public  String toString(int sourceType) {
		this.setSourceType(sourceType);
		return this.toString();
	}
}
