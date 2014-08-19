package com.dianping.ba.finance.exchange.biz.impl
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData
import com.dianping.ba.finance.exchange.biz.dao.ReceiveOrderDao
import com.dianping.ba.finance.exchange.biz.dao.ReceiveOrderRecoDao
import spock.lang.Specification
import spock.lang.Unroll
/**
 * Created by adam.huang on 2014/8/18.
 */
class ReceiveOrderMonitorServiceObjectTest extends Specification {

    ReceiveOrderMonitorServiceObject receiveOrderQueryServiceObjectStub;
    ReceiveOrderDao receiveOrderDaoMock;
    ReceiveOrderRecoDao receiveOrderRecoDaoMock;

    public void setup(){
        receiveOrderQueryServiceObjectStub = new ReceiveOrderMonitorServiceObject();
        receiveOrderDaoMock = Mock();
        receiveOrderQueryServiceObjectStub.receiveOrderDao = receiveOrderDaoMock;
        receiveOrderRecoDaoMock = Mock();
        receiveOrderQueryServiceObjectStub.receiveOrderRecoDao = receiveOrderRecoDaoMock;
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

    @Unroll
    def "findReceiveOrderMonitorDataByTime"(int paramRoId, int expectRoId){
        given:
        receiveOrderDaoMock.findReceiveOrderDataByTime(_ as Date, _ as Date)>>{
            ReceiveOrderData receiveOrderData = ["roId":paramRoId];
            [receiveOrderData];
        }
        expect:
        expectRoId == receiveOrderQueryServiceObjectStub.findReceiveOrderMonitorDataByTime(new Date(),new Date())[0].getRoId();
        where:
        paramRoId  ||  expectRoId
        1          ||  1
        2          ||  2
    }

    def "insertReceiveOrderRecoDatas"(){
        setup:
        when:
        receiveOrderQueryServiceObjectStub.insertReceiveOrderRecoDatas(new Date(), new Date());
        then:
        1 * receiveOrderDaoMock.findReceiveOrderDataByTime(_ as Date, _ as Date)>>{
            ReceiveOrderData receiveOrderData = [];
            [receiveOrderData];
        }

        1 * receiveOrderRecoDaoMock.insertReceiveOrderRecoDatas(_ as List);
    }

    def "insert empty list"(){
        setup:
        when:
        receiveOrderQueryServiceObjectStub.insertReceiveOrderRecoDatas(new Date(), new Date());
        then:
        1 * receiveOrderDaoMock.findReceiveOrderDataByTime(_ as Date, _ as Date)>>{
            [];
        }

        0 * receiveOrderRecoDaoMock.insertReceiveOrderRecoDatas(_ as List);
    }
}
