package com.dianping.ba.finance.exchange.siteweb.services;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.siteweb.util.ConstantUtils;
import com.dianping.finance.common.util.LogUtils;
import com.dianping.mailremote.remote.MailService;

import java.util.HashMap;

public class MonitorMailService {

    private static final AvatarLogger monitorLogger = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.biz.service.MonitorMailService");

    private MailService mailService;

    public boolean sendMail(String mailContent) {
        Long startTime = System.currentTimeMillis();
        try {
            String[] mailAddressArray = ConstantUtils.monitorMailAddress.split(",");
            HashMap<String, String> contentMap = new HashMap<String, String>();
            contentMap.put("title", "接口调用异常");
            contentMap.put("content", mailContent);
            monitorLogger.info("Start email, content:" + mailContent);

            for (String mailAddress : mailAddressArray) {
                mailService.send(1400, mailAddress, contentMap);
                monitorLogger.info("Email message has been sent to " + mailAddress);
            }
        } catch (Exception ex) {
            monitorLogger.error(LogUtils.formatErrorLogMsg(startTime, "MonitorMailService.sendMail", "mailAddress=" + ConstantUtils.monitorMailAddress + "&mailContent=" + mailContent));
        	return false;
		}
		return true;
    }

    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }
}
