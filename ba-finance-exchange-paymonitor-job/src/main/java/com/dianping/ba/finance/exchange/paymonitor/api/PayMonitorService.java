package com.dianping.ba.finance.exchange.paymonitor.api;

import com.dianping.ba.finance.exchange.paymonitor.api.datas.MonitorExceptionData;
import com.dianping.ba.finance.exchange.paymonitor.api.datas.MonitorTodoData;

import java.util.Date;
import java.util.List;

/**
 * Created by adam.huang on 2014/8/7.
 */
public interface PayMonitorService {
    /**
     * 获取最近一次执行监控的时间
     * @return
     */
    public Date getLastMonitorTime();

    /**
     * 添加监控的时间
     * @param date
     * @return
     */
    public int addMonitorTime(Date date);

    /**
     * 添加MonitorToDo
     * @param monitorTodoData
     * @return
     */
    public int addMonitorTodo(MonitorTodoData monitorTodoData);

    /**
     * 添加监控异常记录
     * @param monitorExceptionData
     * @return
     */
    public int addMonitorException(MonitorExceptionData monitorExceptionData);

    /**
     * 获取未处理的MonitorTodo
     * @return
     */
    public List<MonitorTodoData> findUnhandldedMonitorTodoDatas();

    /**
     * 获取未处理的异常记录
     * @return
     */
    public List<MonitorExceptionData> findUnhandledMonitorExceptionDatas();

    /**
     * 更新异常记录为已处理
     * @param exceptionIdList
     * @return
     */
    public int updateExceptionToHandled(List<Integer> exceptionIdList);

    /**
     * 更新MonitorToDo为已处理
     * @param todoIdList
     * @return
     */
    public int updateMonitorTodoDataToHandled(List<Integer> todoIdList);
}
