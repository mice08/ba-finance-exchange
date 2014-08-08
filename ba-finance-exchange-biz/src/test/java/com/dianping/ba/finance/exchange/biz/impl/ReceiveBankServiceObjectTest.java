package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.datas.ReceiveBankData;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.ba.finance.exchange.biz.dao.ReceiveBankDao;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

public class ReceiveBankServiceObjectTest {

    private ReceiveBankServiceObject receiveBankServiceObjectStub;

    private ReceiveBankDao receiveBankDaoMock;

    @Before
    public void setUp() throws Exception {
        receiveBankServiceObjectStub = new ReceiveBankServiceObject();

        receiveBankDaoMock = mock(ReceiveBankDao.class);
        receiveBankServiceObjectStub.setReceiveBankDao(receiveBankDaoMock);
    }

    @Test
    public void testFindAllReceiveBank() throws Exception {
        ReceiveBankData rbData = new ReceiveBankData();
        rbData.setAddTime(new Date());
        rbData.setBankId(123);
        rbData.setBankName("bankName");
        rbData.setBusinessType(BusinessType.ADVERTISEMENT.value());
        rbData.setCompanyId(87);
        when(receiveBankDaoMock.findAllReceiveBank()).thenReturn(Arrays.asList(rbData));

        receiveBankServiceObjectStub.setCache(false);
        List<ReceiveBankData> receiveBankDataList = receiveBankServiceObjectStub.findAllReceiveBank();
        Assert.assertNotNull(receiveBankDataList);
        Assert.assertEquals(123, receiveBankDataList.get(0).getBankId());
    }

    @Test
    public void testFindAllReceiveBankWithCache() throws Exception {
        ReceiveBankData rbData = new ReceiveBankData();
        rbData.setAddTime(new Date());
        rbData.setBankId(123);
        rbData.setBankName("bankName");
        rbData.setBusinessType(BusinessType.ADVERTISEMENT.value());
        rbData.setCompanyId(87);
        when(receiveBankDaoMock.findAllReceiveBank()).thenReturn(Arrays.asList(rbData));

        receiveBankServiceObjectStub.setCache(true);

        receiveBankServiceObjectStub.findAllReceiveBank();
        // get from cache;
        List<ReceiveBankData> receiveBankDataList = receiveBankServiceObjectStub.findAllReceiveBank();
        Assert.assertNotNull(receiveBankDataList);
        Assert.assertEquals(123, receiveBankDataList.get(0).getBankId());
        verify(receiveBankDaoMock, times(1)).findAllReceiveBank();
    }

    @Test
    public void testFindAllReceiveBankClearCache() throws Exception {
        ReceiveBankData rbData = new ReceiveBankData();
        rbData.setAddTime(new Date());
        rbData.setBankId(123);
        rbData.setBankName("bankName");
        rbData.setBusinessType(BusinessType.ADVERTISEMENT.value());
        rbData.setCompanyId(87);
        when(receiveBankDaoMock.findAllReceiveBank()).thenReturn(Arrays.asList(rbData));

        receiveBankServiceObjectStub.setCache(true);

        receiveBankServiceObjectStub.findAllReceiveBank();
        // get from cache;
        List<ReceiveBankData> receiveBankDataList = receiveBankServiceObjectStub.findAllReceiveBank();
        Assert.assertNotNull(receiveBankDataList);
        Assert.assertEquals(123, receiveBankDataList.get(0).getBankId());
        verify(receiveBankDaoMock, times(1)).findAllReceiveBank();

        receiveBankServiceObjectStub.clearCache();

        receiveBankServiceObjectStub.findAllReceiveBank();
        verify(receiveBankDaoMock, times(2)).findAllReceiveBank();


    }

    @Test
    public void testLoadReceiveBankByBankId() throws Exception {
        ReceiveBankData rbData = new ReceiveBankData();
        rbData.setAddTime(new Date());
        rbData.setBankId(123);
        rbData.setBankName("bankName");
        rbData.setBusinessType(BusinessType.ADVERTISEMENT.value());
        rbData.setCompanyId(87);
        when(receiveBankDaoMock.findAllReceiveBank()).thenReturn(Arrays.asList(rbData));

        receiveBankServiceObjectStub.setCache(true);

        ReceiveBankData receiveBankData = receiveBankServiceObjectStub.loadReceiveBankByBankId(123);
        Assert.assertNotNull(receiveBankData);
        Assert.assertEquals(123, receiveBankData.getBankId());
    }
}