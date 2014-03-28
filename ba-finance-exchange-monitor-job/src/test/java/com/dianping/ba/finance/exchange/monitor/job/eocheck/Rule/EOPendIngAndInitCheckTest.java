package com.dianping.ba.finance.exchange.monitor.job.eocheck.Rule;

import com.dianping.ba.finance.exchange.monitor.api.ShopFundAccountFlowMonitorService;
import com.dianping.ba.finance.exchange.monitor.api.datas.ExchangeOrderMonitorData;
import com.dianping.ba.finance.exchange.monitor.api.datas.ShopFundAccountFlowMonitorData;
import com.dianping.ba.finance.exchange.monitor.api.enums.ExceptionType;
import com.dianping.ba.finance.exchange.monitor.api.enums.ExchangeOrderStatus;
import com.dianping.ba.finance.exchange.monitor.job.eocheck.EOCheckResult;
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
 * User: jingzhou.yan
 * Date: 14-3-27
 * Time: 下午4:44
 * To change this template use File | Settings | File Templates.
 */
public class EOPendIngAndInitCheckTest {
    private EOPendIngAndInitCheck eoPendIngAndInitCheckStub;
    private ShopFundAccountFlowMonitorService shopFundAccountFlowMonitorService;

    @Before
    public void setUp() {
        shopFundAccountFlowMonitorService = mock(ShopFundAccountFlowMonitorService.class);
        eoPendIngAndInitCheckStub = new EOPendIngAndInitCheck();
        eoPendIngAndInitCheckStub.setShopFundAccountFlowMonitorService(shopFundAccountFlowMonitorService);
        eoPendIngAndInitCheckStub.setTimeout(5);
    }

    @Test
    public void checkEOPendIngAndInitWithNoFlow() {
        ExchangeOrderMonitorData exchangeOrderMonitorData = new ExchangeOrderMonitorData();
        exchangeOrderMonitorData.setStatus(ExchangeOrderStatus.PENDING.value());
        when(shopFundAccountFlowMonitorService.findShopFundAccountFlowData(anyInt())).thenReturn(null);
        EOCheckResult actual = eoPendIngAndInitCheckStub.check(exchangeOrderMonitorData);
        Assert.assertTrue(actual.getExceptionType()== ExceptionType.EO_INIT_PENDING_WITHOUT_FLOW);
    }

    @Test
    public void checkEOPendIngAndInitWithMoreFlow() {
        List<ShopFundAccountFlowMonitorData> shopFundAccountFlowMonitorDataList=new ArrayList<ShopFundAccountFlowMonitorData>();
        shopFundAccountFlowMonitorDataList.add(new ShopFundAccountFlowMonitorData());
        shopFundAccountFlowMonitorDataList.add(new ShopFundAccountFlowMonitorData());

        ExchangeOrderMonitorData exchangeOrderMonitorData = new ExchangeOrderMonitorData();
        exchangeOrderMonitorData.setStatus(ExchangeOrderStatus.PENDING.value());
        when(shopFundAccountFlowMonitorService.findShopFundAccountFlowData(anyInt())).thenReturn(shopFundAccountFlowMonitorDataList);
        EOCheckResult actual = eoPendIngAndInitCheckStub.check(exchangeOrderMonitorData);

        Assert.assertTrue(actual.getExceptionType()== ExceptionType.EO_INIT_PENDING_WITH_MORE_FLOW);
    }

    @Test
    public void checkOnlyPendIngAndInitFitTheRule()
    {
        ExchangeOrderMonitorData exchangeOrderMonitorData = new ExchangeOrderMonitorData();

        exchangeOrderMonitorData.setStatus(ExchangeOrderStatus.PENDING.value());
        boolean actualPending = eoPendIngAndInitCheckStub.filter(exchangeOrderMonitorData);
        Assert.assertTrue(actualPending);

        exchangeOrderMonitorData.setStatus(ExchangeOrderStatus.INIT.value());
        boolean actualInit = eoPendIngAndInitCheckStub.filter(exchangeOrderMonitorData);
        Assert.assertTrue(actualInit);

        exchangeOrderMonitorData.setStatus(ExchangeOrderStatus.FAIL.value());
        boolean actualFail = eoPendIngAndInitCheckStub.filter(exchangeOrderMonitorData);
        Assert.assertFalse(actualFail);

        exchangeOrderMonitorData.setStatus(ExchangeOrderStatus.SUCCESS.value());
        boolean actualSuccess = eoPendIngAndInitCheckStub.filter(exchangeOrderMonitorData);
        Assert.assertFalse(actualSuccess);

        exchangeOrderMonitorData.setStatus(ExchangeOrderStatus.DEFAULT.value());
        boolean actualDefault = eoPendIngAndInitCheckStub.filter(exchangeOrderMonitorData);
        Assert.assertFalse(actualDefault);
    }
}
