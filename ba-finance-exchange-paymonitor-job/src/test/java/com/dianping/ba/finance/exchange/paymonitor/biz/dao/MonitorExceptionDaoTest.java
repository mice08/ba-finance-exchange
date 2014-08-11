package com.dianping.ba.finance.exchange.paymonitor.biz.dao;

import com.dianping.ba.finance.exchange.paymonitor.api.datas.MonitorExceptionData;
import junit.framework.Assert;
import net.sf.ehcache.search.aggregator.Min;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/config/spring/common/appcontext-*.xml",
        "classpath*:/config/spring/local/appcontext-*.xml"})
public class MonitorExceptionDaoTest {

    @Autowired
    private MonitorExceptionDao monitorExceptionDao;

    @Test
    public void testAddMonitorException() throws Exception {
        MonitorExceptionData monitorExceptionData = new MonitorExceptionData();
        monitorExceptionData.setPpId(1234);
        monitorExceptionData.setExceptionType(1);
        monitorExceptionData.setStatus(1);
        monitorExceptionData.setAddDate(new Date());
        int result = monitorExceptionDao.addMonitorException(monitorExceptionData);
        Assert.assertTrue(result>0);
    }

    @Test
    public void testFindMonitorExceptionDatas() throws Exception {
        int status = 1;
        List<MonitorExceptionData> monitorExceptionDataList = monitorExceptionDao.findMonitorExceptionDatas(status);
        Assert.assertTrue(monitorExceptionDataList.size()>0);
    }

    @Test
    public void testUpdateMonitorExceptionStatus() throws Exception {
        List<Integer> exceptionIdList = new ArrayList<Integer>();
        exceptionIdList.add(23);
        int status = 2;
        int resultCount = monitorExceptionDao.updateMonitorExceptionStatus(exceptionIdList,status);
        Assert.assertTrue(resultCount>0);
    }
}