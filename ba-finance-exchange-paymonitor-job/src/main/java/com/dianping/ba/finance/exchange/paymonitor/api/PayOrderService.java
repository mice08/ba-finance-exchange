package com.dianping.ba.finance.exchange.paymonitor.api;

import com.dianping.ba.finance.exchange.paymonitor.api.datas.PayOrderMonitorData;

/**
 * Created by adam.huang on 2014/8/7.
 */
public interface PayOrderService {
    /**
     * 根据付款序列号获取付款单
     * @param paySequence
     * @return
     */
    public PayOrderMonitorData getPayOrderBySequence(String paySequence);
}
