package com.dianping.ba.finance.exchange.biz.impl

import com.dianping.ba.finance.exchange.api.ReceiveBankService
import com.dianping.ba.finance.exchange.api.ReceiveOrderService
import com.dianping.ba.finance.exchange.api.beans.ReceiveOrderSearchBean
import com.dianping.ba.finance.exchange.api.beans.ReceiveVoucherNotifyBean
import com.dianping.ba.finance.exchange.api.datas.ReceiveBankData
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

    ReceiveBankService receiveBankServiceMock;

    void setup() {
        receiveVoucherServiceObjectStub = []
        receiveOrderServiceMock = Mock()
        receiveVoucherServiceObjectStub.receiveOrderService = receiveOrderServiceMock

        receiveVoucherDaoMock = Mock()
        receiveVoucherServiceObjectStub.receiveVoucherDao = receiveVoucherDaoMock

        receivePayVoucherNotifyMock = Mock()
        receiveVoucherServiceObjectStub.receivePayVoucherNotify = receivePayVoucherNotifyMock

        receiveBankServiceMock = Mock()
        receiveVoucherServiceObjectStub.receiveBankService = receiveBankServiceMock
    }

    def "GenerateUnconfirmedReceiveVoucher For AD"() {
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
        1 * receiveBankServiceMock.loadReceiveBankByBankId(_ as Integer) >> { Integer bankId ->
            ReceiveBankData receiveBankData = [bankId: bankId, companyId: 8]
            receiveBankData
        }
        2 * receiveVoucherDaoMock.insertReceiveVoucherData(_ as ReceiveVoucherData) >> {
            id++
        }
        2 * receivePayVoucherNotifyMock.notifyVoucher(_ as ReceiveVoucherNotifyBean)
    }

    def "GenerateUnconfirmedReceiveVoucher For TG"() {
        setup:
        Date generateDate = new Date();

        when:
        receiveVoucherServiceObjectStub.generateUnconfirmedReceiveVoucher(generateDate)

        then:
        1 * receiveOrderServiceMock.findCalculatedReceiveResult(_ as ReceiveOrderSearchBean) >> {
            ReceiveCalResultData receiveCalResultData = [customerId: 123,
                                                         shopId:123,
                                                         businessType: BusinessType.GROUP_PURCHASE.value(),
                                                         totalAmount: BigDecimal.TEN,
                                                         payChannel: ReceiveOrderPayChannel.POS_MACHINE.value(),
                                                         receiveType: ReceiveType.AD_FEE.value(),
                                                         bankId: 8,
                                                         voucherDate: new Date()]
            [receiveCalResultData]
        }
        1 * receiveBankServiceMock.loadReceiveBankByBankId(_ as Integer) >> { Integer bankId ->
            ReceiveBankData receiveBankData = [bankId: bankId, companyId: 8]
            receiveBankData
        }
        0 * receiveVoucherDaoMock.insertReceiveVoucherData(_ as ReceiveVoucherData) >> {
            id++
        }
        0 * receivePayVoucherNotifyMock.notifyVoucher(_ as ReceiveVoucherNotifyBean)
    }
}
