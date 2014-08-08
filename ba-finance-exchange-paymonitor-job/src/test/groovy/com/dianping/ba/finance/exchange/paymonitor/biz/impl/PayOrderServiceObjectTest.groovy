package com.dianping.ba.finance.exchange.paymonitor.biz.impl

import com.dianping.ba.finance.exchange.paymonitor.biz.dao.PayOrderDao
import spock.lang.Specification

/**
 * Created by adam.huang on 2014/8/7.
 */
class PayOrderServiceObjectTest extends Specification {
    PayOrderServiceObject payOrderServiceObjectStub;
    PayOrderDao payOrderDaoMock;

    def setup(){
        payOrderServiceObjectStub = new PayOrderServiceObject();

        payOrderDaoMock = Mock();
        payOrderServiceObjectStub.payOrderDao = payOrderDaoMock;
    }

    def "GetPayOrderBySequence"() {
        given:
        String sequence = "asdf";
        when:
        payOrderServiceObjectStub.getPayOrderBySequence(sequence);
        then:
        1 * payOrderDaoMock.getPayOrderBySequence(_ as String);
    }
}
