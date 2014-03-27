package com.dianping.ba.finance.exchange.monitor.api;


import com.dianping.ba.finance.exchange.monitor.api.datas.ExceptionData;
import com.dianping.ba.finance.exchange.monitor.api.datas.TodoData;

import java.util.Date;
import java.util.List;


public interface FSMonitorService {

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
    public boolean addMonitorTime(Date date);

    /**
     * 添加ToDo
     * @param todoData
     * @return
     */
    public boolean addTodo(TodoData todoData);

    /**
     * 添加监控异常记录
     * @param exceptionData
     * @return
     */
    public boolean addMonitorException(ExceptionData exceptionData);

    /**
     * 获取未处理的todo
     * @return
     */
    public List<TodoData> findUnhandldedToDoDatas();

    /**
     * 获取未处理的异常记录
     * @return
     */
    public List<ExceptionData> findUnhandledExceptionDatas();

    /**
     * 更新异常记录为已处理
     * @param exceptionIdList
     * @return
     */
    public int updateExceptionToHandled(List<Integer> exceptionIdList);

    /**
     * 更新ToDo为已处理
     * @param todoIdList
     * @return
     */
    public int updateTodoToHandled(List<Integer> todoIdList);

}
