package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.AccountService;
import com.dianping.ba.finance.exchange.api.BankPayResultHandleService;
import com.dianping.ba.finance.exchange.api.PayOrderService;
import com.dianping.ba.finance.exchange.api.datas.AccountEntryData;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.ba.finance.exchange.api.dtos.AccountEntryDTO;
import com.dianping.ba.finance.exchange.api.dtos.BankPayResultDTO;
import com.dianping.ba.finance.exchange.api.enums.AccountEntryDirection;
import com.dianping.ba.finance.exchange.api.enums.AccountEntrySourceType;
import com.dianping.ba.finance.exchange.api.enums.PayOrderStatus;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by will on 15-3-9.
 */
public class BankPayResultHandleServiceObject implements BankPayResultHandleService {

    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.biz.impl.monitor.BankPayResultHandleServiceObject");

    @Autowired
    private PayOrderService payOrderService;
    @Autowired
    private AccountService accountService;

    @Override
    public boolean handleBankPayResult(BankPayResultDTO bankPayResultDTO) {
        PayOrderStatus payOrderStatus = parsePayOrderStatus(bankPayResultDTO.getCode());
        if (payOrderStatus == PayOrderStatus.BANK_PAYING) {
            MONITOR_LOGGER.error(String.format("severity=1], upay order status is BANK_PAYING!. bankPayResultDTO=[%s]", ToStringBuilder.reflectionToString(bankPayResultDTO)));
            return false;
        }
        int poId = NumberUtils.toInt(bankPayResultDTO.getInstId());
        int result = payOrderService.updatePayOrderStatus(poId, PayOrderStatus.BANK_PAYING.value(), payOrderStatus.value(), bankPayResultDTO.getMessage());
        if (result != 1) {
            MONITOR_LOGGER.error(String.format("severity=1], update pay order status failed!. bankPayResultDTO=[%s]", ToStringBuilder.reflectionToString(bankPayResultDTO)));
            return false;
        }
        return addAccountEntry(poId);
    }

    private boolean addAccountEntry(int poId) {
        PayOrderData payOrder = payOrderService.loadPayOrderDataByPOID(poId);
        AccountEntryDTO entry = new AccountEntryDTO();
        entry.setInstId(String.valueOf(poId));
        entry.setSourceType(AccountEntrySourceType.BANK.getSourceType());
        entry.setAmount(payOrder.getPayAmount());//todo:desc
        entry.setBankAccountId(payOrder.getCustomerBankId());//todo:memo
        return accountService.updateAccount(entry);
    }

    private PayOrderStatus parsePayOrderStatus(int code) {
        if (code == 1 || code == 2003 || code == 2004) {
            return PayOrderStatus.BANK_PAYING;
        } else if (code == 2000) {
            return PayOrderStatus.PAY_SUCCESS;
        } else if (code == 2001 || code == -1) {
            return PayOrderStatus.PAY_FAILED;
        }
        return PayOrderStatus.BANK_PAYING;
    }
}
