package com.dianping.ba.finance.exchange.biz.convert;

import com.dianping.ba.finance.exchange.api.datas.ExchangeOrderData;
import com.dianping.ba.finance.exchange.api.dtos.ExchangeOrderDTO;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: 遐
 * Date: 13-12-23
 * Time: 上午10:08
 * To change this template use File | Settings | File Templates.
 */
public class ExchangeOrderConvertTest {

    @Test
    public void testConvertExchangeOrderDataToExchangeOrderDTO(){
        ExchangeOrderData exchangeOrderData = new ExchangeOrderData();
        exchangeOrderData.setExchangeOrderId(1);
        exchangeOrderData.setOrderAmount(BigDecimal.TEN);
        exchangeOrderData.setStatus(1);
        exchangeOrderData.setOrderType(1);

        ExchangeOrderDTO exchangeOrderDTO = ExchangeOrderConvert.buildExchangeOrderDTO(exchangeOrderData);

        Assert.assertEquals(1,exchangeOrderDTO.getExchangeOrderId());
        Assert.assertEquals(BigDecimal.TEN,exchangeOrderDTO.getOrderAmount());
        Assert.assertEquals(1,exchangeOrderDTO.getStatus());
        Assert.assertEquals(1,exchangeOrderDTO.getOrderType());

    }
}
