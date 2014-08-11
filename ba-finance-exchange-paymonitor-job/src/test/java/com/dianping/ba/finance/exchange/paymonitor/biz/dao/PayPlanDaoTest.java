package com.dianping.ba.finance.exchange.paymonitor.biz.dao;

import com.dianping.ba.finance.exchange.paymonitor.api.datas.PayPlanMonitorData;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/config/spring/common/appcontext-*.xml",
        "classpath*:/config/spring/local/appcontext-*.xml"})
public class PayPlanDaoTest {

    @Autowired
    private PayPlanDao payPlanDao;

    @Test
    public void testFindPayPlansByDate() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2014,6,4);
        Date startDate = calendar.getTime();
        calendar.set(2014,8,5);
        Date endDate = calendar.getTime();
        List<PayPlanMonitorData> payPlanMonitorDataList = payPlanDao.findPayPlansByDate(startDate,endDate);
        Assert.assertTrue(payPlanMonitorDataList.size()>0);

    }

    @Test
    public void testGetPayPlanById() throws Exception {
        int ppId = 202195;
        PayPlanMonitorData payPlanMonitorData = payPlanDao.getPayPlanById(ppId);
        Assert.assertNotNull(payPlanMonitorData);
    }

    @Test
    public void testGetPaySequenceById() throws Exception {
        int ppId = 201952;
        String sequence = payPlanDao.getPaySequenceById(ppId);
        Assert.assertNotNull(sequence);
    }
}