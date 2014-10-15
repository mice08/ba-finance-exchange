package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.ba.finance.exchange.api.datas.ReceiveVoucherData;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/config/spring/common/appcontext-*.xml",
        "classpath*:/config/spring/local/appcontext-*.xml"
})
public class ReceiveVoucherDaoTest {
    @Autowired
    private ReceiveVoucherDao receiveVoucherDao;

    @Test
    public void testInsertReceiveVoucherData() throws Exception {
        ReceiveVoucherData rvData = new ReceiveVoucherData();
        rvData.setAddLoginId(-1);
        rvData.setAddTime(new Date());
        rvData.setAmount(BigDecimal.TEN);
        rvData.setBankId(8);
        rvData.setCityId(7);
        rvData.setCompanyId(3);
        rvData.setCustomerId(123);
        rvData.setShopId(1);
        rvData.setUpdateLoginId(-1);
        rvData.setUpdateTime(new Date());
        rvData.setVoucherDate(new Date());
        rvData.setVoucherType(13);
        int voucherId = receiveVoucherDao.insertReceiveVoucherData(rvData);
        Assert.assertTrue(voucherId > 0);
    }
}