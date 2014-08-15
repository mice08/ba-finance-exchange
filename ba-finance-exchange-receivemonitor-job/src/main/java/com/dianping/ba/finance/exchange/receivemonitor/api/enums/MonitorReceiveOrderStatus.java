package com.dianping.ba.finance.exchange.receivemonitor.api.enums;

/**
 * Created by IntelliJ IDEA.
 * User: yaxiong.cheng
 * Date: 2014/8/8
 * Time: 15:18
 * To change this template use File | Settings | File Templates.
 */
public enum MonitorReceiveOrderStatus {
	/**
	 * 0-默认错误
	 */
	DEFAULT(0, "错误"),

	/**
	 * 1-待确认
	 */
	UNCONFIRMED(1, "待确认"),
	/**
	 * 2-已确认
	 */
	CONFIRMED(2, "已确认"),
	/**
	 * 3-作废
	 */
	INVALID(3, "作废");

	private int status;

	private String description;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	MonitorReceiveOrderStatus(int status, String description) {
		this.status = status;
		this.description = description;
	}

	@Override
	public String toString() {
		return this.description;
	}

	public static MonitorReceiveOrderStatus valueOf(int value){
		for (MonitorReceiveOrderStatus monitorReceiveOrderStatus : MonitorReceiveOrderStatus.values()) {
			if (monitorReceiveOrderStatus.getStatus() == value) {
				return monitorReceiveOrderStatus;
			}
		}
		return DEFAULT;
	}

	public int value(){
		return status;
	}
}
