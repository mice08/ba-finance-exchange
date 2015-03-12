package com.dianping.ba.finance.exchange.siteweb.services

import com.dianping.ts.treasure.pool.api.DeductionRuleService
import com.dianping.ts.treasure.pool.api.dtos.guaranteeform.GuaranteeWholeInfoDTO
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by noahshen on 14-11-17.
 */
class TGGuaranteeServiceTest extends Specification {

    TGGuaranteeService tgGuaranteeServiceStub

    DeductionRuleService deductionRuleServiceMock

    void setup() {
        tgGuaranteeServiceStub = []

        deductionRuleServiceMock = Mock()
        tgGuaranteeServiceStub.deductionRuleService = deductionRuleServiceMock
    }

    @Unroll
    def "GetGuaranteeByCustomer"(Integer customerId, Integer resultSize) {
        given:
        deductionRuleServiceMock.getGuaranteeByCustomer(_ as Integer) >> { Integer custId ->
            if (custId == 8787) {
                return null
            }
            GuaranteeWholeInfoDTO guaranteeWholeInfoDTO = []
            [guaranteeWholeInfoDTO]
        }

        expect:
        resultSize == tgGuaranteeServiceStub.getGuaranteeByCustomer(customerId)?.size()

        where:
        customerId | resultSize
        123        | 1
        8787       | 0
    }
}
