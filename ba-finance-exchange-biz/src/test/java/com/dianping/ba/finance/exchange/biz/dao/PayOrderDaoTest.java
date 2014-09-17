package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.ba.finance.exchange.api.beans.POUpdateInfoBean;
import com.dianping.ba.finance.exchange.api.beans.PayOrderSearchBean;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.ba.finance.exchange.api.enums.PayOrderStatus;
import com.dianping.core.type.PageModel;
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
        payOrderData.setPaySequence("P|87873");
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

        int poId = payOrderDao.insertPayOrder(payOrderData);
        Assert.assertTrue(poId > 0);
    }

    @Test
    public void testUpdatePayOrders() {
        List<Integer> poIds =new ArrayList<Integer>();
        poIds.add(3121);
        poIds.add(3116);
        POUpdateInfoBean poUpdateInfoBean = new POUpdateInfoBean();
        poUpdateInfoBean.setPoIdList(poIds);
        poUpdateInfoBean.setLoginId(878787);
        poUpdateInfoBean.setPaidDate(new Date());
        poUpdateInfoBean.setPreStatus(PayOrderStatus.PAY_SUCCESS.value());
        poUpdateInfoBean.setUpdateStatus(PayOrderStatus.REFUND.value());
        poUpdateInfoBean.setMemo("refund87");
        payOrderDao.updatePayOrders(poUpdateInfoBean);
    }

    @Test
    public void testFindPayOrderListByPoIdList() {
        List<Integer> poIds =new ArrayList<Integer>();
        poIds.add(2049);
        poIds.add(2044);
        payOrderDao.findPayOrderListByPoIdList(poIds);
    }

	@Test
    public void testPaginatePayOrderList() {
        PayOrderSearchBean payOrderSearchBean = new PayOrderSearchBean();
        payOrderSearchBean.setBusinessType(1);
        payOrderSearchBean.setPoIdList(Arrays.asList(10001012, 10001013));
        PageModel pageModel = payOrderDao.paginatePayOrderList(payOrderSearchBean, 1, 20);
        Assert.assertNotNull(pageModel);
    }

    @Test
    public void testFindPayOrderTotalAmountByCondition() {
        PayOrderSearchBean payOrderSearchBean = new PayOrderSearchBean();
        payOrderSearchBean.setBusinessType(1);
        payOrderSearchBean.setPoIdList(Arrays.asList(10001012, 10001013));
        BigDecimal amount = payOrderDao.findPayOrderTotalAmountByCondition(payOrderSearchBean);
        Assert.assertTrue(amount.compareTo(BigDecimal.ZERO) >= 0);
    }


	@Test
	public void testFindPayOrderList() {
		PayOrderSearchBean payOrderSearchBean = new PayOrderSearchBean();
		payOrderSearchBean.setBusinessType(1);
        payOrderSearchBean.setPoIdList(Arrays.asList(10001012, 10001013));
        List<PayOrderData> actual = payOrderDao.findPayOrderList(payOrderSearchBean);
		Assert.assertNotNull(actual);
		System.out.print(actual.size());
	}

    @Test
    public void testFindPayOrderIdList() throws Exception {
        PayOrderSearchBean payOrderSearchBean = new PayOrderSearchBean();
        payOrderSearchBean.setBusinessType(BusinessType.GROUP_PURCHASE.value());
        payOrderSearchBean.setStatus(PayOrderStatus.INIT.value());

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, Calendar.AUGUST);
        cal.set(Calendar.DAY_OF_MONTH, 8);
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
        payOrderSearchBean.setBeginTime(cal.getTime());
        List<Integer> actual = payOrderDao.findPayOrderIdList(payOrderSearchBean);
        Assert.assertNotNull(actual);
        System.out.print(actual.size());

    }

    @Test
    public void testLoadPayOrderByPaySequence() throws Exception {
        PayOrderData poData = payOrderDao.loadPayOrderByPaySequence("paySeq_994");
        Assert.assertNotNull(poData);

    }
}
