package com.dianping.ba.finance.exchange.monitor.job.service;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.monitor.job.utils.ConstantUtils;
import com.dianping.finance.common.util.LogUtils;
import com.dianping.mailremote.remote.MailService;
import org.apache.commons.lang.ArrayUtils;

import java.util.HashMap;

public class MonitorMailService {

    private static final AvatarLogger monitorLogger = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.monitor.job.service.MonitorMailService");

    private MailService mailService;

    public void sendMail(String mailContent) {
        Long startTime = System.currentTimeMillis();
        try {
            String[] mailAddressArray = ConstantUtils.monitorMailAddress.split(",");
            if (ArrayUtils.isEmpty(mailAddressArray)) {
                return;
            }

            HashMap<String, String> contentMap = new HashMap<String, String>();
            contentMap.put("title", "付款单异常");
            contentMap.put("content", mailContent);
            monitorLogger.info("Start email, content:" + mailContent);

            for (String mailAddress : mailAddressArray) {
                mailService.send(1400, mailAddress, contentMap);
                monitorLogger.info("Email message has been sent to " + mailAddress);
            }
        } catch (Exception ex) {
            monitorLogger.error(LogUtils.formatErrorLogMsg(startTime, "MonitorMailService.sendMail", "mailAddress=" + ConstantUtils.monitorMailAddress + "&mailContent=" + mailContent));
        }
    }

    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }
}
