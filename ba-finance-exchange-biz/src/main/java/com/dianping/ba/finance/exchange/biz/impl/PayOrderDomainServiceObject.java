package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.AccountService;
import com.dianping.ba.finance.exchange.api.PayOrderDomainService;
import com.dianping.ba.finance.exchange.api.PayOrderService;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.ba.finance.exchange.api.dtos.AccountEntryRequestDTO;
import com.dianping.ba.finance.exchange.api.dtos.BankAccountDTO;
import com.dianping.ba.finance.exchange.api.dtos.BankPayRequestDTO;
import com.dianping.ba.finance.exchange.api.dtos.BankPayResultDTO;
import com.dianping.ba.finance.exchange.api.enums.AccountEntrySourceType;
import com.dianping.ba.finance.exchange.api.enums.PayOrderStatus;
import com.dianping.ba.finance.exchange.api.enums.PayType;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.util.ListUtils;
import com.dianping.swallow.producer.Producer;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by 遐 on 2015/3/9.
 */
public class PayOrderDomainServiceObject implements PayOrderDomainService {

    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.biz.monitor.PayOrderDomainServiceObject");

    private static final Set<Integer> ALLOWED_BANK_PAY_STATUS = Sets.newHashSet(PayOrderStatus.SUBMIT_FOR_PAY.value());

    @Autowired
    private PayOrderService payOrderService;
    @Autowired
    private Producer bankPayProducer;
    @Autowired
    private AccountService accountService;

    @Override
    @Log(logBefore = true, logAfter = true)
    public int pay(List<Integer> poIds, int loginId) {
        MONITOR_LOGGER.info(String.format("PayOrderDomainServiceObject.pay request size [%d]", poIds.size()));
        try {
            List<PayOrderData> payOrderDataList = payOrderService.findPayOrderByIdList(poIds);
            List<Integer> idList = buildPayOrderListForBankPay(payOrderDataList);
            if(CollectionUtils.isEmpty(idList)){
                MONITOR_LOGGER.warn(String.format("No pay order to pay! poIds=[%s]", ListUtils.listToString(poIds, ",")));
                return 0;
            }
            payOrderService.batchUpdatePayOrderStatus(idList, Arrays.asList(PayOrderStatus.SUBMIT_FOR_PAY.value()), PayOrderStatus.BANK_PAYING.value(), loginId);
            for(PayOrderData data: payOrderDataList){
                if(idList.contains(data.getPoId())){
                    BankPayRequestDTO requestDTO = buildBankPayRequest(data);
                    bankPayProducer.sendMessage(requestDTO);
                }
            }
            return idList.size();
        } catch (Exception e) {
            MONITOR_LOGGER.error(String.format("severity=[1],PayOrderDomainServiceObject.pay fail!, poIds=[%s]&loginId=[%d]", poIds, loginId), e);
            return -1;
        }
    }

    @Override
    @Log(logBefore = true, logAfter = true)
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
        AccountEntryRequestDTO entry = new AccountEntryRequestDTO();
        entry.setInstId(String.valueOf(poId));
        entry.setSourceType(AccountEntrySourceType.BANK.getSourceType());
        entry.setAmount(payOrder.getPayAmount());
        entry.setBankAccountId(payOrder.getCustomerBankId());
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

    private List<Integer> buildPayOrderListForBankPay(List<PayOrderData> dataList) {
        if (CollectionUtils.isEmpty(dataList)) {
            return new ArrayList<Integer>();
        }
        List<Integer> idList = new ArrayList<Integer>();
        for (PayOrderData data : dataList) {
            if (orderCanBankPay(data)) {
                idList.add(data.getPoId());
            }
        }
        return idList;
    }

    private boolean orderCanBankPay(PayOrderData order) {
        return order.getPayAmount().compareTo(BigDecimal.ZERO) > 0 &&
                ALLOWED_BANK_PAY_STATUS.contains(order.getStatus()) &&
                (order.getPayType() == PayType.GROUPON_SETTLE.getPayType() ||
                        order.getPayType() == PayType.SHAN_HUI_SETTLE.getPayType() ||
                        order.getPayType() == PayType.SHAN_FU_SETTLE.getPayType());
    }


    private BankPayRequestDTO buildBankPayRequest(PayOrderData payOrderData){
        BankPayRequestDTO requestDTO = new BankPayRequestDTO();
        requestDTO.setInsId(String.valueOf(payOrderData.getPoId()));
        BankAccountDTO payeeBankAccountDTO = accountService.loadBankAccount(payOrderData.getPayeeBankAccountId());
        requestDTO.setAccountNo(payeeBankAccountDTO.getBankAccountNo());
        requestDTO.setAccountName(payeeBankAccountDTO.getBankAccountName());
        requestDTO.setAccountToNo(payOrderData.getBankAccountNo());
        requestDTO.setAccountToName(payOrderData.getBankAccountName());
        requestDTO.setAccountType(1);
        requestDTO.setAccountToType(payOrderData.getBankAccountType());
        requestDTO.setBankBranchCode(payOrderData.getBankCode());
        requestDTO.setBankCode(payOrderData.getMasterBankCode());
        requestDTO.setBankName(payOrderData.getBankName());
        requestDTO.setAmount(payOrderData.getPayAmount());
        requestDTO.setDescription(payOrderData.getMemo());
        return requestDTO;
    }


    public void setPayOrderService(PayOrderService payOrderService) {
        this.payOrderService = payOrderService;
    }

    public void setBankPayProducer(Producer bankPayProducer) {
        this.bankPayProducer = bankPayProducer;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
}
