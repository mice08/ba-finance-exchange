package com.dianping.ba.finance.exchange.receivemonitor.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.receivemonitor.api.datas.TodoData;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: huajiao.zou
 * Date: 14-3-20
 * Time: 下午2:08
 * To change this template use File | Settings | File Templates.
 */
public interface FSMonitorTodoDao extends GenericDao {

    @DAOAction(action = DAOActionType.INSERT)
    int insertTodoData(@DAOParam("todoData") TodoData todoData);

    @DAOAction(action = DAOActionType.QUERY)
    List<TodoData> findToDoDatas(@DAOParam("status") int status);


    @DAOAction(action = DAOActionType.UPDATE)
    int updateToDoStatus(@DAOParam("todoList") List<Integer> todoList, @DAOParam("setStatus") int setStatus, @DAOParam("preStatus") int preStatus);
}
