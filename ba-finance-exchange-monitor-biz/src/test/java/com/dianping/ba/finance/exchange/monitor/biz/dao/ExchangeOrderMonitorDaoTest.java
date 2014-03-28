package com.dianping.ba.finance.exchange.monitor.biz.dao;

import com.dianping.ba.finance.exchange.monitor.api.datas.ExchangeOrderMonitorData;
import com.dianping.ba.finance.exchange.monitor.api.enums.ExchangeOrderStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jingzhou.yan
 * Date: 14-3-27
 * Time: 下午6:29
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/config/spring/common/appcontext-*.xml","classpath*:/config/spring/local/appcontext-*.xml","classpath*:/config/spring/test/appcontext-*.xml"})
public class ExchangeOrderMonitorDaoTest {

    @Autowired
    private ExchangeOrderMonitorDao exchangeOrderMonitorDao;

    @Test
    public void testFindExchangeOrderData() {
        Calendar c1 = Calendar.getInstance();
        c1.set(2014, 0, 1);
        Calendar c2 = Calendar.getInstance();
        c2.set(2014, 3, 26);
        List<ExchangeOrderMonitorData>  actual = exchangeOrderMonitorDao.findExchangeOrderData(c1.getTime(), c2.getTime());
        System.out.println(actual.size());
    }

    @Test
    public void testLoadExchangeOrderData(){

        ExchangeOrderMonitorData actual = exchangeOrderMonitorDao.loadExchangeOrderData(910700);
        System.out.println(actual.getEoId());
    }
}
