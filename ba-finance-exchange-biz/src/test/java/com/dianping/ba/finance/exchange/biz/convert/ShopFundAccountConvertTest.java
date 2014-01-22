package com.dianping.ba.finance.exchange.biz.convert;

import com.dianping.ba.finance.exchange.api.beans.ShopFundAccountBean;
import com.dianping.ba.finance.exchange.api.datas.ExchangeOrderData;
import com.dianping.ba.finance.exchange.api.datas.ShopFundAccountData;
import com.dianping.ba.finance.exchange.api.datas.ShopFundAccountFlowData;
import com.dianping.ba.finance.exchange.api.dtos.ExchangeOrderDTO;
import com.dianping.ba.finance.exchange.api.dtos.ShopFundAccountFlowDTO;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.ba.finance.exchange.api.enums.FlowType;
import com.dianping.ba.finance.exchange.api.enums.SourceType;
import junit.framework.Assert;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: 遐
 * Date: 14-1-16
 * Time: 下午2:35
 * To change this template use File | Settings | File Templates.
 */
public class ShopFundAccountConvertTest {

    @Test
    public void testConvertShopFundAccountFlowDTOToShopFundAccountBean(){
        ShopFundAccountFlowDTO shopFundAccountFlowDTO = new ShopFundAccountFlowDTO();
        shopFundAccountFlowDTO.setBusinessType(BusinessType.PREPAID_CARD);
        shopFundAccountFlowDTO.setCompanyGlobalId("1111-1111");
        shopFundAccountFlowDTO.setCustomerGlobalId("1111-1111");
        shopFundAccountFlowDTO.setShopId(11111);

        ShopFundAccountBean actual = ShopFundAccountConvert.convertShopFundAccountFlowDTOToShopFundAccountBean(shopFundAccountFlowDTO);

        Assert.assertNotNull(actual);
        Assert.assertEquals("1111-1111", actual.getCompanyGlobalId());
        Assert.assertEquals("1111-1111", actual.getCustomerGlobalId());
        Assert.assertEquals(11111, actual.getShopId());
        Assert.assertEquals(4, actual.getBusinessType());
    }

    @Test
    public void testBuildShopFundAccountDataFromShopFundAccountFlowDTO(){
        ShopFundAccountFlowDTO shopFundAccountFlowDTO = new ShopFundAccountFlowDTO();
        shopFundAccountFlowDTO.setShopId(11111);
        shopFundAccountFlowDTO.setBusinessType(BusinessType.PREPAID_CARD);
        shopFundAccountFlowDTO.setCustomerGlobalId("1111-1111");
        shopFundAccountFlowDTO.setCompanyGlobalId("1111-1111");

        ShopFundAccountData actual = ShopFundAccountConvert.buildShopFundAccountDataFromShopFundAccountFlowDTO(shopFundAccountFlowDTO);

        Assert.assertNotNull(actual);
        Assert.assertEquals(11111,actual.getShopId());
        Assert.assertEquals(4, actual.getBusinessType());
        Assert.assertEquals("1111-1111", actual.getCompanyGlobalId());
        Assert.assertEquals("1111-1111", actual.getCustomerGlobalId());
        Assert.assertEquals(BigDecimal.ZERO, actual.getBalanceFrozen());
        Assert.assertEquals(BigDecimal.ZERO, actual.getBalanceTotal());
        Assert.assertEquals(BigDecimal.ZERO, actual.getCredit());
        Assert.assertEquals(BigDecimal.ZERO,actual.getDebit());

    }

    @Test
    public void testBuildShopFundAccountFlowDataFromShopFundAccountFlowDTO() {
        ShopFundAccountFlowDTO shopFundAccountFlowDTO = new ShopFundAccountFlowDTO();
        shopFundAccountFlowDTO.setFlowAmount(BigDecimal.TEN);
        shopFundAccountFlowDTO.setFlowType(FlowType.IN);
        shopFundAccountFlowDTO.setSourceType(SourceType.PaymentPlan);
        shopFundAccountFlowDTO.setBizId("111");

        ShopFundAccountFlowData actual = ShopFundAccountConvert.buildShopFundAccountFlowDataFromShopFundAccountFlowDTO(shopFundAccountFlowDTO, 123);

        Assert.assertNotNull(actual);
        Assert.assertEquals(BigDecimal.TEN, actual.getFlowAmount());
        Assert.assertEquals(FlowType.IN.value(), actual.getFlowType());
        Assert.assertEquals(SourceType.PaymentPlan.value(), actual.getSourceType());
        Assert.assertEquals(123, actual.getFundAccountId());
        Assert.assertEquals("PP|111", actual.getSequence());
    }

    @Test
    public void testConvertShopFundAccountFlowDTOToExchangeOrderData(){
        ShopFundAccountFlowDTO shopFundAccountFlowDTO = new ShopFundAccountFlowDTO();
        shopFundAccountFlowDTO.setFlowAmount(BigDecimal.TEN);
        shopFundAccountFlowDTO.setFlowType(FlowType.IN);
        shopFundAccountFlowDTO.setBankAccountNo("123");
        shopFundAccountFlowDTO.setBankAccountName("bankAccountName");
        shopFundAccountFlowDTO.setBankName("bankName");
        shopFundAccountFlowDTO.setBankCity("bankCity");
        shopFundAccountFlowDTO.setBankProvince("bankProvince");

        ExchangeOrderData actual = ShopFundAccountConvert.convertShopFundAccountFlowDTOToExchangeOrderData(shopFundAccountFlowDTO);

        Assert.assertNotNull(actual);
        Assert.assertEquals(BigDecimal.TEN, actual.getOrderAmount());
        Assert.assertEquals(FlowType.IN.value(), actual.getOrderType());
        Assert.assertEquals("123", actual.getBankAccountNo());
        Assert.assertEquals("bankAccountName", actual.getBankAccountName());
        Assert.assertEquals("bankName", actual.getBankName());
        Assert.assertEquals("bankCity", actual.getBankCity());
        Assert.assertEquals("bankProvince", actual.getBankProvince());
    }

    @Test
    public void testBuildShopFundAccountFlowDataForOut(){
        ExchangeOrderDTO exchangeOrderDTO = new ExchangeOrderDTO();
        exchangeOrderDTO.setExchangeOrderId(123);
        exchangeOrderDTO.setOrderAmount(BigDecimal.TEN);
        exchangeOrderDTO.setRelevantFundAccountId(111);

        ShopFundAccountFlowData actual = ShopFundAccountConvert.buildShopFundAccountFlowDataForOut(exchangeOrderDTO);

        Assert.assertNotNull(actual);
        Assert.assertEquals(123, actual.getExchangeOrderId());
        Assert.assertEquals(BigDecimal.TEN, actual.getFlowAmount());
        Assert.assertEquals(111,actual.getFundAccountId());
        Assert.assertEquals(FlowType.OUT.value(), actual.getFlowType());
        Assert.assertEquals(SourceType.ExchangeOrder.value(), actual.getSourceType());
        Assert.assertEquals("EX|123", actual.getSequence());

    }
}
