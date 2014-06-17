package com.dianping.ba.finance.exchange.api;


import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;

/**
 *  处理收款款的Service类
 */
public interface ReceiveOrderService {

    /**
     * 生成收款单
     * @param receiveOrderData
     */
    int createReceiveOrder(ReceiveOrderData receiveOrderData);


}
