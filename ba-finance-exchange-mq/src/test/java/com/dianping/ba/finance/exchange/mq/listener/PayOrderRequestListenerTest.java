package com.dianping.ba.finance.exchange.mq.listener;

import com.dianping.ba.finance.exchange.api.PayOrderRequestHandleService;
import com.dianping.ba.finance.exchange.api.dtos.PayOrderRequestDTO;
import com.dianping.swallow.common.internal.message.SwallowMessage;
import com.dianping.swallow.consumer.BackoutMessageException;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

/**
 * 付款请求消息监听器
 */
public class PayOrderRequestListenerTest {

    private PayOrderRequestListener payOrderRequestListenerStub;

    private PayOrderRequestHandleService payOrderRequestHandleServiceMock;

    @Before
    public void setUp() throws Exception {
        payOrderRequestHandleServiceMock = mock(PayOrderRequestHandleService.class);
        payOrderRequestListenerStub = new PayOrderRequestListener();
        payOrderRequestListenerStub.setPayOrderRequestHandleService(payOrderRequestHandleServiceMock);
    }


    @Test
    public void testOnMessage() throws Exception {
        String content = "{ \n" +
                "\"paySequence\":\"P|123\",\n" +
                "\"payAmount\":456,\n" +
                "\"bankAccountNo\":\"622612341234\",\n" +
                "\"bankAccountName\":\"张三\",\n" +
                "\"bankName\":\"中国银行\",\n" +
                "\"bankProvince\":\"上海\",\n" +
                "\"bankCity\":\"上海\",\n" +
                "\"requestDate\":1401862698,\n" +
                "\"businessType\": 1,\n" +
                "\"memo\":\"我要付款\"\n" +
                "}\n";
        SwallowMessage message = new SwallowMessage();
        message.setContent(content);
        payOrderRequestListenerStub.onMessage(message);
    }

    @Test(expected = BackoutMessageException.class)
    public void testOnMessageException() throws Exception {
        doThrow(new RuntimeException()).when(payOrderRequestHandleServiceMock).handlePayOrderRequest(any(PayOrderRequestDTO.class));

        String content = "{ \n" +
                "\"paySequence\":\"P|123\",\n" +
                "\"payAmount\":456,\n" +
                "\"bankAccountNo\":\"622612341234\",\n" +
                "\"bankAccountName\":\"张三\",\n" +
                "\"bankName\":\"中国银行\",\n" +
                "\"bankProvince\":\"上海\",\n" +
                "\"bankCity\":\"上海\",\n" +
                "\"requestDate\":1401862698,\n" +
                "\"businessType\": 1,\n" +
                "\"memo\":\"我要付款\"\n" +
                "}\n";
        SwallowMessage message = new SwallowMessage();
        message.setContent(content);
        payOrderRequestListenerStub.onMessage(message);
    }
}
