package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyRecordData;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2014/7/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/config/spring/common/appcontext-*.xml",
        "classpath*:/config/spring/local/appcontext-*.xml"})
public class ReceiveNotifyRecordDaoTest {

    @Autowired
    private ReceiveNotifyRecordDao receiveNotifyRecordDao;

    @Test
    public void testInsertReceiveNotifyRecord(){
        ReceiveNotifyRecordData receiveNotifyRecordData = new ReceiveNotifyRecordData();
        receiveNotifyRecordData.setApplicationId("123456");
        receiveNotifyRecordData.setAttachment("");
        receiveNotifyRecordData.setBizContent("123456789");
        receiveNotifyRecordData.setBusinessType(5);
        receiveNotifyRecordData.setCustomerId(1);
        receiveNotifyRecordData.setMemo("");
        receiveNotifyRecordData.setPayChannel(1);
        receiveNotifyRecordData.setPayerName("someone");
        receiveNotifyRecordData.setPayTime(new Date());
        receiveNotifyRecordData.setRequestTime(new Date());
        receiveNotifyRecordData.setReceiveAmount(new BigDecimal(1000.55));
        receiveNotifyRecordData.setToken("55656565656565656565656556");
        receiveNotifyRecordData.setBankId(1236156);
        int recordId = receiveNotifyRecordDao.insertReceiveNotifyRecord(receiveNotifyRecordData);

        Assert.assertTrue(recordId > 0);
    }
}
