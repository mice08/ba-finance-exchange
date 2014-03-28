package com.dianping.ba.finance.exchange.monitor.job;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.monitor.api.FSMonitorService;
import com.dianping.ba.finance.exchange.monitor.job.service.MonitorMailService;
import com.dianping.ba.finance.exchange.monitor.job.service.MonitorSmsService;
import com.dianping.ba.finance.exchange.monitor.job.utils.LogUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class CheckController {
    private static final AvatarLogger monitorLogger = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.monitor.job.CheckController");

    private List<DataChecker> dataCheckList;
    private ThreadPoolExecutor executorService;
    private FSMonitorService fsMonitorService;
    private MonitorMailService monitorMailService;
    private MonitorSmsService monitorSmsService;

    public void execute() {
        while(true) {
            monitorLogger.info("Start to monitor....");
            Date currentMonitorTime = Calendar.getInstance().getTime();
            long startTime = System.currentTimeMillis();
            int batchCount = dataCheckList.size();
            final CountDownLatch doneSignal = new CountDownLatch(batchCount);
            for(final DataChecker dataCheck: dataCheckList){
                dataCheck.setMonitorStartDate(currentMonitorTime);
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            monitorLogger.info(dataCheck.getClass() + " start checking....");
                            dataCheck.run();
                            monitorLogger.info(dataCheck.getClass() + " end checking....");
                        } finally {
                            doneSignal.countDown();
                        }
                    }
                });
            }
            try {
                doneSignal.await();
//                pcMonitorService.recordCurrentMonitorTime(currentMonitorTime);
//                notifyException();
                monitorLogger.info("End monitoring....");
                Thread.sleep(TimeUnit.SECONDS.toMillis(100));
            } catch (InterruptedException e) {
                monitorLogger.error(LogUtils.formatErrorLogMsg(startTime, "CheckController.execute", ""), e);
            }
        }
    }

//    private void notifyException(){
//        List<Integer> handledExceptionList = new ArrayList<Integer>();
//        List<MonitorExceptionData> exceptionUnHandledList = pcMonitorService.findMonitorExceptionUnHandled();
//        if(CollectionUtils.isEmpty(exceptionUnHandledList)){
//            return;
//        }
//        Map<MonitorExceptionReason, String> exceptionReasonMap = new HashMap<MonitorExceptionReason, String>();
//        for(MonitorExceptionData exceptionData: exceptionUnHandledList){
//            MonitorExceptionReason reasonType = MonitorExceptionReason.valueOf(exceptionData.getExceptionType());
//            int exceptionId = 0;
//            if(exceptionData.getBpId() > 0){
//                exceptionId = exceptionData.getBpId();
//            }else if(exceptionData.getVoucherId() > 0){
//                exceptionId = exceptionData.getVoucherId();
//            }else if(exceptionData.getApId() > 0){
//                exceptionId = exceptionData.getApId();
//            }
//            generateExceptionInfo(reasonType,exceptionReasonMap,exceptionId);
//            handledExceptionList.add(exceptionData.getExceptionId());
//        }
//        String mailInfo = "结算单错误详情：\n";
//        for(Map.Entry<MonitorExceptionReason, String> entry: exceptionReasonMap.entrySet()){
//            mailInfo += String.format( "%s，错误的ID为%s\n", entry.getKey().toString(), entry.getValue());
//        }
//        String smsInfo = "结算异常，详情请见邮件！";
//        monitorSmsService.sendSms(smsInfo);
//        monitorMailService.sendMail(mailInfo);
//        pcMonitorService.changeMonitorExceptionToHandled(handledExceptionList);
//    }
//
//    private void generateExceptionInfo(MonitorExceptionReason reasonType, Map<MonitorExceptionReason, String> exceptionReasonMap,int exceptionId ){
//        String idStr = "";
//        if(exceptionReasonMap.containsKey(reasonType)){
//            idStr = exceptionReasonMap.get(reasonType) + "," + exceptionId;
//        }else {
//            idStr = String.valueOf(exceptionId);
//        }
//        exceptionReasonMap.put(reasonType, idStr);
//    }

    public void setDataCheckList(List<DataChecker> dataCheckList) {
        this.dataCheckList = dataCheckList;
    }

    public void setExecutorService(ThreadPoolExecutor executorService) {
        this.executorService = executorService;
    }

    public void setMonitorSmsService(MonitorSmsService monitorSmsService) {
        this.monitorSmsService = monitorSmsService;
    }

    public void setMonitorMailService(MonitorMailService monitorMailService) {
        this.monitorMailService = monitorMailService;
    }

    public void setFsMonitorService(FSMonitorService fsMonitorService) {
        this.fsMonitorService = fsMonitorService;
    }




}
