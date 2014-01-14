package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.beans.ExchangeOrderSearchBean;
import com.dianping.ba.finance.exchange.api.beans.GenericResult;
import com.dianping.ba.finance.exchange.api.datas.ExchangeOrderData;
import com.dianping.ba.finance.exchange.api.datas.ExchangeOrderSearchStatistics;
import com.dianping.ba.finance.exchange.api.datas.ShopFundAccountFlowData;
import com.dianping.ba.finance.exchange.api.enums.ExchangeOrderStatus;
import com.dianping.ba.finance.exchange.biz.dao.ExchangeOrderDao;
import com.dianping.ba.finance.exchange.biz.producer.ExchangeOrderStatusChangeNotify;
import com.dianping.core.type.PageModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: 遐
 * Date: 13-12-13
 * Time: 下午12:39
 * To change this template use File | Settings | File Templates.
 */
public class ExchangeOrderServiceObjectTest {
    private ExchangeOrderDao exchangeOrderDaoMock;
    private ExchangeOrderStatusChangeNotify exchangeOrderStatusChangeNotifyMock;
    private ExchangeOrderServiceObject exchangeOrderServiceObjectStub;

    @Before
    public void runBeforeTest() {
        exchangeOrderDaoMock = mock(ExchangeOrderDao.class);
        exchangeOrderStatusChangeNotifyMock = mock(ExchangeOrderStatusChangeNotify.class);

        exchangeOrderServiceObjectStub = new ExchangeOrderServiceObject();
        exchangeOrderServiceObjectStub.setExchangeOrderDao(exchangeOrderDaoMock);
        exchangeOrderServiceObjectStub.setExchangeOrderStatusChangeNotify(exchangeOrderStatusChangeNotifyMock);

    }

    @Test
    public void testUpdateExchangeOrderSuccess(){
        ExchangeOrderData exchangeOrderData = new ExchangeOrderData();
        exchangeOrderData.setStatus(ExchangeOrderStatus.PENDING.getExchangeOrderStatus());

        List<Integer> orderIds = new ArrayList<Integer>();
        orderIds.add(1);
        orderIds.add(2);
        orderIds.add(3);

        when(exchangeOrderDaoMock.loadExchangeOrderByOrderId(anyInt())).thenReturn(exchangeOrderData);
        when(exchangeOrderDaoMock.updateExchangeOrderData(anyInt(), any(Date.class), anyInt())).thenReturn(1);

        GenericResult<Integer> result = exchangeOrderServiceObjectStub.updateExchangeOrderToSuccess(orderIds);

        Assert.assertEquals(3,result.getSuccessList().size());
        Assert.assertEquals(0,result.getFailList().size());
        List<Integer> actualIds = result.getSuccessList();
        Assert.assertArrayEquals(orderIds.toArray(new Integer[3]),actualIds.toArray(new Integer[3]));

    }

    @Test
    public void testUpdateExchangeOrderFailWhenOrderIdInvalid(){
        ExchangeOrderData exchangeOrderData = new ExchangeOrderData();
        exchangeOrderData.setStatus(ExchangeOrderStatus.PENDING.getExchangeOrderStatus());

        ShopFundAccountFlowData shopFundAccountFlowData = new ShopFundAccountFlowData();
        shopFundAccountFlowData.setFundAccountId(1);

        List<Integer> orderIds = new ArrayList<Integer>();
        orderIds.add(-1);
        orderIds.add(2);
        orderIds.add(3);

        when(exchangeOrderDaoMock.loadExchangeOrderByOrderId(anyInt())).thenReturn(exchangeOrderData);
        when(exchangeOrderDaoMock.updateExchangeOrderData(anyInt(),any(Date.class),anyInt())).thenReturn(1);

        GenericResult<Integer> result = exchangeOrderServiceObjectStub.updateExchangeOrderToSuccess(orderIds);

        Assert.assertEquals(1, result.getFailList().size());
        Assert.assertEquals(-1, result.getFailList().get(0).intValue());

    }

    @Test
    public void testUpdateExchangeOrderSuccessWhenExchangeTypeIsSuccess(){
        ExchangeOrderData exchangeOrderData = new ExchangeOrderData();
        exchangeOrderData.setStatus(ExchangeOrderStatus.SUCCESS.getExchangeOrderStatus());

        ShopFundAccountFlowData shopFundAccountFlowData = new ShopFundAccountFlowData();
        shopFundAccountFlowData.setFundAccountId(1);

        List<Integer> orderIds = new ArrayList<Integer>();
        orderIds.add(1);
        orderIds.add(2);
        orderIds.add(3);

        when(exchangeOrderDaoMock.loadExchangeOrderByOrderId(anyInt())).thenReturn(exchangeOrderData);

        GenericResult<Integer> result = exchangeOrderServiceObjectStub.updateExchangeOrderToSuccess(orderIds);

        Assert.assertEquals(0,result.getSuccessList().size());
        Assert.assertEquals(3,result.getFailList().size());
        List<Integer> actualIds = result.getFailList();
        Assert.assertArrayEquals(orderIds.toArray(new Integer[3]),actualIds.toArray(new Integer[3]));

    }

    @Test
    public void testUpdateExchangeOrderFailed(){
        ExchangeOrderData exchangeOrderData = new ExchangeOrderData();
        exchangeOrderData.setStatus(ExchangeOrderStatus.INIT.getExchangeOrderStatus());

        List<Integer> orderIds = new ArrayList<Integer>();
        orderIds.add(1);
        orderIds.add(2);
        orderIds.add(3);

        when(exchangeOrderDaoMock.loadExchangeOrderByOrderId(anyInt())).thenReturn(exchangeOrderData);
        when(exchangeOrderDaoMock.updateExchangeOrderData(anyInt(), any(Date.class), anyInt())).thenReturn(-1);

        GenericResult<Integer> result = exchangeOrderServiceObjectStub.updateExchangeOrderToSuccess(orderIds);

        Assert.assertEquals(0,result.getSuccessList().size());
        Assert.assertEquals(3,result.getFailList().size());
        List<Integer> actualIds = result.getFailList();
        Assert.assertArrayEquals(orderIds.toArray(new Integer[3]),actualIds.toArray(new Integer[3]));

    }
    @Test
    public void testUpdateExchangeOrderSuccessWhenExchangeOrderNotExist(){

        ShopFundAccountFlowData shopFundAccountFlowData = new ShopFundAccountFlowData();
        shopFundAccountFlowData.setFundAccountId(1);

        List<Integer> orderIds = new ArrayList<Integer>();
        orderIds.add(1);
        orderIds.add(2);
        orderIds.add(3);

        when(exchangeOrderDaoMock.loadExchangeOrderByOrderId(anyInt())).thenReturn(null);

        GenericResult<Integer> result = exchangeOrderServiceObjectStub.updateExchangeOrderToSuccess(orderIds);

        Assert.assertEquals(0,result.getSuccessList().size());
        Assert.assertEquals(3,result.getFailList().size());
        List<Integer> actualIds = result.getFailList();
        Assert.assertArrayEquals(orderIds.toArray(new Integer[3]),actualIds.toArray(new Integer[3]));

    }

    @Test
    public void testInsertExchangeOrder(){
        ExchangeOrderData exchangeOrderData = new ExchangeOrderData();
        when(exchangeOrderDaoMock.insertExchangeOrder(exchangeOrderData)).thenReturn(1);
        int actual=exchangeOrderServiceObjectStub.insertExchangeOrder(exchangeOrderData);
        Assert.assertEquals(1,actual);
    }

    @Test
    public void testPaginateExchangeOrderList() {
        PageModel pageModel = new PageModel();
        when(exchangeOrderDaoMock.paginateExchangeOrderList(any(ExchangeOrderSearchBean.class), anyInt(), anyInt()))
                .thenReturn(pageModel);
        PageModel result = exchangeOrderServiceObjectStub.paginateExchangeOrderList(new ExchangeOrderSearchBean(), 1, 20);
        Assert.assertNotNull(result);
    }

    @Test
    public void testGetExchangeOrderStatisticResult() {
        ExchangeOrderSearchStatistics result = new ExchangeOrderSearchStatistics();
        result.setTotalAmount(new BigDecimal(1.0));
        result.setTotalCount(10);
        when(exchangeOrderDaoMock.getExchangeOrderStatisticResult(any(ExchangeOrderSearchBean.class)))
                .thenReturn(result);
        ExchangeOrderSearchStatistics result2 = exchangeOrderServiceObjectStub.getExchangeOrderStatisticResult(new ExchangeOrderSearchBean());
        Assert.assertEquals(new BigDecimal(1.0), result2.getTotalAmount());
        Assert.assertEquals(10, result2.getTotalCount());

    }

    @Test
    public void testUpdateExchangeOrderToPending() {
        List<Integer> orderIds = new ArrayList<Integer>();
        orderIds.add(1);
        orderIds.add(2);
        orderIds.add(3);

        when(exchangeOrderDaoMock.updateExchangeOrderToPending(anyListOf(Integer.class), anyInt())).thenReturn(3);

        boolean actual=exchangeOrderServiceObjectStub.updateExchangeOrderToPending(orderIds, ExchangeOrderStatus.PENDING);
        Assert.assertEquals(true, actual);
    }

    @Test
    public void testUpdateExchangeOrderToPendingFalse() {
        List<Integer> orderIds = new ArrayList<Integer>();
        orderIds.add(1);
        orderIds.add(2);
        orderIds.add(3);

        when(exchangeOrderDaoMock.updateExchangeOrderToPending(anyListOf(Integer.class), anyInt())).thenReturn(0);

        boolean actual=exchangeOrderServiceObjectStub.updateExchangeOrderToPending(orderIds, ExchangeOrderStatus.PENDING);
        Assert.assertEquals(false, actual);
    }

    @Test
    public void testUpdateExchangeOrderToPendingError() {
        List<Integer> orderIds = new ArrayList<Integer>();

        boolean actual=exchangeOrderServiceObjectStub.updateExchangeOrderToPending(orderIds, ExchangeOrderStatus.PENDING);
        Assert.assertEquals(false, actual);
    }
}
