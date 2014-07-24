package com.dianping.ba.finance.exchange.biz.impl

import com.dianping.ba.finance.exchange.api.dtos.PayCentreReceiveRequestDTO
import com.dianping.ba.finance.exchange.api.enums.BusinessType
import com.dianping.ba.finance.exchange.biz.service.BizInfoService
import com.dianping.core.type.PageModel
import com.dianping.midas.finance.api.dto.CorporationDTO
import com.dianping.midas.finance.api.service.CorporationService
import spock.lang.Specification

/**
 * Created by IntelliJ IDEA.
 * User: yaxiong.cheng
 * Date: 2014/7/24
 * Time: 11:22
 * To change this template use File | Settings | File Templates.
 */
class BizInfoServiceTest extends Specification {
    def serviceStub = new BizInfoService()
    private CorporationService corporationServiceMock

    void setup() {
        corporationServiceMock = Mock()
        serviceStub.setCorporationService(corporationServiceMock)
    }

    def "testgetBizInfo"() {
        setup:
        def requestDTO = new PayCentreReceiveRequestDTO()
        requestDTO.businessType = BusinessType.ADVERTISEMENT.value()

        when:
        serviceStub.getBizInfo(requestDTO)

        then:
        1 * corporationServiceMock.queryCorporationByBizContent(requestDTO.bizContent)
    }

    def "testgetBizInfoNoType"() {
        setup:
        def requestDTO = new PayCentreReceiveRequestDTO()

        when:
        serviceStub.getBizInfo(requestDTO)

        then:
        0 * corporationServiceMock.queryCorporationByBizContent(requestDTO.bizContent)
    }

    def "testgetBizInfoReturn"(Integer buType,Integer customId) {
        given:
        def requestDTO = new PayCentreReceiveRequestDTO()
        requestDTO.businessType = buType
        requestDTO.bizContent = "ttt"

        corporationServiceMock.queryCorporationByBizContent(_ as String) >> {
            def corporationDTO = [id:100] as CorporationDTO
            return corporationDTO
        }

        expect:
        corporationServiceMock.queryCorporationByBizContent(requestDTO.bizContent)
        customId == serviceStub.getBizInfo(requestDTO)

        where:
        buType ||customId
        1      || 0
        5      || 100
    }

}