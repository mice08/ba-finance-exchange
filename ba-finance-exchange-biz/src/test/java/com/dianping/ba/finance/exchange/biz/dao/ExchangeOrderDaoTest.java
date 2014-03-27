package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.ba.finance.exchange.api.beans.ExchangeOrderSearchBean;
import com.dianping.ba.finance.exchange.api.datas.EOMonitorData;
import com.dianping.ba.finance.exchange.api.datas.ExchangeOrderData;
import com.dianping.ba.finance.exchange.api.datas.ExchangeOrderDisplayData;
import com.dianping.ba.finance.exchange.api.dtos.RefundDTO;
import com.dianping.ba.finance.exchange.api.enums.ExchangeOrderStatus;
import com.dianping.ba.finance.exchange.biz.utils.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: huajiao.zou
 * Date: 14-2-12
 * Time: 下午4:01
 * To change this template use File | Settings | File Templates.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/config/spring/common/appcontext-*.xml", "classpath*:/config/spring/local/appcontext-*.xml"})

public class ExchangeOrderDaoTest {
    @Autowired
    private ExchangeOrderDao exchangeOrderDao;

    @Test
    public void testInsertExchangeOrder() {
        ExchangeOrderData exchangeOrderData = new ExchangeOrderData();
        exchangeOrderData.setAddDate(Calendar.getInstance().getTime());
        exchangeOrderData.setAddLoginId(-1);
        exchangeOrderData.setLastUpdateDate(Calendar.getInstance().getTime());
        exchangeOrderData.setBankAccountName("1111");
        exchangeOrderData.setBizCode("1");
        exchangeOrderData.setOrderAmount(BigDecimal.ONE);

        exchangeOrderDao.insertExchangeOrder(exchangeOrderData);
    }

    @Test
    public void testUpdateExchangeOrderToPending() {
        int status = 1;
        int setStatus = 2;
        int loginId = 2;
        List<Integer> integerList = new ArrayList<Integer>();
        integerList.add(878799);
        exchangeOrderDao.updateExchangeOrderToPending(integerList, status, setStatus, loginId);
    }

    @Test
    public void testUpdateExchangeOrderData() {
        int status = 2;
        int setStatus = 4;
        int loginId = 2;
        List<Integer> integerList = new ArrayList<Integer>();
        integerList.add(878799);
        exchangeOrderDao.updateExchangeOrderToPending(integerList, status, setStatus, loginId);
    }

    @Test
    public void testUpdateExchangeOrderToRefund() {
        RefundDTO refundDTO1 = new RefundDTO();
        refundDTO1.setRefundId("111");
        refundDTO1.setRefundReason("test");
        int preStatus = ExchangeOrderStatus.SUCCESS.value();
        int setStatus = ExchangeOrderStatus.FAIL.value();
        Date todayDate = new Date();
        int loginId = -12000;
        exchangeOrderDao.updateExchangeOrderToRefund(refundDTO1, preStatus, setStatus,todayDate, loginId);
    }

    @Test
    public void testFindExchangeOrderByBizCode() {
        List<String> stringList = new ArrayList<String>();
        String str = "111";
        String str1 = "222";
        stringList.add(str);
        stringList.add(str1);
        List<ExchangeOrderData> exchangeOrderDataList = new ArrayList<ExchangeOrderData>();
        exchangeOrderDataList = exchangeOrderDao.findExchangeOrderByBizCode(stringList);
        Assert.assertEquals(1,exchangeOrderDataList.size());
    }

    @Test
    public void testFindExchangeOrderDataList() {
        ExchangeOrderSearchBean searchBean = new ExchangeOrderSearchBean();
        searchBean.setBizCode("P11194");
        List<ExchangeOrderDisplayData> exchangeOrderDataList= exchangeOrderDao.findExchangeOrderList(searchBean);
        Assert.assertEquals(1,exchangeOrderDataList.size());
    }

    @Test
    public void testUpdateExchangeOrderDataByOrderIdList() throws Exception{
        List<Integer> orderIdList = new ArrayList<Integer>();
        orderIdList.add(878701);
        orderIdList.add(878702);
        int actual = exchangeOrderDao.updateExchangeOrderDataByOrderIdList(orderIdList, DateUtils.removeTime(DateUtils.getTodayDate()),3,4,1);

        Assert.assertEquals(2, actual);
    }

    @Test
    public void testFindExchangeOrderListByIdList(){
        List<Integer> orderIdList = new ArrayList<Integer>();
        orderIdList.add(878701);
        orderIdList.add(878702);
        List<ExchangeOrderData> actual = exchangeOrderDao.findExchangeOrderListByOrderIdList(orderIdList);

        Assert.assertEquals(2, actual.size());
    }

    @Test
    public void testFindEOMonitorDataByFlowIdList() throws Exception {
        List<Integer> flowIds = Arrays.asList(11143679, 11143681);
        List<EOMonitorData> eoMonitorDatas = exchangeOrderDao.findEOMonitorDataByFlowIdList(flowIds);
        System.out.println(eoMonitorDatas);

    }
}
