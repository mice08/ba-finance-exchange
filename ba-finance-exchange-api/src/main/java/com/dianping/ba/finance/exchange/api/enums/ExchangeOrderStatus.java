package com.dianping.ba.finance.exchange.api.enums;

/**
 * Created with IntelliJ IDEA.
 * User: sally.zhu
 * Date: 13-12-13
 * Time: 下午4:04
 */
public enum ExchangeOrderStatus {

	/**
	 * 0-默认错误
	 */
	DEFAULT(0),
	/**
     * 1-初始
     */
    INIT(1),
    /**
     * 2-支付中
     */
    PENDING(2),
    /**
     * 3-付款成功
     */
    SUCCESS(3),
    /**
     * 4-退票
     */
    FAIL(4);

    public int getExchangeOrderStatus() {
        return exchangeOrderStatus;
    }

    public void setExchangeOrderStatus(int exchangeOrderStatus) {
        this.exchangeOrderStatus = exchangeOrderStatus;
    }

    private int exchangeOrderStatus;

    private ExchangeOrderStatus(int exchangeOrderStatus) {
        this.exchangeOrderStatus = exchangeOrderStatus;
    }

    @Override
    public String toString() {
        switch (exchangeOrderStatus) {
            case 1:
                return "初始";
            case 2:
                return "支付中";
            case 3:
                return "付款成功";
            case 4:
                return "退票";
            default:
                return "错误";
        }
    }

	public static ExchangeOrderStatus valueOf(int value){
		switch (value){
			case 1:
				return INIT;
			case 2:
				return PENDING;
            case 3:
                return SUCCESS;
            case 4:
                return FAIL;
			default:
				return DEFAULT;
		}
	}

    public int value(){
        return exchangeOrderStatus;
    }
}
