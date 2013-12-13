package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.biz.dao.ShopFundAccountFlowDAO;
import org.junit.Before;

import static org.mockito.Mockito.mock;

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
}
