package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.datas.ShopFundAccountFlowData;
import com.dianping.ba.finance.exchange.api.dtos.ExchangeOrderDTO;
import com.dianping.ba.finance.exchange.api.dtos.ShopFundAccountFlowDTO;
import com.dianping.ba.finance.exchange.api.enums.ExchangeOrderStatusEnum;
import com.dianping.ba.finance.exchange.biz.dao.ShopFundAccountDAO;
import com.dianping.ba.finance.exchange.biz.dao.ShopFundAccountFlowDAO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: jinyu.yin
 * Date: 13-12-13
 * Time: 下午4:18
 * To change this template use File | Settings | File Templates.
 */
public class ShopFundAccountServiceObjectTest {
    private ShopFundAccountFlowDAO shopFundAccountFlowDAOMock;
    private ShopFundAccountDAO shopFundAccountDAOMock;
    private ShopFundAccountServiceObject shopFundAccountServiceObjectStub;

    @Before
    public void runBeforeTest() {
        shopFundAccountFlowDAOMock = mock(ShopFundAccountFlowDAO.class);
        shopFundAccountDAOMock = mock(ShopFundAccountDAO.class);
        shopFundAccountServiceObjectStub = new ShopFundAccountServiceObject();
        shopFundAccountServiceObjectStub.setShopFundAccountFlowDAO(shopFundAccountFlowDAOMock);
        shopFundAccountServiceObjectStub.setShopFundAccountDAO(shopFundAccountDAOMock);
    }

    @Test
    public void createShopFundAccountFlowIsNull(){
        int actual=shopFundAccountServiceObjectStub.createShopFundAccountFlow(null);
        Assert.assertEquals(-1,actual);
    }

    @Test
    public void createShopFundAccountFlow(){
        int actual=shopFundAccountServiceObjectStub.createShopFundAccountFlow(null);
        Assert.assertEquals(-1,actual);
    }

    @Test
    public void testUpdateShopFundAccountCausedByExchangeOrderSuccess(){
        ExchangeOrderDTO exchangeOrder = new ExchangeOrderDTO();
        exchangeOrder.setStatus(ExchangeOrderStatusEnum.Success.getExchangeOrderStatus());
        exchangeOrder.setExchangeOrderId(1);
        exchangeOrder.setOrderAmount(BigDecimal.TEN);

        Assert.assertTrue(shopFundAccountServiceObjectStub.updateShopFundAccountCausedByExchangeOrderSuccess(exchangeOrder));
    }

    @Test
    public void testUpdateShopFundAccountCausedByExchangeOrderSuccessOfNotSuccess(){
        ExchangeOrderDTO exchangeOrder = new ExchangeOrderDTO();
        exchangeOrder.setStatus(ExchangeOrderStatusEnum.Fail.getExchangeOrderStatus());
        exchangeOrder.setExchangeOrderId(1);
        exchangeOrder.setOrderAmount(BigDecimal.TEN);

        Assert.assertFalse(shopFundAccountServiceObjectStub.updateShopFundAccountCausedByExchangeOrderSuccess(exchangeOrder));
    }

    @Test
    public void testUpdateShopFundAccountCausedByExchangeOrderSuccessOfNull(){
        ExchangeOrderDTO exchangeOrder = null;

        Assert.assertFalse(shopFundAccountServiceObjectStub.updateShopFundAccountCausedByExchangeOrderSuccess(exchangeOrder));
    }

    @Test
    public void testGetPaymentPlanShopFundAccountFlow()
    {
        ExchangeOrderDTO exchangeOrder = new ExchangeOrderDTO();

        ShopFundAccountFlowData shopFundAccountFlowData = new ShopFundAccountFlowData();
        shopFundAccountFlowData.setFundAccountId(1);

        when(shopFundAccountFlowDAOMock.loadShopFundAccountFlow(anyInt(),anyInt(),anyInt())).thenReturn(shopFundAccountFlowData);

        ShopFundAccountFlowDTO actual = shopFundAccountServiceObjectStub.getPaymentPlanShopFundAccountFlow(exchangeOrder);

        Assert.assertEquals(shopFundAccountFlowData.getFundAccountId(), actual.getFundAccountId());
    }

    @Test
    public void testGetPaymentPlanShopFundAccountFlowOfNull()
    {
        ExchangeOrderDTO exchangeOrder = null;

        ShopFundAccountFlowDTO actual = shopFundAccountServiceObjectStub.getPaymentPlanShopFundAccountFlow(exchangeOrder);

        Assert.assertNull(actual);
    }
}
