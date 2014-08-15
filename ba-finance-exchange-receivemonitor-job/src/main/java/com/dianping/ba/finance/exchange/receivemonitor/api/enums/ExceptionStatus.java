package com.dianping.ba.finance.exchange.receivemonitor.api.enums;

public enum ExceptionStatus {

	/**
	 * 1-初始化
	 */
    INIT(1,"初始化"),

	/**
	 * 2-已处理
	 */
    HANDLED(2,"已处理");

    private int status;

	private String description;

    private ExceptionStatus(int status,String desc) {
        this.status = status;
		this.description = desc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int value(){
        return this.status;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
