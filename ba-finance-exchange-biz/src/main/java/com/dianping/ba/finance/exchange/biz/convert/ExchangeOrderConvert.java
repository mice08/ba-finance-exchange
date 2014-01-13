package com.dianping.ba.finance.exchange.biz.convert;

import com.dianping.ba.finance.exchange.api.datas.ExchangeOrderData;
import com.dianping.ba.finance.exchange.api.dtos.ExchangeOrderDTO;
import com.dianping.ba.finance.exchange.api.enums.ExchangeOrderStatusEnum;

/**
 * Created with IntelliJ IDEA.
 * User: 遐
 * Date: 13-12-23
 * Time: 上午10:00
 * To change this template use File | Settings | File Templates.
 */
public class ExchangeOrderConvert {

    /**
     * 构建交易指令DTO
     *
     * @param exchangeOrderData
     * @return
     */
    public static ExchangeOrderDTO buildExchangeOrderDTO(ExchangeOrderData exchangeOrderData) {
        ExchangeOrderDTO exchangeOrderDTO = new ExchangeOrderDTO();
        exchangeOrderDTO.setExchangeOrderId(exchangeOrderData.getExchangeOrderId());
        exchangeOrderDTO.setStatus(exchangeOrderData.getStatus());
        exchangeOrderDTO.setOrderType(exchangeOrderData.getOrderType());
        exchangeOrderDTO.setOrderAmount(exchangeOrderData.getOrderAmount());

        return exchangeOrderDTO;
    }
}
