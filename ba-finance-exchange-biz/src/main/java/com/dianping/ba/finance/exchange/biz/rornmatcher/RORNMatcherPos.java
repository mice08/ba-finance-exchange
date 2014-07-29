package com.dianping.ba.finance.exchange.biz.rornmatcher;

import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData;
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;
import com.dianping.ba.finance.exchange.api.enums.ReceiveOrderPayChannel;
import com.dianping.finance.common.util.DateUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by noahshen on 14-7-26.
 */
public class RORNMatcherPos implements RORNMatcher {

    @Override
    public boolean match(ReceiveOrderData receiveOrderData, ReceiveNotifyData receiveNotifyData) {
        if (receiveOrderData.getPayChannel() == ReceiveOrderPayChannel.POS_MACHINE.value()
                && receiveNotifyData.getPayChannel() == ReceiveOrderPayChannel.POS_MACHINE.value()
                && receiveOrderData.getBusinessType() == receiveNotifyData.getBusinessType()) {
            return timeDifferenceMatch(receiveOrderData, receiveNotifyData)
                    && amountMatch(receiveOrderData, receiveNotifyData)
                    && bankMatch(receiveOrderData, receiveNotifyData)
                    && receiveTypeMatch(receiveOrderData, receiveNotifyData);
        }
        return false;
    }

    private boolean receiveTypeMatch(ReceiveOrderData receiveOrderData, ReceiveNotifyData receiveNotifyData) {
        return receiveOrderData.getReceiveType() == receiveNotifyData.getReceiveType();
    }

    private boolean bankMatch(ReceiveOrderData receiveOrderData, ReceiveNotifyData receiveNotifyData) {
        int roBankId = receiveOrderData.getBankID();
        int rnBankId = receiveNotifyData.getBankId();
        return (roBankId > 0 && rnBankId > 0 && roBankId == rnBankId);
    }

    private boolean amountMatch(ReceiveOrderData receiveOrderData, ReceiveNotifyData receiveNotifyData) {
        BigDecimal roAmount = receiveOrderData.getReceiveAmount();
        BigDecimal rnAmount = receiveNotifyData.getReceiveAmount();
        if (roAmount != null && rnAmount != null) {
            return rnAmount.compareTo(rnAmount) == 0;
        }
        return false;
    }

    private boolean timeDifferenceMatch(ReceiveOrderData receiveOrderData, ReceiveNotifyData receiveNotifyData) {
        Date bankReceiveTime = receiveOrderData.getBankReceiveTime();
        Date payTime = receiveNotifyData.getPayTime();
        if (bankReceiveTime == null || payTime == null) {
            return false;
        }
        long diff = DateUtils.timeDifference(bankReceiveTime, payTime, TimeUnit.DAYS);
        // 同一天
        return diff == 0;
    }
}
