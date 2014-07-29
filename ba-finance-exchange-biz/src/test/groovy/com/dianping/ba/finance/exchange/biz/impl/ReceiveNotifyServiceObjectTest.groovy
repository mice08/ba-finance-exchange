package com.dianping.ba.finance.exchange.biz.impl
import com.dianping.ba.finance.exchange.api.ReceiveNotifyService
import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData
import com.dianping.ba.finance.exchange.api.enums.BusinessType
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
    def "findUnmatchedLeftReceiveNotify"(ReceiveNotifyStatus status, Integer roId, String excludeApplicationId, Boolean noUnMatchedRN) {
        given:
        receiveNotifyDaoMock.findUnmatchedLeftReceiveNotify(_ as Integer, _ as Integer, _ as String) >> {
            ReceiveNotifyData rnData = [receiveNotifyId: 123,]
            [rnData]
        }

        expect:
        noUnMatchedRN == receiveNotifyServiceStub.findUnmatchedLeftReceiveNotify(status, roId, excludeApplicationId).isEmpty()

        where:
        status                      | roId | excludeApplicationId | noUnMatchedRN
        ReceiveNotifyStatus.MATCHED | 456|"123"                | false
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

    @Unroll
    def "loadUnmatchedReceiveNotifyByApplicationId"(ReceiveNotifyStatus status, Integer businessType, String applicationIdParam, Integer rnId, String applicationId) {
        given:
        receiveNotifyDaoMock.loadUnmatchedReceiveNotifyByApplicationId(_ as Integer, _ as Integer, _ as String) >> { args ->
            ReceiveNotifyData rnData = [receiveNotifyId: 123, businessType: args[1], applicationId: args[2]]
            rnData
        }

        expect:
        def rnData = receiveNotifyServiceStub.loadUnmatchedReceiveNotifyByApplicationId(status, businessType, applicationIdParam)
        rnId == rnData.receiveNotifyId
        applicationId == rnData.applicationId

        where:
        status                   | businessType                       | applicationIdParam | rnId | applicationId
        ReceiveNotifyStatus.INIT | BusinessType.ADVERTISEMENT.value() | "123"              | 123  | "123"
    }

    @Unroll
    def "findMatchedReceiveNotify"(Integer roId, Integer rnId) {
        given:
        receiveNotifyDaoMock.findMatchedReceiveNotify(ReceiveNotifyStatus.MATCHED.value(), _ as Integer) >> { args ->
            ReceiveNotifyData rnData = [receiveNotifyId: 123, status: args[0], roMatcherId: args[1]]
            [rnData]
        }

        expect:
        rnId == receiveNotifyServiceStub.findMatchedReceiveNotify(roId)[0].receiveNotifyId;

        where:
        roId | rnId
        123  | 123
    }

    @Unroll
    def "removeReceiveNotifyMatchRelation"(Integer rnId, Integer roId, Boolean updated) {
        given:
        receiveNotifyDaoMock.removeReceiveNotifyMatchRelation(_ as Integer, _ as Integer, ReceiveNotifyStatus.INIT.value()) >> {
            1
        }

        expect:
        updated == receiveNotifyServiceStub.removeReceiveNotifyMatchRelation(rnId, roId);

        where:
        rnId | roId | updated
        123  | 123  | true
    }

    @Unroll
    def "loadMatchedReceiveNotify"(Integer rnId, Integer roId, Integer rnIdResult) {
        given:
        receiveNotifyDaoMock.loadMatchedReceiveNotify(ReceiveNotifyStatus.INIT.value(), _ as Integer, _ as Integer) >> { args ->
            ReceiveNotifyData rnData = [receiveNotifyId: args[1], roMatcherId: args[2]]
            rnData
        }

        expect:
        rnIdResult == receiveNotifyServiceStub.loadMatchedReceiveNotify(rnId, roId).receiveNotifyId;

        where:
        rnId | roId | rnIdResult
        123  | 467  | 123
    }

    @Unroll
    def "updateReceiveNotifyConfirm"(Integer roId, Integer rnId, Boolean updated) {
        given:
        receiveNotifyDaoMock.updateReceiveNotifyConfirm(ReceiveNotifyStatus.CONFIRMED.value(), ReceiveNotifyStatus.INIT.value(), _ as Integer, _ as Integer) >> { args ->
            1
        }

        expect:
        updated == receiveNotifyServiceStub.updateReceiveNotifyConfirm(roId, rnId);

        where:
        roId | rnId | updated
        123  | 467  | true
    }
}
