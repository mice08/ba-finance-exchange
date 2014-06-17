package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;
import com.dianping.ba.finance.exchange.biz.dao.ReceiveOrderDao;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReceiveOrderServiceObjectTest {

    private ReceiveOrderServiceObject receiveOrderServiceObjectStub;

    private ReceiveOrderDao receiveOrderDaoMock;

    @Before
    public void setUp() throws Exception {
        receiveOrderServiceObjectStub = new ReceiveOrderServiceObject();
        receiveOrderDaoMock = mock(ReceiveOrderDao.class);
        receiveOrderServiceObjectStub.setReceiveOrderDao(receiveOrderDaoMock);
    }

    @Test
    public void testCreateReceiveOrder() throws Exception {
        when(receiveOrderDaoMock.insertReceiveOrderData(any(ReceiveOrderData.class))).thenReturn(87);

        ReceiveOrderData roData = new ReceiveOrderData();
        roData.setCustomerId(123);
        roData.setShopId(123);
        int roId = receiveOrderServiceObjectStub.createReceiveOrder(roData);
        Assert.assertEquals(87, roId);
        Assert.assertEquals(87, roData.getRoId());

    }
}