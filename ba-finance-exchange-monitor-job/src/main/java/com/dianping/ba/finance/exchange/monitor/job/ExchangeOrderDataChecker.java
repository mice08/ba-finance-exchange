package com.dianping.ba.finance.exchange.monitor.job;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.monitor.api.FSMonitorService;
import com.dianping.ba.finance.exchange.monitor.api.ExchangeOrderMonitorService;
import com.dianping.ba.finance.exchange.monitor.api.datas.ExceptionData;
import com.dianping.ba.finance.exchange.monitor.api.datas.ExchangeOrderMonitorData;
import com.dianping.ba.finance.exchange.monitor.api.datas.TodoData;
import com.dianping.ba.finance.exchange.monitor.api.enums.ExceptionStatus;
import com.dianping.ba.finance.exchange.monitor.api.enums.ExceptionType;
import com.dianping.ba.finance.exchange.monitor.api.enums.TodoStatus;
import com.dianping.ba.finance.exchange.monitor.job.eocheck.EOCheckResult;
import com.dianping.ba.finance.exchange.monitor.job.eocheck.EOCheckRule;
import com.dianping.ba.finance.exchange.monitor.job.utils.LogUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sally.zhu
 * Date: 14-3-26
 * Time: 下午3:38
 * To change this template use File | Settings | File Templates.
 */
public class ExchangeOrderDataChecker extends DataChecker {

    private static final AvatarLogger monitorLogger = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.monitor.job.ExchangeOrderDataChecker");

    private ExchangeOrderMonitorService exchangeOrderMonitorService;
    private FSMonitorService fsMonitorService;
    private List<EOCheckRule> eoCheckRuleList = new ArrayList<EOCheckRule>();

    @Override
    public boolean run() {
        long startTime = System.currentTimeMillis();
        try {
            checkExchangeOrder();
        } catch (Exception e) {
            monitorLogger.error(LogUtils.formatErrorLogMsg(startTime, "ExchangeOrderDataChecker.run", ""), e);
        }
        return false;
    }

    private void checkExchangeOrder() {
        checkToDoData();
        checkNewData();
    }

    private void checkToDoData() {
        List<TodoData> todoList = fsMonitorService.findUnhandledToDoData();
        for (TodoData todoData : todoList) {
            ExchangeOrderMonitorData eoData = exchangeOrderMonitorService.loadExchangeOrderData(todoData.getEoId());
            checkExchangeOrderData(eoData, todoData);
        }
    }

    private void checkNewData() {
        Date lastMonitorTime = fsMonitorService.getLastMonitorTime();

        List<ExchangeOrderMonitorData> exchangeOrderMonitorDataList = exchangeOrderMonitorService.findExchangeOrderData(lastMonitorTime, getCurrentMonitorTime());
        for (ExchangeOrderMonitorData eoData : exchangeOrderMonitorDataList) {
            checkExchangeOrderData(eoData, null);
        }
    }

    private void checkExchangeOrderData(ExchangeOrderMonitorData eoData, TodoData todoData) {
        for (EOCheckRule rule : eoCheckRuleList) {
            if (!rule.filter(eoData)) {
                continue;
            }
            EOCheckResult result = rule.check(eoData);
            if (result.isValid()) {
                if (todoData != null) {
                    fsMonitorService.updateTodoToHandled(Arrays.asList(todoData.getTodoId()));
                }
                continue;
            }
            if (result.isTimeout()) {
                addException(eoData.getEoId(), result.getExceptionType());
                if (todoData != null) {
                    fsMonitorService.updateTodoToHandled(Arrays.asList(todoData.getTodoId()));
                }
            } else {
                if (todoData == null) {
                    addToDo(eoData.getEoId());
                }
            }
        }
    }

    private void addException(int eoId, ExceptionType exceptionType) {
        ExceptionData exData = new ExceptionData();
        exData.setAddDate(new Date());
        exData.setEoId(eoId);
        exData.setStatus(ExceptionStatus.INIT.value());
        exData.setExceptionType(exceptionType.value());
        fsMonitorService.addMonitorException(exData);
    }

    private void addToDo(int eoId) {
        TodoData toDoData = new TodoData();
        toDoData.setEoId(eoId);
        toDoData.setAddDate(new Date());
        toDoData.setStatus(TodoStatus.INIT.value());
        fsMonitorService.addTodo(toDoData);
    }

    public void setExchangeOrderMonitorService(ExchangeOrderMonitorService exchangeOrderMonitorService) {
        this.exchangeOrderMonitorService = exchangeOrderMonitorService;
    }

    public void setFsMonitorService(FSMonitorService fsMonitorService) {
        this.fsMonitorService = fsMonitorService;
    }

    public void setEoCheckRuleList(List<EOCheckRule> eoCheckRuleList) {
        this.eoCheckRuleList = eoCheckRuleList;
    }



}
