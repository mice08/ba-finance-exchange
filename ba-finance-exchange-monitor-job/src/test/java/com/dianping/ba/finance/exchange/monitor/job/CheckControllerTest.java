package com.dianping.ba.finance.exchange.monitor.job;

import com.dianping.ba.finance.exchange.monitor.api.FSMonitorService;
import com.dianping.ba.finance.exchange.monitor.api.datas.ExceptionData;
import com.dianping.ba.finance.exchange.monitor.job.service.MonitorMailService;
import com.dianping.ba.finance.exchange.monitor.job.service.MonitorSmsService;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: 遐
 * Date: 14-3-28
 * Time: 上午11:57
 * To change this template use File | Settings | File Templates.
 */
public class CheckControllerTest {

	private List<DataChecker> dataCheckListMock;
	private ThreadPoolExecutor executorServiceMock;
	private FSMonitorService fsMonitorServiceMock;
	private MonitorMailService monitorMailServiceMock;
	private MonitorSmsService monitorSmsServiceMock;
	private CheckController checkControllerStub;

    @Before
    public void runBeforeTest(){
		checkControllerStub = new CheckController();
		dataCheckListMock = new ArrayList<DataChecker>();
		executorServiceMock = mock(ThreadPoolExecutor.class);
        fsMonitorServiceMock = mock(FSMonitorService.class);
		monitorMailServiceMock = mock(MonitorMailService.class);
		monitorSmsServiceMock = mock(MonitorSmsService.class);
		checkControllerStub.setDataCheckList(dataCheckListMock);
		checkControllerStub.setExecutorService(executorServiceMock);
		checkControllerStub.setFsMonitorService(fsMonitorServiceMock);
		checkControllerStub.setMonitorMailService(monitorMailServiceMock);
		checkControllerStub.setMonitorSmsService(monitorSmsServiceMock);
    }

    @Test
    public void testFindUnhandledExceptionData(){
		when(fsMonitorServiceMock.addMonitorTime(any(Date.class))).thenReturn(true);
		when(fsMonitorServiceMock.findUnhandledExceptionData()).thenReturn(buildOneExceptionDataList());
		when(fsMonitorServiceMock.updateExceptionToHandled(anyList())).thenReturn(1);
		when(monitorMailServiceMock.sendMail(anyString())).thenReturn(true);
		when(monitorSmsServiceMock.sendSms(anyString())).thenReturn(true);

		boolean actual = checkControllerStub.execute();
		Assert.assertTrue(actual);
    }

	private List<ExceptionData> buildOneExceptionDataList() {
		List<ExceptionData> exceptionUnHandledList = new ArrayList<ExceptionData>();
		ExceptionData exceptionData = new ExceptionData();
		exceptionData.setEoId(1);
		exceptionData.setExceptionId(1);
		exceptionData.setExceptionType(2);
		exceptionData.setStatus(1);
		exceptionUnHandledList.add(exceptionData);
		return exceptionUnHandledList;
	}
}
