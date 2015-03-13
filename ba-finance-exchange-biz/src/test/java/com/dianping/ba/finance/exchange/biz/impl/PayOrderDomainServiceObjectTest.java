package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.AccountService;
import com.dianping.ba.finance.exchange.api.PayOrderService;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.ba.finance.exchange.api.dtos.AccountEntryRequestDTO;
import com.dianping.ba.finance.exchange.api.dtos.BankAccountDTO;
import com.dianping.ba.finance.exchange.api.dtos.BankPayResultDTO;
import com.dianping.ba.finance.exchange.api.enums.PayType;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by 遐 on 2015/3/9.
 */
public class PayOrderDomainServiceObjectTest {

    private PayOrderDomainServiceObject payOrderDomainServiceObjectStub;
    private PayOrderService payOrderServiceMock;
    private AccountService accountServiceMock;

    @Before
    public void setup() {
        payOrderDomainServiceObjectStub = new PayOrderDomainServiceObject();
        payOrderServiceMock = mock(PayOrderService.class);
        accountServiceMock = mock(AccountService.class);
        payOrderDomainServiceObjectStub.setAccountService(accountServiceMock);
        payOrderDomainServiceObjectStub.setPayOrderService(payOrderServiceMock);
    }

    @Test
    public void testPaySuccess() {
        List<Integer> poIds = new ArrayList<Integer>();
        poIds.add(1);
        List<PayOrderData> dataList = new ArrayList<PayOrderData>();
        PayOrderData orderData = new PayOrderData();
        orderData.setPoId(1);
        orderData.setStatus(10);
        orderData.setPayAmount(BigDecimal.TEN);
        orderData.setPayType(PayType.GROUPON_SETTLE.getPayType());
        dataList.add(orderData);
        when(payOrderServiceMock.findPayOrderByIdList(anyList())).thenReturn(dataList);
        BankAccountDTO accountDTO = new BankAccountDTO();
        accountDTO.setBankAccountName("abc");
        accountDTO.setBankAccountNo("1001");
        when(accountServiceMock.loadBankAccount(anyInt())).thenReturn(accountDTO);
        int actual = payOrderDomainServiceObjectStub.pay(poIds, -1);

        Assert.assertEquals(1, actual);
    }

    @Test
    public void testPayFailed() {
        List<Integer> poIds = new ArrayList<Integer>();
        poIds.add(1);
        List<PayOrderData> dataList = new ArrayList<PayOrderData>();
        PayOrderData orderData = new PayOrderData();
        orderData.setPoId(1);
        orderData.setStatus(1);
        orderData.setPayAmount(BigDecimal.TEN);
        orderData.setPayType(PayType.GROUPON_SETTLE.getPayType());
        dataList.add(orderData);
        when(payOrderServiceMock.findPayOrderByIdList(anyList())).thenReturn(dataList);
        int actual = payOrderDomainServiceObjectStub.pay(poIds, -1);

        Assert.assertEquals(0, actual);
    }

    @Test
    public void testHandleBankPayResult() {
        when(payOrderServiceMock.updatePayOrderStatus(anyInt(), anyInt(), anyInt(), anyString())).thenReturn(1);
        when(accountServiceMock.updateAccount(any(AccountEntryRequestDTO.class))).thenReturn(true);
        when(payOrderServiceMock.loadPayOrderDataByPOID(anyInt())).thenReturn(new PayOrderData());
        BankPayResultDTO dto = new BankPayResultDTO();
        dto.setCode(1);
        boolean result = payOrderDomainServiceObjectStub.handleBankPayResult(dto);
        Assert.assertFalse(result);
        dto.setCode(2000);
        result = payOrderDomainServiceObjectStub.handleBankPayResult(dto);
        Assert.assertTrue(result);

        dto.setCode(-1);
        when(payOrderServiceMock.updatePayOrderStatus(anyInt(), anyInt(), anyInt(), anyString())).thenReturn(0);
        result = payOrderDomainServiceObjectStub.handleBankPayResult(dto);
        Assert.assertFalse(result);
    }
}
