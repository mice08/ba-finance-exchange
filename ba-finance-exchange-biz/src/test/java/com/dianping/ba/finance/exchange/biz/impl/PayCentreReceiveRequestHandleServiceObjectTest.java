package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.PayCentreReceiveRequestService;
import com.dianping.ba.finance.exchange.api.ReceiveOrderService;
import com.dianping.ba.finance.exchange.api.dtos.PayCentreReceiveRequestDTO;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.ba.finance.exchange.biz.producer.ReceiveOrderResultNotify;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.mockito.Mockito.mock;

public class PayCentreReceiveRequestHandleServiceObjectTest {

    private PayCentreReceiveRequestHandleServiceObject payCentreReceiveRequestHandleServiceObjectStub;

    private PayCentreReceiveRequestService payCentreReceiveRequestServiceMock;

    private ReceiveOrderService receiveOrderServiceMock;

    private ReceiveOrderResultNotify receiveOrderResultNotifyMock;

    private ExecutorService executorServiceMock;

    @Before
    public void setUp() throws Exception {
        payCentreReceiveRequestHandleServiceObjectStub = new PayCentreReceiveRequestHandleServiceObject();

        payCentreReceiveRequestServiceMock = mock(PayCentreReceiveRequestService.class);
        payCentreReceiveRequestHandleServiceObjectStub.setPayCentreReceiveRequestService(payCentreReceiveRequestServiceMock);

        receiveOrderServiceMock = mock(ReceiveOrderService.class);
        payCentreReceiveRequestHandleServiceObjectStub.setReceiveOrderService(receiveOrderServiceMock);

        receiveOrderResultNotifyMock = mock(ReceiveOrderResultNotify.class);
        payCentreReceiveRequestHandleServiceObjectStub.setReceiveOrderResultNotify(receiveOrderResultNotifyMock);

        executorServiceMock = Executors.newSingleThreadExecutor();
        payCentreReceiveRequestHandleServiceObjectStub.setExecutorService(executorServiceMock);
    }

    @Test
    public void testHandleReceiveRequest() throws Exception {
        PayCentreReceiveRequestDTO payCentreReceiveRequestDTO = new PayCentreReceiveRequestDTO();
        payCentreReceiveRequestDTO.setBankId(123);
        payCentreReceiveRequestDTO.setTradeNo("TS312");
        payCentreReceiveRequestDTO.setBusinessType(BusinessType.ADVERTISEMENT.value());
        payCentreReceiveRequestDTO.setBizContent("8787");
        payCentreReceiveRequestDTO.setReceiveAmount(BigDecimal.TEN);
        payCentreReceiveRequestDTO.setReceiveDate(new Date());

        payCentreReceiveRequestHandleServiceObjectStub.handleReceiveRequest(payCentreReceiveRequestDTO);

    }
}