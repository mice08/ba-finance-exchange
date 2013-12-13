package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.beans.GenericResult;
import com.dianping.ba.finance.exchange.api.datas.ExchangeOrderData;
import com.dianping.ba.finance.exchange.api.datas.ShopFundAccountFlowData;
import com.dianping.ba.finance.exchange.biz.dao.ExchangeOrderDAO;
import com.dianping.ba.finance.exchange.biz.dao.ShopFundAccountFlowDAA;
import com.dianping.ba.finance.exchange.biz.producer.ExchangeOrderStatusChangeNotify;
import com.dianping.ba.finance.exchange.api.enums.ExchangeType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
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
 * Date: 13-12-13
 * Time: 下午12:39
 * To change this template use File | Settings | File Templates.
 */
public class ExchangeOrderServiceObjectTest {

    private ExchangeOrderDAO exchangeOrderDaoMock;
    private ShopFundAccountFlowDAA shopFundAccountFlowDAAMock;
    private ExchangeOrderStatusChangeNotify exchangeOrderStatusChangeNotifyStub;
    private ExchangeOrderServiceObject exchangeOrderServiceObjectStub;

    @Before
    public void runBeforeTest() {
        exchangeOrderDaoMock = mock(ExchangeOrderDAO.class);
        shopFundAccountFlowDAAMock = mock(ShopFundAccountFlowDAA.class);
        exchangeOrderStatusChangeNotifyStub = mock(ExchangeOrderStatusChangeNotify.class);

        exchangeOrderServiceObjectStub = new ExchangeOrderServiceObject();
        exchangeOrderServiceObjectStub.setExchangeOrderDao(exchangeOrderDaoMock);
        exchangeOrderServiceObjectStub.setShopFundAccountFlowDAA(shopFundAccountFlowDAAMock);
        exchangeOrderServiceObjectStub.setExchangeOrderStatusChangeNotify(exchangeOrderStatusChangeNotifyStub);

    }

    @Test
    public void testUpdateExchangeOrderSuccess(){
        ExchangeOrderData exchangeOrderData = new ExchangeOrderData();
        exchangeOrderData.setStatus(ExchangeType.Init.getExchangeType());
        exchangeOrderData.setExchangeOrderId(1);
        exchangeOrderData.setOrderAmount(BigDecimal.TEN);

        ShopFundAccountFlowData shopFundAccountFlowData = new ShopFundAccountFlowData();
        shopFundAccountFlowData.setFundAccountId(1);

        List<Integer> orderIds = new ArrayList<Integer>();
        orderIds.add(1);
        orderIds.add(2);
        orderIds.add(3);

        when(exchangeOrderDaoMock.loadExchangeOrderByOrderId(anyInt())).thenReturn(exchangeOrderData);
        when(exchangeOrderDaoMock.updateExchangeOrderData(anyInt(),any(Date.class),anyInt())).thenReturn(true);
        when(shopFundAccountFlowDAAMock.loadShopFundAccountFlow(anyInt(),anyInt(),anyInt())).thenReturn(shopFundAccountFlowData);

        GenericResult<Integer> result = exchangeOrderServiceObjectStub.updateExchangeOrderToSuccess(orderIds);

        Assert.assertEquals(3,result.getSuccessList().size());
        Assert.assertEquals(0,result.getFailList().size());
        Assert.assertEquals(0,result.getUnprocessedList().size());
        List<Integer> actualIds = result.getSuccessList();
        Assert.assertArrayEquals(orderIds.toArray(new Integer[3]),actualIds.toArray(new Integer[3]));

    }

}
