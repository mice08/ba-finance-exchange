package com.dianping.ba.finance.exchange.monitor.biz.impl;


import com.dianping.ba.finance.exchange.monitor.api.FSMonitorService;
import com.dianping.ba.finance.exchange.monitor.api.datas.ExceptionData;
import com.dianping.ba.finance.exchange.monitor.api.datas.MonitorTimeData;
import com.dianping.ba.finance.exchange.monitor.api.datas.TodoData;
import com.dianping.ba.finance.exchange.monitor.api.enums.ExceptionStatus;
import com.dianping.ba.finance.exchange.monitor.api.enums.TodoStatus;
import com.dianping.ba.finance.exchange.monitor.biz.dao.FSMonitorExceptionDao;
import com.dianping.ba.finance.exchange.monitor.biz.dao.FSMonitorTimeDao;
import com.dianping.ba.finance.exchange.monitor.biz.dao.FSMonitorTodoDao;

import java.util.Date;
import java.util.List;


public class FSMonitorServiceObject implements FSMonitorService {

    private FSMonitorTimeDao fsMonitorTimeDao;
    private FSMonitorTodoDao fsMonitorTodoDao;
    private FSMonitorExceptionDao fsMonitorExceptionDao;

    @Override
    public Date getLastMonitorTime() {
        MonitorTimeData lastMT = fsMonitorTimeDao.loadLastMonitorTime();
        if (lastMT == null) {
            return null;
        }
        return lastMT.getMonitorTime();
    }

    @Override
    public boolean addMonitorTime(Date date) {
        MonitorTimeData mtd = new MonitorTimeData();
        mtd.setMonitorTime(date);
        int mtId = fsMonitorTimeDao.insertMonitorTime(mtd);
        return mtId > 0;
    }

    @Override
    public boolean addTodo(TodoData todoData) {
        int todoId = fsMonitorTodoDao.insertTodoData(todoData);
        return todoId > 0;
    }

    @Override
    public boolean addMonitorException(ExceptionData exceptionData) {
        int exId = fsMonitorExceptionDao.insertMonitorException(exceptionData);
        return exId > 0;
    }

    @Override
    public List<TodoData> findUnhandledToDoData() {
        return fsMonitorTodoDao.findToDoDatas(TodoStatus.INIT.value());
    }

    @Override
    public List<ExceptionData> findUnhandledExceptionData() {
        return fsMonitorExceptionDao.findExceptions(ExceptionStatus.INIT.value());  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int updateExceptionToHandled(List<Integer> exceptionIdList) {
        return fsMonitorExceptionDao.updateStatus(exceptionIdList,
                                                    ExceptionStatus.HANDLED.value(),
                                                    ExceptionStatus.INIT.value());
    }

    @Override
    public int updateTodoToHandled(List<Integer> todoIdList) {
        return fsMonitorTodoDao.updateToDoStatus(todoIdList,
                                                    TodoStatus.HANDLED.value(),
                                                    TodoStatus.INIT.value());
    }

    public void setFsMonitorTimeDao(FSMonitorTimeDao fsMonitorTimeDao) {
        this.fsMonitorTimeDao = fsMonitorTimeDao;
    }

    public void setFsMonitorTodoDao(FSMonitorTodoDao fsMonitorTodoDao) {
        this.fsMonitorTodoDao = fsMonitorTodoDao;
    }

    public void setFsMonitorExceptionDao(FSMonitorExceptionDao fsMonitorExceptionDao) {
        this.fsMonitorExceptionDao = fsMonitorExceptionDao;
    }
}
