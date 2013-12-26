package com.dianping.ba.finance.exchange.biz.producer;

import com.dianping.ba.finance.exchange.api.dtos.ExchangeOrderDTO;
import com.dianping.swallow.producer.Producer;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: 遐
 * Date: 13-12-26
 * Time: 下午6:19
 * To change this template use File | Settings | File Templates.
 */
public class ExchangeOrderStatusChangeNotifyTest {

    private Producer producerMock;
    private ExchangeOrderStatusChangeNotify exchangeOrderStatusChangeNotifyStub;

    @Before
    public void runBeforeTest() {
        producerMock = mock(Producer.class);
        exchangeOrderStatusChangeNotifyStub = new ExchangeOrderStatusChangeNotify();
        exchangeOrderStatusChangeNotifyStub.setProducerClient(producerMock);
    }

    @Test
    public void testSendMessageSuccess() throws Exception{
        ExchangeOrderDTO exchangeOrderDTO = new ExchangeOrderDTO();
        exchangeOrderDTO.setOrderAmount(BigDecimal.TEN);
        exchangeOrderDTO.setOrderType(1);
        exchangeOrderDTO.setExchangeOrderId(1);
        exchangeOrderDTO.setStatus(3);
        exchangeOrderDTO.setRelevantFundAccountFlowId(1);
        exchangeOrderDTO.setRelevantFundAccountId(1);
        when(producerMock.sendMessage(anyString())).thenReturn("message");
        exchangeOrderStatusChangeNotifyStub.exchangeOrderStatusChangeNotify(exchangeOrderDTO);
    }
}
