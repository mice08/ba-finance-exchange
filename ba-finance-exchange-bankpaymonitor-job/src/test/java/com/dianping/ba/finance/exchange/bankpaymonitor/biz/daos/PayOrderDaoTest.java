package com.dianping.ba.finance.exchange.bankpaymonitor.biz.daos;

import com.dianping.ba.finance.exchange.bankpaymonitor.api.datas.PayOrderMonitorData;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/config/spring/common/appcontext-*.xml",
        "classpath*:/config/spring/local/appcontext-*.xml"})
public class PayOrderDaoTest {

    @Autowired
    private PayOrderDao payOrderDao;

    @Test
    public void testFindPayOrders() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2014);
        List<PayOrderMonitorData> result = payOrderDao.findPayOrders(0, 100, cal.getTime(), new Date(), Arrays.asList(1,2,3,4,5));
        Assert.assertNotNull(result);
    }
}