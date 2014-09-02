package com.dianping.ba.finance.exchange.paymonitor.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.paymonitor.api.datas.MonitorTodoData;

import java.util.List;

/**
 * Created by adam.huang on 2014/8/7.
 */
public interface MonitorTodoDao extends GenericDao {

    @DAOAction(action = DAOActionType.INSERT)
    public int insertMonitorTodo(@DAOParam("monitorTodoData") MonitorTodoData monitorTodoData);

    @DAOAction(action = DAOActionType.QUERY)
    public List<MonitorTodoData> findMonitorTodoDatas(@DAOParam("status") int status);

    @DAOAction(action = DAOActionType.UPDATE)
    int updateMonitorToDoStatus(@DAOParam("monitorTodoList") List<Integer> monitorTodoList,
                         @DAOParam("status") int status);
}
