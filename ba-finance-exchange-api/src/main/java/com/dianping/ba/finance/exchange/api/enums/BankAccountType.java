package com.dianping.ba.finance.exchange.api.enums;

import java.io.Serializable;

/**
 *
 */
public enum BankAccountType implements Serializable {
	
	/**
	 * 个人
	 */
	PERSONAL(0),
	/**
	 * 公司
	 */
	COMPANY(1);

	private int bankAccountType;

	private BankAccountType(int bankAccountType) {
		this.setBankAccountType(bankAccountType);
	}

	@Override
	public String toString() {
		switch (bankAccountType) {
			case 0:
				return "个人";
			case 1:
				return "公司";
			default:
				return "异常";
		}
	}

	public static BankAccountType valueOf(int value){
		switch (value){
			case 0:
				return PERSONAL;
			case 1:
				return COMPANY;
			default:
				return PERSONAL;
		}
	}

	private int getBankAccountType() {
		return bankAccountType;
	}

	public void setBankAccountType(int bankAccountType) {
		this.bankAccountType = bankAccountType;
	}

	public int value() {
		return this.bankAccountType;
	}
}
