package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.datas.AuthStatusInfoData;
import com.dianping.ba.finance.exchange.biz.dao.AuthStatusInfoDao;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthStatusInfoServiceObjectTest {

    private AuthStatusInfoServiceObject authStatusInfoServiceObjectStub;
    private AuthStatusInfoDao authStatusInfoDaoMock;

    @Before
    public void setup() {
        authStatusInfoServiceObjectStub = new AuthStatusInfoServiceObject();
        authStatusInfoDaoMock = mock(AuthStatusInfoDao.class);
        authStatusInfoServiceObjectStub.setAuthStatusInfoDao(authStatusInfoDaoMock);
    }

    @Test
    public void testIsRSALock() throws Exception {
        when(authStatusInfoDaoMock.loadStatusByWorkNoAndType(anyString(), anyInt())).thenReturn(new AuthStatusInfoData());
        Assert.assertFalse(authStatusInfoServiceObjectStub.isRSALock("0008655"));
    }

    @Test
    public void testUpdateRetryTimes() throws Exception {
        when(authStatusInfoDaoMock.loadStatusByWorkNoAndType(anyString(), anyInt())).thenReturn(null);
        when(authStatusInfoDaoMock.insertAuthStatusInfo(anyString(), anyInt(), anyInt())).thenReturn(1);
        when(authStatusInfoDaoMock.updateAuthTimes(anyInt())).thenReturn(1);

        Assert.assertTrue(authStatusInfoServiceObjectStub.updateRetryTimes("0008655", 1));

        when(authStatusInfoDaoMock.loadStatusByWorkNoAndType(anyString(), anyInt())).thenReturn(new AuthStatusInfoData());
        Assert.assertTrue(authStatusInfoServiceObjectStub.updateRetryTimes("0008655", 1));
    }

    @Test
    public void testRemoveAuthStatus() {
        when(authStatusInfoDaoMock.updateStatusInvalid(anyString(), anyInt())).thenReturn(1);
        Assert.assertTrue(authStatusInfoServiceObjectStub.removeAuthStatus("0008655", 1));
    }
}