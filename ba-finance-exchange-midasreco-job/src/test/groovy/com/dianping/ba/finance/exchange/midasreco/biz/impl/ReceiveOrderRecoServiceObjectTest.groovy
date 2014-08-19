package com.dianping.ba.finance.exchange.midasreco.biz.impl

import com.dianping.ba.finance.exchange.midasreco.api.datas.ReceiveOrderRecoData
import com.dianping.ba.finance.exchange.midasreco.biz.dao.ReceiveOrderRecoDao
import spock.lang.Specification

/**
 * Created by adam.huang on 2014/8/19.
 */
class ReceiveOrderRecoServiceObjectTest extends Specification {

    ReceiveOrderRecoServiceObject receiveOrderRecoServiceObjectStub;
    ReceiveOrderRecoDao receiveOrderRecoDaoMock;

    public void setup(){
        receiveOrderRecoServiceObjectStub = new ReceiveOrderRecoServiceObject();
        receiveOrderRecoDaoMock = Mock();
        receiveOrderRecoServiceObjectStub.receiveOrderRecoDao = receiveOrderRecoDaoMock;
    }

    def "InsertReceiveOrderRecoDatas"() {
        setup:
        ReceiveOrderRecoData receiveOrderRecoData = [];
        when:
        receiveOrderRecoServiceObjectStub.insertReceiveOrderRecoDatas([receiveOrderRecoData]);
        then:
        1 * receiveOrderRecoDaoMock.insertReceiveOrderRecoDatas(_ as List);
    }

    def "Insert Empty List"() {
        setup:
        when:
        receiveOrderRecoServiceObjectStub.insertReceiveOrderRecoDatas([]);
        then:
        0 * receiveOrderRecoDaoMock.insertReceiveOrderRecoDatas(_ as List);
    }
}
