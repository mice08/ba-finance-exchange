package com.dianping.ba.finance.exchange.monitor.job.eocheck.Rule;

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
 * Time: 上午11:51
 * To change this template use File | Settings | File Templates.
 */
public class EOSuccessFlowCheckTest {

    private EOSuccessFlowCheck eoSuccessFlowCheckStub;
    private ShopFundAccountFlowMonitorService shopFundAccountFlowMonitorServiceMock;

    @Before
    public void runBeforeTest(){
        eoSuccessFlowCheckStub = new EOSuccessFlowCheck();
        shopFundAccountFlowMonitorServiceMock = mock(ShopFundAccountFlowMonitorService.class);
        eoSuccessFlowCheckStub.setShopFundAccountFlowMonitorService(shopFundAccountFlowMonitorServiceMock);
    }

    @Test
    public void testCheckWhenFlowMissing(){
        ExchangeOrderMonitorData monitorData = new ExchangeOrderMonitorData();
        monitorData.setEoId(1);
        monitorData.setLastUpdateDate(new Date());
        List<ShopFundAccountFlowMonitorData> flowDataList = new ArrayList<ShopFundAccountFlowMonitorData>();
        when(shopFundAccountFlowMonitorServiceMock.findShopFundAccountFlowData(anyInt())).thenReturn(flowDataList);

        eoSuccessFlowCheckStub.check(monitorData);
    }

    @Test
    public void testCheckWhenFlowNumberNotCorrect(){
        ExchangeOrderMonitorData monitorData = new ExchangeOrderMonitorData();
        monitorData.setEoId(1);
        monitorData.setLastUpdateDate(new Date());
        List<ShopFundAccountFlowMonitorData> flowDataList = new ArrayList<ShopFundAccountFlowMonitorData>();
        flowDataList.add(new ShopFundAccountFlowMonitorData());
        when(shopFundAccountFlowMonitorServiceMock.findShopFundAccountFlowData(anyInt())).thenReturn(flowDataList);

        eoSuccessFlowCheckStub.check(monitorData);
    }
}
