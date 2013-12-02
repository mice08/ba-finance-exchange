package com.dianping.ba.finance.fundaccount.api.datas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ShopFundAccountAuditData implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 资金账户号
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
	 * 金额（都是正数）
	 */
	private BigDecimal amount;
	/**
	 * 类型：200充值，101消耗
	 */
	private int tradeType;
	/**
	 * 0负数；1正数
	 */
	private int isPositive;
	/**
	 * 添加时间
	 */
	private Date addDate;
	/**
	 * 备注
	 */
	private String memo;
	/**
	 * 源头类型：1.应收2.收款3.应付4.付款
	 */
	private int resourceType;
	/**
	 * 源头ID
	 */
	private int resourceId;

	public int getResourceType() {
		return resourceType;
	}

	public void setResourceType(int resourceType) {
		this.resourceType = resourceType;
	}

	public int getResourceId() {
		return resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public int getTradeType() {
		return tradeType;
	}

	public void setTradeType(int tradeType) {
		this.tradeType = tradeType;
	}

    public int getPositive() {
        return isPositive;
    }

    public void setPositive(int positive) {
        isPositive = positive;
    }

    public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
