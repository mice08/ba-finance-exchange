package com.dianping.ba.finance.exchange.api;

import com.dianping.ba.finance.exchange.api.beans.ShopFundAccountBean;
import com.dianping.ba.finance.exchange.api.datas.ShopFundAccountData;
import com.dianping.ba.finance.exchange.api.datas.ShopFundAccountFlowData;

/**
 *资金账户接口
 */
public interface ShopFundAccountService {
    /**
     * 根据条件查询资金账户
     * @param shopFundAccountBean
     * @return
     */
     public ShopFundAccountData loadShopFundAccountData(ShopFundAccountBean shopFundAccountBean);

    /**
     * 插入资金账户
     * @param shopFundAccountData
     * @return
     */
     public int insertShopFundAccountData(ShopFundAccountData shopFundAccountData);

    /**
     * 插入资金账户流水
     * @param shopFundAccountFlowData
     * @return
     */
     public int insertShopFundAccountFlowData(ShopFundAccountFlowData shopFundAccountFlowData);
}
