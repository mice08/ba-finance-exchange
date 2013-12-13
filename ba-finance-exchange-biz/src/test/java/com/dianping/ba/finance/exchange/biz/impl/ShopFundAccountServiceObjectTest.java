package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.biz.dao.ShopFundAccountDAO;
import com.dianping.ba.finance.exchange.biz.dao.ShopFundAccountFlowDAO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: bingqiu.yuan
 * Date: 13-12-13
 * Time: 下午20:42
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


}
