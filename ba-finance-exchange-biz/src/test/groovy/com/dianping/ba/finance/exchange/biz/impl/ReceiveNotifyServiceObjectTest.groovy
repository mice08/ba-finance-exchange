package com.dianping.ba.finance.exchange.biz.impl

import com.dianping.ba.finance.exchange.api.ReceiveNotifyService
import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData
import com.dianping.ba.finance.exchange.api.enums.ReceiveNotifyResultStatus
import com.dianping.ba.finance.exchange.api.enums.ReceiveNotifyStatus
import com.dianping.ba.finance.exchange.biz.dao.ReceiveNotifyDao
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by Administrator on 2014/7/25.
 */
class ReceiveNotifyServiceObjectTest extends Specification {
    ReceiveNotifyService receiveNotifyServiceStub;
    ReceiveNotifyDao receiveNotifyDaoMock;

    def setup(){
        receiveNotifyServiceStub = new ReceiveNotifyServiceObject();
        receiveNotifyDaoMock = Mock();
        receiveNotifyServiceStub.receiveNotifyDao = receiveNotifyDaoMock;
    }

    def "InsertReceiveNotify"() {
        setup:
        ReceiveNotifyData receiveNotifyData = [];

        when:
        receiveNotifyServiceStub.insertReceiveNotify(receiveNotifyData);

        then:
        1 * receiveNotifyDaoMock.insertReceiveNotify(_ as ReceiveNotifyData);
    }

    @Unroll
    def "updateReceiveNotifyMatchId"(Integer rnIdParam, Boolean updated) {
        given:
        receiveNotifyDaoMock.updateReceiveNotifyMatchId(_ as Integer, _ as Integer, _ as Integer, _ as Integer) >> { args ->
            def rnId = args[3];
            if (rnId == 123) {
                return 1;
            }
            0
        }

        expect:
        updated == receiveNotifyServiceStub.updateReceiveNotifyMatchId(ReceiveNotifyStatus.MATCHED.value(), 8787, ReceiveNotifyStatus.INIT.value(), rnIdParam)

        where:
        rnIdParam | updated
        8787      | false
        123       | true
    }

    @Unroll
    def "getUnMatchedReceiveNotify"(ReceiveNotifyStatus status, Boolean noUnMatchedRN) {
        given:
        receiveNotifyDaoMock.getUnMatchedReceiveNotify(_ as Integer) >> { Integer s ->
            if (s == ReceiveNotifyStatus.CONFIRMED.value()) {
                return []
            }
            ReceiveNotifyData rnData = [receiveNotifyId: 123]
            [rnData]
        }

        expect:
        noUnMatchedRN == receiveNotifyServiceStub.getUnMatchedReceiveNotify(status).isEmpty()

        where:
        status                        | noUnMatchedRN
        ReceiveNotifyStatus.INIT      | false
        ReceiveNotifyStatus.CONFIRMED | true
    }

    @Unroll
    def "findUnmatchedLeftReceiveNotify"(ReceiveNotifyStatus status, String excludeApplicationId, Boolean noUnMatchedRN) {
        given:
        receiveNotifyDaoMock.findUnmatchedLeftReceiveNotify(_ as Integer, _ as String) >> {
            ReceiveNotifyData rnData = [receiveNotifyId: 123]
            [rnData]
        }

        expect:
        noUnMatchedRN == receiveNotifyServiceStub.findUnmatchedLeftReceiveNotify(status, excludeApplicationId).isEmpty()

        where:
        status                      | excludeApplicationId | noUnMatchedRN
        ReceiveNotifyStatus.MATCHED | "123"                | false
    }

    @Unroll
    def "clearReceiveNotifyMatchInfo"(ReceiveNotifyStatus status, Integer rnId, Integer updatedRows) {
        given:
        def rnIdList = [rnId]
        receiveNotifyDaoMock.clearReceiveNotifyMatchInfo(_ as Integer, _ as List) >> {
            10
        }

        expect:
        updatedRows == receiveNotifyServiceStub.clearReceiveNotifyMatchInfo(status, rnIdList)

        where:
        status                   | rnId | updatedRows
        ReceiveNotifyStatus.INIT | 123  | 10
    }
}
