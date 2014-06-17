package com.dianping.ba.finance.exchange.monitor.job.service;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.monitor.job.utils.ConstantUtils;
import com.dianping.finance.common.util.LogUtils;
import com.dianping.sms.biz.SMSService;
import org.apache.commons.lang.ArrayUtils;

import java.util.HashMap;


public class MonitorSmsService {
    private static final AvatarLogger monitorLogger = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.monitor.job.service.MonitorSmsService");

    private SMSService smsService;

    public boolean sendSms(String smsContent) {
        Long startTime = System.currentTimeMillis();
        try {
            String[] mobileNoArray = ConstantUtils.monitorMobileNo.split(",");
            HashMap<String, String> contentMap = new HashMap<String, String>();
            contentMap.put("content", smsContent);
            monitorLogger.info("Start sms, content:" + smsContent);
            for (String mobileNo : mobileNoArray) {
                smsService.send(813, mobileNo, contentMap);
                monitorLogger.info("SMS message has been sent to " + mobileNo);
            }
        } catch (Exception ex) {
            monitorLogger.error(LogUtils.formatErrorLogMsg(startTime, "MonitorSmsService.sendSms", "mobileNo=" + ConstantUtils.monitorMobileNo + "&smsContent=" + smsContent));
        	return false;
		}
		return true;
    }

    public void setSmsService(SMSService smsService) {
        this.smsService = smsService;
    }
}
