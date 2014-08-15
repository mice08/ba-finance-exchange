package com.dianping.ba.finance.receivemonitor.job;

/**
 * Created by IntelliJ IDEA.
 * User: yaxiong.cheng
 * Date: 2014/8/15
 * Time: 15:27
 * To change this template use File | Settings | File Templates.
 */
import com.dianping.ba.finance.exchange.receivemonitor.api.ReceiveMonitorService;
import com.dianping.ba.finance.exchange.receivemonitor.api.datas.ExceptionData;
import com.dianping.ba.finance.exchange.receivemonitor.job.CheckController;
import com.dianping.ba.finance.exchange.receivemonitor.job.DataChecker;
import com.dianping.ba.finance.exchange.receivemonitor.job.service.MonitorMailService;
import com.dianping.ba.finance.exchange.receivemonitor.job.service.MonitorSmsService;
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
	private ReceiveMonitorService receiveMonitorService;
	private MonitorMailService monitorMailServiceMock;
	private MonitorSmsService monitorSmsServiceMock;
	private CheckController checkControllerStub;

	@Before
	public void setup(){
		checkControllerStub = new CheckController();
		dataCheckListMock = new ArrayList<DataChecker>();
		executorServiceMock = mock(ThreadPoolExecutor.class);
		receiveMonitorService = mock(ReceiveMonitorService.class);
		monitorMailServiceMock = mock(MonitorMailService.class);
		monitorSmsServiceMock = mock(MonitorSmsService.class);
		checkControllerStub.setDataCheckList(dataCheckListMock);
		checkControllerStub.setExecutorService(executorServiceMock);
		checkControllerStub.setReceiveMonitorService(receiveMonitorService);
		checkControllerStub.setMonitorMailService(monitorMailServiceMock);
		checkControllerStub.setMonitorSmsService(monitorSmsServiceMock);
	}

	@Test
	public void testFindUnhandledExceptionData(){
		when(receiveMonitorService.addMonitorTime(any(Date.class))).thenReturn(1);
		when(receiveMonitorService.findUnhandledExceptionDatas()).thenReturn(buildOneExceptionDataList());
		when(receiveMonitorService.updateExceptionToHandled(anyList())).thenReturn(1);
		when(monitorMailServiceMock.sendMail(anyString())).thenReturn(true);
		when(monitorSmsServiceMock.sendSms(anyString())).thenReturn(true);

		boolean actual = checkControllerStub.execute();
		Assert.assertTrue(actual);
	}

	private List<ExceptionData> buildOneExceptionDataList() {
		List<ExceptionData> exceptionUnHandledList = new ArrayList<ExceptionData>();
		ExceptionData exceptionData = new ExceptionData();
		exceptionData.setRoId(1);
		exceptionData.setExceptionId(1);
		exceptionData.setExceptionType(2);
		exceptionData.setStatus(1);
		exceptionUnHandledList.add(exceptionData);
		return exceptionUnHandledList;
	}
}
