package com.dianping.ba.finance.exchange.monitor.biz.dao;

import com.dianping.ba.finance.exchange.monitor.api.datas.MonitorTimeData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: noahshen
 * Date: 14-3-26
 * Time: 上午11:39
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/config/spring/common/appcontext-*.xml",
        "classpath*:/config/spring/local/appcontext-*.xml",
        "classpath*:/config/spring/test/appcontext-*.xml"})
public class FSMonitorTimeDaoTest {

    @Autowired
    private FSMonitorTimeDao fsMonitorTimeDao;
    @Test
    public void testLoadLastMonitorTime() throws Exception {
        MonitorTimeData d = fsMonitorTimeDao.loadLastMonitorTime();
        System.out.println(d);
    }

    @Test
    public void testInsertMonitorTime() throws Exception {
        MonitorTimeData mtd = new MonitorTimeData();
        mtd.setMonitorTime(new Date());

        fsMonitorTimeDao.insertMonitorTime(mtd);
        System.out.println(mtd);

    }
}
