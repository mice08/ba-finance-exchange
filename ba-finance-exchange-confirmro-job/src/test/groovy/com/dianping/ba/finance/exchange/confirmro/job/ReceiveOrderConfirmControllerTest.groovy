package com.dianping.ba.finance.exchange.confirmro.job

import com.dianping.ba.finance.exchange.api.ReceiveOrderService
import com.dianping.ba.finance.exchange.api.beans.ReceiveOrderSearchBean
import spock.lang.Specification

/**
 * Created by noahshen on 14-9-3.
 */
class ReceiveOrderConfirmControllerTest extends Specification {

    ReceiveOrderConfirmController receiveOrderConfirmControllerStub;
    ReceiveOrderService receiveOrderServiceMock;

    void setup() {
        receiveOrderConfirmControllerStub = []

        receiveOrderServiceMock = Mock()
        receiveOrderConfirmControllerStub.receiveOrderService = receiveOrderServiceMock;

    }

    def "Execute"() {
        setup:

        when:
        receiveOrderConfirmControllerStub.execute();

        then:
        1 * receiveOrderServiceMock.fireAutoConfirm(_ as ReceiveOrderSearchBean)

    }

    def "Execute throw exceptioin"() {
        setup:

        when:
        receiveOrderConfirmControllerStub.execute();

        then:
        1 * receiveOrderServiceMock.fireAutoConfirm(_ as ReceiveOrderSearchBean) >> {
            throw new RuntimeException("123");
        }

    }
}
