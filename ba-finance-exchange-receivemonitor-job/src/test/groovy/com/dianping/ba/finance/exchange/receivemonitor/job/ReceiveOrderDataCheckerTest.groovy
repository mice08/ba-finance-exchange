package com.dianping.ba.finance.exchange.receivemonitor.job

import com.dianping.ba.finance.exchange.receivemonitor.api.ReceiveMonitorService
import com.dianping.ba.finance.exchange.receivemonitor.api.ReceiveOrderMonitorService
import com.dianping.ba.finance.exchange.receivemonitor.api.datas.ReceiveOrderMonitorData
import com.dianping.ba.finance.exchange.receivemonitor.api.datas.TodoData
import com.dianping.ba.finance.exchange.receivemonitor.job.rocheck.ROCheckResult
import com.dianping.ba.finance.exchange.receivemonitor.job.rocheck.ROCheckRule
import com.dianping.ba.finance.exchange.receivemonitor.job.rocheck.rule.RORCCheck
import spock.lang.Specification

/**
 * Created by IntelliJ IDEA.
 * User: yaxiong.cheng
 * Date: 2014/8/8
 * Time: 16:39
 * To change this template use File | Settings | File Templates.
 */
class ReceiveOrderDataCheckerTest extends Specification {
    ReceiveOrderDataChecker checkerStub
    ReceiveOrderMonitorService receiveOrderMonitorServiceMock
    ReceiveMonitorService receiveMonitorServiceMock
    List<ROCheckRule> roCheckRuleList
    RORCCheck rorcCheck

    void setup(){
        checkerStub = []
        receiveOrderMonitorServiceMock = Mock()
        receiveMonitorServiceMock = Mock()
        checkerStub.receiveOrderMonitorService = receiveOrderMonitorServiceMock
        checkerStub.receiveMonitorService = receiveMonitorServiceMock
        roCheckRuleList = []
        rorcCheck = Mock()
        roCheckRuleList.add(rorcCheck)
        checkerStub.roCheckRuleList = roCheckRuleList
    }

    def "runWithoutQualifiedRO"() {
        given:
        receiveMonitorServiceMock.findUnhandledToDoData() >> []
        receiveOrderMonitorServiceMock.findReceiveOrderData(_ as Date,_ as Date) >> []

        when:
        checkerStub.run()

        then:
        0*receiveMonitorServiceMock.updateTodoToHandled(_ as List<Integer>)
    }

    def "runWithTodoWithoutRo"() {
        given:
        receiveMonitorServiceMock.findUnhandledToDoData() >> {
            TodoData data = [roId:2,
                             status:1]
            [data]
        }
        receiveOrderMonitorServiceMock.loadReceiveOrderData(_ as Integer) >> {
            ReceiveOrderMonitorData roData = [roId:2,
                                              status:2]
            roData
        }
        receiveOrderMonitorServiceMock.findReceiveOrderData(_ as Date,_ as Date) >> []
        rorcCheck.filter(_ as ReceiveOrderMonitorData) >> true
        rorcCheck.check(_ as ReceiveOrderMonitorData) >> {
            ROCheckResult checkResult = [valid:true,
                                         timeout:false]
            checkResult
        }
        when:
        checkerStub.run()

        then:
        1*receiveMonitorServiceMock.updateTodoToHandled(_ as List<Integer>)
    }

    def "runWithTodoWithRo"() {
        setup:

        when:
        checkerStub.run()

        then:
        _ * receiveMonitorServiceMock.findUnhandledToDoData() >> {
            TodoData data = [roId:2,
                             status:1]
            [data]
        }
        1*receiveOrderMonitorServiceMock.loadReceiveOrderData(_ as Integer) >> {
            ReceiveOrderMonitorData roData = [roId:2,
                                              status:2]
            roData
        }
        1*receiveOrderMonitorServiceMock.findReceiveOrderData(_, _) >> {
            ReceiveOrderMonitorData roDataNew = [roId:4,
                                                 status:2]
            [roDataNew]
        }
        2*rorcCheck.filter(_ as ReceiveOrderMonitorData) >> true
        2*rorcCheck.check(_ as ReceiveOrderMonitorData) >> { ReceiveOrderMonitorData roParam ->
            ROCheckResult checkResult = [valid:true,
                                         timeout:false]
            if(roParam.roId == 4) {
                checkResult.valid = false;
            }
            checkResult
        }
        1*receiveMonitorServiceMock.updateTodoToHandled(_ as List<Integer>)
        1*receiveMonitorServiceMock.addTodo(_ as TodoData)
    }

}