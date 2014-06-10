package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.ba.finance.exchange.biz.dao.PayOrderDao;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class PayOrderServiceObjectTest {
    private PayOrderServiceObject payOrderServiceObjectStub;
    private PayOrderDao payOrderDaoMock;

    @Before
    public void before() {
        payOrderDaoMock = mock(PayOrderDao.class);
        payOrderServiceObjectStub = new PayOrderServiceObject();
        payOrderServiceObjectStub.setPayOrderDao(payOrderDaoMock);
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
}
