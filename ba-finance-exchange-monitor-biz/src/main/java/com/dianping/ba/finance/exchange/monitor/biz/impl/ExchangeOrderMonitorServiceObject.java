package com.dianping.ba.finance.exchange.monitor.biz.impl;

import com.dianping.ba.finance.exchange.monitor.api.ExchangeOrderMonitorService;
import com.dianping.ba.finance.exchange.monitor.api.datas.ExchangeOrderMonitorData;
import com.dianping.ba.finance.exchange.monitor.api.enums.ExchangeOrderStatus;
import com.dianping.ba.finance.exchange.monitor.biz.dao.ExchangeOrderMonitorDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: noahshen
 * Date: 14-3-26
 * Time: 下午3:09
 * To change this template use File | Settings | File Templates.
 */
public class ExchangeOrderMonitorServiceObject implements ExchangeOrderMonitorService {

    private ExchangeOrderMonitorDao exchangeOrderMonitorDao;

    @Override
    public List<ExchangeOrderMonitorData> findPendIngAndInitExchangeOrderDatas(Date startDate, Date endDate) {
        List<Integer> statusList = new ArrayList<Integer>();
        statusList.add(ExchangeOrderStatus.PENDING.value());
        statusList.add(ExchangeOrderStatus.INIT.value());
        return exchangeOrderMonitorDao.findExchangeOrderDatas(startDate, endDate, statusList);
    }

    @Override
    public List<ExchangeOrderMonitorData> findSuccessExchangeOrderDatas(Date startDate, Date endDate) {
        List<Integer> statusList = new ArrayList<Integer>();
        statusList.add(ExchangeOrderStatus.SUCCESS.value());
        return exchangeOrderMonitorDao.findExchangeOrderDatas(startDate, endDate, statusList);
    }

    @Override
    public List<ExchangeOrderMonitorData> findPayFailExchangeOrderDatas(Date startDate, Date endDate) {
        List<Integer> statusList = new ArrayList<Integer>();
        statusList.add(ExchangeOrderStatus.FAIL.value());
        return exchangeOrderMonitorDao.findExchangeOrderDatas(startDate, endDate, statusList);
    }

    public void setExchangeOrderMonitorDao(ExchangeOrderMonitorDao exchangeOrderMonitorDao) {
        this.exchangeOrderMonitorDao = exchangeOrderMonitorDao;
    }
}
