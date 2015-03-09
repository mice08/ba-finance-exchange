package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.ba.finance.exchange.api.beans.AccountUpdateInfoBean;
import com.dianping.ba.finance.exchange.api.datas.AccountEntryData;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/config/spring/common/appcontext-*.xml",
        "classpath*:/config/spring/local/appcontext-*.xml"})
public class AccountEntryDaoTest {

    @Autowired
    private AccountEntryDao accountEntryDao;

    @Test
    public void testInsertAccountEntry() throws Exception {
        AccountEntryData data = new AccountEntryData();
        data.setAmount(BigDecimal.ZERO);
        data.setSourceType(1);
        data.setBankAccountId(1);
        data.setEntryNo("a");
        data.setMemo("a");
        data.setInstId("1");
        data.setAccountBalance(BigDecimal.ONE);
        data.setDirection(1);
        data.setDescription("desc");
        data.setAddTime(new Date());
        data.setInstId("1");
        int result = accountEntryDao.insertAccountEntry(data);
        Assert.assertTrue(result > 0);
    }

    @Test
    public void testFindAccountEntryByTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2014);
        List<AccountEntryData> list = accountEntryDao.findAccountEntryByTime(0,1, cal.getTime(), new Date());
        Assert.assertNotNull(list);
    }
}