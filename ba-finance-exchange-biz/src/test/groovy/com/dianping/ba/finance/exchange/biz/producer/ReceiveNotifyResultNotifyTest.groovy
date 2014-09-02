package com.dianping.ba.finance.exchange.biz.producer

import com.dianping.ba.finance.exchange.api.beans.ReceiveNotifyResultBean
import com.dianping.ba.finance.exchange.api.enums.BusinessType
import com.dianping.ba.finance.exchange.api.enums.ReceiveNotifyResultStatus
import com.dianping.finance.common.swallow.SwallowEventBean
import com.dianping.finance.common.swallow.SwallowProducer
import spock.lang.Specification
/**
 * Created by noahshen on 14-8-19.
 */
class ReceiveNotifyResultNotifyTest extends Specification {

    ReceiveNotifyResultNotify receiveNotifyResultNotifyStub;

    SwallowProducer receiveNotifyProducerMock;

    void setup() {
        receiveNotifyResultNotifyStub = []
        receiveNotifyProducerMock = Mock()
        receiveNotifyResultNotifyStub.receiveNotifyProducer = receiveNotifyProducerMock;
    }

    def "ResultNotify AD"() {
        given:
        ReceiveNotifyResultBean resultBean = [applicationId  : "123",
                                              receiveNotifyId: 123,
                                              status         : ReceiveNotifyResultStatus.CONFIRMED,
                                              businessType   : BusinessType.ADVERTISEMENT.value()];

        when:
        receiveNotifyResultNotifyStub.resultNotify(resultBean);

        then:
        1 * receiveNotifyProducerMock.fireSwallowEvent( _ as SwallowEventBean)
    }

    def "ResultNotify Not AD"() {
        given:
        ReceiveNotifyResultBean resultBean = [applicationId  : "123",
                                              receiveNotifyId: 123,
                                              status         : ReceiveNotifyResultStatus.CONFIRMED,
                                              businessType   : BusinessType.GROUP_PURCHASE.value()];

        when:
        receiveNotifyResultNotifyStub.resultNotify(resultBean);

        then:
        0 * receiveNotifyProducerMock.fireSwallowEvent( _ as SwallowEventBean)
    }
}
