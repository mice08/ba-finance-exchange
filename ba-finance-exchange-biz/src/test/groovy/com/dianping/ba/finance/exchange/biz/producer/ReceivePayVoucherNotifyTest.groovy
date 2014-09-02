package com.dianping.ba.finance.exchange.biz.producer

import com.dianping.ba.finance.exchange.api.beans.ReceiveVoucherNotifyBean
import com.dianping.finance.common.swallow.SwallowEventBean
import com.dianping.finance.common.swallow.SwallowProducer
import spock.lang.Specification

/**
 * Created by noahshen on 14-8-22.
 */
class ReceivePayVoucherNotifyTest extends Specification {

    ReceivePayVoucherNotify receivePayVoucherNotifyStub;

    SwallowProducer receivePayVoucherProducerMock;

    void setup() {
        receivePayVoucherNotifyStub = []

        receivePayVoucherProducerMock = Mock()
        receivePayVoucherNotifyStub.receivePayVoucherProducer = receivePayVoucherProducerMock
    }

    def "NotifyVoucher"() {
        setup:
        ReceiveVoucherNotifyBean notifyBean = [amount: BigDecimal.TEN, bizId: "123"]

        when:
        receivePayVoucherNotifyStub.notifyVoucher(notifyBean)

        then:
        1 * receivePayVoucherProducerMock.fireSwallowEvent(_ as SwallowEventBean)
    }
}
