package com.dianping.ba.finance.exchange.paymonitor.job;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.paymonitor.api.PayMonitorService;
import com.dianping.ba.finance.exchange.paymonitor.api.datas.MonitorExceptionData;
import com.dianping.ba.finance.exchange.paymonitor.api.enums.MonitorExceptionType;
import com.dianping.ba.finance.exchange.paymonitor.job.service.MonitorMailService;
import com.dianping.ba.finance.exchange.paymonitor.job.service.MonitorSmsService;
import com.dianping.finance.common.util.LogUtils;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;


public class CheckController {
    private static final AvatarLogger monitorLogger = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.monitor.job.CheckController");

    private List<DataChecker> dataCheckList;
    private ThreadPoolExecutor executorService;
    private PayMonitorService payMonitorService;
    private MonitorMailService monitorMailService;
    private MonitorSmsService monitorSmsService;

	public boolean execute() {
		monitorLogger.info("Start to monitor....");
		Date currentMonitorTime = Calendar.getInstance().getTime();
		long startTime = System.currentTimeMillis();
		int batchCount = dataCheckList.size();
		final CountDownLatch doneSignal = new CountDownLatch(batchCount);
		for (final DataChecker dataCheck : dataCheckList) {
			dataCheck.setCurrentMonitorTime(currentMonitorTime);
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
            payMonitorService.addMonitorTime(currentMonitorTime);
			notifyException();
			monitorLogger.info("End monitoring....");
		} catch (InterruptedException e) {
			monitorLogger.error(LogUtils.formatErrorLogMsg(startTime, "CheckController.execute", ""), e);
		}
		return true;
	}

	private void notifyException() {
		List<Integer> handledExceptionList = new ArrayList<Integer>();
		List<MonitorExceptionData> exceptionUnHandledList = payMonitorService.findUnhandledMonitorExceptionDatas();
		if (CollectionUtils.isEmpty(exceptionUnHandledList)) {
			return;
		}
		Map<MonitorExceptionType, String> exceptionReasonMap = new HashMap<MonitorExceptionType, String>();
		for (MonitorExceptionData exceptionData : exceptionUnHandledList) {
            MonitorExceptionType reasonType = MonitorExceptionType.valueOf(exceptionData.getExceptionType());
			generateExceptionInfo(reasonType, exceptionReasonMap, exceptionData.getPpId());
			handledExceptionList.add(exceptionData.getExceptionId());
		}
		String mailInfo = "付款计划错误详情：\n";
		for (Map.Entry<MonitorExceptionType, String> entry : exceptionReasonMap.entrySet()) {
			mailInfo += String.format("%s，错误的ID为%s\n", entry.getKey().toString(), entry.getValue());
		}
		String smsInfo = "付款计划异常，详情请见邮件！";
		monitorSmsService.sendSms(smsInfo);
		monitorMailService.sendMail(mailInfo);
		payMonitorService.updateExceptionToHandled(handledExceptionList);
	}

	private void generateExceptionInfo(MonitorExceptionType reasonType, Map<MonitorExceptionType, String> exceptionReasonMap, int exceptionId) {
		String idStr = exceptionReasonMap.containsKey(reasonType) ? exceptionReasonMap.get(reasonType) + "," + exceptionId : String.valueOf(exceptionId);
		exceptionReasonMap.put(reasonType, idStr);
	}

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

    public void setPayMonitorService(PayMonitorService payMonitorService) {
        this.payMonitorService = payMonitorService;
    }
}
