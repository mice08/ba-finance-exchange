package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.beans.PayOrderSearchBean;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.ba.finance.exchange.biz.dao.PayOrderDao;
import com.dianping.core.type.PageModel;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

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
    public void testCreatePayOrder() {
        PayOrderData payOrderData = new PayOrderData();
        payOrderData.setAddLoginId(-1);
        payOrderData.setBusinessType(1);
        payOrderData.setPaySequence("1");

        when(payOrderDaoMock.insertPayOrder(any(PayOrderData.class))).thenReturn(2);

        int poId = payOrderServiceObjectStub.createPayOrder(payOrderData);
        Assert.assertEquals(2, poId);
    }

    @Test
    public void testCreatePayOrderFailed() {
        PayOrderData payOrderData = new PayOrderData();
        payOrderData.setAddLoginId(-1);
        payOrderData.setBusinessType(1);
        payOrderData.setPaySequence("1");

        when(payOrderDaoMock.insertPayOrder(any(PayOrderData.class))).thenThrow(new RuntimeException("test"));
        int poId = payOrderServiceObjectStub.createPayOrder(payOrderData);
        Assert.assertEquals(-1, poId);
        verify(payOrderDaoMock, times(5)).insertPayOrder(any(PayOrderData.class));
    }

    @Test
    public void testPaginatePayOrderList(){
        when(payOrderDaoMock.paginatePayOrderList(any(PayOrderSearchBean.class),anyInt(),anyInt())).thenReturn(new PageModel());
        PageModel pageModel=payOrderServiceObjectStub.paginatePayOrderList(new PayOrderSearchBean(),1,1);
        Assert.assertNotNull(pageModel);
    }

    @Test
    public void testFindPayOrderTotalAmount(){
        when(payOrderDaoMock.findPayOrderTotalAmountByCondition(any(PayOrderSearchBean.class))).thenReturn(BigDecimal.ONE);
        BigDecimal amount=payOrderServiceObjectStub.findPayOrderTotalAmount(new PayOrderSearchBean());
        Assert.assertEquals(amount.compareTo(BigDecimal.ONE),0);
    }

}
