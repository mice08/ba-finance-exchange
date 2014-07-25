package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.datas.AccessTokenInfoData;
import com.dianping.ba.finance.exchange.biz.dao.AuthTokenDao;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccessTokenServiceObjectTest {

    private AccessTokenServiceObject accessTokenServiceObjectStub;

    private AuthTokenDao authTokenDaoMock;

    @Before
    public void setUp() throws Exception {
        authTokenDaoMock = mock(AuthTokenDao.class);

        accessTokenServiceObjectStub = new AccessTokenServiceObject();
        accessTokenServiceObjectStub.setAuthTokenDao(authTokenDaoMock);
    }

    @Test
    public void testInit() throws Exception {
        AccessTokenInfoData tokenInfoData = new AccessTokenInfoData();
        tokenInfoData.setAccessToken("123BGDRTGR");
        tokenInfoData.setAddDate(new Date());
        tokenInfoData.setAuthBusinessId(1);
        tokenInfoData.setAuthCode(1);
        tokenInfoData.setBusinessName("团购");
        tokenInfoData.setBusinessType(1);
        tokenInfoData.setTokenId(1);
        when(authTokenDaoMock.findAllAccessTokenInfo()).thenReturn(Arrays.asList(tokenInfoData));


        accessTokenServiceObjectStub.setUseCache(true);
        accessTokenServiceObjectStub.setLoadAllWhenInitial(true);
        accessTokenServiceObjectStub.init();
    }

    @Test
    public void testVerifyAccessToken() throws Exception {
        AccessTokenInfoData tokenInfoData = new AccessTokenInfoData();
        tokenInfoData.setAccessToken("123BGDRTGR");
        tokenInfoData.setAddDate(new Date());
        tokenInfoData.setAuthBusinessId(1);
        tokenInfoData.setAuthCode(1);
        tokenInfoData.setBusinessName("团购");
        tokenInfoData.setBusinessType(1);
        tokenInfoData.setTokenId(1);
        when(authTokenDaoMock.loadAccessTokenInfo(anyString())).thenReturn(tokenInfoData);

        boolean r = accessTokenServiceObjectStub.verifyAccessToken("123BGDRTGR");
        Assert.assertTrue(r);
    }

    @Test
    public void testVerifyAccessTokenNotUseCache() throws Exception {
        AccessTokenInfoData tokenInfoData = new AccessTokenInfoData();
        tokenInfoData.setAccessToken("123BGDRTGR");
        tokenInfoData.setAddDate(new Date());
        tokenInfoData.setAuthBusinessId(1);
        tokenInfoData.setAuthCode(1);
        tokenInfoData.setBusinessName("团购");
        tokenInfoData.setBusinessType(1);
        tokenInfoData.setTokenId(1);
        when(authTokenDaoMock.loadAccessTokenInfo(anyString())).thenReturn(tokenInfoData);

        accessTokenServiceObjectStub.setUseCache(false);
        boolean r = accessTokenServiceObjectStub.verifyAccessToken("123BGDRTGR");
        Assert.assertTrue(r);
    }

    @Test
    public void testVerifyAccessTokenAndBusinessType() throws Exception {
        AccessTokenInfoData tokenInfoData = new AccessTokenInfoData();
        tokenInfoData.setAccessToken("123BGDRTGR");
        tokenInfoData.setAddDate(new Date());
        tokenInfoData.setAuthBusinessId(1);
        tokenInfoData.setAuthCode(1);
        tokenInfoData.setBusinessName("团购");
        tokenInfoData.setBusinessType(1);
        tokenInfoData.setTokenId(1);
        when(authTokenDaoMock.loadAccessTokenInfo(anyString())).thenReturn(tokenInfoData);

        boolean r = accessTokenServiceObjectStub.verifyAccessToken("123BGDRTGR", 1);
        Assert.assertTrue(r);
    }

    @Test
    public void testClearCache() throws Exception {
        accessTokenServiceObjectStub.setUseCache(true);
        accessTokenServiceObjectStub.clearCache();
    }
}