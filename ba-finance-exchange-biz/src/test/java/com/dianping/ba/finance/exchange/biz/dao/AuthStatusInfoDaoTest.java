package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.ba.finance.exchange.api.datas.AuthStatusInfoData;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/config/spring/common/appcontext-*.xml",
        "classpath*:/config/spring/local/appcontext-*.xml"})
public class AuthStatusInfoDaoTest {

    @Autowired
    private AuthStatusInfoDao authStatusInfoDao;

    @Test
    public void testInsertAuthStatusInfo() throws Exception {
        int result = authStatusInfoDao.insertAuthStatusInfo("0008655", 1, 1);
        Assert.assertTrue(result > 0);
    }

    @Test
    public void testLoadStatusByWorkNoAndType() throws Exception {
        AuthStatusInfoData data = authStatusInfoDao.loadStatusByWorkNoAndType("0008655", 1);
        Assert.assertNotNull(data);
    }

    @Test
    public void testUpdateAuthTimes() throws Exception {
        int result = authStatusInfoDao.updateAuthTimes(1, 2);
        Assert.assertTrue(result > 0);
    }

    @Test
    public void testUpdateStatusInvalid() throws Exception {
        int result = authStatusInfoDao.updateStatusInvalid("0008655", 1);
        Assert.assertTrue(result > 0);
    }
}