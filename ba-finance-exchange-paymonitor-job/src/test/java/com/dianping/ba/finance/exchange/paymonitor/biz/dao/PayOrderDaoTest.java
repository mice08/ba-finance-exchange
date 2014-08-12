package com.dianping.ba.finance.exchange.paymonitor.biz.dao;

import com.dianping.ba.finance.exchange.paymonitor.api.datas.PayOrderMonitorData;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/config/spring/common/appcontext-*.xml",
        "classpath*:/config/spring/local/appcontext-*.xml"})
public class PayOrderDaoTest {

    @Autowired
    private PayOrderDao payOrderDao;

    @Test
    public void testGetPayOrderBySequence() throws Exception {
        String sequence = "TG_202194_1407149690338";
        PayOrderMonitorData payOrderMonitorData = payOrderDao.loadPayOrderBySequence(sequence);
        Assert.assertNotNull(payOrderMonitorData);
    }
}