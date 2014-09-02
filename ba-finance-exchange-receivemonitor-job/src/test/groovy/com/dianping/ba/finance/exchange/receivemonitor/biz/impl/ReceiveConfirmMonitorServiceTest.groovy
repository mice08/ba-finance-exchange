package com.dianping.ba.finance.exchange.receivemonitor.biz.impl

import com.dianping.ba.finance.exchange.receivemonitor.api.datas.ReceiveConfirmMonitorData
import com.dianping.ba.finance.exchange.receivemonitor.api.enums.ReceiveConfirmStatus
import com.dianping.ba.finance.exchange.receivemonitor.biz.dao.ReceiveConfirmMonitorDao
import org.spockframework.compiler.model.Spec
import spock.lang.Specification

/**
 * Created by IntelliJ IDEA.
 * User: yaxiong.cheng
 * Date: 2014/8/8
 * Time: 15:36
 * To change this template use File | Settings | File Templates.
 */
class ReceiveConfirmMonitorServiceTest extends Specification {
    ReceiveConfirmMonitorServiceObject serviceStub
    ReceiveConfirmMonitorDao receiveConfirmMonitorDaoMock

    void setup() {
        serviceStub = []
        receiveConfirmMonitorDaoMock = Mock()
        serviceStub.receiveConfirmMonitorDao = receiveConfirmMonitorDaoMock
    }

    def "loadReceiveConfirmData"(Integer rcId,Integer rcIdResult) {
        given:
        receiveConfirmMonitorDaoMock.loadReceiveConfirmData(_ as Integer) >> { Integer rcId2 ->
            ReceiveConfirmMonitorData data = [rcId:rcId2,
                                              status:ReceiveConfirmStatus.CONFIRM_SUCCESS.value()]
            data
        }
        expect:
        ReceiveConfirmMonitorData result = serviceStub.loadReceiveConfirmData(rcId)
        result.getRcId() == rcIdResult
        result.getStatus() == ReceiveConfirmStatus.CONFIRM_SUCCESS.value()

        where:
        rcId  ||  rcIdResult
        13    ||   13
    }
}