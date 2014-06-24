package com.dianping.ba.finance.exchange.biz.impl

import com.dianping.ba.finance.exchange.api.datas.ExchangeOrderData
import com.dianping.ba.finance.exchange.biz.dao.ExchangeOrderDao
import spock.lang.Specification

/**
 * Created by noahshen on 14-5-26.
 */
class ExampleServiceSpockTest extends Specification {

    def exampleServiceStub = new ExampleService();

    private ExchangeOrderDao exchangeOrderDaoMock;

    void setup() {
        exchangeOrderDaoMock = Mock()
        exampleServiceStub.setExchangeOrderDao(exchangeOrderDaoMock)
    }

    def "Find exchangeOrder list by orderId List"() {
        when:
        exampleServiceStub.findExchangeOrderListByOrderIdList([87871])

        then:
        1 * exchangeOrderDaoMock.findExchangeOrderListByOrderIdList(_ as List<Integer>)
    }

    def "Find exchangeOrder list by orderId List using where"(int orderId, int eoId) {
        given:
        exchangeOrderDaoMock.findExchangeOrderListByOrderIdList(_ as List<Integer>) >> {
            def eoData = [exchangeOrderId: 87871, bankName: "bankName"] as ExchangeOrderData;
            [eoData]
        }
        expect:
        eoId == exampleServiceStub.findExchangeOrderListByOrderIdList([orderId])[0].exchangeOrderId

        where:
        orderId || eoId
        87871 | 87871
    }

    def "Get max"(int a, int b, int c) {
        expect:
        c == exampleServiceStub.max(a, b)

        where:
        a | b || c
        1 | 2 || 2
        7 | 6 || 7
        0 | 0 || 0
    }
}
