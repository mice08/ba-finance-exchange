package com.dianping.ba.finance.exchange.api;


import com.dianping.ba.finance.exchange.api.dtos.PayRequestDTO;

/**
 *
 */
public interface PayRequestHandleService {

    /**
     * 处理新提交的付款请求
     * @param payRequestDTO
     */
    public boolean handleNewPayRequest(PayRequestDTO payRequestDTO);

}
