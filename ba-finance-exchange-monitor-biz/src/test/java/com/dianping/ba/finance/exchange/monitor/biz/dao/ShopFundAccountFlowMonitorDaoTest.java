package com.dianping.ba.finance.exchange.monitor.biz.dao;

import com.dianping.ba.finance.exchange.monitor.api.datas.ShopFundAccountFlowMonitorData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jingzhou.yan
 * Date: 14-3-27
 * Time: 下午6:41
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/config/spring/common/appcontext-*.xml",
        "classpath*:/config/spring/local/appcontext-*.xml",
        "classpath*:/config/spring/test/appcontext-*.xml"})
public class ShopFundAccountFlowMonitorDaoTest {

    @Autowired
    private ShopFundAccountFlowMonitorDao shopFundAccountFlowMonitorDao;

    @Test
    public void findShopFundAccountFlowDatasTest()
    {
        List<ShopFundAccountFlowMonitorData> actual= shopFundAccountFlowMonitorDao.findShopFundAccountFlowDatas(910701);
        System.out.println(actual.size());
    }

}
