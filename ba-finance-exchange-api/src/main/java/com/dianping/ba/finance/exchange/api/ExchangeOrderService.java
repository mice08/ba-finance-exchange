package com.dianping.ba.finance.exchange.api;

import com.dianping.ba.finance.exchange.api.beans.ExchangeOrderBean;

/**
 *资金账户接口
 */
public interface ExchangeOrderService {
    /**
     * 创建交易订单
     * @param exchangeOrderBean
     * @return
     */
      public int createExchangeOrder(ExchangeOrderBean exchangeOrderBean);
}
