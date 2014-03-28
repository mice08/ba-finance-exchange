package com.dianping.ba.finance.exchange.monitor.biz.impl;

import com.dianping.ba.finance.exchange.monitor.api.datas.ExceptionData;
import com.dianping.ba.finance.exchange.monitor.api.datas.MonitorTimeData;
import com.dianping.ba.finance.exchange.monitor.api.datas.TodoData;
import com.dianping.ba.finance.exchange.monitor.api.enums.ExceptionType;
import com.dianping.ba.finance.exchange.monitor.api.enums.TodoStatus;
import com.dianping.ba.finance.exchange.monitor.biz.dao.FSMonitorExceptionDao;
import com.dianping.ba.finance.exchange.monitor.biz.dao.FSMonitorTimeDao;
import com.dianping.ba.finance.exchange.monitor.biz.dao.FSMonitorTodoDao;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: noahshen
 * Date: 14-3-26
 * Time: 下午2:15
 * To change this template use File | Settings | File Templates.
 */
public class FSMonitorServiceObjectTest {

    private FSMonitorServiceObject fsMonitorServiceObjectStub;

    private FSMonitorTimeDao fsMonitorTimeDaoMock;
    private FSMonitorTodoDao fsMonitorTodoDaoMock;
    private FSMonitorExceptionDao fsMonitorExceptionDaoMock;

    @Before
    public void setUp() throws Exception {
        fsMonitorServiceObjectStub = new  FSMonitorServiceObject();

        fsMonitorTimeDaoMock = mock(FSMonitorTimeDao.class);
        fsMonitorTodoDaoMock = mock(FSMonitorTodoDao.class);
        fsMonitorExceptionDaoMock = mock(FSMonitorExceptionDao.class);
        fsMonitorServiceObjectStub.setFsMonitorExceptionDao(fsMonitorExceptionDaoMock);
        fsMonitorServiceObjectStub.setFsMonitorTimeDao(fsMonitorTimeDaoMock);
        fsMonitorServiceObjectStub.setFsMonitorTodoDao(fsMonitorTodoDaoMock);
    }

    @Test
    public void testGetLastMonitorTime() throws Exception {
        MonitorTimeData mtData = new MonitorTimeData();
        mtData.setMonitorId(123);
        Date d = new Date();
        mtData.setMonitorTime(d);
        when(fsMonitorTimeDaoMock.loadLastMonitorTime()).thenReturn(mtData);
        Date lastDate = fsMonitorServiceObjectStub.getLastMonitorTime();
        Assert.assertEquals(d, lastDate);

    }

    @Test
    public void testAddMonitorTime() throws Exception {
        int mtId = 1;
        when(fsMonitorTimeDaoMock.insertMonitorTime(any(MonitorTimeData.class))).thenReturn(mtId);

        boolean r = fsMonitorServiceObjectStub.addMonitorTime(new Date());
        Assert.assertTrue(r);
    }

    @Test
    public void testAddTodo() throws Exception {
        int todoId = 1;
        when(fsMonitorTodoDaoMock.insertTodoData(any(TodoData.class))).thenReturn(todoId);

        TodoData todoDate = new TodoData();
        todoDate.setEoId(123);
        todoDate.setStatus(TodoStatus.INIT.value());
        boolean r = fsMonitorServiceObjectStub.addTodo(todoDate);
        Assert.assertTrue(r);

    }

    @Test
    public void testAddMonitorException() throws Exception {
        int exId = 1;
        when(fsMonitorExceptionDaoMock.insertMonitorException(any(ExceptionData.class))).thenReturn(exId);

        ExceptionData exData = new ExceptionData();
        exData.setEoId(123);
        exData.setStatus(TodoStatus.INIT.value());
        boolean r = fsMonitorServiceObjectStub.addMonitorException(exData);
        Assert.assertTrue(r);
    }

    @Test
    public void testFindUnhandldedToDoDatas() throws Exception {
        int ppId = 123;
        List<TodoData> todoDatas = new ArrayList<TodoData>();
        TodoData todoDate = new TodoData();
        todoDate.setAddDate(new Date());
        todoDate.setEoId(ppId);
        todoDate.setStatus(TodoStatus.INIT.value());
        todoDatas.add(todoDate);

        when(fsMonitorTodoDaoMock.findToDoDatas(anyInt())).thenReturn(todoDatas);
        List<TodoData> todoDataResults = fsMonitorServiceObjectStub.findUnhandledToDoData();
        Assert.assertNotNull(todoDataResults);
        Assert.assertEquals(ppId, todoDataResults.get(0).getEoId());
    }

    @Test
    public void testFindUnhandledExceptionDatas() throws Exception {
        int ppId = 123;
        List<ExceptionData> exDatas = new ArrayList<ExceptionData>();
        ExceptionData exData = new ExceptionData();
        exData.setAddDate(new Date());
        exData.setEoId(ppId);
        exData.setStatus(TodoStatus.INIT.value());
        exData.setExceptionType(ExceptionType.DEFAULT.value());
        exDatas.add(exData);

        when(fsMonitorExceptionDaoMock.findExceptions(anyInt())).thenReturn(exDatas);
        List<ExceptionData> results = fsMonitorServiceObjectStub.findUnhandledExceptionData();
        Assert.assertNotNull(results);
        Assert.assertEquals(ppId, results.get(0).getEoId());
    }

    @Test
    public void testUpdateExceptionToHandled() throws Exception {
        when(fsMonitorExceptionDaoMock.updateStatus(anyList(),anyInt(), anyInt())).thenReturn(1);

        List<Integer> ids = Arrays.asList(1);
        int u = fsMonitorServiceObjectStub.updateExceptionToHandled(ids);
        Assert.assertTrue(u > 0);
    }

    @Test
    public void testUpdateTodoToHandled() throws Exception {
        when(fsMonitorTodoDaoMock.updateToDoStatus(anyList(),anyInt(), anyInt())).thenReturn(1);

        List<Integer> ids = Arrays.asList(1);
        int u = fsMonitorServiceObjectStub.updateTodoToHandled(ids);
        Assert.assertTrue(u > 0);
    }
}
