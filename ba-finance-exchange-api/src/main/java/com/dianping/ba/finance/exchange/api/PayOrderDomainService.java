package com.dianping.ba.finance.exchange.api;

import com.dianping.ba.finance.exchange.api.dtos.BankPayResultDTO;

import java.util.List;

/**
 * Created by 遐 on 2015/3/9.
 */
public interface PayOrderDomainService {

    /**
     * 支付付款单
     * @param poIds
     * @param loginId
     */
    int pay(List<Integer> poIds, int loginId);

    int payWithAuth(List<Integer> poIds, int loginId, String userName, String token);

    boolean handleBankPayResult(BankPayResultDTO bankPayResultDTO);
}
