package com.dianping.ba.finance.exchange.paymonitor.biz.impl;

import com.dianping.ba.finance.exchange.paymonitor.api.PayPlanService;
import com.dianping.ba.finance.exchange.paymonitor.api.datas.PayPlanMonitorData;
import com.dianping.ba.finance.exchange.paymonitor.biz.dao.PayPlanDao;

import java.util.Date;
import java.util.List;

/**
 * Created by adam.huang on 2014/8/7.
 */
public class PayPlanServiceObject implements PayPlanService {

    private PayPlanDao payPlanDao;

    @Override
    public List<PayPlanMonitorData> findPayPlansByDate(Date startDate, Date endDate) {
        return payPlanDao.findPayPlansByDate(startDate,endDate);
    }

    @Override
    public PayPlanMonitorData getPayPlanById(int ppId) {
        return payPlanDao.getPayPlanById(ppId);
    }

    @Override
    public String getPaySequenceById(int ppId) {
        return payPlanDao.getPaySequenceById(ppId);
    }

    public void setPayPlanDao(PayPlanDao payPlanDao) {
        this.payPlanDao = payPlanDao;
    }
}
