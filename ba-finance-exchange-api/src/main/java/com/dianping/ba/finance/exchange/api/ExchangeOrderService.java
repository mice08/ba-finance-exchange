package com.dianping.ba.finance.exchange.api;

import com.dianping.ba.finance.exchange.api.beans.ExchangeOrderBean;
import com.dianping.ba.finance.exchange.api.beans.GenericResult;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: 遐
 * Date: 13-12-13
 * Time: 上午10:28
 * To change this template use File | Settings | File Templates.
 */
public interface ExchangeOrderService {
    /**
     * 创建交易订单
     * @param exchangeOrderBean
     * @return
     */
      public int createExchangeOrder(ExchangeOrderBean exchangeOrderBean);

    /**
     * 更新交易指令成功
     *
     * @param orderIds 交易指令集
     * @return 更新结果集
     */
    GenericResult<Integer> updateExchangeOrderToSuccess(List<Integer> orderIds);
}
