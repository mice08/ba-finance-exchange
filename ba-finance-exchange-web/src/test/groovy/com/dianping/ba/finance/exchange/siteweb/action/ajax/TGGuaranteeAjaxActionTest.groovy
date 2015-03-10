package com.dianping.ba.finance.exchange.siteweb.action.ajax

import com.dianping.ba.finance.exchange.siteweb.beans.GuaranteeInfoBean
import com.dianping.ba.finance.exchange.siteweb.services.TGGuaranteeService
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by noahshen on 14-11-18.
 */
class TGGuaranteeAjaxActionTest extends Specification {

    TGGuaranteeAjaxAction tgGuaranteeAjaxActionStub

    TGGuaranteeService tgGuaranteeServiceMock

    void setup() {
        tgGuaranteeAjaxActionStub = []

        tgGuaranteeServiceMock = Mock()
        tgGuaranteeAjaxActionStub.tgGuaranteeService = tgGuaranteeServiceMock

    }

    @Unroll
    def "FindGuaranteeInfoByCustomerId"(Integer customerId, Integer resultSize) {
        given:
        tgGuaranteeAjaxActionStub.customerId = customerId
        tgGuaranteeServiceMock.getGuaranteeByCustomer(_ as Integer) >> { Integer custId ->
            if (custId == 8787) {
                return null
            }
            GuaranteeInfoBean guaranteeInfoBean = []
            guaranteeInfoBean.guaranteeBillId = "billId"
            guaranteeInfoBean.leftAmount = 87G
            [guaranteeInfoBean]
        }

        expect:
        tgGuaranteeAjaxActionStub.findGuaranteeInfoByCustomerId()
        resultSize == tgGuaranteeAjaxActionStub.msg["guarantee"]?.size()

        where:
        customerId | resultSize
        123        | 1
        8787       | 0
    }
}
