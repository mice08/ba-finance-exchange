package com.dianping.ba.finance.exchange.monitor.job.eocheck.rule;

import com.dianping.ba.finance.exchange.monitor.api.ShopFundAccountFlowMonitorService;
import com.dianping.ba.finance.exchange.monitor.api.datas.ExchangeOrderMonitorData;
import com.dianping.ba.finance.exchange.monitor.api.datas.ShopFundAccountFlowMonitorData;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: 遐
 * Date: 14-3-28
 * Time: 上午11:31
 * To change this template use File | Settings | File Templates.
 */
public class EORefundFlowCheckTest {

    private ShopFundAccountFlowMonitorService shopFundAccountFlowMonitorServiceMock;
    private EORefundFlowCheck eoRefundFlowCheckStub;

    @Before
    public void runBeforeTest(){
        shopFundAccountFlowMonitorServiceMock = mock(ShopFundAccountFlowMonitorService.class);
        eoRefundFlowCheckStub = new EORefundFlowCheck();
        eoRefundFlowCheckStub.setShopFundAccountFlowMonitorService(shopFundAccountFlowMonitorServiceMock);
    }

    @Test
    public void testCheckWhenNoFlowFound(){
        ExchangeOrderMonitorData monitorData = new ExchangeOrderMonitorData();
        monitorData.setEoId(1);
        monitorData.setLastUpdateDate(new Date());
        List<ShopFundAccountFlowMonitorData> shopFundAccountFlowMonitorDataList = new ArrayList<ShopFundAccountFlowMonitorData>();

        when(shopFundAccountFlowMonitorServiceMock.findShopFundAccountFlowData(anyInt())).thenReturn(shopFundAccountFlowMonitorDataList);
        eoRefundFlowCheckStub.check(monitorData);
    }

    @Test
    public void testCheckWhenFlowNumberNotCorrect(){
        ExchangeOrderMonitorData monitorData = new ExchangeOrderMonitorData();
        monitorData.setEoId(1);
        monitorData.setLastUpdateDate(new Date());
        List<ShopFundAccountFlowMonitorData> shopFundAccountFlowMonitorDataList = new ArrayList<ShopFundAccountFlowMonitorData>();
        shopFundAccountFlowMonitorDataList.add(new ShopFundAccountFlowMonitorData());

        when(shopFundAccountFlowMonitorServiceMock.findShopFundAccountFlowData(anyInt())).thenReturn(shopFundAccountFlowMonitorDataList);
        eoRefundFlowCheckStub.check(monitorData);
    }
}
