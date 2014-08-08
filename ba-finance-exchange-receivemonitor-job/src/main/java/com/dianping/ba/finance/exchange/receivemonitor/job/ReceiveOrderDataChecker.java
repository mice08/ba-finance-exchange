package com.dianping.ba.finance.exchange.receivemonitor.job;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.receivemonitor.api.ReceiveMonitorService;
import com.dianping.ba.finance.exchange.receivemonitor.api.ReceiveOrderMonitorService;
import com.dianping.ba.finance.exchange.receivemonitor.api.datas.ExceptionData;
import com.dianping.ba.finance.exchange.receivemonitor.api.datas.ReceiveOrderMonitorData;
import com.dianping.ba.finance.exchange.receivemonitor.api.datas.TodoData;
import com.dianping.ba.finance.exchange.receivemonitor.api.enums.ExceptionStatus;
import com.dianping.ba.finance.exchange.receivemonitor.api.enums.ExceptionType;
import com.dianping.ba.finance.exchange.receivemonitor.api.enums.TodoStatus;
import com.dianping.ba.finance.exchange.receivemonitor.job.rocheck.ROCheckResult;
import com.dianping.ba.finance.exchange.receivemonitor.job.rocheck.ROCheckRule;
import com.dianping.finance.common.util.LogUtils;

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
public class ReceiveOrderDataChecker extends DataChecker {

    private static final AvatarLogger monitorLogger = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.monitor.job.ReceiveOrderDataChecker");

    private ReceiveOrderMonitorService receiveOrderMonitorService;
    private ReceiveMonitorService receiveMonitorService;
    private List<ROCheckRule> roCheckRuleList = new ArrayList<ROCheckRule>();

    @Override
    public boolean run() {
        long startTime = System.currentTimeMillis();
        try {
            checkReceiveOrder();
        } catch (Exception e) {
            monitorLogger.error(LogUtils.formatErrorLogMsg(startTime, "ReceiveOrderDataChecker.run", ""), e);
        }
        return false;
    }

    private void checkReceiveOrder() {
        checkToDoData();
        checkNewData();
    }

    private void checkToDoData() {
        List<TodoData> todoList = receiveMonitorService.findUnhandledToDoData();
        for (TodoData todoData : todoList) {
            ReceiveOrderMonitorData roData = receiveOrderMonitorService.loadReceiveOrderData(todoData.getRoId());
            checkReceiveOrderData(roData, todoData);
        }
    }

    private void checkNewData() {
        Date lastMonitorTime = receiveMonitorService.getLastMonitorTime();

        List<ReceiveOrderMonitorData> receiveOrderMonitorDataList = receiveOrderMonitorService.findReceiveOrderData(lastMonitorTime, getCurrentMonitorTime());
        for (ReceiveOrderMonitorData roData : receiveOrderMonitorDataList) {
            checkReceiveOrderData(roData, null);
        }
    }

    private void checkReceiveOrderData(ReceiveOrderMonitorData roData, TodoData todoData) {
        for (ROCheckRule rule : roCheckRuleList) {
            if (!rule.filter(roData)) {
                continue;
            }
            ROCheckResult result = rule.check(roData);
            if (result.isValid()) {
                if (todoData != null) {
                    receiveMonitorService.updateTodoToHandled(Arrays.asList(todoData.getTodoId()));
                }
                continue;
            }
            if (result.isTimeout()) {
                addException(roData.getRoId(), result.getExceptionType());
                if (todoData != null) {
                    receiveMonitorService.updateTodoToHandled(Arrays.asList(todoData.getTodoId()));
                }
            } else {
                if (todoData == null) {
                    addToDo(roData.getRoId());
                }
            }
        }
    }

    private void addException(int roId, ExceptionType exceptionType) {
        ExceptionData exData = new ExceptionData();
        exData.setAddDate(new Date());
        exData.setRoId(roId);
        exData.setStatus(ExceptionStatus.INIT.value());
        exData.setExceptionType(exceptionType.value());
        receiveMonitorService.addMonitorException(exData);
    }

    private void addToDo(int roId) {
        TodoData toDoData = new TodoData();
        toDoData.setRoId(roId);
        toDoData.setAddDate(new Date());
        toDoData.setStatus(TodoStatus.INIT.value());
        receiveMonitorService.addTodo(toDoData);
    }


	public void setReceiveOrderMonitorService(ReceiveOrderMonitorService receiveOrderMonitorService) {
		this.receiveOrderMonitorService = receiveOrderMonitorService;
	}

	public void setReceiveMonitorService(ReceiveMonitorService receiveMonitorService) {
		this.receiveMonitorService = receiveMonitorService;
	}

	public void setRoCheckRuleList(List<ROCheckRule> roCheckRuleList) {
		this.roCheckRuleList = roCheckRuleList;
	}
}
