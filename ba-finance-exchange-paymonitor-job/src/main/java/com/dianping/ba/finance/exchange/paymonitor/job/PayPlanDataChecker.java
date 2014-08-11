package com.dianping.ba.finance.exchange.paymonitor.job;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.paymonitor.api.PayMonitorService;
import com.dianping.ba.finance.exchange.paymonitor.api.PayPlanService;
import com.dianping.ba.finance.exchange.paymonitor.api.datas.MonitorExceptionData;
import com.dianping.ba.finance.exchange.paymonitor.api.datas.MonitorTodoData;
import com.dianping.ba.finance.exchange.paymonitor.api.datas.PayPlanMonitorData;
import com.dianping.ba.finance.exchange.paymonitor.api.enums.MonitorExceptionStatus;
import com.dianping.ba.finance.exchange.paymonitor.api.enums.MonitorExceptionType;
import com.dianping.ba.finance.exchange.paymonitor.api.enums.MonitorTodoStatus;
import com.dianping.ba.finance.exchange.paymonitor.job.paycheck.PayCheckResult;
import com.dianping.ba.finance.exchange.paymonitor.job.paycheck.PayCheckRule;
import com.dianping.finance.common.util.LogUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by adam.huang on 2014/8/8.
 */
public class PayPlanDataChecker extends DataChecker {

    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.paymonitor.job.PayPlanDataChecker");

    private PayPlanService payPlanService;
    private PayMonitorService payMonitorService;

    private List<PayCheckRule> payCheckRuleList;

    @Override
    public boolean run() {
        long startTime = System.currentTimeMillis();
        try{
            checkPayPlan();
        }catch (Exception e){
            MONITOR_LOGGER.error(LogUtils.formatErrorLogMsg(startTime, "PayPlanDataChecker.run", ""), e);
        }
        return false;
    }

    void checkPayPlan() {
        checkTodo();
        checkNewData();
    }

    void checkTodo() {
        List<MonitorTodoData> monitorTodoDataList = payMonitorService.findUnhandldedMonitorTodoDatas();
        for(MonitorTodoData monitorTodoData : monitorTodoDataList){
            PayPlanMonitorData payPlanMonitorData = payPlanService.getPayPlanById(monitorTodoData.getPpId());
            checkPayPlanData(payPlanMonitorData, monitorTodoData);
        }
    }

    void checkNewData() {
        Date lastMonitorTime = payMonitorService.getLastMonitorTime();
        List<PayPlanMonitorData> payPlanMonitorDataList = payPlanService.findPayPlansByDate(lastMonitorTime,this.getCurrentMonitorTime());
        for(PayPlanMonitorData payPlanMonitorData : payPlanMonitorDataList){
            checkPayPlanData(payPlanMonitorData, null);
        }
    }

    void checkPayPlanData(PayPlanMonitorData payPlanMonitorData, MonitorTodoData monitorTodoData) {
        for(PayCheckRule payCheckRule : payCheckRuleList){
            if(!payCheckRule.filter(payPlanMonitorData)) {
                continue;
            }

            PayCheckResult result = payCheckRule.check(payPlanMonitorData);
            if(result.isValided()){
                if(monitorTodoData != null){
                    payMonitorService.updateMonitorTodoDataToHandled(Arrays.asList(monitorTodoData.getTodoId()));
                }
            } else {
                if(result.isTimeout()){
                    addMonitorException(payPlanMonitorData.getPpId(),result.getMonitorExceptionType());
                    if(monitorTodoData != null){
                        payMonitorService.updateMonitorTodoDataToHandled(Arrays.asList(monitorTodoData.getTodoId()));
                    }
                } else {
                    if(monitorTodoData == null){
                        addMonitorTodo(payPlanMonitorData.getPpId());
                    }
                }
            }
        }
    }

    private void addMonitorException(int ppId, MonitorExceptionType monitorExceptionType) {
        MonitorExceptionData monitorExceptionData = new MonitorExceptionData();
        monitorExceptionData.setPpId(ppId);
        monitorExceptionData.setExceptionType(monitorExceptionType.value());
        monitorExceptionData.setAddDate(new Date());
        monitorExceptionData.setStatus(MonitorExceptionStatus.INIT.value());
        payMonitorService.addMonitorException(monitorExceptionData);
    }

    private void addMonitorTodo(int ppId){
        MonitorTodoData monitorTodoData = new MonitorTodoData();
        monitorTodoData.setPpId(ppId);
        monitorTodoData.setStatus(MonitorTodoStatus.INIT.value());
        monitorTodoData.setAddDate(new Date());
        payMonitorService.addMonitorTodo(monitorTodoData);
    }

    public void setPayPlanService(PayPlanService payPlanService) {
        this.payPlanService = payPlanService;
    }

    public void setPayMonitorService(PayMonitorService payMonitorService) {
        this.payMonitorService = payMonitorService;
    }

    public void setPayCheckRuleList(List<PayCheckRule> payCheckRuleList) {
        this.payCheckRuleList = payCheckRuleList;
    }
}
