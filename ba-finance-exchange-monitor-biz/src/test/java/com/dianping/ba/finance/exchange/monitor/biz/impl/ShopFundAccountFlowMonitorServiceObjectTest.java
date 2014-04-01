package com.dianping.ba.finance.exchange.monitor.biz.impl;

import com.dianping.ba.finance.exchange.monitor.api.datas.ShopFundAccountFlowMonitorData;
import com.dianping.ba.finance.exchange.monitor.biz.dao.ShopFundAccountFlowMonitorDao;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: 遐
 * Date: 14-3-28
 * Time: 上午11:21
 * To change this template use File | Settings | File Templates.
 */
public class ShopFundAccountFlowMonitorServiceObjectTest {

    private ShopFundAccountFlowMonitorServiceObject shopFundAccountFlowMonitorServiceObjectStub;
    private ShopFundAccountFlowMonitorDao shopFundAccountFlowMonitorDaoMock;

    @Before
    public void runBeforeTest(){
        shopFundAccountFlowMonitorDaoMock = mock(ShopFundAccountFlowMonitorDao.class);
        shopFundAccountFlowMonitorServiceObjectStub = new ShopFundAccountFlowMonitorServiceObject();
        shopFundAccountFlowMonitorServiceObjectStub.setShopFundAccountFlowMonitorDao(shopFundAccountFlowMonitorDaoMock);
    }

    @Test
    public void testFindShopFundAccountFlowData(){
        when(shopFundAccountFlowMonitorDaoMock.findShopFundAccountFlowData(anyInt())).thenReturn(new ArrayList<ShopFundAccountFlowMonitorData>());

        List<ShopFundAccountFlowMonitorData> actual = shopFundAccountFlowMonitorServiceObjectStub.findShopFundAccountFlowData(1);

        Assert.assertNotNull(actual);
    }
}
