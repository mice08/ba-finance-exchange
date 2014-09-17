package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.ba.finance.exchange.api.datas.PayRequestData;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/config/spring/common/appcontext-*.xml",
        "classpath*:/config/spring/local/appcontext-*.xml"})
public class PayRequestDaoTest {

    @Autowired
    private PayRequestDao payRequestDao;

    @Test
    public void testInsertPayRequest() throws Exception {
        PayRequestData payRequestData = new PayRequestData();
        payRequestData.setAddLoginId(-123);
        payRequestData.setAddTime(new Date());
        payRequestData.setBankAccountName("bankAccountName");
        payRequestData.setBankAccountNo("accountNo");
        payRequestData.setBankAccountType(1);
        payRequestData.setBankBranchName("branchName");
        payRequestData.setBankCity("city");
        payRequestData.setBankCode("bankCode");
        payRequestData.setBankFullBranchName("fullName");
        payRequestData.setBankName("bankName");
        payRequestData.setBankProvince("province");
        payRequestData.setBusinessType(BusinessType.EXPENSE.value());
        payRequestData.setMemo("memo");
        payRequestData.setPayAmount(BigDecimal.TEN);
        payRequestData.setPayBankAccountNo("payBankNo");
        payRequestData.setPayBankName("payBankName");
        payRequestData.setPayeeName("payeeName_123");
        payRequestData.setPaySequence("paySequence");
        payRequestData.setRequestMemo("requestMemo");
        payRequestData.setRequestTime(new Date());
        payRequestData.setStatus(1);
        payRequestData.setUpdateLoginId(-321);
        payRequestData.setUpdateTime(new Date());
        int prId = payRequestDao.insertPayRequest(payRequestData);
        Assert.assertTrue(prId > 0);
    }

    @Test
    public void testUpdatePayRequest() throws Exception {
        int u = payRequestDao.updatePayRequest(1, 2, "memo123");
        Assert.assertTrue(u > 0);

    }
}