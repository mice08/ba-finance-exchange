package com.dianping.ba.finance.exchange.api;


import com.dianping.ba.finance.exchange.api.dtos.PayOrderRequestDTO;

/**
 *
 */
public interface PayOrderRequestHandleService {

    /**
     * 处理提交的付款请求
     * @param payOrderRequestDTO
     */
    public boolean handlePayOrderRequest(PayOrderRequestDTO payOrderRequestDTO);
}
