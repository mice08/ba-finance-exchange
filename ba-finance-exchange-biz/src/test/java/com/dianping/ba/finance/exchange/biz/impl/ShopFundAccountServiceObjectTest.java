package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.ShopFundAccountService;
import com.dianping.ba.finance.exchange.api.beans.ShopFundAccountBean;
import com.dianping.ba.finance.exchange.api.datas.ExchangeOrderData;
import com.dianping.ba.finance.exchange.api.datas.ShopFundAccountData;
import com.dianping.ba.finance.exchange.api.datas.ShopFundAccountFlowData;
import com.dianping.ba.finance.exchange.api.dtos.ExchangeOrderDTO;
import com.dianping.ba.finance.exchange.api.dtos.ShopFundAccountFlowDTO;
import com.dianping.ba.finance.exchange.api.enums.BusinessTypeEnum;
import com.dianping.ba.finance.exchange.api.enums.ExchangeOrderStatusEnum;
import com.dianping.ba.finance.exchange.api.enums.FlowTypeEnum;
import com.dianping.ba.finance.exchange.api.enums.SourceTypeEnum;
import com.dianping.ba.finance.exchange.biz.dao.ShopFundAccountDao;
import com.dianping.ba.finance.exchange.biz.dao.ShopFundAccountFlowDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Calendar;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: jinyu.yin
 * Date: 13-12-13
 * Time: 下午4:18
 * To change this template use File | Settings | File Templates.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/config/spring/common/appcontext-*.xml", "classpath*:/config/spring/local/appcontext-*.xml" })

public class ShopFundAccountServiceObjectTest {
    private ShopFundAccountFlowDao shopFundAccountFlowDaoMock;
    private ShopFundAccountDao shopFundAccountDaoMock;
    private ShopFundAccountServiceObject shopFundAccountServiceObjectStub;
    private ExchangeOrderServiceObject exchangeOrderServiceObjectMock;

    @Autowired
    private ShopFundAccountService shopFundAccountServiceWithoutMock;

    @Before
    public void runBeforeTest() {
        shopFundAccountFlowDaoMock = mock(ShopFundAccountFlowDao.class);
        shopFundAccountDaoMock = mock(ShopFundAccountDao.class);
        exchangeOrderServiceObjectMock=mock(ExchangeOrderServiceObject.class);
        shopFundAccountServiceObjectStub = new ShopFundAccountServiceObject();
        shopFundAccountServiceObjectStub.setShopFundAccountFlowDao(shopFundAccountFlowDaoMock);
        shopFundAccountServiceObjectStub.setShopFundAccountDao(shopFundAccountDaoMock);
        shopFundAccountServiceObjectStub.setExchangeOrderService(exchangeOrderServiceObjectMock);
    }

    @Test
    public void createShopFundAccountFlowIsNull(){
        int actual=shopFundAccountServiceObjectStub.createShopFundAccountFlow(null);
        Assert.assertEquals(-1,actual);
    }

    @Test
    public void createShopFundAccountFlowWhenAccountNull(){
        ShopFundAccountFlowDTO input = setShopFundAccountFlowDTO();
        int exchangeOrderId=3;
        int fundAccountFlowId=2;
        when(shopFundAccountDaoMock.loadShopFundAccountData(any(ShopFundAccountBean.class))).thenReturn(null);
        when(shopFundAccountFlowDaoMock.insertShopFundAccountFlow(any(ShopFundAccountFlowData.class))).thenReturn(fundAccountFlowId);
        when(exchangeOrderServiceObjectMock.insertExchangeOrder(any(ExchangeOrderData.class))).thenReturn(exchangeOrderId);
        when(shopFundAccountFlowDaoMock.updateExchangeOrderId(exchangeOrderId,fundAccountFlowId)).thenReturn(4);

        int actual=shopFundAccountServiceObjectStub.createShopFundAccountFlow(input);
        Assert.assertEquals(3,actual);
    }

    @Test
    public void createShopFundAccountFlowWhenAccountExist(){
        ShopFundAccountFlowDTO input = setShopFundAccountFlowDTO();
        int exchangeOrderId=3;
        int fundAccountFlowId=2;
        ShopFundAccountData existShopFundAccountData=new ShopFundAccountData();
        existShopFundAccountData.setFundAccountId(111);
        when(shopFundAccountDaoMock.loadShopFundAccountData(any(ShopFundAccountBean.class))).thenReturn(existShopFundAccountData);
        when(shopFundAccountDaoMock.insertShopFundAccount(any(ShopFundAccountData.class))).thenReturn(1);
        when(shopFundAccountFlowDaoMock.insertShopFundAccountFlow(any(ShopFundAccountFlowData.class))).thenReturn(fundAccountFlowId);
        when(exchangeOrderServiceObjectMock.insertExchangeOrder(any(ExchangeOrderData.class))).thenReturn(exchangeOrderId);
        when(shopFundAccountFlowDaoMock.updateExchangeOrderId(exchangeOrderId,fundAccountFlowId)).thenReturn(4);

        int actual=shopFundAccountServiceObjectStub.createShopFundAccountFlow(input);
        Assert.assertEquals(3,actual);

        //db
        int result=shopFundAccountServiceWithoutMock.createShopFundAccountFlow(input);
        System.out.print("exchangeOrderId:"+result);
        System.out.println();
    }

    private ShopFundAccountFlowDTO setShopFundAccountFlowDTO() {
        ShopFundAccountFlowDTO input=new ShopFundAccountFlowDTO();
        input.setShopId(0);
        input.setCustomerGlobalId("1111");
        input.setBankAccountName("sb");
        input.setCompanyGlobalId("2222");
        input.setBankAccountNo("1234");
        input.setBankName("工行");
        input.setBusinessType(BusinessTypeEnum.PREPAID_CARD);
        input.setFlowAmount(new BigDecimal(2));
        input.setFlowType(FlowTypeEnum.Input);
        input.setSourceType(SourceTypeEnum.PaymentPlan);
        return input;
    }

    @Test
    public void testUpdateShopFundAccountSuccess(){
        ExchangeOrderDTO exchangeOrder = new ExchangeOrderDTO();
        exchangeOrder.setStatus(ExchangeOrderStatusEnum.SUCCESS.getExchangeOrderStatus());
        exchangeOrder.setExchangeOrderId(1);
        exchangeOrder.setOrderAmount(BigDecimal.TEN);

        when(shopFundAccountFlowDaoMock.insertShopFundAccountFlow(any(ShopFundAccountFlowData.class))).thenReturn(1);

        boolean actual = shopFundAccountServiceObjectStub.updateShopFundAccountCausedBySuccessfulExchangeOrder(exchangeOrder);
        Assert.assertTrue(actual);
    }

    @Test
    public void testUpdateShopFundAccountsWhenStatusIsFAIL(){
        ExchangeOrderDTO exchangeOrder = new ExchangeOrderDTO();
        exchangeOrder.setStatus(ExchangeOrderStatusEnum.FAIL.getExchangeOrderStatus());
        exchangeOrder.setExchangeOrderId(1);
        exchangeOrder.setOrderAmount(BigDecimal.TEN);

        Assert.assertFalse(shopFundAccountServiceObjectStub.updateShopFundAccountCausedBySuccessfulExchangeOrder(exchangeOrder));
    }

    @Test
    public void testUpdateShopFundAccountWhenExchangeOrderIsNull(){
        ExchangeOrderDTO exchangeOrder = null;

        Assert.assertFalse(shopFundAccountServiceObjectStub.updateShopFundAccountCausedBySuccessfulExchangeOrder(exchangeOrder));
    }

    @Test
    public void testGetPaymentPlanShopFundAccountFlow() {

        ShopFundAccountFlowData shopFundAccountFlowData = new ShopFundAccountFlowData();
        shopFundAccountFlowData.setFundAccountId(1);

        when(shopFundAccountFlowDaoMock.loadShopFundAccountFlow(anyInt(), anyInt(), anyInt())).thenReturn(shopFundAccountFlowData);

        ShopFundAccountFlowDTO actual = shopFundAccountServiceObjectStub.getPaymentPlanShopFundAccountFlow(1);

        Assert.assertEquals(shopFundAccountFlowData.getFundAccountId(), actual.getFundAccountId());
    }

    @Test
    public void testGetPaymentPlanShopFundAccountFlowWhenOrderIdIsInvalid() {

        ShopFundAccountFlowDTO actual = shopFundAccountServiceObjectStub.getPaymentPlanShopFundAccountFlow(-1);

        Assert.assertNull(actual);
    }

    @Test
    public void testGetPaymentPlanShopFundAccountFlowSuccess() {
        ShopFundAccountFlowData shopFundAccountFlowData = createShopFundAccountFlowData();

        when(shopFundAccountFlowDaoMock.loadShopFundAccountFlow(anyInt(), anyInt(), anyInt())).thenReturn(shopFundAccountFlowData);

        ShopFundAccountFlowDTO actual = shopFundAccountServiceObjectStub.getPaymentPlanShopFundAccountFlow(1);

        Assert.assertEquals(shopFundAccountFlowData.getFundAccountId(), actual.getFundAccountId());
    }

    private ShopFundAccountFlowData createShopFundAccountFlowData(){
        ShopFundAccountFlowData shopFundAccountFlowData = new ShopFundAccountFlowData();
        shopFundAccountFlowData.setFundAccountId(1);
        shopFundAccountFlowData.setFundAccountFlowId(1);
        shopFundAccountFlowData.setFlowAmount(BigDecimal.TEN);
        shopFundAccountFlowData.setFlowType(1);
        shopFundAccountFlowData.setSourceType(1);
        shopFundAccountFlowData.setExchangeOrderId(1);
        shopFundAccountFlowData.setAddDate(Calendar.getInstance().getTime());
        shopFundAccountFlowData.setLastUpdateDate(Calendar.getInstance().getTime());
        shopFundAccountFlowData.setMemo("memo");
        return shopFundAccountFlowData;
    }
}
