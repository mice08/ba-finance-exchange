package com.dianping.ba.finance.exchange.api;


import com.dianping.ba.finance.exchange.api.dtos.PayCentreReceiveRequestDTO;

/**
 *  支付中心收款处理接口
 */
public interface PayCentreReceiveRequestHandleService {

    /**
     * 处理支付中心收款请求
     * @param payCentreReceiveRequestDTO
     * @return
     */
    public boolean handleReceiveRequest(PayCentreReceiveRequestDTO payCentreReceiveRequestDTO);
}
