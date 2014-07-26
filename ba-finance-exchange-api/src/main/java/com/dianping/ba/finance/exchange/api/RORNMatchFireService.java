package com.dianping.ba.finance.exchange.api;

import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData;
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;

/**
 * Created by noahshen on 14-7-25.
 */
public interface RORNMatchFireService {

    /**
     * 新增收款单，触发匹配操作
     * @param newROData
     */
    void executeMatchingForNewReceiveOrder(ReceiveOrderData newROData);

    /**
     * 新增收款通知，触发匹配操作
     * @param newRNData
     */
    void executeMatchingForNewReceiveNotify(ReceiveNotifyData newRNData);

    /**
     * 当收款单确认后
     * @param confirmedROData
     */
    void executeMatchingForReceiveOrderConfirmed(ReceiveOrderData confirmedROData);

}
