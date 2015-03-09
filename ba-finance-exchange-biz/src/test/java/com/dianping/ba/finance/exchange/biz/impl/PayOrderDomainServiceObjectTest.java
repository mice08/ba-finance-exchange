package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.AccountService;
import com.dianping.ba.finance.exchange.api.PayOrderService;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.ba.finance.exchange.api.enums.PayType;
import com.dianping.swallow.producer.Producer;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by 遐 on 2015/3/9.
 */
public class PayOrderDomainServiceObjectTest {

    private PayOrderDomainServiceObject payOrderDomainServiceObjectStub;
    private PayOrderService payOrderServiceMock;
    private AccountService accountServiceMock;
    private Producer bankPayProducerMock;


    @Before
    public void setup() {
        payOrderDomainServiceObjectStub = new PayOrderDomainServiceObject();
        payOrderServiceMock = mock(PayOrderService.class);
        bankPayProducerMock = mock(Producer.class);
        accountServiceMock = mock(AccountService.class);
        payOrderDomainServiceObjectStub.setAccountService(accountServiceMock);
        payOrderDomainServiceObjectStub.setPayOrderService(payOrderServiceMock);
        payOrderDomainServiceObjectStub.setBankPayProducer(bankPayProducerMock);
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
}
