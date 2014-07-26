package com.dianping.ba.finance.exchange.biz.impl

import com.dianping.ba.finance.exchange.api.RORNMatchService
import com.dianping.ba.finance.exchange.api.ReceiveNotifyService
import com.dianping.ba.finance.exchange.api.beans.RORNMatchingResultBean
import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData
import com.dianping.ba.finance.exchange.api.enums.ReceiveNotifyStatus
import spock.lang.Specification

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by noahshen on 14-7-26.
 */
class RORNMatchFireServiceObjectTest extends Specification {

    private RORNMatchFireServiceObject rornMatchFireServiceObjectStub;

    private ReceiveNotifyService receiveNotifyServiceMock;

    private RORNMatchService rornMatchServiceMock;

    private ExecutorService executorService;

    void setup() {
        rornMatchFireServiceObjectStub = []

        receiveNotifyServiceMock = Mock();
        rornMatchFireServiceObjectStub.receiveNotifyService = receiveNotifyServiceMock;

        rornMatchServiceMock = Mock();
        rornMatchFireServiceObjectStub.rornMatchService = rornMatchServiceMock;

        executorService = Executors.newSingleThreadExecutor();
        rornMatchFireServiceObjectStub.executorService = executorService;
    }

    def "executeMatchingForNewReceiveOrder"() {
        setup:
        def ReceiveOrderData roData = [roId: 123]

        when:
        rornMatchFireServiceObjectStub.executeMatchingForNewReceiveOrder(roData);

        then:
        _
    }

    def "doExecuteMatchingForNewReceiveOrder"() {
        setup:
        def ReceiveOrderData roData = [roId: 123]

        when:
        rornMatchFireServiceObjectStub.doExecuteMatchingForNewReceiveOrder(roData);

        then:
        1 * receiveNotifyServiceMock.getUnMatchedReceiveNotify(_ as ReceiveNotifyStatus) >> {
            ReceiveNotifyData rnData = [receiveNotifyId: 123]
            [rnData]
        }

        1 * rornMatchServiceMock.matching(_ as List<ReceiveOrderData>, _ as List<ReceiveNotifyData>) >> {
            RORNMatchingResultBean resultBean = []
            resultBean
        }
    }

    def "doExecuteMatchingForNewReceiveOrder, Match but updated failed"() {
        setup:
        def ReceiveOrderData roData = [roId: 123]

        when:
        rornMatchFireServiceObjectStub.doExecuteMatchingForNewReceiveOrder(roData);

        then:
        4 * receiveNotifyServiceMock.getUnMatchedReceiveNotify(_ as ReceiveNotifyStatus) >> {
            ReceiveNotifyData rnData = [receiveNotifyId: 123]
            [rnData]
        }

        4 * rornMatchServiceMock.matching(_ as List<ReceiveOrderData>, _ as List<ReceiveNotifyData>) >> { args ->
            RORNMatchingResultBean resultBean = [matchUpdateFailedROList: args[0]]
            resultBean
        }
    }
}
