package com.dianping.ba.finance.exchange.biz.impl

import com.dianping.ba.finance.exchange.api.ReceiveOrderService
import com.dianping.ba.finance.exchange.api.beans.ReceiveOrderSearchBean
import com.dianping.ba.finance.exchange.api.beans.ReceiveVoucherNotifyBean
import com.dianping.ba.finance.exchange.api.datas.ReceiveCalResultData
import com.dianping.ba.finance.exchange.api.datas.ReceiveVoucherData
import com.dianping.ba.finance.exchange.api.enums.BusinessType
import com.dianping.ba.finance.exchange.api.enums.ReceiveOrderPayChannel
import com.dianping.ba.finance.exchange.api.enums.ReceiveType
import com.dianping.ba.finance.exchange.biz.dao.ReceiveVoucherDao
import com.dianping.ba.finance.exchange.biz.producer.ReceivePayVoucherNotify
import spock.lang.Specification

/**
 * Created by noahshen on 14-8-22.
 */
class ReceiveVoucherServiceObjectTest extends Specification {

    static Integer id = 1;

    ReceiveVoucherServiceObject receiveVoucherServiceObjectStub;

    ReceiveOrderService receiveOrderServiceMock;

    ReceiveVoucherDao receiveVoucherDaoMock;

    ReceivePayVoucherNotify receivePayVoucherNotifyMock;

    void setup() {
        receiveVoucherServiceObjectStub = []
        receiveOrderServiceMock = Mock()
        receiveVoucherServiceObjectStub.receiveOrderService = receiveOrderServiceMock

        receiveVoucherDaoMock = Mock()
        receiveVoucherServiceObjectStub.receiveVoucherDao = receiveVoucherDaoMock

        receivePayVoucherNotifyMock = Mock()
        receiveVoucherServiceObjectStub.receivePayVoucherNotify = receivePayVoucherNotifyMock

    }

    def "GenerateUnconfirmedReceiveVoucher"() {
        setup:
        Date generateDate = new Date();

        when:
        receiveVoucherServiceObjectStub.generateUnconfirmedReceiveVoucher(generateDate)

        then:
        1 * receiveOrderServiceMock.findCalculatedReceiveResult(_ as ReceiveOrderSearchBean) >> {
            ReceiveCalResultData receiveCalResultData = [customerId: 123,
                                                         shopId:123,
                                                         businessType: BusinessType.ADVERTISEMENT.value(),
                                                         totalAmount: BigDecimal.TEN,
                                                         payChannel: ReceiveOrderPayChannel.POS_MACHINE.value(),
                                                         receiveType: ReceiveType.AD_FEE.value(),
                                                         bankId: 8,
                                                         voucherDate: new Date()]
            [receiveCalResultData]
        }
        2 * receiveVoucherDaoMock.insertReceiveVoucherData(_ as ReceiveVoucherData) >> {
            id++
        }
        2 * receivePayVoucherNotifyMock.notifyVoucher(_ as ReceiveVoucherNotifyBean)
    }
}
