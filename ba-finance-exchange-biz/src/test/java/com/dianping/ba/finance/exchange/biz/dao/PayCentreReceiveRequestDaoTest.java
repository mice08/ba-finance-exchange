package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.ba.finance.exchange.api.datas.PayCentreReceiveRequestData;
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
public class PayCentreReceiveRequestDaoTest {

    @Autowired
    private PayCentreReceiveRequestDao payCentreReceiveRequestDao;

    @Test
    public void testInsertPayCentreReceiveRequest() throws Exception {
        PayCentreReceiveRequestData payCentreReceiveRequestData=new PayCentreReceiveRequestData();
        payCentreReceiveRequestData.setReceiveDate(new Date());
        payCentreReceiveRequestData.setTradeType(1);
        payCentreReceiveRequestData.setPayChannel(10);
        payCentreReceiveRequestData.setPayMethod(5);
        payCentreReceiveRequestData.setBankId(1);
        payCentreReceiveRequestData.setBizContent("AD");
        payCentreReceiveRequestData.setBusinessType(6);
        payCentreReceiveRequestData.setReceiveAmount(BigDecimal.TEN);
        payCentreReceiveRequestData.setTradeNo("P123");
        payCentreReceiveRequestData.setAddTime(new Date());
        int requestId = payCentreReceiveRequestDao.insertPayCentreReceiveRequest(payCentreReceiveRequestData);
        Assert.assertTrue(requestId > 1);
    }
}