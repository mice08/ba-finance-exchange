package com.dianping.ba.finance.exchange.biz.impl
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData
import com.dianping.ba.finance.exchange.api.enums.BusinessType
import com.dianping.ba.finance.exchange.biz.service.BizInfoService
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
    private BizInfoService bizInfoServiceStub;
    private CorporationService corporationServiceMock

    void setup() {
        bizInfoServiceStub = []

        corporationServiceMock = Mock()
        bizInfoServiceStub.corporationService = corporationServiceMock;
    }

    def "GetBizInfoReturn"(Integer businessType, String bizContentParam, Integer customerId) {
        given:
        ReceiveOrderData roData = [businessType: businessType, bizContent: bizContentParam]
        corporationServiceMock.queryCorporationByBizContent(_ as String) >> { String bizContent ->
            CorporationDTO corporationDTO = [id: 100, name: bizContent + "-客户名称"]
            corporationDTO
        }

        expect:
        customerId == bizInfoServiceStub.getBizInfo(roData)?.customerId

        where:
        businessType                        | bizContentParam | customerId
        BusinessType.GROUP_PURCHASE.value() | null            | null
        BusinessType.GROUP_PURCHASE.value() | "团购合同123"       | null
        BusinessType.ADVERTISEMENT.value()  | "广告合同"          | 100
    }

}