package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.ba.finance.exchange.api.beans.ReceiveNotifySearchBean;
import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.ba.finance.exchange.api.enums.ReceiveNotifyStatus;
import com.dianping.ba.finance.exchange.api.enums.ReceiveType;
import com.dianping.core.type.PageModel;
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
        receiveNotifyData.setApplicationId("124568787");
        receiveNotifyData.setAttachment("");
        receiveNotifyData.setBizContent("123456789");
        receiveNotifyData.setBusinessType(5);
        receiveNotifyData.setCustomerId(1);
        receiveNotifyData.setMemo("");
        receiveNotifyData.setPayChannel(1);
        receiveNotifyData.setReceiveType(ReceiveType.AD_FEE.value());
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
        List<ReceiveNotifyData> receiveNotifyDataList = receiveNotifyDao.findUnmatchedLeftReceiveNotify(ReceiveNotifyStatus.MATCHED.value(), 467, "87871");
        Assert.assertNotNull(receiveNotifyDataList);
    }

    @Test
    public void testClearReceiveNotifyMatchInfo() throws Exception {
        int u = receiveNotifyDao.clearReceiveNotifyMatchInfo(ReceiveNotifyStatus.INIT.value(), Arrays.asList(11));
        Assert.assertTrue(u > 0);
    }

    @Test
    public void testLoadUnmatchedReceiveNotifyByApplicationId() throws Exception {
        ReceiveNotifyData receiveNotifyData = receiveNotifyDao.loadUnmatchedReceiveNotifyByApplicationId(ReceiveNotifyStatus.INIT.value(), BusinessType.ADVERTISEMENT.value(), "87871");
        Assert.assertNotNull(receiveNotifyData);
    }

    @Test
    public void testFindMatchedReceiveNotify() throws Exception {
        List<ReceiveNotifyData> receiveNotifyDataList = receiveNotifyDao.findMatchedReceiveNotify(ReceiveNotifyStatus.INIT.value(), 467);
        Assert.assertNotNull(receiveNotifyDataList);
    }

    @Test
    public void testRemoveReceiveNotifyMatchRelation() throws Exception {
        int u = receiveNotifyDao.removeReceiveNotifyMatchRelation(9, 269, ReceiveNotifyStatus.MATCHED.value());
        Assert.assertTrue(u > 0);
    }

    @Test
    public void testLoadMatchedReceiveNotify() throws Exception {
        ReceiveNotifyData rnData = receiveNotifyDao.loadMatchedReceiveNotify(ReceiveNotifyStatus.INIT.value(), 11, 467);
        Assert.assertNotNull(rnData);
    }

    @Test
    public void testUpdateReceiveNotifyConfirm() throws Exception {
        int u = receiveNotifyDao.updateReceiveNotifyConfirm(ReceiveNotifyStatus.CONFIRMED.value(), ReceiveNotifyStatus.INIT.value(), 467, 9);
        Assert.assertTrue(u > 0);
    }

    @Test
    public void testPaginateReceiveNotifyList() throws Exception {
        ReceiveNotifySearchBean receiveNotifySearchBean = new ReceiveNotifySearchBean();
        receiveNotifySearchBean.setBusinessType(5);
        receiveNotifySearchBean.setReceiveAmount(BigDecimal.valueOf(10.03));
        receiveNotifySearchBean.setBankId(11);
        PageModel pageModel = receiveNotifyDao.paginateReceiveNotifyList(receiveNotifySearchBean,1,20);
        Assert.assertTrue(pageModel.getRecordCount()>0);
    }

    @Test
    public void testLoadTotalReceiveAmountByCondition() throws Exception {
        ReceiveNotifySearchBean receiveNotifySearchBean = new ReceiveNotifySearchBean();
        receiveNotifySearchBean.setBusinessType(5);
        receiveNotifySearchBean.setReceiveAmount(BigDecimal.valueOf(10.03));
        BigDecimal totalAmount = receiveNotifyDao.loadTotalReceiveAmountByCondition(receiveNotifySearchBean);
        Assert.assertTrue(totalAmount.doubleValue() == 100.3);
    }
}
