package com.dianping.ba.finance.exchange.receivemonitor.api.enums;


public enum TodoStatus {

	/**
	 * 1-初始状态
	 */
    INIT(1,"初始"),

	/**
	 * 2—已处理
	 */
    HANDLED(2,"已处理");

    private int status;

	private String description;

    private TodoStatus(int status,String description) {
        this.status = status;
		this.description = description;
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
