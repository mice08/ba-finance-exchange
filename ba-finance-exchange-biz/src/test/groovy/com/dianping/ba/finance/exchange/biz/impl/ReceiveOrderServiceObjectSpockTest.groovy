package com.dianping.ba.finance.exchange.biz.impl
import com.dianping.ba.finance.exchange.api.RORNMatchFireService
import com.dianping.ba.finance.exchange.api.RORNMatchService
import com.dianping.ba.finance.exchange.api.ReceiveNotifyService
import com.dianping.ba.finance.exchange.api.beans.BizInfoBean
import com.dianping.ba.finance.exchange.api.beans.ReceiveOrderResultBean
import com.dianping.ba.finance.exchange.api.beans.ReceiveOrderSearchBean
import com.dianping.ba.finance.exchange.api.beans.ReceiveOrderUpdateBean
import com.dianping.ba.finance.exchange.api.datas.ReceiveCalResultData
import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData
import com.dianping.ba.finance.exchange.api.enums.*
import com.dianping.ba.finance.exchange.biz.dao.ReceiveOrderDao
import com.dianping.ba.finance.exchange.biz.producer.ReceiveOrderResultNotify
import com.dianping.ba.finance.exchange.biz.service.BizInfoService
import spock.lang.Specification
import spock.lang.Unroll
/**
 * Created by noahshen on 14-7-28.
 */
class ReceiveOrderServiceObjectSpockTest extends Specification {

    private ReceiveOrderServiceObject receiveOrderServiceObjectStub;

    private ReceiveOrderDao receiveOrderDaoMock;

    private RORNMatchFireService rornMatchFireServiceMock;

    private ReceiveOrderResultNotify receiveOrderResultNotifyMock;

    private ReceiveNotifyService receiveNotifyServiceMock;

    private RORNMatchService rornMatchServiceMock;

    private BizInfoService bizInfoServiceMock;


    void setup() {
        receiveOrderServiceObjectStub = []

        receiveOrderDaoMock = Mock()
        receiveOrderServiceObjectStub.receiveOrderDao = receiveOrderDaoMock;

        rornMatchFireServiceMock = Mock()
        receiveOrderServiceObjectStub.rornMatchFireService = rornMatchFireServiceMock;

        receiveOrderResultNotifyMock = Mock()
        receiveOrderServiceObjectStub.receiveOrderResultNotify = receiveOrderResultNotifyMock

        receiveNotifyServiceMock = Mock()
        receiveOrderServiceObjectStub.receiveNotifyService = receiveNotifyServiceMock;

        rornMatchServiceMock = Mock();
        receiveOrderServiceObjectStub.rornMatchService = rornMatchServiceMock;

        bizInfoServiceMock = Mock();
        receiveOrderServiceObjectStub.bizInfoService = bizInfoServiceMock;
    }

    @Unroll
    def "ConfirmReceiveOrderAndReceiveNotify"(Integer roId, Integer rnId, Boolean updated) {
        given:
        receiveNotifyServiceMock.loadMatchedReceiveNotify(_ as Integer, _ as Integer) >> { args ->
            if (args[0] == 87871) {
                return null
            }
            ReceiveNotifyData rnData = [receiveNotifyId: args[0],
                                        roMatcherId    : args[1],
                                        bizContent     : "AD123",
                                        customerId     : 123,
                                        receiveType    : ReceiveType.AD_FEE.value()]
            rnData
        }
        receiveOrderDaoMock.loadReceiveOrderDataByRoId(_ as Integer) >> { Integer roId2 ->
            if (roId2 == 87872) {
                return null
            }
            ReceiveOrderData roData = [roId       : roId2,
                                       status     : ReceiveOrderStatus.UNCONFIRMED.value(),
                                       receiveTime: new Date(),
                                       receiveType: ReceiveType.AD_FEE.value()]
            roData
        }
        receiveNotifyServiceMock.updateReceiveNotifyConfirm(_ as Integer, _ as Integer) >> { args ->
            return args[0] == 87873
        }
        receiveOrderDaoMock.updateReceiveOrder(_ as ReceiveOrderData) >> {
            1
        }

        expect:
        updated == receiveOrderServiceObjectStub.confirmReceiveOrderAndReceiveNotify(roId, rnId, -1)

        where:
        roId  | rnId   | updated
        123   | 87871  | false
        87872 | 123    | false
        87874 | 123    | false
        87873 | 123456 | true
    }

    @Unroll
    def "manuallyUpdateReceiveOrder"(ReceiveOrderStatus status, String applicationId, Boolean updated) {
        given:
        ReceiveOrderUpdateBean receiveOrderUpdateBean = [status       : status.value(),
                                                         applicationId: applicationId,
                                                         receiveTime  : new Date(),
                                                         customerId   : 10,
                                                         bizContent   : "bizContent",
                                                         receiveType  : ReceiveType.AD_FEE]

        receiveOrderDaoMock.loadReceiveOrderDataByRoId(_ as Integer) >> { Integer roId2 ->
            if (roId2 == 87872) {
                return null
            }
            ReceiveOrderData roData = [roId       : roId2,
                                       status     : ReceiveOrderStatus.UNCONFIRMED.value(),
                                       receiveTime: new Date(),
                                       receiveType: ReceiveType.AD_FEE.value()]
            roData
        }
        receiveNotifyServiceMock.loadUnmatchedReceiveNotifyByApplicationId(ReceiveNotifyStatus.INIT , _ as Integer, _ as String) >> {
            ReceiveNotifyData receiveNotifyData = []
            receiveNotifyData
        }

        rornMatchServiceMock.doMatch(_ as ReceiveOrderData, _ as ReceiveNotifyData) >> true;

        receiveNotifyServiceMock.updateReceiveNotifyConfirm(_ as Integer, _ as Integer) >> true;

        receiveOrderDaoMock.updateReceiveOrder(_ as ReceiveOrderData) >> 1

        expect:
        updated == receiveOrderServiceObjectStub.manuallyUpdateReceiveOrder(receiveOrderUpdateBean)

        where:
        status                         | applicationId | updated
        ReceiveOrderStatus.UNCONFIRMED | ""            | true
        ReceiveOrderStatus.CONFIRMED   | ""            | true
        ReceiveOrderStatus.CONFIRMED   | "123"         | true

    }

    def "updateReceiveOrderConfirm" (ReceiveOrderStatus status,Integer resultInt) {
        given:
        ReceiveOrderUpdateBean receiveOrderUpdateBean = [status       : status.value(),
                                                         receiveTime  : new Date(),
                                                         customerId   : 10,
                                                         receiveType  : ReceiveType.TG_GUARANTEE,
                                                         roId: 1234]
        receiveOrderDaoMock.loadReceiveOrderDataByRoId(_ as Integer) >> { Integer roId2 ->
            ReceiveOrderData roData = [roId       : roId2,
                                       businessType:BusinessType.GROUP_PURCHASE.value(),
                                       receiveType  : ReceiveType.TG_GUARANTEE.value()]
            roData
        }
        receiveOrderResultNotifyMock.receiveResultNotify(_ as ReceiveOrderResultBean) >> {};

        receiveOrderDaoMock.updateReceiveOrder(_ as ReceiveOrderData) >> 1
        expect:
        resultInt == receiveOrderServiceObjectStub.updateReceiveOrderConfirm(receiveOrderUpdateBean)

        where:
        status                         | resultInt
        ReceiveOrderStatus.CONFIRMED   | 1
    }

    def "findCalculatedReceiveResult" (ReceiveOrderStatus status, Date date, Integer resultSize) {
        given:
        ReceiveOrderSearchBean searchBean = [status      : status.value(),
                                             addTimeBegin: date,
                                             addTimeEnd  : date,
                                             payChannel  : ReceiveOrderPayChannel.POS_MACHINE.value()]

        receiveOrderDaoMock.findCalculatedReceiveResult(_ as ReceiveOrderSearchBean, _ as String) >> { args ->
            ReceiveCalResultData rcData = [customerId  : 123,
                                           businessType: BusinessType.GROUP_PURCHASE.value(),
                                           totalAmount : BigDecimal.TEN]
            [rcData]
        }

        expect:
        resultSize == receiveOrderServiceObjectStub.findCalculatedReceiveResult(searchBean)?.size()

        where:
        status                         | date       | resultSize
        ReceiveOrderStatus.UNCONFIRMED | new Date() | 1
    }

    def "cancelReceiveOrder"(){
        setup:
        int roId = 1;
        when:
        receiveOrderServiceObjectStub.cancelReceiveOrder(roId);
        then:
        1 * receiveOrderDaoMock.updateReceiveOrderByRoId(_ as Integer, _ as ReceiveOrderUpdateBean);

        1 * receiveNotifyServiceMock.findMatchedReceiveNotify(_ as Integer)>>{
            ReceiveNotifyData receiveNotifyData1 = [];
            ReceiveNotifyData receiveNotifyData2 = [];
            [receiveNotifyData1, receiveNotifyData2];
        }

        2 * receiveNotifyServiceMock.removeReceiveNotifyMatchRelation(_ as Integer, _ as Integer);

        2 * rornMatchFireServiceMock.executeMatchingForNewReceiveNotify(_ as ReceiveNotifyData);
    }

    @Unroll
    def "fireAutoConfirm"(ReceiveOrderStatus status, Integer bankId, BusinessType businessType, Boolean result){
        given:
        ReceiveOrderSearchBean searchBean = [status      : status.value(),
                                             bankId      : bankId,
                                             businessType: businessType.value()]

        receiveOrderDaoMock.findReceiveOrderBySearchBean(_ as ReceiveOrderSearchBean) >> { ReceiveOrderSearchBean b ->
            if (b.businessType == BusinessType.GROUP_PURCHASE.value()) {
                return null;
            }
            ReceiveOrderData roData = [roId        : 123,
                                       status      : status.value(),
                                       businessType: BusinessType.ADVERTISEMENT.value(),
                                       receiveType : ReceiveType.AD_FEE.value()]
            if (b.bankId == 8) {
                roData.bizContent = "AD123"
            } else if (b.bankId == 1) {
                roData.bizContent = "AD456"
            }

            [roData]
        }
        bizInfoServiceMock.getBizInfo(_ as ReceiveOrderData) >> { ReceiveOrderData roDataForBiz ->
            if (roDataForBiz.bizContent == "AD456") {
                return null
            }
            BizInfoBean bizInfoBean = new BizInfoBean(customerId: 123)
            bizInfoBean
        }

        receiveNotifyServiceMock.findMatchedReceiveNotify(_ as Integer) >> {
            ReceiveNotifyData rnData = [receiveNotifyId: 789]
            [rnData]
        }
        receiveOrderDaoMock.updateReceiveOrder(_ as ReceiveOrderData) >> {
            1
        }
        receiveOrderDaoMock.loadReceiveOrderDataByRoId(_ as Integer) >> {
            ReceiveOrderData roData = [roId        : 123,
                                       status      : status.value(),
                                       businessType: BusinessType.ADVERTISEMENT.value(),
                                       receiveTime : new Date(),
                                       customerId  : 123,
                                       bizContent  : "AD",
                                       receiveType : ReceiveType.AD_FEE.value()]
            roData
        }


        expect:
        result == receiveOrderServiceObjectStub.fireAutoConfirm(searchBean)

        where:
        status                       | bankId | businessType                | result
        ReceiveOrderStatus.CONFIRMED | 8      | BusinessType.GROUP_PURCHASE | true
        ReceiveOrderStatus.CONFIRMED | 7      | BusinessType.ADVERTISEMENT | true
        ReceiveOrderStatus.CONFIRMED | 1      | BusinessType.ADVERTISEMENT | true
        ReceiveOrderStatus.CONFIRMED | 8      | BusinessType.ADVERTISEMENT | true
    }

    def "findReceiverOrderList"() {
        setup:
        ReceiveOrderSearchBean receiveOrderSearchBean = [];
        when:
        receiveOrderServiceObjectStub.findReceiverOrderList(receiveOrderSearchBean);
        then:
        1 * receiveOrderDaoMock.findReceiveOrderBySearchBean(_ as ReceiveOrderSearchBean);
    }

    def "changeCustomer"() {
        setup:
        when:
        receiveOrderServiceObjectStub.changeCustomer(1, 124);
        then:
        1 * receiveOrderDaoMock.updateCustomerId(_ as Integer, _ as Integer);
    }
}
