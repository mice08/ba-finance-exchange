package com.dianping.ba.finance.fundaccount.api.datas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ShopFundAccountData implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	private int fundAccountId;
	/**
	 * 分店号
	 */
	private int shopId;
	/**
	 * 账户类型：0默认；1团购；2预约预订
	 */
	private int accountType;
	/**
	 * 收入
	 */
	private BigDecimal credit;
	/**
	 * 支出
	 */
	private BigDecimal debit;
	/**
	 * 余额
	 */
	private BigDecimal balance;
	/**
	 * 添加时间
	 */
	private Date addDate;
	/**
	 * 更新时间
	 */
	private Date updateDate;

	public int getFundAccountId() {
		return fundAccountId;
	}

	public void setFundAccountId(int fundAccountId) {
		this.fundAccountId = fundAccountId;
	}

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	public int getAccountType() {
		return accountType;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

	public BigDecimal getCredit() {
		return credit;
	}

	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}

	public BigDecimal getDebit() {
		return debit;
	}

	public void setDebit(BigDecimal debit) {
		this.debit = debit;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
