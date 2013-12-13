package com.dianping.ba.finance.exchange.api.enums;

/**
 * Created with IntelliJ IDEA.
 * User: sally.zhu
 * Date: 13-12-13
 * Time: 下午4:04
 */
public enum ExchangeOrderStatusEnum {

	/**
	 * 0-默认错误
	 */
	Default(0),
	/**
     * 1-初始
     */
    Init(1),
    /**
     * 2-成功
     */
    Success(2),
    /**
     * 3-支付中
     */
	Pending(3),
    /**
     * 4-失败
     */
    Fail(4);

    public int getExchangeOrderStatus() {
        return exchangeOrderStatus;
    }

    public void setExchangeOrderStatus(int exchangeOrderStatus) {
        this.exchangeOrderStatus = exchangeOrderStatus;
    }

    private int exchangeOrderStatus;

    private ExchangeOrderStatusEnum(int exchangeOrderStatus) {
        this.exchangeOrderStatus = exchangeOrderStatus;
    }

    @Override
    public String toString() {
        switch (exchangeOrderStatus) {
            case 1:
                return "初始";
            case 2:
                return "成功";
            case 3:
                return "支付中";
            case 4:
                return "失败";
            default:
                return "错误";
        }
    }

	public static ExchangeOrderStatusEnum valueOf(int value){
		switch (value){
			case 1:
				return Init;
			case 2:
				return Success;
            case 3:
                return Pending;
            case 4:
                return Fail;
			default:
				return Default;
		}
	}
}
