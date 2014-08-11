package com.dianping.ba.finance.exchange.paymonitor.biz.impl;

import com.dianping.ba.finance.exchange.paymonitor.api.PayOrderService;
import com.dianping.ba.finance.exchange.paymonitor.api.datas.PayOrderMonitorData;
import com.dianping.ba.finance.exchange.paymonitor.biz.dao.PayOrderDao;

/**
 * Created by adam.huang on 2014/8/7.
 */
public class PayOrderServiceObject implements PayOrderService{
    private PayOrderDao payOrderDao;

    @Override
    public PayOrderMonitorData getPayOrderBySequence(String paySequence) {
        return payOrderDao.getPayOrderBySequence(paySequence);
    }

    public void setPayOrderDao(PayOrderDao payOrderDao) {
        this.payOrderDao = payOrderDao;
    }
}
