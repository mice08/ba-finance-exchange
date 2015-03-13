package com.dianping.ba.finance.exchange.bankpaymonitor.biz.impl;

import com.dianping.ba.finance.exchange.bankpaymonitor.api.datas.PayOrderMonitorData;
import com.dianping.ba.finance.exchange.bankpaymonitor.biz.daos.PayOrderDao;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PayOrderServiceObjectTest {

    private PayOrderServiceObject payOrderServiceObjectStub;
    private PayOrderDao payOrderDaoMock;

    @Before
    public void setUp() throws Exception {
        payOrderServiceObjectStub = new PayOrderServiceObject();
        payOrderDaoMock = mock(PayOrderDao.class);
        payOrderServiceObjectStub.setPayOrderDao(payOrderDaoMock);
    }

    @Test
    public void testFindPayOrders() throws Exception {
        when(payOrderDaoMock.findPayOrders(anyInt(), anyInt(), any(Date.class), any(Date.class), anyListOf(Integer.class))).thenReturn(new ArrayList<PayOrderMonitorData>());
        Assert.assertNotNull(payOrderServiceObjectStub.findPayOrders(1,1,new Date(), new Date()));
    }
}