package com.dianping.ba.finance.exchange.bankpaymonitor.biz.impl;

import com.dianping.ba.finance.exchange.bankpaymonitor.api.datas.BankPayMonitorExceptionData;
import com.dianping.ba.finance.exchange.bankpaymonitor.api.enums.BankPayException;
import com.dianping.ba.finance.exchange.bankpaymonitor.biz.daos.BankPayMonitorExceptionDao;
import com.dianping.ba.finance.exchange.bankpaymonitor.biz.daos.MonitorTimeDao;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MonitorServiceObjectTest {

    private MonitorServiceObject monitorServiceObjectStub;
    private MonitorTimeDao monitorTimeDaoMock;
    private BankPayMonitorExceptionDao bankPayMonitorExceptionDaoMock;

    @Before
    public void setUp() throws Exception {
        monitorServiceObjectStub = new MonitorServiceObject();
        monitorTimeDaoMock = mock(MonitorTimeDao.class);
        bankPayMonitorExceptionDaoMock = mock(BankPayMonitorExceptionDao.class);
        monitorServiceObjectStub.setBankPayMonitorExceptionDao(bankPayMonitorExceptionDaoMock);
        monitorServiceObjectStub.setMonitorTimeDao(monitorTimeDaoMock);
    }

    @Test
    public void testLoadLastMonitorTime() throws Exception {
        when(monitorTimeDaoMock.loadLastMonitorTime()).thenReturn(new Date());
        Assert.assertNotNull(monitorServiceObjectStub.loadLastMonitorTime());
    }

    @Test
    public void testInsertMonitorTime() throws Exception {
        when(monitorTimeDaoMock.insertMonitorTime(any(Date.class))).thenReturn(1);
        Assert.assertTrue(monitorServiceObjectStub.insertMonitorTime(new Date()));
    }

    @Test
    public void testInsertMonitorResult() throws Exception {
        when(bankPayMonitorExceptionDaoMock.insertBankPayResult(any(BankPayMonitorExceptionData.class))).thenReturn(1);
        Assert.assertTrue(monitorServiceObjectStub.insertMonitorResult(1, BankPayException.DIFF_STATUS));
    }
}