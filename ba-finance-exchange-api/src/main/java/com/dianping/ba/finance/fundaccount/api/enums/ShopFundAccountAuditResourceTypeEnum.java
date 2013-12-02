package com.dianping.ba.finance.fundaccount.api.enums;

/**
 * 商家资金账户流水--源头类型
 * @author junjie.mao
 *
 */
public enum ShopFundAccountAuditResourceTypeEnum {
	/**
	 * 应收计划
	 */
	ACCOUNTRECEIVABLE(1),
	/**
	 * 收款订单
	 */
	RECEIVEORDER(2),
	/**
	 * 应付计划
	 */
	ACCOUNTPAYABLE(3),
	/**
	 * 付款订单
	 */
	PAYORDER(4);
	
	private int shopFundAccountAuditResourceType;
	
	private ShopFundAccountAuditResourceTypeEnum(int shopFundAccountAuditResourceType) {
		this.setShopFundAccountAuditResourceType(shopFundAccountAuditResourceType);
	}
	
	@Override
	public String toString() {
		switch (shopFundAccountAuditResourceType) {
			case 1:
				return "应收计划";
			case 2:
				return "收款订单";
			case 3:
				return "应付计划";
			case 4:
				return "付款订单";
			default:
				return "错误";
		}
	}
	
	public void setShopFundAccountAuditResourceType(int shopFundAccountAuditResourceType) {
		this.shopFundAccountAuditResourceType = shopFundAccountAuditResourceType;
	}
	
	public int getShopFundAccountAuditResourceType() {
		return shopFundAccountAuditResourceType;
	}
}
