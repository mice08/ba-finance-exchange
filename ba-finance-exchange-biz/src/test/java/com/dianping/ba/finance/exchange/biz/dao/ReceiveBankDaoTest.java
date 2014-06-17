package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.ba.finance.exchange.api.datas.ReceiveBankData;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/config/spring/common/appcontext-*.xml",
        "classpath*:/config/spring/local/appcontext-*.xml"
})
public class ReceiveBankDaoTest {

    @Autowired
    private ReceiveBankDao receiveBankDao;

    @Test
    public void testFindAllReceiveBank() throws Exception {
        List<ReceiveBankData> receiveBankDataList = receiveBankDao.findAllReceiveBank();
        Assert.assertNotNull(receiveBankDataList);
    }
}