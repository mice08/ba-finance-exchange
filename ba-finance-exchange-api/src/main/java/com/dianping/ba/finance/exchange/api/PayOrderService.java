package com.dianping.ba.finance.exchange.api;


import com.dianping.ba.finance.exchange.api.datas.PayOrderData;

/**
 *  处理付款单的Service类
 */
public interface PayOrderService {

    /**
     * 生成付款单
     * @param payOrderData
     */
    int createPayOrder(PayOrderData payOrderData);
}
