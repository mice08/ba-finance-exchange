package com.dianping.ba.finance.exchange.biz.producer;

import com.dianping.ba.finance.exchange.api.beans.PayOrderResultBean;
import com.dianping.ba.finance.exchange.api.enums.PayResultStatus;
import com.dianping.finance.common.swallow.SwallowEventBean;
import com.dianping.finance.common.swallow.SwallowProducer;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class PayOrderResultNotifyTest {

    private PayOrderResultNotify payOrderResultNotifyStub;

    private SwallowProducer payOrderProducerMock;

    @Before
    public void setUp() throws Exception {
        payOrderResultNotifyStub = new PayOrderResultNotify();
        payOrderProducerMock = mock(SwallowProducer.class);
        payOrderResultNotifyStub.setPayOrderProducer(payOrderProducerMock);
    }

    @Test
    public void testPayResultNotify() throws Exception {
        PayOrderResultBean payOrderResultBean = new PayOrderResultBean();
        payOrderResultBean.setMemo("memo");
        payOrderResultBean.setStatus(PayResultStatus.PAY_SUCCESS);
        payOrderResultNotifyStub.payResultNotify(payOrderResultBean);
        verify(payOrderProducerMock, times(1)).fireSwallowEvent(any(SwallowEventBean.class));
    }
}