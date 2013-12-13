package com.dianping.ba.finance.exchange.api;

import com.dianping.ba.finance.exchange.api.beans.ShopFundAccountBean;
import com.dianping.ba.finance.exchange.api.datas.ShopFundAccountData;
import com.dianping.ba.finance.exchange.api.datas.ShopFundAccountFlowData;
import com.dianping.ba.finance.exchange.api.dtos.ShopFundAccountFlowDTO;

/**
 * Created with IntelliJ IDEA.
 * User: 遐
 * Date: 13-12-13
 * Time: 上午9:19
 * To change this template use File | Settings | File Templates.
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

    /**
     *  外部调用资金账户流水接口
     * @param shopFundAccountFlowDTO
     * @return
     */
    public int createShopFundAccountFlow(ShopFundAccountFlowDTO shopFundAccountFlowDTO) throws Exception;
}
