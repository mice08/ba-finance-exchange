package com.dianping.ba.finance.exchange.bankpaymonitor.job;

import com.dianping.ba.finance.exchange.api.enums.PayOrderStatus;
import com.dianping.ba.finance.exchange.bankpaymonitor.api.MonitorService;
import com.dianping.ba.finance.exchange.bankpaymonitor.api.PayOrderService;
import com.dianping.ba.finance.exchange.bankpaymonitor.api.datas.PayOrderMonitorData;
import com.dianping.ba.finance.exchange.bankpaymonitor.api.enums.BankPayException;
import com.dianping.ba.finance.paymentplatform.api.PaymentQueryService;
import com.dianping.ba.finance.paymentplatform.api.dtos.PaymentRecordDTO;
import com.dianping.ba.finance.paymentplatform.api.enums.PaymentRecordStatus;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BankPayMonitorTest {

    private BankPayMonitor bankPayMonitorStub;
    private MonitorService monitorServiceMock;
    private PayOrderService payOrderServiceMock;
    private PaymentQueryService paymentQueryServiceMock;
    private ExecutorService executorServiceMock;

    @Before
    public void setUp() throws Exception {
        bankPayMonitorStub = new BankPayMonitor();
        monitorServiceMock = mock(MonitorService.class);
        payOrderServiceMock = mock(PayOrderService.class);
        paymentQueryServiceMock = mock(PaymentQueryService.class);
        bankPayMonitorStub.setMonitorService(monitorServiceMock);
        bankPayMonitorStub.setPayOrderService(payOrderServiceMock);
        bankPayMonitorStub.setPaymentQueryService(paymentQueryServiceMock);
        executorServiceMock = Executors.newFixedThreadPool(1);
        bankPayMonitorStub.setExecutorService(executorServiceMock);
    }

    @Test
    public void testCheck() throws Exception {
        PaymentRecordDTO dto1 = new PaymentRecordDTO();
        dto1.setStatus(PaymentRecordStatus.PAY_SUCCESS.getCode());
        dto1.setInsId("1");
        PaymentRecordDTO dto2 = new PaymentRecordDTO();
        dto2.setStatus(PaymentRecordStatus.PAY_FAILED.getCode());
        dto2.setInsId("1");
        PaymentRecordDTO dto3 = new PaymentRecordDTO();
        dto3.setStatus(PaymentRecordStatus.PAY_FAILED.getCode());
        dto3.setInsId("2");
        PaymentRecordDTO dto4 = new PaymentRecordDTO();
        dto4.setStatus(PaymentRecordStatus.PAY_FAILED.getCode());
        dto4.setInsId("2");
        PaymentRecordDTO dto5 = new PaymentRecordDTO();
        dto5.setStatus(PaymentRecordStatus.PAY_SUCCESS.getCode());
        dto5.setInsId("3");
        PaymentRecordDTO dto6 = new PaymentRecordDTO();
        dto6.setStatus(PaymentRecordStatus.PAY_SUCCESS.getCode());
        dto6.setInsId("3");
        PaymentRecordDTO dto7 = new PaymentRecordDTO();
        dto7.setStatus(PaymentRecordStatus.PAY_FAILED.getCode());
        dto7.setInsId("3");
        PayOrderMonitorData data1 = new PayOrderMonitorData();
        data1.setStatus(PayOrderStatus.PAY_SUCCESS.value());
        data1.setPayCode("1");
        PayOrderMonitorData data2 = new PayOrderMonitorData();
        data2.setStatus(PayOrderStatus.PAY_SUCCESS.value());
        data2.setPayCode("2");
        PayOrderMonitorData data3 = new PayOrderMonitorData();
        data3.setStatus(PayOrderStatus.PAY_SUCCESS.value());
        data3.setPayCode("3");
        when(monitorServiceMock.insertMonitorTime(new Date())).thenReturn(true);
        when(monitorServiceMock.loadLastMonitorTime()).thenReturn(new Date());
        when(monitorServiceMock.insertMonitorResult(anyInt(), any(BankPayException.class))).thenReturn(true);
        when(payOrderServiceMock.findPayOrders(anyInt(), anyInt(), any(Date.class), any(Date.class))).thenReturn(Arrays.asList(data1, data2, data3));
        when(paymentQueryServiceMock.queryPaymentRecordByInsIds(anyListOf(String.class))).thenReturn(Arrays.asList(dto1, dto2, dto3, dto4, dto5, dto6, dto7));
        bankPayMonitorStub.check();

    }
}