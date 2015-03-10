package com.dianping.ba.finance.exchange.siteweb.action.ajax

import com.dianping.ba.finance.exchange.api.ReceiveNotifyService
import com.dianping.ba.finance.exchange.api.enums.ReceiveNotifyStatus
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by noahshen on 14-8-19.
 */
class UpdateReceiveNotifyStatusAjaxActionTest extends Specification {

    UpdateReceiveNotifyStatusAjaxAction updateReceiveNotifyStatusAjaxActionStub

    ReceiveNotifyService receiveNotifyServiceMock;

    void setup() {
        updateReceiveNotifyStatusAjaxActionStub = []

        receiveNotifyServiceMock = Mock()
        updateReceiveNotifyStatusAjaxActionStub.receiveNotifyService = receiveNotifyServiceMock
    }

    @Unroll
    def "ConfirmReceiveNotify"(Integer rnId, String result) {
        given:
        updateReceiveNotifyStatusAjaxActionStub.rnId = rnId;
        receiveNotifyServiceMock.updateReceiveNotifyStatus(_ as Integer, _ as ReceiveNotifyStatus, _ as ReceiveNotifyStatus, _ as String) >> { args ->
            def id = args[0]
            if (id == 8787) {
                return 0
            }
            if (id == 87871) {
                throw new RuntimeException("error")
            }
            1
        }

        expect:
        result == updateReceiveNotifyStatusAjaxActionStub.confirmReceiveNotify();

        where:
        rnId  | result
        -1    | "success"
        123   | "success"
        8787  | "success"
        87871 | "success"
    }

    @Unroll
    def "RejectReceiveNotify"() {
        given:
        updateReceiveNotifyStatusAjaxActionStub.rnId = rnId;
        receiveNotifyServiceMock.updateReceiveNotifyStatus(_ as Integer, _ as ReceiveNotifyStatus, _ as ReceiveNotifyStatus, _ as String) >> { args ->
            def id = args[0]
            if (id == 8787) {
                return 0
            }
            if (id == 87871) {
                throw new RuntimeException("error")
            }
            1
        }

        expect:
        result == updateReceiveNotifyStatusAjaxActionStub.rejectReceiveNotify();

        where:
        rnId  | result
        -1    | "success"
        123   | "success"
        8787  | "success"
        87871 | "success"
    }
}
