package com.dianping.ba.finance.exchange.mq.listener;

import com.dianping.ba.finance.exchange.api.PayOrderDomainService;
import com.dianping.ba.finance.exchange.api.dtos.BankPayResultDTO;
import com.dianping.swallow.common.internal.message.SwallowMessage;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PayResultListenerTest {

    private PayOrderDomainService payOrderDomainServiceMock;
    private PayResultListener bankPayResultListenerStub;

    @Before
    public void setup() {
        bankPayResultListenerStub = new PayResultListener();
        payOrderDomainServiceMock = mock(PayOrderDomainService.class);
        bankPayResultListenerStub.setPayOrderDomainService(payOrderDomainServiceMock);
    }

    @Test
    public void testOnMessage() throws Exception {
        String content = "{ \n" +
                "\"instId\":\"1\",\n" +
                "\"code\":456,\n" +
                "\"message\": \"abc\"" +
                "}\n";
        SwallowMessage message = new SwallowMessage();
        message.setContent(content);
        when(payOrderDomainServiceMock.handleBankPayResult(any(BankPayResultDTO.class))).thenReturn(true);
        bankPayResultListenerStub.onMessage(message);
    }
}