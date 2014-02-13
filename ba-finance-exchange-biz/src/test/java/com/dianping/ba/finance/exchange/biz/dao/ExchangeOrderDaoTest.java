package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.ba.finance.exchange.api.datas.ExchangeOrderData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
        exchangeOrderDao.updateExchangeOrderToPending(integerList,status,setStatus,loginId);
    }
}
