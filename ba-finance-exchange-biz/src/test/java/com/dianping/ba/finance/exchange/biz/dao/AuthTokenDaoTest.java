package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.ba.finance.exchange.api.datas.AccessTokenInfoData;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/config/spring/common/appcontext-*.xml",
        "classpath*:/config/spring/local/appcontext-*.xml"})
public class AuthTokenDaoTest {

    @Autowired
    private AuthTokenDao authTokenDaoStub;

    @Test
    public void testFindAllAccessTokenInfo() throws Exception {
        List<AccessTokenInfoData> tokenInfoDataList = authTokenDaoStub.findAllAccessTokenInfo();
        Assert.assertFalse(tokenInfoDataList.isEmpty());
    }

    @Test
    public void testLoadAccessTokenInfo() throws Exception {
        AccessTokenInfoData tokenInfoData = authTokenDaoStub.loadAccessTokenInfo("FCBA1DF9D50FF8B4D6D435C3A5A081F24C9CB967");
        Assert.assertNotNull(tokenInfoData);
    }
}