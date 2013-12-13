package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.datas.ShopFundAccountFlowData;
import com.dianping.ba.finance.exchange.api.dtos.ExchangeOrderDTO;
import com.dianping.ba.finance.exchange.api.enums.ExchangeOrderStatusEnum;
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
    private ShopFundAccountServiceObject shopFundAccountServiceObjectStub;

    @Before
    public void runBeforeTest() {
        shopFundAccountFlowDAOMock = mock(ShopFundAccountFlowDAO.class);
        shopFundAccountServiceObjectStub = new ShopFundAccountServiceObject();
        shopFundAccountServiceObjectStub.setShopFundAccountFlowDAO(shopFundAccountFlowDAOMock);
    }

    @Test
    public void testUpdateShopFundAccountCausedByExchangeOrderSuccess(){

        ExchangeOrderDTO exchangeOrder = new ExchangeOrderDTO();
        exchangeOrder.setStatus(ExchangeOrderStatusEnum.Success.getExchangeOrderStatus());
        exchangeOrder.setExchangeOrderId(1);
        exchangeOrder.setOrderAmount(BigDecimal.TEN);

        ShopFundAccountFlowData shopFundAccountFlowData = new ShopFundAccountFlowData();
        shopFundAccountFlowData.setFundAccountId(1);

        when(shopFundAccountFlowDAOMock.loadShopFundAccountFlow(anyInt(),anyInt(),anyInt())).thenReturn(shopFundAccountFlowData);

        Assert.assertTrue(shopFundAccountServiceObjectStub.updateShopFundAccountCausedByExchangeOrderSuccess(exchangeOrder));
    }
}
