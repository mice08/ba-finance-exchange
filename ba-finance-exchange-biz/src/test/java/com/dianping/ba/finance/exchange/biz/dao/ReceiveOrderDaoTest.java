package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.ba.finance.exchange.api.beans.ReceiveOrderSearchBean;
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;
import com.dianping.core.type.PageModel;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Calendar;
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

    @Test
    public void testPaginateReceiveOrderList() throws Exception {
        ReceiveOrderSearchBean searchBean = new ReceiveOrderSearchBean();
        searchBean.setCustomerId(8787);
        searchBean.setBusinessType(4);

        Calendar receiveTimeBeginCal = Calendar.getInstance();
        receiveTimeBeginCal.set(Calendar.MONTH, 05);
        receiveTimeBeginCal.set(Calendar.DAY_OF_MONTH, 16);
        searchBean.setReceiveTimeBegin(receiveTimeBeginCal.getTime());

        Calendar receiveTimeEndCal = Calendar.getInstance();
        receiveTimeEndCal.set(Calendar.MONTH, 05);
        receiveTimeEndCal.set(Calendar.DAY_OF_MONTH, 18);
        searchBean.setReceiveTimeEnd(receiveTimeEndCal.getTime());

        searchBean.setPayChannel(1);

        Calendar bankReceiveTimeBeginCal = Calendar.getInstance();
        bankReceiveTimeBeginCal.set(Calendar.MONTH, 05);
        bankReceiveTimeBeginCal.set(Calendar.DAY_OF_MONTH, 16);
        searchBean.setBankReceiveTimeBegin(bankReceiveTimeBeginCal.getTime());

        Calendar bankReceiveTimeEndCal = Calendar.getInstance();
        bankReceiveTimeEndCal.set(Calendar.MONTH, 05);
        bankReceiveTimeEndCal.set(Calendar.DAY_OF_MONTH, 18);
        searchBean.setBankReceiveTimeEnd(bankReceiveTimeEndCal.getTime());

        PageModel pm = receiveOrderDao.paginateReceiveOrderList(searchBean, 1, 20);
        Assert.assertNotNull(pm);
    }

    @Test
    public void testPaginateReceiveOrderListBank() throws Exception {
        ReceiveOrderSearchBean searchBean = new ReceiveOrderSearchBean();
        searchBean.setBankId(8787);

        PageModel pm = receiveOrderDao.paginateReceiveOrderList(searchBean, 1, 20);
        Assert.assertNotNull(pm);
    }

    @Test
    public void testLoadReceiveOrderTotalAmountByCondition() throws Exception {
        ReceiveOrderSearchBean searchBean = new ReceiveOrderSearchBean();
        searchBean.setCustomerId(8787);
        searchBean.setBusinessType(5);

        Calendar receiveTimeBeginCal = Calendar.getInstance();
        receiveTimeBeginCal.set(Calendar.MONTH, 05);
        receiveTimeBeginCal.set(Calendar.DAY_OF_MONTH, 16);
        searchBean.setReceiveTimeBegin(receiveTimeBeginCal.getTime());

        Calendar receiveTimeEndCal = Calendar.getInstance();
        receiveTimeEndCal.set(Calendar.MONTH, 05);
        receiveTimeEndCal.set(Calendar.DAY_OF_MONTH, 18);
        searchBean.setReceiveTimeEnd(receiveTimeEndCal.getTime());

        searchBean.setPayChannel(2);

        Calendar bankReceiveTimeBeginCal = Calendar.getInstance();
        bankReceiveTimeBeginCal.set(Calendar.MONTH, 05);
        bankReceiveTimeBeginCal.set(Calendar.DAY_OF_MONTH, 16);
        searchBean.setBankReceiveTimeBegin(bankReceiveTimeBeginCal.getTime());

        Calendar bankReceiveTimeEndCal = Calendar.getInstance();
        bankReceiveTimeEndCal.set(Calendar.MONTH, 05);
        bankReceiveTimeEndCal.set(Calendar.DAY_OF_MONTH, 18);
        searchBean.setBankReceiveTimeEnd(bankReceiveTimeEndCal.getTime());

        BigDecimal totalAmount = receiveOrderDao.loadReceiveOrderTotalAmountByCondition(searchBean);
        Assert.assertNotNull(totalAmount);

    }

    @Test
    public void testLoadReceiveOrderTotalAmountByConditionBank() throws Exception {
        ReceiveOrderSearchBean searchBean = new ReceiveOrderSearchBean();
        searchBean.setBankId(8787);

        BigDecimal totalAmount = receiveOrderDao.loadReceiveOrderTotalAmountByCondition(searchBean);
        Assert.assertNotNull(totalAmount);

    }
}