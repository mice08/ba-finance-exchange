package com.dianping.ba.finance.exchange.biz.impl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jinyu.yin
 * Date: 13-12-14
 * Time: 下午6:14
 * To change this template use File | Settings | File Templates.
 */
public class mainTest {
    public static void main(String[] args) throws Exception
    {
        ExchangeOrderServiceObject exchangeOrderServiceObject = ComponentContainer.SPRING.lookup("exchangeOrderService");

        List<Integer> orderIds = new ArrayList<Integer>();
        orderIds.add(new Integer(1));
        exchangeOrderServiceObject.updateExchangeOrderToSuccess(orderIds) ;
    }
}
