package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.ba.finance.exchange.api.beans.PayOrderSearchBean;
import com.dianping.core.type.PageModel;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
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
 * Time: 下午4:01
 * To change this template use File | Settings | File Templates.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/config/spring/common/appcontext-*.xml", "classpath*:/config/spring/local/appcontext-*.xml"})

public class PayOrderDaoTest {
    @Autowired
    private PayOrderDao payOrderDao;

    @Test
    public void testInsertPayOrder() {
        PayOrderData payOrderData = new PayOrderData();
        payOrderData.setAddTime(Calendar.getInstance().getTime());
        payOrderData.setAddLoginId(-1);
        payOrderData.setUpdateLoginId(-1);
        payOrderData.setUpdateTime(Calendar.getInstance().getTime());
        payOrderData.setBankAccountName("1111");
        payOrderData.setPaySequence("P|1");
        payOrderData.setPayCode("paycode");
        payOrderData.setCustomerId(12387);
        payOrderData.setPayAmount(BigDecimal.ONE);
        payOrderData.setCustomerBankId(8787);
        payOrderData.setBankAccountName("accountName");
        payOrderData.setBankAccountNo("No123");
        payOrderData.setBankBranchName("branchName");
        payOrderData.setBankCity("city");
        payOrderData.setBankCode("code");
        payOrderData.setBankFullBranchName("fullBranchName");
        payOrderData.setBankProvince("province");
        payOrderData.setBankName("bankName");

        payOrderDao.insertPayOrder(payOrderData);
    }

    @Test
    public void testPaginatePayOrderList(){
        PayOrderSearchBean payOrderSearchBean=new PayOrderSearchBean();
        payOrderSearchBean.setBusinessType(1);
        PageModel pageModel=payOrderDao.paginatePayOrderList(payOrderSearchBean,1,20);
        junit.framework.Assert.assertNotNull(pageModel);
    }

    @Test
    public void testFindPayOrderTotalAmountByCondition(){
        PayOrderSearchBean payOrderSearchBean=new PayOrderSearchBean();
        payOrderSearchBean.setBusinessType(1);
        BigDecimal amount=payOrderDao.findPayOrderTotalAmountByCondition(payOrderSearchBean);
        junit.framework.Assert.assertTrue(amount.compareTo(BigDecimal.ZERO)>=0);
    }

}
