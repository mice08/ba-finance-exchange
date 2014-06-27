package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.datas.PayCentreReceiveRequestData;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.ba.finance.exchange.biz.dao.PayCentreReceiveRequestDao;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PayCentreReceiveRequestServiceObjectTest {

    private PayCentreReceiveRequestServiceObject payCentreReceiveRequestServiceObjectStub;

    private PayCentreReceiveRequestDao payCentreReceiveRequestDaoMock;

    @Before
    public void setUp() throws Exception {
        payCentreReceiveRequestDaoMock = mock(PayCentreReceiveRequestDao.class);
        payCentreReceiveRequestServiceObjectStub = new PayCentreReceiveRequestServiceObject();
        payCentreReceiveRequestServiceObjectStub.setPayCentreReceiveRequestDao(payCentreReceiveRequestDaoMock);
    }

    @Test
    public void testInsertPayCentreReceiveRequest() throws Exception {
        when(payCentreReceiveRequestDaoMock.insertPayCentreReceiveRequest(any(PayCentreReceiveRequestData.class))).thenReturn(8787);

        PayCentreReceiveRequestData payCentreReceiveRequestData = new PayCentreReceiveRequestData();
        payCentreReceiveRequestData.setTradeNo("TS123");
        payCentreReceiveRequestData.setBusinessType(BusinessType.ADVERTISEMENT.value());
        payCentreReceiveRequestData.setBizContent("AD123");
        payCentreReceiveRequestData.setBankId(1);
        payCentreReceiveRequestData.setMemo("memo87");
        payCentreReceiveRequestData.setReceiveAmount(BigDecimal.TEN);
        payCentreReceiveRequestData.setReceiveDate(new Date());
        payCentreReceiveRequestData.setPayChannel(10);
        payCentreReceiveRequestData.setPayMethod(5);

        boolean b = payCentreReceiveRequestServiceObjectStub.insertPayCentreReceiveRequest(payCentreReceiveRequestData);
        Assert.assertTrue(b);
        Assert.assertEquals(8787, payCentreReceiveRequestData.getRequestId());
    }
}