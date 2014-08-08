package com.dianping.ba.finance.exchange.receivemonitor.biz.impl

import com.dianping.ba.finance.exchange.receivemonitor.api.ReceiveOrderMonitorService
import com.dianping.ba.finance.exchange.receivemonitor.api.datas.ReceiveOrderMonitorData
import com.dianping.ba.finance.exchange.receivemonitor.api.enums.MonitorReceiveOrderStatus
import com.dianping.ba.finance.exchange.receivemonitor.biz.dao.ReceiveOrderMonitorDao
import spock.lang.Specification

/**
 * Created by IntelliJ IDEA.
 * User: yaxiong.cheng
 * Date: 2014/8/8
 * Time: 14:44
 * To change this template use File | Settings | File Templates.
 */
class ReceiveOrderMonitorServiceTest extends Specification {
    ReceiveOrderMonitorServiceObject serviceStub
    ReceiveOrderMonitorDao receiveOrderMonitorDaoMock

    void setup() {
        serviceStub = []
        receiveOrderMonitorDaoMock = Mock()
        serviceStub.receiveOrderMonitorDao = receiveOrderMonitorDaoMock
    }

    def "findReceiveOrderData"() {
        given:
        def beginDate = new Date()
        def endDate = new Date()
        receiveOrderMonitorDaoMock.findReceiveOrderData(_ as Date,_ as Date) >> {
            ReceiveOrderMonitorData roData1 = [roId: 1]
            ReceiveOrderMonitorData roData2 = [roId: 2]
            return [roData1,roData2]
        }

        expect:
        serviceStub.findReceiveOrderData(beginDate,endDate).size()==2

    }

    def "loadReceiveOrderData"(Integer roId,Integer roIdResult) {
        given:
        receiveOrderMonitorDaoMock.loadReceiveOrderData(_ as Integer) >> { Integer roId2 ->
            ReceiveOrderMonitorData roData = [roId:roId2,
                                              status:MonitorReceiveOrderStatus.CONFIRMED.value()]
            roData
        }
        expect:
        ReceiveOrderMonitorData result = serviceStub.loadReceiveOrderData(roId)
        result.getStatus() == MonitorReceiveOrderStatus.CONFIRMED.value()
        result.getRoId() == roIdResult

        where:
        roId  || roIdResult
        13    || 13
    }
}