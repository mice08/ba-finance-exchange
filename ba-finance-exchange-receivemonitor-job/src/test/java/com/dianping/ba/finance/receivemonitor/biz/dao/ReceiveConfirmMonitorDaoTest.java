package com.dianping.ba.finance.receivemonitor.biz.dao;

import com.dianping.ba.finance.exchange.receivemonitor.api.datas.ReceiveConfirmMonitorData;
import com.dianping.ba.finance.exchange.receivemonitor.api.datas.ReceiveOrderMonitorData;
import com.dianping.ba.finance.exchange.receivemonitor.biz.dao.ReceiveConfirmMonitorDao;
import com.dianping.ba.finance.exchange.receivemonitor.biz.dao.ReceiveOrderMonitorDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
@ContextConfiguration(locations = {
		"classpath*:/config/spring/common/appcontext-*.xml",
		"classpath*:/config/spring/local/appcontext-*.xml",
		"classpath*:/config/spring/test/appcontext-*.xml"})
public class ReceiveConfirmMonitorDaoTest {

    @Autowired
    private ReceiveConfirmMonitorDao receiveConfirmMonitorDao;



    @Test
    public void testLoadReceiveOrderData(){
		ReceiveConfirmMonitorData actual = receiveConfirmMonitorDao.loadReceiveConfirmData(18);
        System.out.println(actual.getRcId());
	}
}
