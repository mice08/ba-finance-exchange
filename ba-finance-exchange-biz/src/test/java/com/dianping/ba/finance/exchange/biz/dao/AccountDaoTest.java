package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.ba.finance.exchange.api.beans.AccountUpdateInfoBean;
import com.dianping.ba.finance.exchange.api.datas.AccountData;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/config/spring/common/appcontext-*.xml",
        "classpath*:/config/spring/local/appcontext-*.xml"})
public class AccountDaoTest {

    @Autowired
    private AccountDao accountDao;

    @Test
    public void testUpdateAccount() throws Exception {
        AccountUpdateInfoBean bean = new AccountUpdateInfoBean();
        bean.setBalance(BigDecimal.ONE);
        bean.setCredit(BigDecimal.ONE);
        bean.setDebit(BigDecimal.ZERO);
        bean.setId(1);
        int result = accountDao.updateAccount(bean);
        Assert.assertTrue(result > 0);
    }

    @Test
    public void testLoadAccountByBankAccount() throws Exception {
        AccountData data = accountDao.loadAccountByBankAccount(1);
        Assert.assertNotNull(data);
    }
}