package com.dianping.ba.finance.exchange.biz.impl

import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData
import com.dianping.ba.finance.exchange.biz.dao.ReceiveOrderDao
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by adam.huang on 2014/8/18.
 */
class ReceiveOrderQueryServiceObjectTest extends Specification {

    ReceiveOrderMonitorServiceObject receiveOrderQueryServiceObjectStub;
    ReceiveOrderDao receiveOrderDaoMock;

    public void setup(){
        receiveOrderQueryServiceObjectStub = new ReceiveOrderMonitorServiceObject();
        receiveOrderDaoMock = Mock();
        receiveOrderQueryServiceObjectStub.receiveOrderDao = receiveOrderDaoMock;
    }

    @Unroll
    def "LoadReceiveNotifyDTOById"(int paramRoId, int expectRoId) {
        given:
        receiveOrderDaoMock.loadReceiveOrderDataByRoId(_ as Integer)>>{ args ->
            ReceiveOrderData receiveOrderData = ["roId":args[0]];
            receiveOrderData;
        }
        expect:
        expectRoId == receiveOrderQueryServiceObjectStub.loadReceiveOrderMonitorDTOById(paramRoId).getRoId();
        where:
        paramRoId  ||  expectRoId
        1          ||  1
        2          ||  2
    }
}
