package com.dianping.ba.finance.exchange.paymonitor.biz.dao;

import com.dianping.ba.finance.exchange.paymonitor.api.datas.MonitorTodoData;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/config/spring/common/appcontext-*.xml",
        "classpath*:/config/spring/local/appcontext-*.xml"})
public class MonitorTodoDaoTest {

    @Autowired
    private MonitorTodoDao monitorTodoDao;

    @Test
    public void testAddMonitorTodo() throws Exception {
        MonitorTodoData monitorTodoData = new MonitorTodoData();
        monitorTodoData.setPpId(12234);
        monitorTodoData.setAddDate(new Date());
        monitorTodoData.setStatus(1);
        int result = monitorTodoDao.addMonitorTodo(monitorTodoData);
        Assert.assertTrue(result>0);
    }

    @Test
    public void testFindMonitorTodoDatas() throws Exception {
        int status = 1;
        List<MonitorTodoData> monitorTodoDataList = monitorTodoDao.findMonitorTodoDatas(status);
        Assert.assertTrue(monitorTodoDataList.size()>0);
    }

    @Test
    public void testUpdateMonitorToDoStatus() throws Exception {
        int status = 2;
        int todoId = 1;
        List<Integer> todoList = new ArrayList<Integer>();
        todoList.add(todoId);
        int resultCount = monitorTodoDao.updateMonitorToDoStatus(todoList,status);
        Assert.assertTrue(resultCount>0);
    }
}