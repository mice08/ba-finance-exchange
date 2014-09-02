package com.dianping.ba.finance.exchange.biz.rornmatcher

import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData
import com.dianping.ba.finance.exchange.api.enums.BusinessType
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
                Integer roReceiveType, Integer rnReceiveType,
                Integer rnCustomerId,
                Integer roBusinessType, Integer rnBusinessType,
                String roBizContent, String rnBizContent,
                Boolean match) {

        given:
        ReceiveOrderData roData = [payChannel     : roPayChannel,
                                   bankReceiveTime: roBankReceiveTime,
                                   receiveAmount  : roAmount,
                                   bankID         : roBankId,
                                   receiveType    : roReceiveType,
                                   businessType   : roBusinessType,
                                   bizContent     : roBizContent]

        ReceiveNotifyData rnData = [payChannel   : rnPayChannel,
                                    payTime      : rnPayTime,
                                    receiveAmount: rnAmount,
                                    bankId       : rnBankId,
                                    receiveType  : rnReceiveType,
                                    customerId   : rnCustomerId,
                                    businessType : rnBusinessType,
                                    bizContent   : rnBizContent]

        expect:
        match == rornMatcherPosStub.match(roData, rnData)


        where:
        roPayChannel                               | rnPayChannel                               | roBankReceiveTime                      | rnPayTime                              | roAmount | rnAmount | roBankId | rnBankId | roReceiveType | rnReceiveType | rnCustomerId | roBusinessType                      | rnBusinessType                      | roBizContent | rnBizContent | match
        ReceiveOrderPayChannel.CASH.value()        | ReceiveOrderPayChannel.CASH.value()        | null                                   | null                                   | null     | null     | 0        | 0        | 1             | 2             | 123          | BusinessType.GROUP_PURCHASE.value() | BusinessType.GROUP_PURCHASE.value() | "123"        | "biz"        | false
        ReceiveOrderPayChannel.POS_MACHINE.value() | ReceiveOrderPayChannel.CASH.value()        | null                                   | null                                   | null     | null     | 0        | 0        | 2             | 3             | 123          | BusinessType.GROUP_PURCHASE.value() | BusinessType.GROUP_PURCHASE.value() | "123"        | "biz"        | false
        ReceiveOrderPayChannel.POS_MACHINE.value() | ReceiveOrderPayChannel.POS_MACHINE.value() | null                                   | null                                   | null     | null     | 0        | 0        | 3             | 4             | 123          | BusinessType.GROUP_PURCHASE.value() | BusinessType.GROUP_PURCHASE.value() | "123"        | "biz"        | false
        ReceiveOrderPayChannel.POS_MACHINE.value() | ReceiveOrderPayChannel.POS_MACHINE.value() | Date.parse("yyyy-MM-dd", "2014-07-26") | Date.parse("yyyy-MM-dd", "2014-07-25") | null     | null     | 0        | 0        | 5             | 6             | 123          | BusinessType.GROUP_PURCHASE.value() | BusinessType.GROUP_PURCHASE.value() | "123"        | "biz"        | false
        ReceiveOrderPayChannel.POS_MACHINE.value() | ReceiveOrderPayChannel.POS_MACHINE.value() | Date.parse("yyyy-MM-dd", "2014-07-26") | Date.parse("yyyy-MM-dd", "2014-07-26") | 200.87   | 200      | 0        | 0        | 1             | 2             | 123          | BusinessType.GROUP_PURCHASE.value() | BusinessType.GROUP_PURCHASE.value() | "123"        | "biz"        | false
        ReceiveOrderPayChannel.POS_MACHINE.value() | ReceiveOrderPayChannel.POS_MACHINE.value() | Date.parse("yyyy-MM-dd", "2014-07-26") | Date.parse("yyyy-MM-dd", "2014-07-26") | 200.87   | 200.87   | 123      | 456      | 1             | 2             | 123          | BusinessType.GROUP_PURCHASE.value() | BusinessType.GROUP_PURCHASE.value() | "123"        | "biz"        | false
        ReceiveOrderPayChannel.POS_MACHINE.value() | ReceiveOrderPayChannel.POS_MACHINE.value() | Date.parse("yyyy-MM-dd", "2014-07-26") | Date.parse("yyyy-MM-dd", "2014-07-26") | 200.87   | 200.87   | 123      | 456      | 1             | 2             | 123          | BusinessType.GROUP_PURCHASE.value() | BusinessType.GROUP_PURCHASE.value() | "123"        | "biz"        | false
        ReceiveOrderPayChannel.POS_MACHINE.value() | ReceiveOrderPayChannel.POS_MACHINE.value() | Date.parse("yyyy-MM-dd", "2014-07-26") | Date.parse("yyyy-MM-dd", "2014-07-26") | 200.87   | 200.87   | 123      | 123      | 1             | 1             | 0            | BusinessType.GROUP_PURCHASE.value() | BusinessType.GROUP_PURCHASE.value() | "123"        | "123"        | false
        ReceiveOrderPayChannel.POS_MACHINE.value() | ReceiveOrderPayChannel.POS_MACHINE.value() | Date.parse("yyyy-MM-dd", "2014-07-26") | Date.parse("yyyy-MM-dd", "2014-07-26") | 200.87   | 200.87   | 123      | 123      | 1             | 1             | 123          | BusinessType.GROUP_PURCHASE.value() | BusinessType.GROUP_PURCHASE.value() | "123"        | "biz"        | true
        ReceiveOrderPayChannel.POS_MACHINE.value() | ReceiveOrderPayChannel.POS_MACHINE.value() | Date.parse("yyyy-MM-dd", "2014-07-26") | Date.parse("yyyy-MM-dd", "2014-07-26") | 200.87   | 200.87   | 123      | 123      | 1             | 1             | 0            | BusinessType.ADVERTISEMENT.value()  | BusinessType.ADVERTISEMENT.value()  | "123"        | "biz"        | false
        ReceiveOrderPayChannel.POS_MACHINE.value() | ReceiveOrderPayChannel.POS_MACHINE.value() | Date.parse("yyyy-MM-dd", "2014-07-26") | Date.parse("yyyy-MM-dd", "2014-07-26") | 200.87   | 200.87   | 123      | 123      | 1             | 1             | 123          | BusinessType.ADVERTISEMENT.value()  | BusinessType.ADVERTISEMENT.value()  | "123"        | "biz"        | false
        ReceiveOrderPayChannel.POS_MACHINE.value() | ReceiveOrderPayChannel.POS_MACHINE.value() | Date.parse("yyyy-MM-dd", "2014-07-26") | Date.parse("yyyy-MM-dd", "2014-07-26") | 200.87   | 200.87   | 123      | 123      | 1             | 1             | 123          | BusinessType.ADVERTISEMENT.value()  | BusinessType.ADVERTISEMENT.value()  | "AD123"      | "AD567"      | false
        ReceiveOrderPayChannel.POS_MACHINE.value() | ReceiveOrderPayChannel.POS_MACHINE.value() | Date.parse("yyyy-MM-dd", "2014-07-26") | Date.parse("yyyy-MM-dd", "2014-07-26") | 200.87   | 200.87   | 123      | 123      | 1             | 1             | 123          | BusinessType.ADVERTISEMENT.value()  | BusinessType.ADVERTISEMENT.value()  | "AD123"      | "AD123"      | true

    }
}