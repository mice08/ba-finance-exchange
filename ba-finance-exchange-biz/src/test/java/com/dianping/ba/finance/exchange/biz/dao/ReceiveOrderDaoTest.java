package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;
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
public class ReceiveOrderDaoTest {

    @Autowired
    private ReceiveOrderDao receiveOrderDao;


    @Test
    public void testInsertReceiveOrderData() throws Exception {
        ReceiveOrderData receiveOrderData = new ReceiveOrderData();
        receiveOrderData.setAddLoginId(123);
        receiveOrderData.setAddTime(new Date());
        receiveOrderData.setBankID(8787);
        receiveOrderData.setBankReceiveTime(new Date());
        receiveOrderData.setBizContent("bizContent");
        receiveOrderData.setBusinessType(1);
        receiveOrderData.setCustomerId(678);
        receiveOrderData.setMemo("memo");
        receiveOrderData.setPayChannel(1);
        receiveOrderData.setPayTime(new Date());
        receiveOrderData.setReceiveAmount(new BigDecimal(87));
        receiveOrderData.setReceiveTime(new Date());
        receiveOrderData.setReceiveType(1);
        receiveOrderData.setShopId(567);
        receiveOrderData.setTradeNo("tradeNo");
        receiveOrderData.setUpdateLoginId(7);
        receiveOrderData.setUpdateTime(new Date());
        receiveOrderData.setStatus(7);
        int roId = receiveOrderDao.insertReceiveOrderData(receiveOrderData);
        Assert.assertTrue(roId > 1);
    }
}