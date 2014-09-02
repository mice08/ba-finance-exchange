package com.dianping.ba.finance.exchange.paymonitor.job;

import com.dianping.ba.finance.exchange.paymonitor.api.PayMonitorService;
import com.dianping.ba.finance.exchange.paymonitor.api.PayPlanService;
import com.dianping.ba.finance.exchange.paymonitor.api.datas.MonitorExceptionData;
import com.dianping.ba.finance.exchange.paymonitor.api.datas.MonitorTodoData;
import com.dianping.ba.finance.exchange.paymonitor.api.datas.PayPlanMonitorData;
import com.dianping.ba.finance.exchange.paymonitor.api.enums.MonitorExceptionType;
import com.dianping.ba.finance.exchange.paymonitor.api.enums.MonitorTodoStatus;
import com.dianping.ba.finance.exchange.paymonitor.api.enums.PayPlanStatus;
import com.dianping.ba.finance.exchange.paymonitor.job.paycheck.PayCheckResult;
import com.dianping.ba.finance.exchange.paymonitor.job.paycheck.PayCheckRule;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.*;

public class PayPlanDataCheckerTest {
    private PayPlanDataChecker payPlanDataCheckerStub;

    private PayPlanService payPlanServiceMock;

    private PayMonitorService payMonitorServiceMock;

    private PayCheckRule payCheckRuleMock;

    @Before
    public void setUp() throws Exception {
        payPlanDataCheckerStub = new PayPlanDataChecker();
        payPlanServiceMock = mock(PayPlanService.class);
        payMonitorServiceMock = mock(PayMonitorService.class);
        payCheckRuleMock = mock(PayCheckRule.class);
        payPlanDataCheckerStub.setPayMonitorService(payMonitorServiceMock);
        payPlanDataCheckerStub.setPayPlanService(payPlanServiceMock);
        payPlanDataCheckerStub.setPayCheckRuleList(Arrays.asList(payCheckRuleMock));

    }

    @Test
    public void testCheckNewDataSuccess() throws Exception {
        payPlanDataCheckerStub.setCurrentMonitorTime(new Date());

        when(payMonitorServiceMock.getLastMonitorTime()).thenReturn(new Date());

        List<PayPlanMonitorData> ppDatas = new ArrayList<PayPlanMonitorData>();
        PayPlanMonitorData data = new PayPlanMonitorData();
        data.setPpId(123);
        data.setStatus(PayPlanStatus.INIT.value());
        data.setUpdateTime(new Date());
        ppDatas.add(data);
        when(payPlanServiceMock.findPayPlansByDate(any(Date.class), any(Date.class))).thenReturn(ppDatas);

        when(payCheckRuleMock.filter(any(PayPlanMonitorData.class))).thenReturn(true);

        PayCheckResult result = new PayCheckResult();
        result.setValided(true);
        when(payCheckRuleMock.check(any(PayPlanMonitorData.class))).thenReturn(result);

        payPlanDataCheckerStub.checkNewData();

        verify(payMonitorServiceMock, Mockito.times(0)).addMonitorException(any(MonitorExceptionData.class));
        verify(payMonitorServiceMock, Mockito.times(0)).addMonitorTodo(any(MonitorTodoData.class));
    }

    @Test
    public void testCheckNewDataTimeout() throws Exception {
        payPlanDataCheckerStub.setCurrentMonitorTime(new Date());

        when(payMonitorServiceMock.getLastMonitorTime()).thenReturn(new Date());

        List<PayPlanMonitorData> ppDatas = new ArrayList<PayPlanMonitorData>();
        PayPlanMonitorData data = new PayPlanMonitorData();
        data.setPpId(123);
        data.setStatus(PayPlanStatus.INIT.value());
        data.setUpdateTime(new Date());
        ppDatas.add(data);
        when(payPlanServiceMock.findPayPlansByDate(any(Date.class), any(Date.class))).thenReturn(ppDatas);

        when(payCheckRuleMock.filter(any(PayPlanMonitorData.class))).thenReturn(true);

        PayCheckResult result = new PayCheckResult();
        result.setValided(false);
        result.setTimeout(true);
        result.setMonitorExceptionType(MonitorExceptionType.DEFAULT);
        when(payCheckRuleMock.check(any(PayPlanMonitorData.class))).thenReturn(result);

        payPlanDataCheckerStub.checkNewData();

        verify(payMonitorServiceMock, Mockito.times(1)).addMonitorException(any(MonitorExceptionData.class));
        verify(payMonitorServiceMock, Mockito.times(0)).addMonitorTodo(any(MonitorTodoData.class));
    }

    @Test
    public void testCheckNewDataInvalidNotTimeout() throws Exception {
        payPlanDataCheckerStub.setCurrentMonitorTime(new Date());

        when(payMonitorServiceMock.getLastMonitorTime()).thenReturn(new Date());

        List<PayPlanMonitorData> ppDatas = new ArrayList<PayPlanMonitorData>();
        PayPlanMonitorData data = new PayPlanMonitorData();
        data.setPpId(123);
        data.setStatus(PayPlanStatus.INIT.value());
        data.setUpdateTime(new Date());
        ppDatas.add(data);
        when(payPlanServiceMock.findPayPlansByDate(any(Date.class), any(Date.class))).thenReturn(ppDatas);

        when(payCheckRuleMock.filter(any(PayPlanMonitorData.class))).thenReturn(true);

        PayCheckResult result = new PayCheckResult();
        result.setValided(false);
        result.setTimeout(false);
        result.setMonitorExceptionType(MonitorExceptionType.DEFAULT);
        when(payCheckRuleMock.check(any(PayPlanMonitorData.class))).thenReturn(result);

        payPlanDataCheckerStub.checkNewData();

        verify(payMonitorServiceMock, Mockito.times(0)).addMonitorException(any(MonitorExceptionData.class));
        verify(payMonitorServiceMock, Mockito.times(1)).addMonitorTodo(any(MonitorTodoData.class));
    }

    @Test
    public void testCheckToDo() throws Exception {
        payPlanDataCheckerStub.setCurrentMonitorTime(new Date());

        List<MonitorTodoData> todoDatas = new ArrayList<MonitorTodoData>();
        MonitorTodoData todoData = new MonitorTodoData();
        todoData.setPpId(123);
        todoData.setAddDate(new Date());
        todoData.setStatus(MonitorTodoStatus.INIT.value());
        todoData.setTodoId(1);
        todoDatas.add(todoData);
        when(payMonitorServiceMock.findUnhandldedMonitorTodoDatas()).thenReturn(todoDatas);

        PayPlanMonitorData ppData = new PayPlanMonitorData();
        ppData.setPpId(123);
        ppData.setStatus(PayPlanStatus.INIT.value());
        ppData.setUpdateTime(new Date());
        when(payPlanServiceMock.getPayPlanById(anyInt())).thenReturn(ppData);


        when(payCheckRuleMock.filter(any(PayPlanMonitorData.class))).thenReturn(true);

        PayCheckResult result = new PayCheckResult();
        result.setValided(true);
        result.setTimeout(false);
        result.setMonitorExceptionType(MonitorExceptionType.DEFAULT);
        when(payCheckRuleMock.check(any(PayPlanMonitorData.class))).thenReturn(result);

        payPlanDataCheckerStub.checkTodo();

        verify(payMonitorServiceMock, Mockito.times(0)).addMonitorException(any(MonitorExceptionData.class));
        verify(payMonitorServiceMock, Mockito.times(0)).addMonitorTodo(any(MonitorTodoData.class));

    }

    @Test
    public void testCheckToDoInvalidNotTimeout() throws Exception {
        payPlanDataCheckerStub.setCurrentMonitorTime(new Date());

        List<MonitorTodoData> todoDatas = new ArrayList<MonitorTodoData>();
        MonitorTodoData todoData = new MonitorTodoData();
        todoData.setPpId(123);
        todoData.setAddDate(new Date());
        todoData.setStatus(MonitorTodoStatus.INIT.value());
        todoData.setTodoId(1);
        todoDatas.add(todoData);
        when(payMonitorServiceMock.findUnhandldedMonitorTodoDatas()).thenReturn(todoDatas);

        PayPlanMonitorData ppData = new PayPlanMonitorData();
        ppData.setPpId(123);
        ppData.setStatus(PayPlanStatus.INIT.value());
        ppData.setUpdateTime(new Date());
        when(payPlanServiceMock.getPayPlanById(anyInt())).thenReturn(ppData);


        when(payCheckRuleMock.filter(any(PayPlanMonitorData.class))).thenReturn(true);

        PayCheckResult result = new PayCheckResult();
        result.setValided(false);
        result.setTimeout(false);
        result.setMonitorExceptionType(MonitorExceptionType.DEFAULT);
        when(payCheckRuleMock.check(any(PayPlanMonitorData.class))).thenReturn(result);

        payPlanDataCheckerStub.checkTodo();

        verify(payMonitorServiceMock, Mockito.times(0)).addMonitorException(any(MonitorExceptionData.class));
        verify(payMonitorServiceMock, Mockito.times(0)).addMonitorTodo(any(MonitorTodoData.class));
    }

    @Test
    public void testCheckToDoInvalidTimeout() throws Exception {
        payPlanDataCheckerStub.setCurrentMonitorTime(new Date());

        List<MonitorTodoData> todoDatas = new ArrayList<MonitorTodoData>();
        MonitorTodoData todoData = new MonitorTodoData();
        todoData.setPpId(123);
        todoData.setAddDate(new Date());
        todoData.setStatus(MonitorTodoStatus.INIT.value());
        todoData.setTodoId(1);
        todoDatas.add(todoData);
        when(payMonitorServiceMock.findUnhandldedMonitorTodoDatas()).thenReturn(todoDatas);

        PayPlanMonitorData ppData = new PayPlanMonitorData();
        ppData.setPpId(123);
        ppData.setStatus(PayPlanStatus.INIT.value());
        ppData.setUpdateTime(new Date());
        when(payPlanServiceMock.getPayPlanById(anyInt())).thenReturn(ppData);


        when(payCheckRuleMock.filter(any(PayPlanMonitorData.class))).thenReturn(true);

        PayCheckResult result = new PayCheckResult();
        result.setValided(false);
        result.setTimeout(true);
        result.setMonitorExceptionType(MonitorExceptionType.DEFAULT);
        when(payCheckRuleMock.check(any(PayPlanMonitorData.class))).thenReturn(result);

        payPlanDataCheckerStub.checkTodo();

        verify(payMonitorServiceMock, Mockito.times(1)).addMonitorException(any(MonitorExceptionData.class));
        verify(payMonitorServiceMock, Mockito.times(0)).addMonitorTodo(any(MonitorTodoData.class));
        verify(payMonitorServiceMock, Mockito.times(1)).updateMonitorTodoDataToHandled(anyList());
    }
}