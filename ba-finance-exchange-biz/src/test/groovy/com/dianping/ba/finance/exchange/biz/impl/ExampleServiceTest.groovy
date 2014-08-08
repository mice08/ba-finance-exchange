package com.dianping.ba.finance.exchange.biz.impl
import com.dianping.ba.finance.exchange.api.datas.ExchangeOrderData
import com.dianping.ba.finance.exchange.biz.dao.ExchangeOrderDao
import groovy.mock.interceptor.MockFor
import org.junit.Before
import org.junit.Test

import static org.mockito.Matchers.anyList
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

/**
 * Created by noahshen on 14-5-26.
 */
class ExampleServiceTest {

    def exampleServiceStub = new ExampleService();

    private ExchangeOrderDao exchangeOrderDaoMock;

    @Before
    void setUp() {
        exampleServiceStub.setServiceName("exampleService");
        exchangeOrderDaoMock = mock(ExchangeOrderDao.class);
        exampleServiceStub.setExchangeOrderDao(exchangeOrderDaoMock);
    }

    @Test
    void testHello() {
        def result = exampleServiceStub.hello("noah");
        assert result == "From=noah, ServiceName=exampleService"
    }

    @Test
    void testFindExchangeOrderListByOrderIdList() {
        ExchangeOrderData eoData = [exchangeOrderId: 123, bankName: "bankName" ] as ExchangeOrderData;
        when(exchangeOrderDaoMock.findExchangeOrderListByOrderIdList(anyList())).thenReturn(Arrays.asList(eoData));

        List<ExchangeOrderData> eoDataList = exampleServiceStub.findExchangeOrderListByOrderIdList(Arrays.asList(123));
        assert !eoDataList.isEmpty()
        assert eoDataList.get(0).getExchangeOrderId() == 123
    }

    @Test
    void testFindExchangeOrderListByOrderIdListWithGMock() {

        MockFor exchangeOrderDaoMock = new MockFor(ExchangeOrderDao)
        exchangeOrderDaoMock.demand.findExchangeOrderListByOrderIdList {
            def eoData = [exchangeOrderId: 8787, bankName: "bankName"] as ExchangeOrderData;
            [eoData]
        }
        exampleServiceStub.exchangeOrderDao = exchangeOrderDaoMock.proxyDelegateInstance();

        def eoDataList = exampleServiceStub.findExchangeOrderListByOrderIdList([123]);
        assert eoDataList
        assert eoDataList[0].exchangeOrderId == 8787

    }
}
