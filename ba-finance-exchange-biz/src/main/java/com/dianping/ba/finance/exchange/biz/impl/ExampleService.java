package com.dianping.ba.finance.exchange.biz.impl;


import com.dianping.ba.finance.exchange.api.datas.ExchangeOrderData;
import com.dianping.ba.finance.exchange.biz.dao.ExchangeOrderDao;

import java.util.List;

public class ExampleService {

    private String serviceName;

    private ExchangeOrderDao exchangeOrderDao;

    public String hello(String from) {
        return String.format("From=%s, ServiceName=%s", from, serviceName);
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public List<ExchangeOrderData> findExchangeOrderListByOrderIdList(List<Integer> orderIdList) {
        return exchangeOrderDao.findExchangeOrderListByOrderIdList(orderIdList);
    }


    public void setExchangeOrderDao(ExchangeOrderDao exchangeOrderDao) {
        this.exchangeOrderDao = exchangeOrderDao;
    }
}
