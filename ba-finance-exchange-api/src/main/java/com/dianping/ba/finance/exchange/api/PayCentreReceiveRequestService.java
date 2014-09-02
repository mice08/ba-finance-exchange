package com.dianping.ba.finance.exchange.api;

import com.dianping.ba.finance.exchange.api.datas.PayCentreReceiveRequestData;

/**
 *
 */
public interface PayCentreReceiveRequestService {

    /**
     * 插入支付中心收款请求表
     * @param payCentreReceiveRequestData
     * @return
     */
    public boolean insertPayCentreReceiveRequest(PayCentreReceiveRequestData payCentreReceiveRequestData);
}
