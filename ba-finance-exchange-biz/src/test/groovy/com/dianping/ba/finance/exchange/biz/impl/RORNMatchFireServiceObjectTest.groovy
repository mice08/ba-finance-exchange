package com.dianping.ba.finance.exchange.biz.impl

import com.dianping.ba.finance.exchange.api.RORNMatchService
import com.dianping.ba.finance.exchange.api.ReceiveNotifyService
import com.dianping.ba.finance.exchange.api.ReceiveOrderService
import com.dianping.ba.finance.exchange.api.beans.RORNMatchingResultBean
import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData
import com.dianping.ba.finance.exchange.api.enums.ReceiveNotifyStatus
import com.dianping.ba.finance.exchange.api.enums.ReceiveOrderStatus
import spock.lang.Specification

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by noahshen on 14-7-26.
 */
class RORNMatchFireServiceObjectTest extends Specification {

    private RORNMatchFireServiceObject rornMatchFireServiceObjectStub;

    private ReceiveNotifyService receiveNotifyServiceMock;

    private ReceiveOrderService receiveOrderServiceMock;

    private RORNMatchService rornMatchServiceMock;

    private ExecutorService executorService;

    void setup() {
        rornMatchFireServiceObjectStub = []

        receiveNotifyServiceMock = Mock();
        rornMatchFireServiceObjectStub.receiveNotifyService = receiveNotifyServiceMock;

        rornMatchServiceMock = Mock();
        rornMatchFireServiceObjectStub.rornMatchService = rornMatchServiceMock;

        receiveOrderServiceMock = Mock();
        rornMatchFireServiceObjectStub.receiveOrderService = receiveOrderServiceMock;

        executorService = Executors.newSingleThreadExecutor();
        rornMatchFireServiceObjectStub.executorService = executorService;
    }

    def "executeMatchingForNewReceiveOrder"() {
        setup:
        ReceiveOrderData roData = [roId: 123]

        when:
        rornMatchFireServiceObjectStub.executeMatchingForNewReceiveOrder(roData);

        then:
        _
    }

    def "doExecuteMatchingForNewReceiveOrder"() {
        setup:
        ReceiveOrderData roData = [roId: 123]

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
        ReceiveOrderData roData = [roId: 123]

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


    def "executeMatchingForNewReceiveNotify"() {
        setup:
        ReceiveNotifyData rnData = [receiveNotifyId: 123]

        when:
        rornMatchFireServiceObjectStub.executeMatchingForNewReceiveNotify(rnData);

        then:
        _
    }


    def "doExecuteMatchingForNewReceiveNotify"() {
        setup:
        ReceiveNotifyData rnData = [receiveNotifyId: 123]

        when:
        rornMatchFireServiceObjectStub.doExecuteMatchingForNewReceiveNotify(rnData);

        then:
        1 * receiveOrderServiceMock.findUnmatchAndUnconfirmedReceiveOrder(_ as ReceiveOrderStatus) >> {
            ReceiveOrderData roData = [roId: 123]
            [roData]
        }

        1 * rornMatchServiceMock.matching(_ as List<ReceiveOrderData>, _ as List<ReceiveNotifyData>)
    }


    def "executeMatchingForReceiveOrderConfirmed"() {
        setup:
        ReceiveOrderData roData = [roId: 123]

        when:
        rornMatchFireServiceObjectStub.executeMatchingForReceiveOrderConfirmed(roData);

        then:
        _
    }



    def "doExecuteMatchingForReceiveOrderConfirmed application null"() {
        setup:
        ReceiveOrderData roData = [roId: 123]

        when:
        rornMatchFireServiceObjectStub.doExecuteMatchingForReceiveOrderConfirmed(roData);

        then:
        0 * receiveNotifyServiceMock.findUnmatchedLeftReceiveNotify(_ as ReceiveNotifyStatus, _ as String) >> {
            ReceiveNotifyData rnData = [receiveNotifyId: 123]
            [rnData]
        }

        0 * receiveNotifyServiceMock.clearReceiveNotifyMatchInfo(_ as ReceiveNotifyStatus, _ as List)

        0 * receiveOrderServiceMock.findUnmatchAndUnconfirmedReceiveOrder(_ as ReceiveOrderStatus) >> {
            ReceiveOrderData otherUnmatchedROData = [roId: 123]
            [otherUnmatchedROData]
        }

        0 * rornMatchServiceMock.matching(_ as List<ReceiveOrderData>, _ as List<ReceiveNotifyData>)
    }


    def "doExecuteMatchingForReceiveOrderConfirmed"() {
        setup:
        ReceiveOrderData roData = [roId: 123, applicationId: "123"]

        when:
        rornMatchFireServiceObjectStub.doExecuteMatchingForReceiveOrderConfirmed(roData);

        then:
        1 * receiveNotifyServiceMock.findUnmatchedLeftReceiveNotify(_ as ReceiveNotifyStatus, _ as String) >> {
            ReceiveNotifyData rnData = [receiveNotifyId: 123]
            [rnData]
        }

        1 * receiveNotifyServiceMock.clearReceiveNotifyMatchInfo(_ as ReceiveNotifyStatus, _ as List)

        1 * receiveOrderServiceMock.findUnmatchAndUnconfirmedReceiveOrder(_ as ReceiveOrderStatus) >> {
            ReceiveOrderData otherUnmatchedROData = [roId: 123]
            [otherUnmatchedROData]
        }

        1 * rornMatchServiceMock.matching(_ as List<ReceiveOrderData>, _ as List<ReceiveNotifyData>)
    }



}
