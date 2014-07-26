package com.dianping.ba.finance.exchange.biz.rornmatcher;

import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData;
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;
import com.dianping.ba.finance.exchange.api.enums.ReceiveOrderPayChannel;
import com.dianping.finance.common.util.DateUtils;
import com.dianping.finance.common.util.LionConfigUtils;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
import java.util.Date;

/**
 * Created by noahshen on 14-7-26.
 */
public class RORNMatcherTelTransfer implements RORNMatcher {

    public static final String TEL_TRANSFER_TIME_DIFF = "ba-finance-exchange-biz.telTransfer.time.diff.days";

    @Override
    public boolean match(ReceiveOrderData receiveOrderData, ReceiveNotifyData receiveNotifyData) {
        if (receiveOrderData.getPayChannel() == ReceiveOrderPayChannel.TELEGRAPHIC_TRANSFER.value()
                && receiveNotifyData.getPayChannel() == ReceiveOrderPayChannel.TELEGRAPHIC_TRANSFER.value()) {
            return payerNameMatch(receiveOrderData, receiveNotifyData)
                    && timeDifferenceMatch(receiveOrderData, receiveNotifyData)
                    && amountMatch(receiveOrderData, receiveNotifyData)
                    && bankMatch(receiveOrderData, receiveNotifyData);
        }
        return false;
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
        int allowDiffDay = Integer.parseInt(LionConfigUtils.getProperty(TEL_TRANSFER_TIME_DIFF, "3"));
        return Math.abs(diff) <= allowDiffDay;
    }

    private boolean payerNameMatch(ReceiveOrderData receiveOrderData, ReceiveNotifyData receiveNotifyData) {
        String payerName = receiveOrderData.getPayerAccountName();
        String payerNameNotify = receiveNotifyData.getPayerName();
        return StringUtils.equals(payerName, payerNameNotify);
    }
}
