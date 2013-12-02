package com.dianping.ba.finance.fundaccount.biz.impl;

import com.dianping.ba.finance.fundaccount.api.ShopFundAccountService;
import com.dianping.ba.finance.fundaccount.biz.dao.ShopFundAccountInfoDao;

public class ShopFundAccountServiceObject implements ShopFundAccountService {
	private ShopFundAccountInfoDao shopFundAccountInfoDao;

	public void setShopFundAccountInfoDao(ShopFundAccountInfoDao shopFundAccountInfoDao) {
		this.shopFundAccountInfoDao = shopFundAccountInfoDao;
	}

}

