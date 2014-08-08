package com.dianping.ba.finance.exchange.biz.impl

import com.dianping.ba.finance.exchange.api.ReceiveNotifyService
import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData
import com.dianping.ba.finance.exchange.biz.rornmatcher.RORNMatcher
import spock.lang.Specification
import spock.lang.Unroll

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by noahshen on 14-7-26.
 */
class RORNMatchServiceObjectTest extends Specification {

    private RORNMatchServiceObject rornMatchServiceObjectStub;

    private ReceiveNotifyService receiveNotifyServiceMock;

    private RORNMatcher rornMatcherMock;

    private ExecutorService executorService;

    void setup() {
        rornMatchServiceObjectStub = []

        receiveNotifyServiceMock = Mock()
        rornMatchServiceObjectStub.receiveNotifyService = receiveNotifyServiceMock

        rornMatcherMock = Mock()
        rornMatchServiceObjectStub.matchers = [rornMatcherMock];

    }

    @Unroll
    def "Matching"(Integer roIdParam, Integer rnIdParam, Boolean noFailedRO) {
        given:
        ReceiveOrderData roData = [roId: roIdParam]
        ReceiveNotifyData rnData = [receiveNotifyId: rnIdParam]

        rornMatcherMock.match(_ as ReceiveOrderData, _ as ReceiveNotifyData) >> { ReceiveOrderData roDataParam, ReceiveNotifyData rnDataParam ->
            if (rnDataParam.receiveNotifyId == 7878) {
                return false;
            }
            true;
        }

        receiveNotifyServiceMock.updateReceiveNotifyMatchId(_ as Integer, _ as Integer, _ as Integer, _ as Integer) >> { args ->
            def roId = args[1]
            def rnId = args[3]
            if (roId == 87123 && rnId == 87123) {
                return false
            }
            true
        }

        expect:
        noFailedRO == rornMatchServiceObjectStub.matching([roData], [rnData]).matchUpdateFailedROList.isEmpty();

        where:
        roIdParam | rnIdParam | noFailedRO
        123       | 7878      | true
        87123     | 7878      | true
        87123     | 87123     | false

    }

}
