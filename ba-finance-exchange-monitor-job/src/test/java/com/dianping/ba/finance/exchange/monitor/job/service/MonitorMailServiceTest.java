package com.dianping.ba.finance.exchange.monitor.job.service;

import com.dianping.mailremote.remote.MailService;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.HashMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MonitorMailServiceTest {

    private MonitorMailService monitorMailServiceStub;
    private MailService mailServiceMock;

    @Before
    public void runBeforeTest() {
        mailServiceMock = mock(MailService.class);
        monitorMailServiceStub = new MonitorMailService();
        monitorMailServiceStub.setMailService(mailServiceMock);
    }

    @Test
    public void sendMail() throws ParseException {
        HashMap<String, String> contentMap = new HashMap<String, String>();
        contentMap.put("content", "");
        when(mailServiceMock.send(1400, "", contentMap)).thenReturn(true);
        monitorMailServiceStub.sendMail("");
    }

}
