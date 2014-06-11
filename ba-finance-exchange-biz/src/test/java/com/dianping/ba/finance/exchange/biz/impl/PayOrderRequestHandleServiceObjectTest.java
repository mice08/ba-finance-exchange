package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.PayOrderService;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.ba.finance.exchange.api.dtos.PayOrderRequestDTO;
import com.dianping.ba.finance.exchange.api.dtos.PayResultNotifyDTO;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.ba.finance.exchange.api.enums.PayResultStatus;
import com.dianping.finance.common.swallow.SwallowEventBean;
import com.dianping.finance.common.swallow.SwallowProducer;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

public class PayOrderRequestHandleServiceObjectTest {

    private PayOrderRequestHandleServiceObject payOrderRequestHandleServiceObjectStub;

    private long timeoutMock;

    private PayOrderService payOrderServiceMock;

    private SwallowProducer payOrderProducerMock;

    private ExecutorService executorServiceMock;

    @Before
    public void setUp() throws Exception {
        payOrderRequestHandleServiceObjectStub = new PayOrderRequestHandleServiceObject();

        timeoutMock = TimeUnit.MINUTES.toMillis(15);// 15分钟
        payOrderRequestHandleServiceObjectStub.setTimeout(timeoutMock);

        payOrderServiceMock = mock(PayOrderService.class);
        payOrderRequestHandleServiceObjectStub.setPayOrderService(payOrderServiceMock);

        payOrderProducerMock = mock(SwallowProducer.class);
        payOrderRequestHandleServiceObjectStub.setPayOrderProducer(payOrderProducerMock);

        executorServiceMock = Executors.newSingleThreadExecutor();
        payOrderRequestHandleServiceObjectStub.setExecutorService(executorServiceMock);
    }

    @Test
    public void testHandlePayOrderRequestTimeout() throws Exception {
        PayOrderRequestDTO payOrderRequestDTO = new PayOrderRequestDTO();
        payOrderRequestDTO.setPaySequence("TS312");
        payOrderRequestDTO.setBusinessType(BusinessType.GROUP_PURCHASE.value());
        payOrderRequestDTO.setPayAmount(BigDecimal.TEN);

        Calendar requestCal = Calendar.getInstance();
        requestCal.set(Calendar.MINUTE, requestCal.get(Calendar.MINUTE) - 16); // 16分钟前发送的消息
        payOrderRequestDTO.setRequestDate(requestCal.getTime());

        payOrderRequestHandleServiceObjectStub.handlePayOrderRequest(payOrderRequestDTO);

        Thread.sleep(1000);

        SwallowEventBean swallowEventBean = new SwallowEventBean();
        swallowEventBean.setEventKey("EX_FS_PAY_RESULT");
        PayResultNotifyDTO notifyDTO = new PayResultNotifyDTO();
        notifyDTO.setPaySequence("TS312");
        notifyDTO.setPoId(0);
        notifyDTO.setMemo("fail");
        notifyDTO.setStatus(PayResultStatus.REQUEST_FAIL.value());
        swallowEventBean.setObject(notifyDTO);
        verify(payOrderProducerMock, timeout(5000).times(1)).fireSwallowEvent(any(SwallowEventBean.class));

    }

    @Test
    public void testHandlePayOrderRequestInsertFailed() throws Exception {
        PayOrderRequestDTO payOrderRequestDTO = new PayOrderRequestDTO();
        payOrderRequestDTO.setPaySequence("TS312");
        payOrderRequestDTO.setBusinessType(BusinessType.GROUP_PURCHASE.value());
        payOrderRequestDTO.setPayAmount(BigDecimal.TEN);

        Calendar requestCal = Calendar.getInstance();
        payOrderRequestDTO.setRequestDate(requestCal.getTime());

        when(payOrderServiceMock.createPayOrder(any(PayOrderData.class))).thenReturn(-1);
        payOrderRequestHandleServiceObjectStub.handlePayOrderRequest(payOrderRequestDTO);


        SwallowEventBean swallowEventBean = new SwallowEventBean();
        swallowEventBean.setEventKey("EX_FS_PAY_RESULT");
        PayResultNotifyDTO notifyDTO = new PayResultNotifyDTO();
        notifyDTO.setPaySequence("TS312");
        notifyDTO.setPoId(0);
        notifyDTO.setMemo("fail");
        notifyDTO.setMemo(PayResultStatus.REQUEST_FAIL.toString());
        notifyDTO.setStatus(PayResultStatus.REQUEST_FAIL.value());
        swallowEventBean.setObject(notifyDTO);
        verify(payOrderProducerMock, timeout(5000).times(1)).fireSwallowEvent(any(SwallowEventBean.class));

    }

    @Test
    public void testHandleNewPayRequestInvalidPlanAmount() throws Exception {
        PayOrderRequestDTO payOrderRequestDTO = new PayOrderRequestDTO();
        payOrderRequestDTO.setPaySequence("TS312");
        payOrderRequestDTO.setBusinessType(BusinessType.GROUP_PURCHASE.value());
        payOrderRequestDTO.setPayAmount(BigDecimal.ZERO);

        Calendar requestCal = Calendar.getInstance();
        payOrderRequestDTO.setRequestDate(requestCal.getTime());

        when(payOrderServiceMock.createPayOrder(any(PayOrderData.class))).thenReturn(-1);
        payOrderRequestHandleServiceObjectStub.handlePayOrderRequest(payOrderRequestDTO);

        SwallowEventBean swallowEventBean = new SwallowEventBean();
        swallowEventBean.setEventKey("EX_FS_PAY_RESULT");
        PayResultNotifyDTO notifyDTO = new PayResultNotifyDTO();
        notifyDTO.setPaySequence("TS312");
        notifyDTO.setPoId(0);
        notifyDTO.setMemo("fail");
        notifyDTO.setMemo(PayResultStatus.REQUEST_FAIL.toString());
        notifyDTO.setStatus(PayResultStatus.REQUEST_FAIL.value());
        swallowEventBean.setObject(notifyDTO);
        verify(payOrderProducerMock, timeout(5000).times(1)).fireSwallowEvent(any(SwallowEventBean.class));

    }

    @Test
    public void testHandleNewPayRequestSuccess() throws Exception {
        PayOrderRequestDTO payOrderRequestDTO = new PayOrderRequestDTO();
        payOrderRequestDTO.setPaySequence("TS312");
        payOrderRequestDTO.setBusinessType(BusinessType.GROUP_PURCHASE.value());
        payOrderRequestDTO.setPayAmount(BigDecimal.TEN);

        Calendar requestCal = Calendar.getInstance();
        payOrderRequestDTO.setRequestDate(requestCal.getTime());

        when(payOrderServiceMock.createPayOrder(any(PayOrderData.class))).thenReturn(-1);
        payOrderRequestHandleServiceObjectStub.handlePayOrderRequest(payOrderRequestDTO);

        SwallowEventBean swallowEventBean = new SwallowEventBean();
        swallowEventBean.setEventKey("EX_FS_PAY_RESULT");
        PayResultNotifyDTO notifyDTO = new PayResultNotifyDTO();
        notifyDTO.setPaySequence("TS312");
        notifyDTO.setPoId(0);
        notifyDTO.setMemo("fail");
        notifyDTO.setMemo(PayResultStatus.REQUEST_FAIL.toString());
        notifyDTO.setStatus(PayResultStatus.REQUEST_FAIL.value());
        swallowEventBean.setObject(notifyDTO);
        verify(payOrderProducerMock, timeout(5000).times(1)).fireSwallowEvent(any(SwallowEventBean.class));


    }
}