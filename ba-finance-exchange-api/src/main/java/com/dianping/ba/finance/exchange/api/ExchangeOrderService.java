package com.dianping.ba.finance.exchange.api;

import com.dianping.ba.finance.exchange.api.beans.GenericResult;

/**
 * Created with IntelliJ IDEA.
 * User: 遐
 * Date: 13-12-13
 * Time: 上午10:28
 * To change this template use File | Settings | File Templates.
 */
public interface ExchangeOrderService {

    GenericResult<Integer> updateExchangeOrderToSuccess(int[] orderIds);
}
