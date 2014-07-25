package com.dianping.ba.finance.exchange.api.beans;

/**
 * Created by IntelliJ IDEA.
 * User: yaxiong.cheng
 * Date: 2014/7/25
 * Time: 10:28
 * To change this template use File | Settings | File Templates.
 */
public class BizInfoBean {
	private int customerId;
	private String customerName;

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Override
	public String toString() {
		return "BizInfoBean{" +
				"customerId=" + customerId +
				", customerName='" + customerName + '\'' +
				'}';
	}
}
