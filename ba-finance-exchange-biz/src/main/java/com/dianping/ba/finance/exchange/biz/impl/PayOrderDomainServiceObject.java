package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.PayOrderDomainService;
import com.dianping.ba.finance.exchange.api.PayOrderService;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.ba.finance.exchange.api.dtos.BankPayResultDTO;
import com.dianping.ba.finance.exchange.api.enums.PayOrderStatus;
import com.dianping.ba.finance.exchange.api.enums.PayType;
import com.dianping.ba.finance.exchange.biz.enums.PayResultStatus;
import com.dianping.ba.finance.paymentplatform.api.AccountService;
import com.dianping.ba.finance.paymentplatform.api.PaymentDomainService;
import com.dianping.ba.finance.paymentplatform.api.dtos.BankAccountDTO;
import com.dianping.ba.finance.paymentplatform.api.dtos.PayResponseDTO;
import com.dianping.ba.finance.paymentplatform.api.dtos.PaymentRequestDTO;
import com.dianping.ba.finance.paymentplatform.api.enums.Channel;
import com.dianping.ba.finance.paymentplatform.api.enums.PayRequestResult;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.util.LionConfigUtils;
import com.dianping.finance.common.util.ListUtils;
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
import java.util.concurrent.ExecutorService;

/**
 * Created by 遐 on 2015/3/9.
 */
public class PayOrderDomainServiceObject implements PayOrderDomainService {

    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.biz.monitor.PayOrderDomainServiceObject");

    private static final Set<Integer> ALLOWED_BANK_PAY_STATUS = Sets.newHashSet(PayOrderStatus.SUBMIT_FOR_PAY.value());

    public static final String BANK_ORDER_PAY_REQUEST = "BANK_ORDER_PAY_REQUEST_NOTIFY";

    @Autowired
    private PayOrderService payOrderService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ExecutorService executorService;
    @Autowired
    private PaymentDomainService paymentDomainService;

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
            asyncPay(payOrderDataList, idList);
            return idList.size();
        } catch (Exception e) {
            MONITOR_LOGGER.error(String.format("severity=[1],PayOrderDomainServiceObject.pay fail!, poIds=[%s]&loginId=[%d]", poIds, loginId), e);
            return -1;
        }
    }

    private void asyncPay(final List<PayOrderData> payOrderDataList, final List<Integer> poIdList) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                doPay(payOrderDataList, poIdList);
            }
        });
    }

    private void doPay(final List<PayOrderData> payOrderDataList, final List<Integer> poIdList){
        for(PayOrderData data: payOrderDataList){
            if(poIdList.contains(data.getPoId())){
                PaymentRequestDTO requestDTO = buildBankPayRequest(data);
                PayResponseDTO payResponseDTO = paymentDomainService.pay(requestDTO);
                if(payResponseDTO.getCode() == PayRequestResult.SUCCESS.getCode()){
                    PayOrderData payOrderData = payOrderService.loadPayOrderDataByPOID(data.getPoId());
                    payOrderService.updatePayCode(data.getPoId(), payResponseDTO.getPayCode() + "|" + payOrderData.getPayCode());
                } else {
                    payOrderService.updatePayOrderStatus(data.getPoId(), PayOrderStatus.BANK_PAYING.value(), PayOrderStatus.SUBMIT_FAILED.value(), payResponseDTO.getMessage());
                }
            }
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
        return true;
    }

    private PayOrderStatus parsePayOrderStatus(int code) {
        if (code == PayResultStatus.PAY_SUCCESS.getCode()) {
            return PayOrderStatus.PAY_SUCCESS;
        } else if (code == PayResultStatus.PAY_FAILED.getCode() || code == PayResultStatus.REQUEST_FAILED.getCode()) {
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

    private PaymentRequestDTO buildBankPayRequest(PayOrderData payOrderData){
        PaymentRequestDTO requestDTO = new PaymentRequestDTO();
        BankAccountDTO payeeBankAccountDTO = accountService.loadBankAccount(payOrderData.getPayeeBankAccountId());
        if(payeeBankAccountDTO != null){
            requestDTO.setFromAccountNo(payeeBankAccountDTO.getBankAccountNo());
            requestDTO.setFromAccountName(payeeBankAccountDTO.getBankAccountName());
            requestDTO.setFromBankName("中国民生银行");// todo hard code
        }
        requestDTO.setChannel(Channel.MINSHENG_BANK.getCode());
        requestDTO.setBizType(1);
        String token = LionConfigUtils.getProperty("ba-finance-exchange-service.pay.token", "abc1234");
        requestDTO.setToken(token);
        requestDTO.setOutBizId(String.valueOf(payOrderData.getPoId()));
        requestDTO.setToAccountNo(payOrderData.getBankAccountNo());
        requestDTO.setToBankAccountName(payOrderData.getBankAccountName());
        requestDTO.setBankAccountType(payOrderData.getBankAccountType());
        requestDTO.setToBranchCode(payOrderData.getBankCode());
        requestDTO.setToBankCode(payOrderData.getMasterBankCode());
        requestDTO.setToFullBranchName(payOrderData.getBankName());
        requestDTO.setAmount(payOrderData.getPayAmount());
        requestDTO.setExplain(payOrderData.getMemo());
        return requestDTO;
    }



    public void setPayOrderService(PayOrderService payOrderService) {
        this.payOrderService = payOrderService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
}
