package com.dianping.ba.finance.exchange.paymonitor.biz.impl;

import com.dianping.ba.finance.exchange.paymonitor.api.PayMonitorService;
import com.dianping.ba.finance.exchange.paymonitor.api.datas.MonitorExceptionData;
import com.dianping.ba.finance.exchange.paymonitor.api.datas.MonitorTodoData;
import com.dianping.ba.finance.exchange.paymonitor.api.enums.MonitorExceptionStatus;
import com.dianping.ba.finance.exchange.paymonitor.api.enums.MonitorTodoStatus;
import com.dianping.ba.finance.exchange.paymonitor.biz.dao.MonitorExceptionDao;
import com.dianping.ba.finance.exchange.paymonitor.biz.dao.MonitorTimeDao;
import com.dianping.ba.finance.exchange.paymonitor.biz.dao.MonitorTodoDao;

import java.util.Date;
import java.util.List;

/**
 * Created by adam.huang on 2014/8/7.
 */
public class PayMonitorServiceObject implements PayMonitorService {

    private MonitorTodoDao monitorTodoDao;
    private MonitorExceptionDao monitorExceptionDao;
    private MonitorTimeDao monitorTimeDao;

    @Override
    public Date getLastMonitorTime() {
        return monitorTimeDao.loadLastMonitorTime();
    }

    @Override
    public int addMonitorTime(Date date) {
        return monitorTimeDao.insertMonitorTime(date);
    }

    @Override
    public int addMonitorTodo(MonitorTodoData monitorTodoData) {
        return monitorTodoDao.insertMonitorTodo(monitorTodoData);
    }

    @Override
    public int addMonitorException(MonitorExceptionData monitorExceptionData) {
        return monitorExceptionDao.insertMonitorException(monitorExceptionData);
    }

    @Override
    public List<MonitorTodoData> findUnhandldedMonitorTodoDatas() {
        return monitorTodoDao.findMonitorTodoDatas(MonitorTodoStatus.INIT.value());
    }

    @Override
    public List<MonitorExceptionData> findUnhandledMonitorExceptionDatas() {
        return monitorExceptionDao.findMonitorExceptionDatas(MonitorExceptionStatus.INIT.value());
    }

    @Override
    public int updateExceptionToHandled(List<Integer> exceptionIdList) {
        return monitorExceptionDao.updateMonitorExceptionStatus(exceptionIdList,
                MonitorExceptionStatus.HANDLED.value());
    }

    @Override
    public int updateMonitorTodoDataToHandled(List<Integer> todoIdList) {
        return monitorTodoDao.updateMonitorToDoStatus(todoIdList,
                MonitorTodoStatus.HANDLED.value());
    }

    public void setMonitorTodoDao(MonitorTodoDao monitorTodoDao) {
        this.monitorTodoDao = monitorTodoDao;
    }

    public void setMonitorExceptionDao(MonitorExceptionDao monitorExceptionDao) {
        this.monitorExceptionDao = monitorExceptionDao;
    }

    public void setMonitorTimeDao(MonitorTimeDao monitorTimeDao) {
        this.monitorTimeDao = monitorTimeDao;
    }
}
