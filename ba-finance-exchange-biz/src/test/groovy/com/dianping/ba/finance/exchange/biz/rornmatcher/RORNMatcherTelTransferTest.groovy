package com.dianping.ba.finance.exchange.biz.rornmatcher

import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData
import com.dianping.ba.finance.exchange.api.enums.ReceiveOrderPayChannel
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by noahshen on 14-7-26.
 */
class RORNMatcherTelTransferTest extends Specification {

    private RORNMatcherTelTransfer rornMatcherTelTransferStub;
    void setup() {
        rornMatcherTelTransferStub = []
    }

    @Unroll
    def "Match"(Integer roPayChannel, Integer rnPayChannel,
                String roPayerName, String rnPayerName,
                Date roBankReceiveTime, Date rnPayTime,
                BigDecimal roAmount, BigDecimal rnAmount,
                Integer roBankId, Integer rnBankId,
                Integer roReceiveType, Integer rnReceiveType,
                Boolean match) {

        given:
        ReceiveOrderData roData = [payChannel      : roPayChannel,
                                   payerAccountName: roPayerName,
                                   bankReceiveTime : roBankReceiveTime,
                                   receiveAmount   : roAmount,
                                   bankID          : roBankId,
                                   receiveType     : roReceiveType]


        ReceiveNotifyData rnData = [payChannel   : rnPayChannel,
                                    payerName    : rnPayerName,
                                    payTime      : rnPayTime,
                                    receiveAmount: rnAmount,
                                    bankId       : rnBankId,
                                    receiveType  : rnReceiveType]

        expect:
        match == rornMatcherTelTransferStub.match(roData, rnData)


        where:
        roPayChannel                                        | rnPayChannel                                        | roPayerName  | rnPayerName  | roBankReceiveTime                      | rnPayTime                              | roAmount | rnAmount | roBankId | rnBankId | roReceiveType | rnReceiveType | match
        ReceiveOrderPayChannel.CASH.value()                 | ReceiveOrderPayChannel.CASH.value()                 | null         | null         | null                                   | null                                   | null     | null     | 0        | 0        | 1             | 2             | false
        ReceiveOrderPayChannel.TELEGRAPHIC_TRANSFER.value() | ReceiveOrderPayChannel.CASH.value()                 | null         | null         | null                                   | null                                   | null     | null     | 0        | 0        | 1             | 2             | false
        ReceiveOrderPayChannel.TELEGRAPHIC_TRANSFER.value() | ReceiveOrderPayChannel.TELEGRAPHIC_TRANSFER.value() | "payerName1" | "payerName2" | null                                   | null                                   | null     | null     | 0        | 0        | 1             | 2             | false
        ReceiveOrderPayChannel.TELEGRAPHIC_TRANSFER.value() | ReceiveOrderPayChannel.TELEGRAPHIC_TRANSFER.value() | "payerName1" | "payerName1" | null                                   | null                                   | null     | null     | 0        | 0        | 1             | 2             | false
        ReceiveOrderPayChannel.TELEGRAPHIC_TRANSFER.value() | ReceiveOrderPayChannel.TELEGRAPHIC_TRANSFER.value() | "payerName1" | "payerName1" | Date.parse("yyyy-MM-dd", "2014-07-26") | Date.parse("yyyy-MM-dd", "2014-07-20") | null     | null     | 0        | 0        | 1             | 2             | false
        ReceiveOrderPayChannel.TELEGRAPHIC_TRANSFER.value() | ReceiveOrderPayChannel.TELEGRAPHIC_TRANSFER.value() | "payerName1" | "payerName1" | Date.parse("yyyy-MM-dd", "2014-07-26") | Date.parse("yyyy-MM-dd", "2014-07-23") | 200.87   | 200      | 0        | 0        | 1             | 2             | false
        ReceiveOrderPayChannel.TELEGRAPHIC_TRANSFER.value() | ReceiveOrderPayChannel.TELEGRAPHIC_TRANSFER.value() | "payerName1" | "payerName1" | Date.parse("yyyy-MM-dd", "2014-07-26") | Date.parse("yyyy-MM-dd", "2014-07-26") | 200.87   | 200.87   | 123      | 456      | 1             | 2             | false
        ReceiveOrderPayChannel.TELEGRAPHIC_TRANSFER.value() | ReceiveOrderPayChannel.TELEGRAPHIC_TRANSFER.value() | "payerName1" | "payerName1" | Date.parse("yyyy-MM-dd", "2014-07-26") | Date.parse("yyyy-MM-dd", "2014-07-26") | 200.87   | 200.87   | 123      | 123      | 1             | 2             | false
        ReceiveOrderPayChannel.TELEGRAPHIC_TRANSFER.value() | ReceiveOrderPayChannel.TELEGRAPHIC_TRANSFER.value() | "payerName1" | "payerName1" | Date.parse("yyyy-MM-dd", "2014-07-26") | Date.parse("yyyy-MM-dd", "2014-07-26") | 200.87   | 200.87   | 123      | 123      | 1             | 1             | true

    }
}
