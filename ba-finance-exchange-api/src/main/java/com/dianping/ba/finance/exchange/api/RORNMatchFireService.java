package com.dianping.ba.finance.exchange.api;

import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;

/**
 * Created by noahshen on 14-7-25.
 */
public interface RORNMatchFireService {


    public void executeMatchingForNewReceiveOrder(ReceiveOrderData newROData);

}
