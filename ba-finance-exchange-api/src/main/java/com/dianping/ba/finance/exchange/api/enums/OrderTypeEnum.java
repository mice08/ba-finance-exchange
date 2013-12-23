package com.dianping.ba.finance.exchange.api.enums;

/**
 * 交易类型
 * @author junjie.mao
 *
 */
public enum OrderTypeEnum {

	/**
	 * 默认
	 */
	Default(0),
	/**
	 * 付款
	 */
	Payment(1),
    /**
     * 收款
     */
    Receivable(2);


    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    private int orderType;

	private OrderTypeEnum(int orderType) {
		this.setOrderType(orderType);
	}

	@Override
	public String toString() {
		switch (orderType) {
			case 1:
				return "付款";
			case 2:
                return "收款";
			default:
				return "默认";
		}
	}

	public  String toString(int flowType) {
		this.setOrderType(flowType);
		return this.toString();
	}
}
