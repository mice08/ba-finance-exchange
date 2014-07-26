package com.dianping.ba.finance.exchange.biz.rornmatcher

import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData
import com.dianping.ba.finance.exchange.api.enums.ReceiveOrderPayChannel
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by noahshen on 14-7-26.
 */
class RORNMatcherPosTest extends Specification {

    private RORNMatcherPos rornMatcherPosStub;

    void setup() {
        rornMatcherPosStub = []
    }

    @Unroll
    def "Match"(Integer roPayChannel, Integer rnPayChannel,
                Date roBankReceiveTime, Date rnPayTime,
                BigDecimal roAmount, BigDecimal rnAmount,
                Integer roBankId, Integer rnBankId,
                Boolean match) {

        given:
        ReceiveOrderData roData = [payChannel   : roPayChannel,
                                   bankReceiveTime: roBankReceiveTime,
                                   receiveAmount: roAmount,
                                   bankID       : roBankId]

        ReceiveNotifyData rnData = [payChannel   : rnPayChannel,
                                    payTime      : rnPayTime,
                                    receiveAmount: rnAmount,
                                    bankId       : rnBankId]

        expect:
        match == rornMatcherPosStub.match(roData, rnData)


        where:
        roPayChannel                               | rnPayChannel                               | roBankReceiveTime                      | rnPayTime                              | roAmount | rnAmount | roBankId | rnBankId | match
        ReceiveOrderPayChannel.CASH.value()        | ReceiveOrderPayChannel.CASH.value()        | null                                   | null                                   | null     | null     | 0        | 0        | false
        ReceiveOrderPayChannel.POS_MACHINE.value() | ReceiveOrderPayChannel.CASH.value()        | null                                   | null                                   | null     | null     | 0        | 0        | false
        ReceiveOrderPayChannel.POS_MACHINE.value() | ReceiveOrderPayChannel.POS_MACHINE.value() | null                                   | null                                   | null     | null     | 0        | 0        | false
        ReceiveOrderPayChannel.POS_MACHINE.value() | ReceiveOrderPayChannel.POS_MACHINE.value() | Date.parse("yyyy-MM-dd", "2014-07-26") | Date.parse("yyyy-MM-dd", "2014-07-25") | null     | null     | 0        | 0        | false
        ReceiveOrderPayChannel.POS_MACHINE.value() | ReceiveOrderPayChannel.POS_MACHINE.value() | Date.parse("yyyy-MM-dd", "2014-07-26") | Date.parse("yyyy-MM-dd", "2014-07-26") | 200.87   | 200      | 0        | 0        | false
        ReceiveOrderPayChannel.POS_MACHINE.value() | ReceiveOrderPayChannel.POS_MACHINE.value() | Date.parse("yyyy-MM-dd", "2014-07-26") | Date.parse("yyyy-MM-dd", "2014-07-26") | 200.87   | 200.87   | 123      | 456      | false
        ReceiveOrderPayChannel.POS_MACHINE.value() | ReceiveOrderPayChannel.POS_MACHINE.value() | Date.parse("yyyy-MM-dd", "2014-07-26") | Date.parse("yyyy-MM-dd", "2014-07-26") | 200.87   | 200.87   | 123      | 123      | true

    }
}