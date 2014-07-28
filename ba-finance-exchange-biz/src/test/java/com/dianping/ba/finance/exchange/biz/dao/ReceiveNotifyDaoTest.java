package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData;
import com.dianping.ba.finance.exchange.api.enums.ReceiveNotifyStatus;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2014/7/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/config/spring/common/appcontext-*.xml",
        "classpath*:/config/spring/local/appcontext-*.xml"})
public class ReceiveNotifyDaoTest {
    @Autowired
    private ReceiveNotifyDao receiveNotifyDao;

    @Test
    public void testInsertReceiveNotify(){
        ReceiveNotifyData receiveNotifyData = new ReceiveNotifyData();
        receiveNotifyData.setApplicationId("12456");
        receiveNotifyData.setAttachment("");
        receiveNotifyData.setBizContent("123456789");
        receiveNotifyData.setBusinessType(5);
        receiveNotifyData.setCustomerId(1);
        receiveNotifyData.setMemo("");
        receiveNotifyData.setPayChannel(1);
        receiveNotifyData.setPayerName("someone");
        receiveNotifyData.setPayTime(new Date());
        receiveNotifyData.setReceiveAmount(new BigDecimal(1000.55));
        receiveNotifyData.setStatus(1);
        receiveNotifyData.setRoMatcherId(1);
        receiveNotifyData.setAddTime(new Date());
        receiveNotifyData.setAddLoginId(-1);
        receiveNotifyData.setUpdateTime(new Date());
        receiveNotifyData.setUpdateLoginId(-1);
        receiveNotifyData.setBankId(123456789);
        int recordId = receiveNotifyDao.insertReceiveNotify(receiveNotifyData);

        Assert.assertTrue(recordId > 0);
    }

    @Test
    public void testUpdateReceiveNotifyMatchId() throws Exception {
        int u = receiveNotifyDao.updateReceiveNotifyMatchId(ReceiveNotifyStatus.MATCHED.value(), 8787123, ReceiveNotifyStatus.INIT.value(), 15);
        Assert.assertTrue(u > 0);
    }

    @Test
    public void testGetUnMatchedReceiveNotify() throws Exception {
        List<ReceiveNotifyData> receiveNotifyDataList = receiveNotifyDao.getUnMatchedReceiveNotify(ReceiveNotifyStatus.INIT.value());
        Assert.assertNotNull(receiveNotifyDataList);
    }

    @Test
    public void testFindUnmatchedLeftReceiveNotify() throws Exception {
        List<ReceiveNotifyData> receiveNotifyDataList = receiveNotifyDao.findUnmatchedLeftReceiveNotify(ReceiveNotifyStatus.MATCHED.value(), "87872");
        Assert.assertNotNull(receiveNotifyDataList);
    }

    @Test
    public void testClearReceiveNotifyMatchInfo() throws Exception {
        int u = receiveNotifyDao.clearReceiveNotifyMatchInfo(ReceiveNotifyStatus.INIT.value(), Arrays.asList(11));
        Assert.assertTrue(u > 0);
    }
}
