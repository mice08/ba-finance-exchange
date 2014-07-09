package com.dianping.ba.finance.exchange.biz.producer;

import com.dianping.ba.finance.exchange.api.beans.ReceiveOrderResultBean;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.ba.finance.exchange.api.enums.ReceiveOrderPayChannel;
import com.dianping.ba.finance.exchange.api.enums.ReceiveType;
import com.dianping.finance.common.swallow.SwallowEventBean;
import com.dianping.finance.common.swallow.SwallowProducer;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 *  ReceiveOrderResultNotify test
 */
public class ReceiveOrderResultNotifyTest {

    private SwallowProducer producerMock;
    private ReceiveOrderResultNotify receiveOrderResultNotifyStub;

    @Before
    public void runBeforeTest() {
        producerMock = mock(SwallowProducer.class);
        receiveOrderResultNotifyStub = new ReceiveOrderResultNotify();
        receiveOrderResultNotifyStub.setReceiveOrderProducer(producerMock);
    }

    @Test
    public void testReceiveResultNotify() throws Exception{
        ReceiveOrderResultBean receiveOrderResultBean = new ReceiveOrderResultBean();
        receiveOrderResultBean.setBusinessType(BusinessType.ADVERTISEMENT);
        receiveOrderResultBean.setRoId(1);
        receiveOrderResultBean.setLoginId(-1);
        receiveOrderResultBean.setTradeNo("P123");
        receiveOrderResultBean.setBankReceiveTime(new Date());
        receiveOrderResultBean.setBankId(1);
        receiveOrderResultBean.setCustomerId(123456);
        receiveOrderResultBean.setPayChannel(ReceiveOrderPayChannel.CASH);
        receiveOrderResultBean.setReceiveAmount(BigDecimal.TEN);
        receiveOrderResultBean.setReceiveType(ReceiveType.AD_FEE);
        receiveOrderResultBean.setPayTime(new Date());
        receiveOrderResultNotifyStub.receiveResultNotify(receiveOrderResultBean);
        verify(producerMock, times(1)).fireSwallowEvent(any(SwallowEventBean.class));
    }
    @Test
    public void testReceiveResultNotifyTS() throws Exception{
        ReceiveOrderResultBean receiveOrderResultBean = new ReceiveOrderResultBean();
        receiveOrderResultBean.setBusinessType(BusinessType.GROUP_PURCHASE);
        receiveOrderResultBean.setRoId(1);
        receiveOrderResultBean.setLoginId(-1);
        receiveOrderResultBean.setTradeNo("P123");
        receiveOrderResultBean.setBankReceiveTime(new Date());
        receiveOrderResultBean.setBankId(1);
        receiveOrderResultBean.setCustomerId(123456);
        receiveOrderResultBean.setPayChannel(ReceiveOrderPayChannel.CASH);
        receiveOrderResultBean.setReceiveAmount(BigDecimal.TEN);
        receiveOrderResultBean.setReceiveType(ReceiveType.TG_SHELVING_FEE);
        receiveOrderResultBean.setPayTime(new Date());
        receiveOrderResultNotifyStub.receiveResultNotify(receiveOrderResultBean);
        verify(producerMock, times(1)).fireSwallowEvent(any(SwallowEventBean.class));
    }
}
