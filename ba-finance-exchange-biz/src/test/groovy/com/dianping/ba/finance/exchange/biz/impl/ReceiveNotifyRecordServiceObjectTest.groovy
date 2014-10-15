package com.dianping.ba.finance.exchange.biz.impl
import com.dianping.ba.finance.exchange.api.ReceiveNotifyRecordService
import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyRecordData
import com.dianping.ba.finance.exchange.biz.dao.ReceiveNotifyRecordDao
import spock.lang.Specification
/**
 * Created by Administrator on 2014/7/25.
 */
class ReceiveNotifyRecordServiceObjectTest extends Specification {
    ReceiveNotifyRecordService receiveNotifyRecordServiceStub;
    ReceiveNotifyRecordDao receiveNotifyRecordDaoMock;

    def setup(){
        receiveNotifyRecordServiceStub = new ReceiveNotifyRecordServiceObject();
        receiveNotifyRecordDaoMock = Mock();
        receiveNotifyRecordServiceStub.receiveNotifyRecordDao = receiveNotifyRecordDaoMock;
    }

    def "InsertReceiveNotifyRecord"() {
        setup:
            ReceiveNotifyRecordData receiveNotifyRecordData = [];

        when:
            receiveNotifyRecordServiceStub.insertReceiveNotifyRecord(receiveNotifyRecordData);

        then:
            1 * receiveNotifyRecordDaoMock.insertReceiveNotifyRecord(_ as ReceiveNotifyRecordData);
    }

    def "changeCustomer"() {
        setup:
        when:
        receiveNotifyRecordServiceStub.changeCustomer(1, 123);
        then:
        1 * receiveNotifyRecordDaoMock.updateCustomerId(_ as Integer, _ as Integer);
    }
}
