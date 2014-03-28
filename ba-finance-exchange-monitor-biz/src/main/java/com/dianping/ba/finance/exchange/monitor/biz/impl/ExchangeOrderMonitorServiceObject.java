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
    public List<ExchangeOrderMonitorData> findExchangeOrderData(Date startDate, Date endDate) {
        return exchangeOrderMonitorDao.findExchangeOrderData(startDate, endDate);
    }

    @Override
    public ExchangeOrderMonitorData loadExchangeOrderData(int eoId) {
        return exchangeOrderMonitorDao.loadExchangeOrderData(eoId);
    }


    public void setExchangeOrderMonitorDao(ExchangeOrderMonitorDao exchangeOrderMonitorDao) {
        this.exchangeOrderMonitorDao = exchangeOrderMonitorDao;
    }
}
