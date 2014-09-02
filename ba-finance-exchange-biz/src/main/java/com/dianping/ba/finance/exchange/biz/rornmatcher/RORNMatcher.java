package com.dianping.ba.finance.exchange.biz.rornmatcher;

import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData;
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;

/**
 * Created by noahshen on 14-7-26.
 */
public interface RORNMatcher {

    boolean match(ReceiveOrderData receiveOrderData, ReceiveNotifyData receiveNotifyData);
}
