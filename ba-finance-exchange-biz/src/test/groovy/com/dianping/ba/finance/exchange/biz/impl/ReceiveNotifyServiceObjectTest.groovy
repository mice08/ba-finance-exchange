package com.dianping.ba.finance.exchange.biz.impl

import com.dianping.ba.finance.exchange.api.ReceiveNotifyService
import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData
import com.dianping.ba.finance.exchange.biz.dao.ReceiveNotifyDao
import spock.lang.Specification

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
}
