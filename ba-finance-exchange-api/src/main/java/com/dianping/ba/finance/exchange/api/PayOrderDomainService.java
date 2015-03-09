package com.dianping.ba.finance.exchange.api;

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
    void pay(List<Integer> poIds, int loginId);
}
