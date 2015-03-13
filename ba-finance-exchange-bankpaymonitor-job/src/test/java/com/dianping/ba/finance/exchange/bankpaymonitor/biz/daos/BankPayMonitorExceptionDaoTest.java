package com.dianping.ba.finance.exchange.bankpaymonitor.biz.daos;

import com.dianping.ba.finance.exchange.bankpaymonitor.api.datas.BankPayMonitorExceptionData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/config/spring/common/appcontext-*.xml",
        "classpath*:/config/spring/local/appcontext-*.xml"})
public class BankPayMonitorExceptionDaoTest {

    @Autowired
    private BankPayMonitorExceptionDao bankPayMonitorExceptionDao;

    @Test
    public void testInsertBankPayResult() throws Exception {
        BankPayMonitorExceptionData data = new BankPayMonitorExceptionData();
        data.setPoId(1);
        data.setMemo("a");
        data.setCheckStatus(1);
        bankPayMonitorExceptionDao.insertBankPayResult(data);
    }
}