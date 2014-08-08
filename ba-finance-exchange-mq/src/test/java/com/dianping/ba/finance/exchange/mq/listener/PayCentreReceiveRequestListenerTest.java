package com.dianping.ba.finance.exchange.mq.listener;

import com.dianping.ba.finance.exchange.api.PayCentreReceiveRequestHandleService;
import com.dianping.ba.finance.exchange.api.dtos.PayCentreReceiveRequestDTO;
import com.dianping.swallow.common.internal.message.SwallowMessage;
import com.dianping.swallow.consumer.BackoutMessageException;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

/**
 * 支付中心收款请求消息监听器
 */
public class PayCentreReceiveRequestListenerTest {

    private PayCentreReceiveRequestListener payCentreReceiveRequestListenerStub;

    private PayCentreReceiveRequestHandleService payCentreReceiveRequestHandleServiceMock;

    @Before
    public void setUp() throws Exception {
        payCentreReceiveRequestHandleServiceMock = mock(PayCentreReceiveRequestHandleService.class);
        payCentreReceiveRequestListenerStub = new PayCentreReceiveRequestListener();
        payCentreReceiveRequestListenerStub.setPayCentreReceiveRequestHandleService(payCentreReceiveRequestHandleServiceMock);
    }


    @Test
    public void testOnMessage() throws Exception {
        String content = "{ \n" +
                "\"tradeNo\":\"P|123-1\",\n" +
                "\"receiveAmount\":456,\n" +
                "\"tradeType\": 1,\n" +
                "\"bankId\": 1,\n" +
                "\"bizContent\":\"AD2014SH0001\",\n" +
                "\"oriTradeNo\":\"\",\n" +
                "\"payChannel\": 10,\n" +
                "\"payMethod\": 5,\n" +
                "\"receiveDate\":1401862698,\n" +
                "\"businessType\": 6,\n" +
                "\"memo\":\"收款\"\n" +
                "}\n";
        SwallowMessage message = new SwallowMessage();
        message.setContent(content);
        payCentreReceiveRequestListenerStub.onMessage(message);
    }

    @Test(expected = BackoutMessageException.class)
    public void testOnMessageException() throws Exception {
        doThrow(new RuntimeException()).when(payCentreReceiveRequestHandleServiceMock).handleReceiveRequest(any(PayCentreReceiveRequestDTO.class));

        String content = "{ \n" +
                "\"tradeNo\":\"P|123-1\",\n" +
                "\"receiveAmount\":456,\n" +
                "\"tradeType\": 1,\n" +
                "\"bankId\": 1,\n" +
                "\"bizContent\":\"AD2014SH0001\",\n" +
                "\"oriTradeNo\":\"\",\n" +
                "\"payChannel\": 10,\n" +
                "\"payMethod\": 5,\n" +
                "\"receiveDate\":1401862698,\n" +
                "\"businessType\": 6,\n" +
                "\"memo\":\"收款\"\n" +
                "}\n";
        SwallowMessage message = new SwallowMessage();
        message.setContent(content);
        payCentreReceiveRequestListenerStub.onMessage(message);
    }
}
