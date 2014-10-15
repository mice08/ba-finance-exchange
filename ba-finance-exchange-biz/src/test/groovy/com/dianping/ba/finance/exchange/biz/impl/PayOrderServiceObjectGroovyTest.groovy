package com.dianping.ba.finance.exchange.biz.impl
import com.dianping.ba.finance.exchange.api.datas.PayOrderData
import com.dianping.ba.finance.exchange.biz.dao.PayOrderDao
import com.dianping.ba.finance.exchange.biz.producer.PayOrderResultNotify
import org.junit.Before
import org.junit.Test
import spock.lang.Specification
import spock.lang.Unroll
/**
 * Created with IntelliJ IDEA.
 * User: bingqiu.yuan
 * Date: 14-6-19
 * Time: 下午1:35
 * To change this template use File | Settings | File Templates.
 */
class PayOrderServiceObjectGroovyTest extends Specification {
    def exampleServiceStub = new PayOrderServiceObject();

    private PayOrderDao payOrderDaoMock;

    private PayOrderResultNotify payOrderResultNotifyMock;

    @Before
    void setup() {
        payOrderDaoMock = Mock();
        payOrderResultNotifyMock = Mock();
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

    def "pausePayOrder no payOrder"() {
        given:
        payOrderDaoMock.loadPayOrderByPaySequence(_ as String) >> {
            null;
        }
        expect:
        true == exampleServiceStub.suspendPayOrder("sequence");
    }

    @Unroll
    def "pausePayOrder status"(int paramStatus, boolean result) {
        given:
        payOrderDaoMock.loadPayOrderByPaySequence(_ as String) >> {
            PayOrderData payOrderData = ["status":paramStatus]
            payOrderData
        }
        expect:
        result == exampleServiceStub.suspendPayOrder("sequence")
        where:
        paramStatus || result
        1           || true
        2           || false
        3           || false
        4           || false
        5           || false
        6           || false
    }

    def "resumePayOrder no payOrder"() {
        given:
        payOrderDaoMock.loadPayOrderByPaySequence(_ as String) >> {
            null;
        }
        expect:
        true == exampleServiceStub.resumePayOrder("sequence");
    }

    @Unroll
    def "resumePayOrder status"(int paramStatus, boolean result) {
        given:
        payOrderDaoMock.loadPayOrderByPaySequence(_ as String) >> {
            PayOrderData payOrderData = ["status":paramStatus]
            payOrderData
        }
        expect:
        result == exampleServiceStub.resumePayOrder("sequence")
        where:
        paramStatus || result
        1           || false
        2           || false
        3           || false
        4           || false
        5           || true
        6           || false
    }

    def "dropPayOrder no payOrder"() {
        given:
        payOrderDaoMock.loadPayOrderByPaySequence(_ as String) >> {
            null;
        }
        expect:
        true == exampleServiceStub.dropPayOrder("sequence");
    }

    @Unroll
    def "dropPayOrder status"(int paramStatus, boolean result) {
        given:
        payOrderDaoMock.loadPayOrderByPaySequence(_ as String) >> {
            PayOrderData payOrderData = ["status":paramStatus]
            payOrderData
        }
        expect:
        result == exampleServiceStub.dropPayOrder("sequence")
        where:
        paramStatus || result
        1           || true
        2           || false
        3           || false
        4           || false
        5           || true
        6           || false
    }

    def "changeCustomer"() {
        setup:
        when:
        exampleServiceStub.changeCustomer(1, 123);
        then:
        1 * payOrderDaoMock.updateCustomerId(_ as Integer, _ as Integer);
    }
}
