package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.PayOrderService;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;

/**
 * 处理付款单的Service类
 */
public class PayOrderServiceObject implements PayOrderService {

    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.biz.monitor.PayOrderServiceObject");

    @Override
    public int createPayPlan(PayOrderData payOrderData){
        return 1;
    }
}
