package com.dianping.ba.finance.exchange.monitor.job.service;

import com.dianping.sms.biz.SMSService;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.HashMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MonitorSmsServiceTest {

    private MonitorSmsService monitorSmsServiceStub;
    private SMSService smsServiceMock;

    @Before
    public void runBeforeTest() {
        smsServiceMock = mock(SMSService.class);
        monitorSmsServiceStub = new MonitorSmsService();
        monitorSmsServiceStub.setSmsService(smsServiceMock);
    }

    @Test
    public void sendSms() throws ParseException {
        HashMap<String, String> contentMap = new HashMap<String, String>();
        contentMap.put("content", "");
        when(smsServiceMock.send(813, "", contentMap)).thenReturn(1);
        monitorSmsServiceStub.sendSms("");
    }
}
