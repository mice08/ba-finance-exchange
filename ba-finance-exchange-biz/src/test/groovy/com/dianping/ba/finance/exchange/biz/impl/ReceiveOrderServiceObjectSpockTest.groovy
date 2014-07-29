package com.dianping.ba.finance.exchange.biz.impl

import com.dianping.ba.finance.exchange.api.RORNMatchFireService
import com.dianping.ba.finance.exchange.api.ReceiveNotifyService
import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData
import com.dianping.ba.finance.exchange.api.enums.ReceiveOrderStatus
import com.dianping.ba.finance.exchange.biz.dao.ReceiveOrderDao
import com.dianping.ba.finance.exchange.biz.producer.ReceiveOrderResultNotify
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
    }

    @Unroll
    def "ConfirmReceiveOrderAndReceiveNotify"(Integer roId, Integer rnId, Boolean updated) {
        given:
        receiveNotifyServiceMock.loadMatchedReceiveNotify(_ as Integer, _ as Integer) >> { args ->
            if (args[0] == 87871) {
                return null
            }
            ReceiveNotifyData rnData = [receiveNotifyId: args[0], roMatcherId: args[1]]
            rnData
        }
        receiveOrderDaoMock.loadReceiveOrderDataByRoId(_ as Integer) >> { Integer roId2 ->
            if (roId2 == 87872) {
                return null
            }
            ReceiveOrderData roData = [roId: roId2, status: ReceiveOrderStatus.UNCONFIRMED.value()]
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
}
