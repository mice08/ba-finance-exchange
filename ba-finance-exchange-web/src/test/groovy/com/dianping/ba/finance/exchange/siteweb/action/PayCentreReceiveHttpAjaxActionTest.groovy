package com.dianping.ba.finance.exchange.siteweb.action;

import com.dianping.ba.finance.exchange.api.PayCentreReceiveRequestHandleService
import com.dianping.ba.finance.exchange.api.dtos.PayCentreReceiveRequestDTO
import com.dianping.ba.finance.exchange.api.enums.ReceiveOrderPayChannel
import spock.lang.Specification
/**
 * Created by noahshen on 14-7-31.
 */
class PayCentreReceiveHttpAjaxActionTest extends Specification {

    PayCentreReceiveHttpAjaxAction payCentreReceiveHttpAjaxActionStub;

    PayCentreReceiveRequestHandleService payCentreReceiveRequestHandleServiceMock;

    void setup() {
        payCentreReceiveHttpAjaxActionStub = []

        payCentreReceiveRequestHandleServiceMock = Mock()
        payCentreReceiveHttpAjaxActionStub.payCentreReceiveRequestHandleService = payCentreReceiveRequestHandleServiceMock
    }

    def "ReceiveFromPayCentre"() {
        setup:
        payCentreReceiveHttpAjaxActionStub.tradeNo = "tradeNo"
        payCentreReceiveHttpAjaxActionStub.payMethod = ReceiveOrderPayChannel.POS_MACHINE.value()
        payCentreReceiveHttpAjaxActionStub.paymentChannel = 10;
        payCentreReceiveHttpAjaxActionStub.bankId = 11
        payCentreReceiveHttpAjaxActionStub.amount = "11.87"
        payCentreReceiveHttpAjaxActionStub.receiveDate = "20140731131313"

        when:
        payCentreReceiveHttpAjaxActionStub.webExecute();

        then:
        1 * payCentreReceiveRequestHandleServiceMock.handleReceiveRequest(_ as PayCentreReceiveRequestDTO)
    }

    def "ReceiveFromPayCentre throw exception"() {
        setup:
        payCentreReceiveHttpAjaxActionStub.tradeNo = "tradeNo"
        payCentreReceiveHttpAjaxActionStub.payMethod = ReceiveOrderPayChannel.POS_MACHINE.value()
        payCentreReceiveHttpAjaxActionStub.paymentChannel = 10;
        payCentreReceiveHttpAjaxActionStub.bankId = 11
        payCentreReceiveHttpAjaxActionStub.amount = "11.87"
        payCentreReceiveHttpAjaxActionStub.receiveDate = "20140731131313"

        when:
        payCentreReceiveHttpAjaxActionStub.webExecute();

        then:
        1 * payCentreReceiveRequestHandleServiceMock.handleReceiveRequest(_ as PayCentreReceiveRequestDTO) >> {
            throw new RuntimeException()
        }
    }
}
