package com.dianping.ba.finance.exchange.receivemonitor.job.rocheck
import com.dianping.ba.finance.exchange.receivemonitor.api.datas.ReceiveOrderMonitorData
import spock.lang.Specification
/**
 * Created by IntelliJ IDEA.
 * User: yaxiong.cheng
 * Date: 2014/8/8
 * Time: 15:51
 * To change this template use File | Settings | File Templates.
 */
class ROCheckBaseTest extends Specification {
    ROCheckBase checkBaseStub

    void setup(){
        checkBaseStub = new ROCheckBaseForTest()
    }

    def "checkIfTimeout"(Date date, Boolean result) {
        given:
        checkBaseStub.timeout = 5

        expect:
        result == checkBaseStub.checkIfTimeout(date)

        where:
        date           | result
        new Date() - 1 | true
        new Date()     | false
    }

    private class ROCheckBaseForTest extends ROCheckBase {

        @Override
        boolean filter(ReceiveOrderMonitorData receiveOrderMonitorData) {
            return false
        }

        @Override
        ROCheckResult check(ReceiveOrderMonitorData receiveOrderMonitorData) {
            return null
        }
    }
}