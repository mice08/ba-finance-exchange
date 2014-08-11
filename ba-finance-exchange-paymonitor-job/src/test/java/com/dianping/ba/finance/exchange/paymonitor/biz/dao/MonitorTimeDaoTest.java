package com.dianping.ba.finance.exchange.paymonitor.biz.dao;

import com.dianping.ba.finance.exchange.paymonitor.api.datas.MonitorTimeData;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/config/spring/common/appcontext-*.xml",
        "classpath*:/config/spring/local/appcontext-*.xml"})
public class MonitorTimeDaoTest {

    @Autowired
    private MonitorTimeDao monitorTimeDao;

    @Test
    public void testGetLastMonitorTime() throws Exception {
        Date date = monitorTimeDao.getLastMonitorTime();
        Assert.assertNotNull(date);
    }

    @Test
    public void testAddMonitorTime() throws Exception {
        int result = monitorTimeDao.addMonitorTime(new Date());
        Assert.assertTrue(result>0);
    }
}