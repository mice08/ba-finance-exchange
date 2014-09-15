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
        MONITOR_LOGGER.info(LogUtils.formatInfoLogMsg(startTime, "PayPlanDataChecker.run", ""));
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
        MONITOR_LOGGER.info("============checkTodo start===============");
        List<MonitorTodoData> monitorTodoDataList = payMonitorService.findUnhandldedMonitorTodoDatas();
        for(MonitorTodoData monitorTodoData : monitorTodoDataList){
            PayPlanMonitorData payPlanMonitorData = payPlanService.getPayPlanById(monitorTodoData.getPpId());
            checkPayPlanData(payPlanMonitorData, monitorTodoData);
        }
        MONITOR_LOGGER.info("============checkTodo end===============");
    }

    void checkNewData() {
        MONITOR_LOGGER.info("============checkNewData start===============");
        Date lastMonitorTime = payMonitorService.getLastMonitorTime();
        List<PayPlanMonitorData> payPlanMonitorDataList = payPlanService.findPayPlansByDate(lastMonitorTime,this.getCurrentMonitorTime());
        for(PayPlanMonitorData payPlanMonitorData : payPlanMonitorDataList){
            checkPayPlanData(payPlanMonitorData, null);
        }
        MONITOR_LOGGER.info("============checkNewData end===============");
    }

    void checkPayPlanData(PayPlanMonitorData payPlanMonitorData, MonitorTodoData monitorTodoData) {
        MONITOR_LOGGER.info("checkPayPlanData: " + payPlanMonitorData.toString());
        for(PayCheckRule payCheckRule : payCheckRuleList){
            if(!payCheckRule.filter(payPlanMonitorData)) {
                continue;
            }

            PayCheckResult result = payCheckRule.check(payPlanMonitorData);
            MONITOR_LOGGER.info("checkResult: " + result.toString());
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
        MONITOR_LOGGER.info("============addMonitorException===============");
        MonitorExceptionData monitorExceptionData = new MonitorExceptionData();
        monitorExceptionData.setPpId(ppId);
        monitorExceptionData.setExceptionType(monitorExceptionType.value());
        monitorExceptionData.setAddDate(new Date());
        monitorExceptionData.setStatus(MonitorExceptionStatus.INIT.value());
        MONITOR_LOGGER.info("monitorExceptionData: " + monitorExceptionData.toString());
        payMonitorService.addMonitorException(monitorExceptionData);
    }

    private void addMonitorTodo(int ppId){
        MONITOR_LOGGER.info("============addMonitorTodo===============");
        MonitorTodoData monitorTodoData = new MonitorTodoData();
        monitorTodoData.setPpId(ppId);
        monitorTodoData.setStatus(MonitorTodoStatus.INIT.value());
        monitorTodoData.setAddDate(new Date());
        MONITOR_LOGGER.info("monitorTodoData: " + monitorTodoData.toString());
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
