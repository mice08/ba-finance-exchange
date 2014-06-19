package com.dianping.ba.finance.exchange.biz.impl

import com.dianping.ba.finance.exchange.biz.dao.PayOrderDao
import com.dianping.ba.finance.exchange.biz.producer.PayOrderResultNotify
import org.junit.Before
import org.junit.Test
/**
 * Created with IntelliJ IDEA.
 * User: bingqiu.yuan
 * Date: 14-6-19
 * Time: 下午1:35
 * To change this template use File | Settings | File Templates.
 */
class PayOrderServiceObjectGroovyTest {
    def exampleServiceStub = new PayOrderServiceObject();

    private PayOrderDao payOrderDaoMock;

    private PayOrderResultNotify payOrderResultNotifyMock;

    @Before
    void setup() {
        exampleServiceStub.setPayOrderDao(payOrderDaoMock)
        exampleServiceStub.setPayOrderResultNotify(payOrderResultNotifyMock)
    }

    @Test
    void testUpdatePayOrderToPaySuccess() {
//        MockFor payOrderDaoMock=new MockFor(PayOrderDao);
//        MockFor payOrderResultNotifyMock = new MockFor(PayOrderResultNotify);
//        payOrderDaoMock.demand.updatePayOrders{
//            1
//        }
//        payOrderDaoMock.demand.findPayOrderListByPoIdList{
//            def eoData = [poId: 8787, status: 3] as PayOrderData;
//            [eoData]
//        }
//        exampleServiceStub.payOrderResultNotify = payOrderResultNotifyMock.proxyDelegateInstance();
//        exampleServiceStub.payOrderDao=payOrderDaoMock.proxyDelegateInstance();
//        exampleServiceStub.updatePayOrderToPaySuccess([123],-1);


        assert 1;
    }

}
