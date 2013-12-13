package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.biz.dao.ExchangeOrderDAO;
import com.dianping.ba.finance.exchange.biz.dao.ShopFundAccountFlowDAO;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

/**
 * Created with IntelliJ IDEA.
 * User: 遐
 * Date: 13-12-13
 * Time: 下午12:39
 * To change this template use File | Settings | File Templates.
 */
public class ExchangeOrderServiceObjectTest {

    private ExchangeOrderDAO exchangeOrderDaoMock;
    private ShopFundAccountFlowDAO shopFundAccountFlowDAOMock;
    private ExchangeOrderServiceObject exchangeOrderServiceObjectStub;

    @Before
    public void runBeforeTest() {
        exchangeOrderDaoMock = mock(ExchangeOrderDAO.class);
        shopFundAccountFlowDAOMock = mock(ShopFundAccountFlowDAO.class);

        exchangeOrderServiceObjectStub = new ExchangeOrderServiceObject();
        exchangeOrderServiceObjectStub.setExchangeOrderDao(exchangeOrderDaoMock);
        exchangeOrderServiceObjectStub.setShopFundAccountFlowDAO(shopFundAccountFlowDAOMock);

    }

    @Test
    public void testUpdateExchangeOrderSuccess(){

    }

}
