package com.dianping.ba.finance.exchange.biz.impl

import com.dianping.ba.finance.exchange.api.datas.PayRequestData
import com.dianping.ba.finance.exchange.api.enums.PayRequestStatus
import com.dianping.ba.finance.exchange.biz.dao.PayRequestDao
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by noahshen on 14-9-17.
 */
class PayRequestServiceObjectTest extends Specification {

    PayRequestServiceObject payRequestServiceObjectStub;

    PayRequestDao payRequestDaoMock;

    void setup() {
        payRequestServiceObjectStub = []

        payRequestDaoMock = Mock()
        payRequestServiceObjectStub.payRequestDao = payRequestDaoMock
    }

    @Unroll
    def "InsertPayRequest"(String paySequence, Boolean result) {
        given:
        PayRequestData prData = [paySequence: paySequence];
        payRequestDaoMock.insertPayRequest(_ as PayRequestData) >> { PayRequestData d ->
            if (d.paySequence == "123") {
                return 1
            }
            0
        }

        expect:
        result == payRequestServiceObjectStub.insertPayRequest(prData);

        where:
        paySequence | result
        "123"       | true
        "456"       | false
    }

    @Unroll
    def "UpdatePayRequest"(Integer prId, Boolean result) {
        given:
        payRequestDaoMock.updatePayRequest(_ as Integer, _ as Integer, _ as String) >> { args ->
            1
        }

        expect:
        result == payRequestServiceObjectStub.updatePayRequest(prId, PayRequestStatus.SUCCESS, "memo");

        where:
        prId | result
        123  | true
    }
}
