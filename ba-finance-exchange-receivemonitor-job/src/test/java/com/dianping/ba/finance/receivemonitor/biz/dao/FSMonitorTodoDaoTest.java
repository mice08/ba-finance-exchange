package com.dianping.ba.finance.receivemonitor.biz.dao;

import com.dianping.ba.finance.exchange.receivemonitor.api.datas.TodoData;
import com.dianping.ba.finance.exchange.receivemonitor.api.enums.TodoStatus;
import com.dianping.ba.finance.exchange.receivemonitor.biz.dao.FSMonitorTodoDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: noahshen
 * Date: 14-3-26
 * Time: 下午12:51
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/config/spring/common/appcontext-*.xml",
        "classpath*:/config/spring/local/appcontext-*.xml",
		"classpath*:/config/spring/test/appcontext-*.xml"})
public class FSMonitorTodoDaoTest {

    @Autowired
    private FSMonitorTodoDao fsMonitorTodoDao;

    @Test
    public void testInsertTodoData() throws Exception {
        TodoData todoData = new TodoData();
        todoData.setRoId(123);
        todoData.setStatus(TodoStatus.INIT.value());
        todoData.setAddDate(new Date());
        int todoId = fsMonitorTodoDao.insertTodoData(todoData);
        System.out.println(todoId);
    }

    @Test
    public void testFindToDoDatas() throws Exception {
        List<TodoData> todoDatas = fsMonitorTodoDao.findToDoDatas(TodoStatus.INIT.value());
        System.out.println(todoDatas);

    }

    @Test
    public void testUpdateToDoStatus() throws Exception {
        List<Integer> todoIdList = Arrays.asList(1);
        int setStatus = TodoStatus.HANDLED.value();
        int preStatus = TodoStatus.INIT.value();
        fsMonitorTodoDao.updateToDoStatus(todoIdList, setStatus, preStatus);
    }
}
