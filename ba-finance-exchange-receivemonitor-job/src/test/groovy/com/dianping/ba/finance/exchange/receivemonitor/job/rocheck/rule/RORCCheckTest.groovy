package com.dianping.ba.finance.exchange.receivemonitor.job.rocheck.rule

import com.dianping.ba.finance.exchange.receivemonitor.api.ReceiveConfirmMonitorService
import com.dianping.ba.finance.exchange.receivemonitor.api.datas.ReceiveConfirmMonitorData
import com.dianping.ba.finance.exchange.receivemonitor.api.datas.ReceiveOrderMonitorData
import com.dianping.ba.finance.exchange.receivemonitor.api.enums.ReceiveConfirmStatus
import com.dianping.ba.finance.exchange.receivemonitor.biz.impl.ReceiveConfirmMonitorServiceObject
import com.dianping.ba.finance.exchange.receivemonitor.job.rocheck.ROCheckResult
import spock.lang.Specification

/**
 * Created by IntelliJ IDEA.
 * User: yaxiong.cheng
 * Date: 2014/8/8
 * Time: 16:10
 * To change this template use File | Settings | File Templates.
 */
class RORCCheckTest extends Specification {
    RORCCheck checkStub
    ReceiveConfirmMonitorService serviceMock

    void setup(){
        checkStub = []
        serviceMock = Mock()
        checkStub.receiveConfirmMonitorService = serviceMock
    }

    def "filter"(Integer status,boolean result) {
        given:
        ReceiveOrderMonitorData data = [status:status]

        expect:
        result == checkStub.filter(data)

        where:
        status  ||  result
        1       ||  false
        2       ||  true
    }

    def "check"(Integer roId,Integer status,boolean valid) {
        given:
        ReceiveOrderMonitorData data = [roId:roId,
                                        lastUpdateDate:new Date(),
                                        status:status]
        serviceMock.loadReceiveConfirmData(_ as Integer) >> { Integer roId1 ->
            if(roId == 1) {
                return null
            }
            else if(roId == 2) {
                ReceiveConfirmMonitorData rcData = [status:ReceiveConfirmStatus.CONFIRM_SUCCESS.value()]
                rcData
            }
            else {
                ReceiveConfirmMonitorData rcData = [status:ReceiveConfirmStatus.CONFIRM_FAILURE.value()]
                rcData
            }
        }

        expect:
        ROCheckResult checkResult = checkStub.check(data)
        checkResult.isValid() == valid

        where:
        roId    |  status  |  valid
        1       |   2      |  false
        2       |   2      |  true
        3       |   2      |  false
    }
}