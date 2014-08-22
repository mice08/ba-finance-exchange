package com.dianping.ba.finance.exchange.biz.impl
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData
import com.dianping.ba.finance.exchange.api.dtos.ReceiveOrderMonitorSearchDTO
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
        receiveOrderDaoMock.findReceiveOrderDataByTime(_ as Date, _ as Date, _ as Integer)>>{
            ReceiveOrderData receiveOrderData = ["roId":paramRoId];
            [receiveOrderData];
        }
        ReceiveOrderMonitorSearchDTO receiveOrderMonitorSearchDTO = ["startTime":new Date(),"endTime":new Date(),"businessType":1];
        expect:
        expectRoId == receiveOrderQueryServiceObjectStub.findReceiveOrderMonitorDataByTime(receiveOrderMonitorSearchDTO)[0].getRoId();
        where:
        paramRoId  ||  expectRoId
        1          ||  1
        2          ||  2
    }

    def "insertReceiveOrderRecoDatas"(){
        setup:
        ReceiveOrderMonitorSearchDTO receiveOrderMonitorSearchDTO = ["startTime":new Date(),"endTime":new Date(),"businessType":1];
        when:
        receiveOrderQueryServiceObjectStub.insertReceiveOrderRecoDatas(receiveOrderMonitorSearchDTO);
        then:
        1 * receiveOrderDaoMock.findReceiveOrderDataByTime(_ as Date, _ as Date, _ as Integer)>>{
            ReceiveOrderData receiveOrderData = [];
            [receiveOrderData];
        }

        1 * receiveOrderRecoDaoMock.insertReceiveOrderRecoDatas(_ as List);
    }

    def "insert empty list"(){
        setup:
        ReceiveOrderMonitorSearchDTO receiveOrderMonitorSearchDTO = ["startTime":new Date(),"endTime":new Date(),"businessType":1];
        when:
        receiveOrderQueryServiceObjectStub.insertReceiveOrderRecoDatas(receiveOrderMonitorSearchDTO);
        then:
        1 * receiveOrderDaoMock.findReceiveOrderDataByTime(_ as Date, _ as Date, _ as Integer)>>{
            [];
        }

        0 * receiveOrderRecoDaoMock.insertReceiveOrderRecoDatas(_ as List);
    }
}
