package com.dianping.ba.finance.exchange.api;


import com.dianping.ba.finance.exchange.api.datas.PayRequestData;
import com.dianping.ba.finance.exchange.api.enums.PayRequestStatus;

/**
 *
 */
public interface PayRequestService {

    /**
     *
     * @param payRequestData
     * @return
     */
    public boolean insertPayRequest(PayRequestData payRequestData);

    /**
     * 更新付款请求
     * @param prId
     * @param status
     * @param memo
     * @return
     */
    public boolean updatePayRequest(int prId, PayRequestStatus status, String memo);
}
