package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.ba.finance.exchange.biz.dao.PayOrderDao;
import com.dianping.ba.finance.exchange.biz.producer.PayOrderResultNotify;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class PayOrderServiceObjectTest {
    private PayOrderServiceObject payOrderServiceObjectStub;
    private PayOrderDao payOrderDaoMock;
    private PayOrderResultNotify payOrderResultNotifyMock;

    @Before
    public void before() {
        payOrderDaoMock = mock(PayOrderDao.class);
        payOrderResultNotifyMock=mock(PayOrderResultNotify.class);
        payOrderServiceObjectStub = new PayOrderServiceObject();
        payOrderServiceObjectStub.setPayOrderDao(payOrderDaoMock);
        payOrderServiceObjectStub.setPayOrderResultNotify(payOrderResultNotifyMock);
    }

    @Test
    public void testCreatePaOrder() {
        PayOrderData payOrderData = new PayOrderData();
        payOrderData.setAddLoginId(-1);
        payOrderData.setBusinessType(1);
        payOrderData.setPaySequence("1");

        when(payOrderDaoMock.insertPayOrder(any(PayOrderData.class))).thenReturn(2);

        int poId = payOrderServiceObjectStub.createPayOrder(payOrderData);
        Assert.assertEquals(2, poId);

        when(payOrderDaoMock.insertPayOrder(any(PayOrderData.class))).thenThrow(new RuntimeException("test"));
        poId = payOrderServiceObjectStub.createPayOrder(payOrderData);
        Assert.assertEquals(-1, poId);
    }

    @Test
    public void testUpdatePayOrderToPaying() {
        List<Integer> poIds = new ArrayList<Integer>();
        poIds.add(1);
        poIds.add(2);
        poIds.add(3);

        when(payOrderDaoMock.updatePayOrders(anyListOf(Integer.class), anyInt(), anyInt(), any(Date.class),anyInt())).thenReturn(3);

        int actual = payOrderServiceObjectStub.updatePayOrderToPaying(poIds, -1);
        Assert.assertEquals(3, actual);

        when(payOrderDaoMock.updatePayOrders(anyListOf(Integer.class), anyInt(), anyInt(), any(Date.class),anyInt())).thenThrow(new RuntimeException("test"));
        actual = payOrderServiceObjectStub.updatePayOrderToPaying(poIds, -1);
        Assert.assertEquals(-1, actual);
    }

    @Test
    public void testUpdatePayOrderToPaySuccess() {
        List<Integer> poIds = new ArrayList<Integer>();
        poIds.add(1);
        poIds.add(2);
        poIds.add(3);

        when(payOrderDaoMock.updatePayOrders(anyListOf(Integer.class), anyInt(), anyInt(), any(Date.class),anyInt())).thenReturn(3);

        int actual = payOrderServiceObjectStub.updatePayOrderToPaySuccess(poIds, -1);
        Assert.assertEquals(3, actual);

        when(payOrderDaoMock.updatePayOrders(anyListOf(Integer.class), anyInt(), anyInt(), any(Date.class),anyInt())).thenThrow(new RuntimeException("test"));
        actual = payOrderServiceObjectStub.updatePayOrderToPaySuccess(poIds, -1);
        Assert.assertEquals(-1, actual);
    }
}
