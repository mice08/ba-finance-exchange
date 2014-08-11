package com.dianping.ba.finance.exchange.paymonitor.job;

import com.dianping.ba.finance.exchange.paymonitor.api.PayMonitorService;
import com.dianping.ba.finance.exchange.paymonitor.api.datas.MonitorExceptionData;
import com.dianping.ba.finance.exchange.paymonitor.job.service.MonitorMailService;
import com.dianping.ba.finance.exchange.paymonitor.job.service.MonitorSmsService;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CheckControllerTest {
    private List<DataChecker> dataCheckListMock;
    private ThreadPoolExecutor executorServiceMock;
    private PayMonitorService payMonitorServiceMock;
    private MonitorMailService monitorMailServiceMock;
    private MonitorSmsService monitorSmsServiceMock;
    private CheckController checkControllerStub;

    @Before
    public void setup(){
        checkControllerStub = new CheckController();
        dataCheckListMock = new ArrayList<DataChecker>();
        executorServiceMock = mock(ThreadPoolExecutor.class);
        payMonitorServiceMock = mock(PayMonitorService.class);
        monitorMailServiceMock = mock(MonitorMailService.class);
        monitorSmsServiceMock = mock(MonitorSmsService.class);
        checkControllerStub.setDataCheckList(dataCheckListMock);
        checkControllerStub.setExecutorService(executorServiceMock);
        checkControllerStub.setPayMonitorService(payMonitorServiceMock);
        checkControllerStub.setMonitorMailService(monitorMailServiceMock);
        checkControllerStub.setMonitorSmsService(monitorSmsServiceMock);
    }

    @Test
    public void testFindUnhandledExceptionData(){
        when(payMonitorServiceMock.addMonitorTime(any(Date.class))).thenReturn(1);
        when(payMonitorServiceMock.findUnhandledMonitorExceptionDatas()).thenReturn(buildOneExceptionDataList());
        when(payMonitorServiceMock.updateExceptionToHandled(anyList())).thenReturn(1);
        when(monitorMailServiceMock.sendMail(anyString())).thenReturn(true);
        when(monitorSmsServiceMock.sendSms(anyString())).thenReturn(true);

        boolean actual = checkControllerStub.execute();
        Assert.assertTrue(actual);
    }

    private List<MonitorExceptionData> buildOneExceptionDataList() {
        List<MonitorExceptionData> exceptionUnHandledList = new ArrayList<MonitorExceptionData>();
        MonitorExceptionData exceptionData = new MonitorExceptionData();
        exceptionData.setPpId(1);
        exceptionData.setExceptionId(1);
        exceptionData.setExceptionType(2);
        exceptionData.setStatus(1);
        exceptionUnHandledList.add(exceptionData);
        return exceptionUnHandledList;
    }
}