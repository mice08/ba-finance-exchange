package com.dianping.ba.finance.exchange.receivemonitor.api.enums;


public enum ExceptionType {

	/**
	 * 0-默认 未知错误
	 */
    DEFAULT(0,"未知错误"),

	/**
	 * 1-收款单状态已确认，无对应收款确认且超时
	 */
    RO_SUCCESS_WITHOUT_RC_TIMEOUT(1,"收款单状态已确认，无对应收款确认且超时"),

	/**
	 * 2-收款单状态已确认，对应收款确认状态为确认失败
	 */
    RO_SUCCESS_WITH_WRONG_RC(2,"收款单状态已确认，对应收款确认状态为确认失败");

    private int type;

	private String description;

    private ExceptionType(int type,String description) {
        this.type = type;
		this.description = description;
    }

    public static ExceptionType valueOf(int type) {
        switch (type) {
            case 1:
                return RO_SUCCESS_WITHOUT_RC_TIMEOUT;
            case 2:
                return RO_SUCCESS_WITH_WRONG_RC;
            default:
                return DEFAULT;
        }
    }

    public String toString() {
        switch (type) {
            case 1:
                return "收款单状态已确认，无对应收款确认且超时";
            case 2:
                return "收款单状态已确认，对应收款确认状态为确认失败";
            default:
                return "未知错误";
        }

    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int value() {
        return this.type;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
