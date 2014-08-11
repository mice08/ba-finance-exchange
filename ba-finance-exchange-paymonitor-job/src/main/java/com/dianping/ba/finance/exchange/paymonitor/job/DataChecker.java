package com.dianping.ba.finance.exchange.paymonitor.job;

import java.util.Date;

public abstract class DataChecker {

	private Date currentMonitorTime;

	public void setCurrentMonitorTime(Date currentMonitorTime) {
		this.currentMonitorTime = currentMonitorTime;
	}

	public Date getCurrentMonitorTime() {
		return currentMonitorTime;
	}

	abstract public boolean run();

}
