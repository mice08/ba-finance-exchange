package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.beans.ReceiveOrderResultBean;
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;
import com.dianping.ba.finance.exchange.api.enums.ReceiveOrderStatus;
import com.dianping.ba.finance.exchange.biz.dao.ReceiveOrderDao;
import com.dianping.ba.finance.exchange.biz.producer.ReceiveOrderResultNotify;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ReceiveOrderServiceObjectTest {

    private ReceiveOrderServiceObject receiveOrderServiceObjectStub;

    private ReceiveOrderDao receiveOrderDaoMock;

    private ReceiveOrderResultNotify receiveOrderResultNotifyMock;

    @Before
    public void setUp() throws Exception {
        receiveOrderServiceObjectStub = new ReceiveOrderServiceObject();

        receiveOrderDaoMock = mock(ReceiveOrderDao.class);
        receiveOrderServiceObjectStub.setReceiveOrderDao(receiveOrderDaoMock);

        receiveOrderResultNotifyMock = mock(ReceiveOrderResultNotify.class);
        receiveOrderServiceObjectStub.setReceiveOrderResultNotify(receiveOrderResultNotifyMock);
    }

    @Test
    public void testCreateReceiveOrder() throws Exception {
        when(receiveOrderDaoMock.insertReceiveOrderData(any(ReceiveOrderData.class))).thenReturn(87);

        ReceiveOrderData roData = new ReceiveOrderData();
        roData.setCustomerId(123);
        roData.setShopId(123);
        roData.setStatus(ReceiveOrderStatus.CONFIRMED.value());
        int roId = receiveOrderServiceObjectStub.createReceiveOrder(roData);
        Assert.assertEquals(87, roId);
        Assert.assertEquals(87, roData.getRoId());

        verify(receiveOrderResultNotifyMock, times(1)).receiveResultNotify(any(ReceiveOrderResultBean.class));
    }

}