package com.dianping.ba.finance.exchange.monitor.job;

import com.dianping.ba.finance.exchange.api.enums.ExchangeOrderStatus;
import com.dianping.ba.finance.exchange.monitor.api.ExchangeOrderMonitorService;
import com.dianping.ba.finance.exchange.monitor.api.FSMonitorService;
import com.dianping.ba.finance.exchange.monitor.api.ShopFundAccountFlowMonitorService;
import com.dianping.ba.finance.exchange.monitor.api.datas.ExceptionData;
import com.dianping.ba.finance.exchange.monitor.api.datas.ExchangeOrderMonitorData;
import com.dianping.ba.finance.exchange.monitor.api.datas.ShopFundAccountFlowMonitorData;
import com.dianping.ba.finance.exchange.monitor.api.datas.TodoData;
import com.dianping.ba.finance.exchange.monitor.job.eocheck.EOCheckBase;
import com.dianping.ba.finance.exchange.monitor.job.eocheck.EOCheckRule;
import com.dianping.ba.finance.exchange.monitor.job.eocheck.Rule.EOPendIngAndInitCheck;
import com.dianping.ba.finance.exchange.monitor.job.eocheck.Rule.EORefundFlowCheck;
import com.dianping.ba.finance.exchange.monitor.job.eocheck.Rule.EOSuccessFlowCheck;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: 遐
 * Date: 14-3-28
 * Time: 上午11:57
 * To change this template use File | Settings | File Templates.
 */
public class ExchangeOrderDataCheckerTest {

    private ExchangeOrderDataChecker exchangeOrderDataCheckerStub;
    private ExchangeOrderMonitorService exchangeOrderMonitorServiceMock;
    private FSMonitorService fsMonitorServiceMock;
    private ShopFundAccountFlowMonitorService shopFundAccountFlowMonitorServiceMock;

    @Before
    public void runBeforeTest(){
        exchangeOrderDataCheckerStub = new ExchangeOrderDataChecker();
        exchangeOrderMonitorServiceMock = mock(ExchangeOrderMonitorService.class);
        fsMonitorServiceMock = mock(FSMonitorService.class);
        shopFundAccountFlowMonitorServiceMock = mock(ShopFundAccountFlowMonitorService.class);
        exchangeOrderDataCheckerStub.setExchangeOrderMonitorService(exchangeOrderMonitorServiceMock);
        exchangeOrderDataCheckerStub.setFsMonitorService(fsMonitorServiceMock);
    }

    @Test
    public void testRunWithoutTodoDataAndNoException(){
        EOPendIngAndInitCheck pendIngAndInitCheck = new EOPendIngAndInitCheck();
        pendIngAndInitCheck.setShopFundAccountFlowMonitorService(shopFundAccountFlowMonitorServiceMock);
        List<EOCheckRule> eoCheckBaseList = new ArrayList<EOCheckRule>();
        eoCheckBaseList.add(pendIngAndInitCheck);
        exchangeOrderDataCheckerStub.setPpCheckRules(eoCheckBaseList);
        when(fsMonitorServiceMock.findUnhandledToDoData()).thenReturn(new ArrayList<TodoData>());
        when(fsMonitorServiceMock.getLastMonitorTime()).thenReturn(new Date());
        List<ExchangeOrderMonitorData> exchangeOrderMonitorDataList = new ArrayList<ExchangeOrderMonitorData>();
        ExchangeOrderMonitorData monitorData = new ExchangeOrderMonitorData();
        monitorData.setEoId(1);
        monitorData.setStatus(ExchangeOrderStatus.INIT.value());

        exchangeOrderMonitorDataList.add(monitorData);
        when(exchangeOrderMonitorServiceMock.findExchangeOrderData(any(Date.class),any(Date.class))).thenReturn(exchangeOrderMonitorDataList);
        List<ShopFundAccountFlowMonitorData> flowDataList = new ArrayList<ShopFundAccountFlowMonitorData>();
        ShopFundAccountFlowMonitorData shopFundAccountFlowMonitorData = new ShopFundAccountFlowMonitorData();
        flowDataList.add(shopFundAccountFlowMonitorData);
        when(shopFundAccountFlowMonitorServiceMock.findShopFundAccountFlowData(anyInt())).thenReturn(flowDataList);

        exchangeOrderDataCheckerStub.run();

        verify(fsMonitorServiceMock, never()).addMonitorException(any(ExceptionData.class));

    }

    @Test
    public void testRunWithoutTodoDataAndFlowMissing(){
        EOPendIngAndInitCheck pendIngAndInitCheck = new EOPendIngAndInitCheck();
        pendIngAndInitCheck.setShopFundAccountFlowMonitorService(shopFundAccountFlowMonitorServiceMock);
        List<EOCheckRule> eoCheckBaseList = new ArrayList<EOCheckRule>();
        eoCheckBaseList.add(pendIngAndInitCheck);
        exchangeOrderDataCheckerStub.setPpCheckRules(eoCheckBaseList);
        when(fsMonitorServiceMock.findUnhandledToDoData()).thenReturn(new ArrayList<TodoData>());
        when(fsMonitorServiceMock.getLastMonitorTime()).thenReturn(new Date());
        List<ExchangeOrderMonitorData> exchangeOrderMonitorDataList = new ArrayList<ExchangeOrderMonitorData>();
        ExchangeOrderMonitorData monitorData = new ExchangeOrderMonitorData();
        monitorData.setEoId(1);
        monitorData.setStatus(ExchangeOrderStatus.INIT.value());

        exchangeOrderMonitorDataList.add(monitorData);
        when(exchangeOrderMonitorServiceMock.findExchangeOrderData(any(Date.class),any(Date.class))).thenReturn(exchangeOrderMonitorDataList);
        List<ShopFundAccountFlowMonitorData> flowDataList = new ArrayList<ShopFundAccountFlowMonitorData>();
        when(shopFundAccountFlowMonitorServiceMock.findShopFundAccountFlowData(anyInt())).thenReturn(flowDataList);

        exchangeOrderDataCheckerStub.run();

        verify(fsMonitorServiceMock).addMonitorException(any(ExceptionData.class));
    }

    @Test
    public void testRunUsingSuccessFlowCheck(){
        EOSuccessFlowCheck eoSuccessFlowCheck = new EOSuccessFlowCheck();
        eoSuccessFlowCheck.setShopFundAccountFlowMonitorService(shopFundAccountFlowMonitorServiceMock);
        List<EOCheckRule> eoCheckBaseList = new ArrayList<EOCheckRule>();
        eoCheckBaseList.add(eoSuccessFlowCheck);
        exchangeOrderDataCheckerStub.setPpCheckRules(eoCheckBaseList);
        when(fsMonitorServiceMock.findUnhandledToDoData()).thenReturn(new ArrayList<TodoData>());
        when(fsMonitorServiceMock.getLastMonitorTime()).thenReturn(new Date());
        List<ExchangeOrderMonitorData> exchangeOrderMonitorDataList = new ArrayList<ExchangeOrderMonitorData>();
        ExchangeOrderMonitorData monitorData = new ExchangeOrderMonitorData();
        monitorData.setEoId(1);
        monitorData.setStatus(ExchangeOrderStatus.SUCCESS.value());

        exchangeOrderMonitorDataList.add(monitorData);
        when(exchangeOrderMonitorServiceMock.findExchangeOrderData(any(Date.class),any(Date.class))).thenReturn(exchangeOrderMonitorDataList);
        List<ShopFundAccountFlowMonitorData> flowDataList = new ArrayList<ShopFundAccountFlowMonitorData>();
        ShopFundAccountFlowMonitorData shopFundAccountFlowMonitorData = new ShopFundAccountFlowMonitorData();
        flowDataList.add(shopFundAccountFlowMonitorData);
        when(shopFundAccountFlowMonitorServiceMock.findShopFundAccountFlowData(anyInt())).thenReturn(flowDataList);

        exchangeOrderDataCheckerStub.run();

        verify(fsMonitorServiceMock, never()).addMonitorException(any(ExceptionData.class));

    }

    @Test
    public void testRunUsingSuccessFlowCheckWithException(){
        EOSuccessFlowCheck eoSuccessFlowCheck = new EOSuccessFlowCheck();
        eoSuccessFlowCheck.setShopFundAccountFlowMonitorService(shopFundAccountFlowMonitorServiceMock);
        List<EOCheckRule> eoCheckBaseList = new ArrayList<EOCheckRule>();
        eoCheckBaseList.add(eoSuccessFlowCheck);
        exchangeOrderDataCheckerStub.setPpCheckRules(eoCheckBaseList);
        when(fsMonitorServiceMock.findUnhandledToDoData()).thenReturn(new ArrayList<TodoData>());
        when(fsMonitorServiceMock.getLastMonitorTime()).thenReturn(new Date());
        List<ExchangeOrderMonitorData> exchangeOrderMonitorDataList = new ArrayList<ExchangeOrderMonitorData>();
        ExchangeOrderMonitorData monitorData = new ExchangeOrderMonitorData();
        monitorData.setEoId(1);
        monitorData.setStatus(ExchangeOrderStatus.SUCCESS.value());
        monitorData.setLastUpdateDate(DateUtils.addMinutes(new Date(), -6));

        exchangeOrderMonitorDataList.add(monitorData);
        when(exchangeOrderMonitorServiceMock.findExchangeOrderData(any(Date.class),any(Date.class))).thenReturn(exchangeOrderMonitorDataList);
        List<ShopFundAccountFlowMonitorData> flowDataList = new ArrayList<ShopFundAccountFlowMonitorData>();
        when(shopFundAccountFlowMonitorServiceMock.findShopFundAccountFlowData(anyInt())).thenReturn(flowDataList);

        exchangeOrderDataCheckerStub.run();

        verify(fsMonitorServiceMock).addMonitorException(any(ExceptionData.class));

    }


    @Test
    public void testRunUsingSuccessFlowCheckWhenNotTimeout(){
        EOSuccessFlowCheck eoSuccessFlowCheck = new EOSuccessFlowCheck();
        eoSuccessFlowCheck.setShopFundAccountFlowMonitorService(shopFundAccountFlowMonitorServiceMock);
        List<EOCheckRule> eoCheckBaseList = new ArrayList<EOCheckRule>();
        eoCheckBaseList.add(eoSuccessFlowCheck);
        exchangeOrderDataCheckerStub.setPpCheckRules(eoCheckBaseList);
        when(fsMonitorServiceMock.findUnhandledToDoData()).thenReturn(new ArrayList<TodoData>());
        when(fsMonitorServiceMock.getLastMonitorTime()).thenReturn(new Date());
        List<ExchangeOrderMonitorData> exchangeOrderMonitorDataList = new ArrayList<ExchangeOrderMonitorData>();
        ExchangeOrderMonitorData monitorData = new ExchangeOrderMonitorData();
        monitorData.setEoId(1);
        monitorData.setStatus(ExchangeOrderStatus.SUCCESS.value());
        monitorData.setLastUpdateDate(DateUtils.addMinutes(new Date(), -3));

        exchangeOrderMonitorDataList.add(monitorData);
        when(exchangeOrderMonitorServiceMock.findExchangeOrderData(any(Date.class),any(Date.class))).thenReturn(exchangeOrderMonitorDataList);
        List<ShopFundAccountFlowMonitorData> flowDataList = new ArrayList<ShopFundAccountFlowMonitorData>();
        when(shopFundAccountFlowMonitorServiceMock.findShopFundAccountFlowData(anyInt())).thenReturn(flowDataList);

        exchangeOrderDataCheckerStub.run();

        verify(fsMonitorServiceMock).addTodo(any(TodoData.class));

    }
}
