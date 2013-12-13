package com.dianping.ba.finance.exchange.api;

import com.dianping.ba.finance.exchange.api.dtos.ExchangeOrderDTO;
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
     * 因为交易指令成功而更新资金账户并插入资金账户流水
     *
     * @param orderIds 交易指令
     * @return 成功与否
     */
    boolean updateShopFundAccountCausedByExchangeOrderSuccess(ExchangeOrderDTO exchangeOrder);

    /**
     * 根据交易指令获取资金账户PaymentPlan流水
     *
     * @param orderIds 交易指令
     * @return 成功与否
     */
    ShopFundAccountFlowDTO getPaymentPlanShopFundAccountFlow(ExchangeOrderDTO exchangeOrder);
}
