package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.ba.finance.exchange.api.datas.ShopFundAccountData;
import com.dianping.ba.finance.exchange.api.datas.ShopFundAccountFlowData;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: huajiao.zou
 * Date: 14-2-12
 * Time: 下午3:42
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/config/spring/common/appcontext-*.xml","classpath*:/config/spring/local/appcontext-*.xml" })

public class ShopFundAccountDaoTest {
      @Autowired
    private ShopFundAccountDao shopFundAccountDao;

    @Autowired
    private ShopFundAccountFlowDao shopFundAccountFlowDao;

    @Test
    public void testInsertShopFundAccount(){
        ShopFundAccountData shopFundAccountData = new ShopFundAccountData();
        shopFundAccountData.setAddDate(Calendar.getInstance().getTime());
        shopFundAccountData.setAddLoginId(-1);
        shopFundAccountData.setCompanyGlobalId("111");
        shopFundAccountData.setCredit(BigDecimal.ONE);
        shopFundAccountData.setDebit(BigDecimal.TEN);
        shopFundAccountData.setLastUpdateDate(Calendar.getInstance().getTime());
        shopFundAccountData.setBalanceTotal(BigDecimal.ONE);
        shopFundAccountData.setBalanceFrozen(BigDecimal.TEN);
        shopFundAccountDao.insertShopFundAccount(shopFundAccountData);

    }

    @Test
    public void testInsertShopFundAccountFlow(){
        ShopFundAccountFlowData shopFundAccountFlowData = new ShopFundAccountFlowData();
        shopFundAccountFlowData.setAddDate(Calendar.getInstance().getTime());
        shopFundAccountFlowData.setAddLoginId(-1);
        shopFundAccountFlowData.setExchangeOrderId(1);
        shopFundAccountFlowData.setFlowAmount(BigDecimal.ONE);
        shopFundAccountFlowData.setFlowType(1);
        shopFundAccountFlowData.setLastUpdateDate(Calendar.getInstance().getTime());
        shopFundAccountFlowDao.insertShopFundAccountFlow(shopFundAccountFlowData);

    }

    @Test
    public void testLoadShopFundAccountFlowById(){
        ShopFundAccountFlowData actual = shopFundAccountFlowDao.loadShopFundAccountFlowById(11194);

        Assert.assertNotNull(actual);
        Assert.assertEquals(2289,actual.getFundAccountId());
        Assert.assertEquals("PP|1",actual.getSequence());
    }
}
