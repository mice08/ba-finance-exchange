package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.PayOrderService;
import com.dianping.ba.finance.exchange.api.PayRequestService;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.ba.finance.exchange.api.datas.PayRequestData;
import com.dianping.ba.finance.exchange.api.dtos.PayRequestDTO;
import com.dianping.ba.finance.exchange.api.enums.BankAccountType;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.ba.finance.exchange.api.enums.PayRequestStatus;
import com.dianping.ba.finance.exchange.biz.constants.EventConstant;
import com.dianping.finance.common.swallow.SwallowEventBean;
import com.dianping.finance.common.swallow.SwallowProducer;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.*;

public class PayRequestHandleServiceObjectTest {

    private PayRequestHandleServiceObject payRequestHandleServiceObjectStub;

    private long timeoutMock;

    private PayRequestService payRequestServiceMock;

    private PayOrderService payOrderServiceMock;

    private SwallowProducer payRequestProducerMock;

    private ExecutorService executorService;

    @Before
    public void setUp() throws Exception {
        payRequestHandleServiceObjectStub = new PayRequestHandleServiceObject();

        timeoutMock = TimeUnit.MINUTES.toMillis(15);// 15分钟
        payRequestHandleServiceObjectStub.setTimeout(timeoutMock);

        payRequestServiceMock = mock(PayRequestService.class);
        payRequestHandleServiceObjectStub.setPayRequestService(payRequestServiceMock);

        payOrderServiceMock = mock(PayOrderService.class);
        payRequestHandleServiceObjectStub.setPayOrderService(payOrderServiceMock);


        payRequestProducerMock = mock(SwallowProducer.class);
        payRequestHandleServiceObjectStub.setPayRequestProducer(payRequestProducerMock);

        executorService = Executors.newSingleThreadExecutor();
        payRequestHandleServiceObjectStub.setExecutorService(executorService);
    }

    @Test
    public void testHandleNewPayRequestTimeout() throws Exception {
        PayRequestDTO payRequestDTO = new PayRequestDTO();
        payRequestDTO.setBusinessType(123);
        payRequestDTO.setPaySequence("TS312");
        payRequestDTO.setBusinessType(BusinessType.EXPENSE.value());
        payRequestDTO.setPayAmount(BigDecimal.TEN);
        payRequestDTO.setBankAccountNo("bankAccountNo");
        payRequestDTO.setBankAccountName("bankAccountName");
        payRequestDTO.setBankBranchName("branchName");
        payRequestDTO.setBankName("bankName");
        payRequestDTO.setBankFullBranchName("fullBranchName");
        payRequestDTO.setBankCode("bankCode");
        payRequestDTO.setBankAccountType(BankAccountType.PERSONAL.value());
        payRequestDTO.setPayBankAccountNo("payBankAccountNo");
        payRequestDTO.setPayBankName("payBankName");

        Calendar requestCal = Calendar.getInstance();
        requestCal.set(Calendar.MINUTE, requestCal.get(Calendar.MINUTE) - 16); // 16分钟前发送的消息
        payRequestDTO.setRequestTime(requestCal.getTime());

        payRequestHandleServiceObjectStub.handleNewPayRequest(payRequestDTO);

        verify(payRequestServiceMock, timeout(5000).times(1)).insertPayRequest(any(PayRequestData.class));

        verify(payRequestServiceMock, timeout(5000).times(1)).updatePayRequest(anyInt(), eq(PayRequestStatus.TIMEOUT), anyString());

        SwallowEventBean swallowEventBean = new SwallowEventBean();
        swallowEventBean.setEventKey(EventConstant.EXPENSE_PAY_RESULT_EVENT_KEY);
        swallowEventBean.setMessageType(BusinessType.EXPENSE.name());
        verify(payRequestProducerMock, timeout(5000).times(1)).fireSwallowEvent(any(SwallowEventBean.class));
    }

    @Test
    public void testHandleNewPayRequestInsertFailed() throws Exception {
        PayRequestDTO payRequestDTO = new PayRequestDTO();
        payRequestDTO.setBusinessType(123);
        payRequestDTO.setPaySequence("TS312");
        payRequestDTO.setBusinessType(BusinessType.EXPENSE.value());
        payRequestDTO.setPayAmount(BigDecimal.TEN);
        payRequestDTO.setBankAccountNo("bankAccountNo");
        payRequestDTO.setBankAccountName("bankAccountName");
        payRequestDTO.setBankBranchName("branchName");
        payRequestDTO.setBankName("bankName");
        payRequestDTO.setBankFullBranchName("fullBranchName");
        payRequestDTO.setBankCode("bankCode");
        payRequestDTO.setBankAccountType(BankAccountType.PERSONAL.value());
        payRequestDTO.setPayBankAccountNo("payBankAccountNo");
        payRequestDTO.setPayBankName("payBankName");

        Calendar requestCal = Calendar.getInstance();
        payRequestDTO.setRequestTime(requestCal.getTime());

        when(payOrderServiceMock.createPayOrder(any(PayOrderData.class))).thenReturn(-1);

        payRequestHandleServiceObjectStub.handleNewPayRequest(payRequestDTO);

        verify(payRequestServiceMock, timeout(5000).times(1)).insertPayRequest(any(PayRequestData.class));
        verify(payRequestServiceMock, timeout(5000).times(1)).updatePayRequest(anyInt(), eq(PayRequestStatus.HANDLE_REQUEST_FAILED), anyString());
        verify(payRequestProducerMock, timeout(5000).times(1)).fireSwallowEvent(any(SwallowEventBean.class));

    }

    @Test
    public void testHandleNewPayRequestInvalidAmount() throws Exception {
        PayRequestDTO payRequestDTO = new PayRequestDTO();
        payRequestDTO.setBusinessType(123);
        payRequestDTO.setPaySequence("TS312");
        payRequestDTO.setBusinessType(BusinessType.EXPENSE.value());
        payRequestDTO.setPayAmount(new BigDecimal(-100));
        payRequestDTO.setBankAccountNo("bankAccountNo");
        payRequestDTO.setBankAccountName("bankAccountName");
        payRequestDTO.setBankBranchName("branchName");
        payRequestDTO.setBankName("bankName");
        payRequestDTO.setBankFullBranchName("fullBranchName");
        payRequestDTO.setBankCode("bankCode");
        payRequestDTO.setBankAccountType(BankAccountType.PERSONAL.value());
        payRequestDTO.setPayBankAccountNo("payBankAccountNo");
        payRequestDTO.setPayBankName("payBankName");

        Calendar requestCal = Calendar.getInstance();
        payRequestDTO.setRequestTime(requestCal.getTime());

        when(payOrderServiceMock.createPayOrder(any(PayOrderData.class))).thenReturn(-1);
        payRequestHandleServiceObjectStub.handleNewPayRequest(payRequestDTO);

        verify(payRequestServiceMock, timeout(5000).times(1)).insertPayRequest(any(PayRequestData.class));
        verify(payRequestServiceMock, timeout(5000).times(1)).updatePayRequest(anyInt(), eq(PayRequestStatus.INVALID_AMOUNT), anyString());
        verify(payRequestProducerMock, timeout(5000).times(1)).fireSwallowEvent(any(SwallowEventBean.class));
    }

    @Test
    public void testHandleNewPayRequestSuccess() throws Exception {
        PayRequestDTO payRequestDTO = new PayRequestDTO();
        payRequestDTO.setBusinessType(123);
        payRequestDTO.setPaySequence("TS312");
        payRequestDTO.setBusinessType(BusinessType.EXPENSE.value());
        payRequestDTO.setPayAmount(BigDecimal.TEN);
        payRequestDTO.setBankAccountNo("bankAccountNo");
        payRequestDTO.setBankAccountName("bankAccountName");
        payRequestDTO.setBankBranchName("branchName");
        payRequestDTO.setBankName("bankName");
        payRequestDTO.setBankFullBranchName("fullBranchName");
        payRequestDTO.setBankCode("bankCode");
        payRequestDTO.setBankAccountType(BankAccountType.PERSONAL.value());
        payRequestDTO.setPayBankAccountNo("payBankAccountNo");
        payRequestDTO.setPayBankName("payBankName");

        Calendar requestCal = Calendar.getInstance();
        payRequestDTO.setRequestTime(requestCal.getTime());

        when(payOrderServiceMock.createPayOrder(any(PayOrderData.class))).thenReturn(1);

        payRequestHandleServiceObjectStub.handleNewPayRequest(payRequestDTO);

        verify(payRequestServiceMock, timeout(5000).times(1)).insertPayRequest(any(PayRequestData.class));
        verify(payRequestServiceMock, timeout(5000).times(1)).updatePayRequest(anyInt(), eq(PayRequestStatus.SUCCESS), anyString());
        verify(payRequestProducerMock, timeout(5000).times(1)).fireSwallowEvent(any(SwallowEventBean.class));

    }

    @Test
    public void testHandleNewPayRequestSuccessBankAccountNo() throws Exception {
        PayRequestDTO payRequestDTO = new PayRequestDTO();
        payRequestDTO.setBusinessType(123);
        payRequestDTO.setPaySequence("TS312");
        payRequestDTO.setBusinessType(BusinessType.EXPENSE.value());
        payRequestDTO.setPayAmount(BigDecimal.TEN);
//        payRequestDTO.setBankAccountNo("bankAccountNo");
        payRequestDTO.setBankAccountName("bankAccountName");
        payRequestDTO.setBankBranchName("branchName");
        payRequestDTO.setBankName("bankName");
        payRequestDTO.setBankFullBranchName("fullBranchName");
        payRequestDTO.setBankCode("bankCode");
        payRequestDTO.setBankAccountType(BankAccountType.PERSONAL.value());
        payRequestDTO.setPayBankAccountNo("payBankAccountNo");
        payRequestDTO.setPayBankName("payBankName");

        Calendar requestCal = Calendar.getInstance();
        payRequestDTO.setRequestTime(requestCal.getTime());

        when(payOrderServiceMock.createPayOrder(any(PayOrderData.class))).thenReturn(1);

        payRequestHandleServiceObjectStub.handleNewPayRequest(payRequestDTO);

        verify(payRequestServiceMock, timeout(5000).times(1)).insertPayRequest(any(PayRequestData.class));
        verify(payRequestServiceMock, timeout(5000).times(1)).updatePayRequest(anyInt(), eq(PayRequestStatus.INVALID_FIELDS), anyString());
        verify(payRequestProducerMock, timeout(5000).times(1)).fireSwallowEvent(any(SwallowEventBean.class));

    }

    @Test
    public void testHandleNewPayRequestSuccessBankAccountName() throws Exception {
        PayRequestDTO payRequestDTO = new PayRequestDTO();
        payRequestDTO.setBusinessType(123);
        payRequestDTO.setPaySequence("TS312");
        payRequestDTO.setBusinessType(BusinessType.EXPENSE.value());
        payRequestDTO.setPayAmount(BigDecimal.TEN);
        payRequestDTO.setBankAccountNo("bankAccountNo");
//        payRequestDTO.setBankAccountName("bankAccountName");
        payRequestDTO.setBankBranchName("branchName");
        payRequestDTO.setBankName("bankName");
        payRequestDTO.setBankFullBranchName("fullBranchName");
        payRequestDTO.setBankCode("bankCode");
        payRequestDTO.setBankAccountType(BankAccountType.PERSONAL.value());
        payRequestDTO.setPayBankAccountNo("payBankAccountNo");
        payRequestDTO.setPayBankName("payBankName");

        Calendar requestCal = Calendar.getInstance();
        payRequestDTO.setRequestTime(requestCal.getTime());

        when(payOrderServiceMock.createPayOrder(any(PayOrderData.class))).thenReturn(1);

        payRequestHandleServiceObjectStub.handleNewPayRequest(payRequestDTO);

        verify(payRequestServiceMock, timeout(5000).times(1)).insertPayRequest(any(PayRequestData.class));
        verify(payRequestServiceMock, timeout(5000).times(1)).updatePayRequest(anyInt(), eq(PayRequestStatus.INVALID_FIELDS), anyString());
        verify(payRequestProducerMock, timeout(5000).times(1)).fireSwallowEvent(any(SwallowEventBean.class));

    }

    @Test
    public void testHandleNewPayRequestEmptyBranchName() throws Exception {
        PayRequestDTO payRequestDTO = new PayRequestDTO();
        payRequestDTO.setBusinessType(123);
        payRequestDTO.setPaySequence("TS312");
        payRequestDTO.setBusinessType(BusinessType.EXPENSE.value());
        payRequestDTO.setPayAmount(BigDecimal.TEN);
        payRequestDTO.setBankAccountNo("bankAccountNo");
        payRequestDTO.setBankAccountName("bankAccountName");
//        payRequestDTO.setBankBranchName("branchName");
        payRequestDTO.setBankName("bankName");
        payRequestDTO.setBankFullBranchName("fullBranchName");
        payRequestDTO.setBankCode("bankCode");
        payRequestDTO.setBankAccountType(BankAccountType.PERSONAL.value());
        payRequestDTO.setPayBankAccountNo("payBankAccountNo");
        payRequestDTO.setPayBankName("payBankName");

        Calendar requestCal = Calendar.getInstance();
        payRequestDTO.setRequestTime(requestCal.getTime());

        when(payOrderServiceMock.createPayOrder(any(PayOrderData.class))).thenReturn(1);

        payRequestHandleServiceObjectStub.handleNewPayRequest(payRequestDTO);

        verify(payRequestServiceMock, timeout(5000).times(1)).insertPayRequest(any(PayRequestData.class));
        verify(payRequestServiceMock, timeout(5000).times(1)).updatePayRequest(anyInt(), eq(PayRequestStatus.INVALID_FIELDS), anyString());
        verify(payRequestProducerMock, timeout(5000).times(1)).fireSwallowEvent(any(SwallowEventBean.class));

    }

    @Test
    public void testHandleNewPayRequestEmptyBankName() throws Exception {
        PayRequestDTO payRequestDTO = new PayRequestDTO();
        payRequestDTO.setBusinessType(123);
        payRequestDTO.setPaySequence("TS312");
        payRequestDTO.setBusinessType(BusinessType.EXPENSE.value());
        payRequestDTO.setPayAmount(BigDecimal.TEN);
        payRequestDTO.setBankAccountNo("bankAccountNo");
        payRequestDTO.setBankAccountName("bankAccountName");
        payRequestDTO.setBankBranchName("branchName");
//        payRequestDTO.setBankName("bankName");
        payRequestDTO.setBankFullBranchName("fullBranchName");
        payRequestDTO.setBankCode("bankCode");
        payRequestDTO.setBankAccountType(BankAccountType.PERSONAL.value());
        payRequestDTO.setPayBankAccountNo("payBankAccountNo");
        payRequestDTO.setPayBankName("payBankName");

        Calendar requestCal = Calendar.getInstance();
        payRequestDTO.setRequestTime(requestCal.getTime());

        when(payOrderServiceMock.createPayOrder(any(PayOrderData.class))).thenReturn(1);

        payRequestHandleServiceObjectStub.handleNewPayRequest(payRequestDTO);

        verify(payRequestServiceMock, timeout(5000).times(1)).insertPayRequest(any(PayRequestData.class));
        verify(payRequestServiceMock, timeout(5000).times(1)).updatePayRequest(anyInt(), eq(PayRequestStatus.INVALID_FIELDS), anyString());
        verify(payRequestProducerMock, timeout(5000).times(1)).fireSwallowEvent(any(SwallowEventBean.class));

    }

    @Test
    public void testHandleNewPayRequestEmptyFullBranchName() throws Exception {
        PayRequestDTO payRequestDTO = new PayRequestDTO();
        payRequestDTO.setBusinessType(123);
        payRequestDTO.setPaySequence("TS312");
        payRequestDTO.setBusinessType(BusinessType.EXPENSE.value());
        payRequestDTO.setPayAmount(BigDecimal.TEN);
        payRequestDTO.setBankAccountNo("bankAccountNo");
        payRequestDTO.setBankAccountName("bankAccountName");
        payRequestDTO.setBankBranchName("branchName");
        payRequestDTO.setBankName("bankName");
//        payRequestDTO.setBankFullBranchName("fullBranchName");
        payRequestDTO.setBankCode("bankCode");
        payRequestDTO.setBankAccountType(BankAccountType.PERSONAL.value());
        payRequestDTO.setPayBankAccountNo("payBankAccountNo");
        payRequestDTO.setPayBankName("payBankName");

        Calendar requestCal = Calendar.getInstance();
        payRequestDTO.setRequestTime(requestCal.getTime());

        when(payOrderServiceMock.createPayOrder(any(PayOrderData.class))).thenReturn(1);

        payRequestHandleServiceObjectStub.handleNewPayRequest(payRequestDTO);

        verify(payRequestServiceMock, timeout(5000).times(1)).insertPayRequest(any(PayRequestData.class));
        verify(payRequestServiceMock, timeout(5000).times(1)).updatePayRequest(anyInt(), eq(PayRequestStatus.INVALID_FIELDS), anyString());
        verify(payRequestProducerMock, timeout(5000).times(1)).fireSwallowEvent(any(SwallowEventBean.class));

    }

    @Test
    public void testHandleNewPayRequestEmptyBankAccountType() throws Exception {
        PayRequestDTO payRequestDTO = new PayRequestDTO();
        payRequestDTO.setBusinessType(123);
        payRequestDTO.setPaySequence("TS312");
        payRequestDTO.setBusinessType(BusinessType.EXPENSE.value());
        payRequestDTO.setPayAmount(BigDecimal.TEN);
        payRequestDTO.setBankAccountNo("bankAccountNo");
        payRequestDTO.setBankAccountName("bankAccountName");
        payRequestDTO.setBankBranchName("branchName");
        payRequestDTO.setBankName("bankName");
        payRequestDTO.setBankFullBranchName("fullBranchName");
        payRequestDTO.setBankCode("bankCode");
//        payRequestDTO.setBankAccountType(BankAccountType.PERSONAL.value());
        payRequestDTO.setPayBankAccountNo("payBankAccountNo");
        payRequestDTO.setPayBankName("payBankName");

        Calendar requestCal = Calendar.getInstance();
        payRequestDTO.setRequestTime(requestCal.getTime());

        when(payOrderServiceMock.createPayOrder(any(PayOrderData.class))).thenReturn(1);

        payRequestHandleServiceObjectStub.handleNewPayRequest(payRequestDTO);

        verify(payRequestServiceMock, timeout(5000).times(1)).insertPayRequest(any(PayRequestData.class));
        verify(payRequestServiceMock, timeout(5000).times(1)).updatePayRequest(anyInt(), eq(PayRequestStatus.INVALID_FIELDS), anyString());
        verify(payRequestProducerMock, timeout(5000).times(1)).fireSwallowEvent(any(SwallowEventBean.class));

    }

    @Test
    public void testHandleNewPayRequestEmptyPayBankAccountNo() throws Exception {
        PayRequestDTO payRequestDTO = new PayRequestDTO();
        payRequestDTO.setBusinessType(123);
        payRequestDTO.setPaySequence("TS312");
        payRequestDTO.setBusinessType(BusinessType.EXPENSE.value());
        payRequestDTO.setPayAmount(BigDecimal.TEN);
        payRequestDTO.setBankAccountNo("bankAccountNo");
        payRequestDTO.setBankAccountName("bankAccountName");
        payRequestDTO.setBankBranchName("branchName");
        payRequestDTO.setBankName("bankName");
        payRequestDTO.setBankFullBranchName("fullBranchName");
        payRequestDTO.setBankCode("bankCode");
        payRequestDTO.setBankAccountType(BankAccountType.PERSONAL.value());
//        payRequestDTO.setPayBankAccountNo("payBankAccountNo");
        payRequestDTO.setPayBankName("payBankName");

        Calendar requestCal = Calendar.getInstance();
        payRequestDTO.setRequestTime(requestCal.getTime());

        when(payOrderServiceMock.createPayOrder(any(PayOrderData.class))).thenReturn(1);

        payRequestHandleServiceObjectStub.handleNewPayRequest(payRequestDTO);

        verify(payRequestServiceMock, timeout(5000).times(1)).insertPayRequest(any(PayRequestData.class));
        verify(payRequestServiceMock, timeout(5000).times(1)).updatePayRequest(anyInt(), eq(PayRequestStatus.INVALID_FIELDS), anyString());
        verify(payRequestProducerMock, timeout(5000).times(1)).fireSwallowEvent(any(SwallowEventBean.class));

    }

    @Test
    public void testHandleNewPayRequestEmptyPayBankName() throws Exception {
        PayRequestDTO payRequestDTO = new PayRequestDTO();
        payRequestDTO.setBusinessType(123);
        payRequestDTO.setPaySequence("TS312");
        payRequestDTO.setBusinessType(BusinessType.EXPENSE.value());
        payRequestDTO.setPayAmount(BigDecimal.TEN);
        payRequestDTO.setBankAccountNo("bankAccountNo");
        payRequestDTO.setBankAccountName("bankAccountName");
        payRequestDTO.setBankBranchName("branchName");
        payRequestDTO.setBankName("bankName");
        payRequestDTO.setBankFullBranchName("fullBranchName");
        payRequestDTO.setBankCode("bankCode");
        payRequestDTO.setBankAccountType(BankAccountType.PERSONAL.value());
        payRequestDTO.setPayBankAccountNo("payBankAccountNo");
//        payRequestDTO.setPayBankName("payBankName");

        Calendar requestCal = Calendar.getInstance();
        payRequestDTO.setRequestTime(requestCal.getTime());

        when(payOrderServiceMock.createPayOrder(any(PayOrderData.class))).thenReturn(1);

        payRequestHandleServiceObjectStub.handleNewPayRequest(payRequestDTO);

        verify(payRequestServiceMock, timeout(5000).times(1)).insertPayRequest(any(PayRequestData.class));
        verify(payRequestServiceMock, timeout(5000).times(1)).updatePayRequest(anyInt(), eq(PayRequestStatus.INVALID_FIELDS), anyString());
        verify(payRequestProducerMock, timeout(5000).times(1)).fireSwallowEvent(any(SwallowEventBean.class));

    }
}