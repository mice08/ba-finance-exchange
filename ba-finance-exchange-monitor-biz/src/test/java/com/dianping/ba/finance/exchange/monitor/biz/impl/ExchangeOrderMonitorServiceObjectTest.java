package com.dianping.ba.finance.exchange.monitor.biz.impl;

import com.dianping.ba.finance.exchange.monitor.api.datas.ExchangeOrderMonitorData;
import com.dianping.ba.finance.exchange.monitor.biz.dao.ExchangeOrderMonitorDao;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: 遐
 * Date: 14-3-28
 * Time: 上午11:16
 * To change this template use File | Settings | File Templates.
 */
public class ExchangeOrderMonitorServiceObjectTest {

    private ExchangeOrderMonitorServiceObject exchangeOrderMonitorServiceObjectStub;
    private ExchangeOrderMonitorDao exchangeOrderMonitorDaoMock;

    @Before
    public void runBeforeTest(){
        exchangeOrderMonitorDaoMock = mock(ExchangeOrderMonitorDao.class);
        exchangeOrderMonitorServiceObjectStub = new ExchangeOrderMonitorServiceObject();
        exchangeOrderMonitorServiceObjectStub.setExchangeOrderMonitorDao(exchangeOrderMonitorDaoMock);
    }

    @Test
    public void testFindExchangeOrderData(){
        when(exchangeOrderMonitorDaoMock.findExchangeOrderData(any(Date.class), any(Date.class))).thenReturn(new ArrayList<ExchangeOrderMonitorData>());

        List<ExchangeOrderMonitorData> actual = exchangeOrderMonitorServiceObjectStub.findExchangeOrderData(new Date(), new Date());

        Assert.assertNotNull(actual);
    }

    @Test
    public void testLoadExchangeOrderData(){
        ExchangeOrderMonitorData monitorData = new ExchangeOrderMonitorData();
        monitorData.setEoId(1);
        when(exchangeOrderMonitorDaoMock.loadExchangeOrderData(anyInt())).thenReturn(monitorData);

        ExchangeOrderMonitorData actual = exchangeOrderMonitorServiceObjectStub.loadExchangeOrderData(1);

        Assert.assertNotNull(actual);
        Assert.assertEquals(1, actual.getEoId());
    }
}
