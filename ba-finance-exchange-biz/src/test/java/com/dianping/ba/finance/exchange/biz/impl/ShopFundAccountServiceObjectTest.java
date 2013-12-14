package com.dianping.ba.finance.exchange.biz.impl;

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
import com.dianping.ba.finance.exchange.biz.convert.ShopFundAccountConvert;
import com.dianping.ba.finance.exchange.biz.dao.ShopFundAccountDAO;
import com.dianping.ba.finance.exchange.biz.dao.ShopFundAccountFlowDAO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

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
public class ShopFundAccountServiceObjectTest {
    private ShopFundAccountFlowDAO shopFundAccountFlowDAOMock;
    private ShopFundAccountDAO shopFundAccountDAOMock;
    private ShopFundAccountServiceObject shopFundAccountServiceObjectStub;
    private ExchangeOrderServiceObject exchangeOrderServiceObjectMock;

    @Before
    public void runBeforeTest() {
        shopFundAccountFlowDAOMock = mock(ShopFundAccountFlowDAO.class);
        shopFundAccountDAOMock = mock(ShopFundAccountDAO.class);
        exchangeOrderServiceObjectMock=mock(ExchangeOrderServiceObject.class);
        shopFundAccountServiceObjectStub = new ShopFundAccountServiceObject();
        shopFundAccountServiceObjectStub.setShopFundAccountFlowDAO(shopFundAccountFlowDAOMock);
        shopFundAccountServiceObjectStub.setShopFundAccountDAO(shopFundAccountDAOMock);
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
        when(shopFundAccountDAOMock.loadShopFundAccountData(any(ShopFundAccountBean.class))).thenReturn(null);
        when(shopFundAccountFlowDAOMock.insertShopFundAccountFlow(any(ShopFundAccountFlowData.class))).thenReturn(fundAccountFlowId);
        when(exchangeOrderServiceObjectMock.insertExchangeOrder(any(ExchangeOrderData.class))).thenReturn(exchangeOrderId);
        when(shopFundAccountFlowDAOMock.updateExchangeOrderId(exchangeOrderId,fundAccountFlowId)).thenReturn(4);

        int actual=shopFundAccountServiceObjectStub.createShopFundAccountFlow(input);
        Assert.assertEquals(3,actual);
    }

    @Test
    public void createShopFundAccountFlowWhenAccountExist(){
        ShopFundAccountFlowDTO input = setShopFundAccountFlowDTO();
        int exchangeOrderId=3;
        int fundAccountFlowId=2;
        when(shopFundAccountDAOMock.loadShopFundAccountData(any(ShopFundAccountBean.class))).thenReturn(null);
        when(shopFundAccountDAOMock.insertShopFundAccount(any(ShopFundAccountData.class))).thenReturn(1);
        when(shopFundAccountFlowDAOMock.insertShopFundAccountFlow(any(ShopFundAccountFlowData.class))).thenReturn(fundAccountFlowId);
        when(exchangeOrderServiceObjectMock.insertExchangeOrder(any(ExchangeOrderData.class))).thenReturn(exchangeOrderId);
        when(shopFundAccountFlowDAOMock.updateExchangeOrderId(exchangeOrderId,fundAccountFlowId)).thenReturn(4);

        int actual=shopFundAccountServiceObjectStub.createShopFundAccountFlow(input);
        Assert.assertEquals(3,actual);
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
    public void testUpdateShopFundAccountCausedByExchangeOrderSuccess(){
        ExchangeOrderDTO exchangeOrder = new ExchangeOrderDTO();
        exchangeOrder.setStatus(ExchangeOrderStatusEnum.Success.getExchangeOrderStatus());
        exchangeOrder.setExchangeOrderId(1);
        exchangeOrder.setOrderAmount(BigDecimal.TEN);

        ShopFundAccountFlowData shopFundAccountFlowData = new ShopFundAccountFlowData();
        shopFundAccountFlowData.setFundAccountId(1);

        when(shopFundAccountFlowDAOMock.loadShopFundAccountFlow(anyInt(),anyInt(),anyInt())).thenReturn(shopFundAccountFlowData);

        Assert.assertTrue(shopFundAccountServiceObjectStub.updateShopFundAccountCausedByExchangeOrderSuccess(exchangeOrder));
    }

}
