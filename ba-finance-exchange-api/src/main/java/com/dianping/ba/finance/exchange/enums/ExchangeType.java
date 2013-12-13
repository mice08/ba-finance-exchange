package com.dianping.ba.finance.exchange.enums;

/**
 * Created with IntelliJ IDEA.
 * User: sally.zhu
 * Date: 13-12-13
 * Time: 下午4:04
 */
public enum ExchangeType {

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
     * 3-失败
     */
    Fail(3);

    private int exchangeType;

    private ExchangeType(int exchangeType) {
        this.exchangeType = exchangeType;
    }

    @Override
    public String toString() {
        switch (exchangeType) {
            case 1:
                return "初始";
            case 2:
                return "成功";
            case 3:
                return "失败";
            default:
                return "错误";
        }
    }

    public int getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(int exchangeType) {
        this.exchangeType = exchangeType;
    }

	public static ExchangeType valueOf(int value){
		switch (value){
			case 1:
				return Init;
			case 2:
				return Success;
			case 3:
				return Fail;
			default:
				return Default;
		}
	}
}
