package com.dianping.ba.finance.exchange.payresult.job;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.bankorder.api.FSBankQueryService;
import com.dianping.ba.finance.bankorder.api.dtos.BaseResponseDTO;
import com.dianping.ba.finance.exchange.api.AccountService;
import com.dianping.ba.finance.exchange.api.PayOrderService;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.ba.finance.exchange.api.dtos.AccountEntryRequestDTO;
import com.dianping.ba.finance.exchange.api.enums.AccountEntrySourceType;
import com.dianping.ba.finance.exchange.api.enums.PayOrderStatus;
import com.dianping.core.type.PageModel;
import com.dianping.finance.common.util.LionConfigUtils;

import java.util.List;

/**
 * Created by noahshen on 15/3/10.
 */
public class PaymentStatusChecker {

    private static final AvatarLogger LOGGER = AvatarLoggerFactory.getLogger(PaymentStatusChecker.class);

    private PayOrderService payOrderService;

    private FSBankQueryService fsBankQueryService;

    private AccountService accountService;

    public void checkPaymentStatus() {
        final int batchSize = Integer.parseInt(LionConfigUtils.getProperty("ba-finance-exchange-payresult-job.batch.groupSize", "200"));
        int page = 1;
        for(;;) {
            PageModel pageModel = payOrderService.paginatePayOrderListByStatus(PayOrderStatus.BANK_PAYING.value(), page, batchSize);
            if (pageModel == null || pageModel.getRecords() == null) {
                break;
            }
            List<PayOrderData> payOrderDataList = (List<PayOrderData>) pageModel.getRecords();
            for (PayOrderData payOrderData : payOrderDataList) {
                BaseResponseDTO responseDTO = null;
                try {
                    responseDTO = fsBankQueryService.queryPayResult(String.valueOf(payOrderData.getPoId()));
                } catch (Exception e) {
                    LOGGER.error(e);
                    continue;
                }
                if (responseDTO == null) {
                    continue;
                }

                if (responseDTO.getCode() == com.dianping.ba.finance.bankorder.api.enums.PayOrderStatus.PAY_SUCCESS.getCode()) {
                    int i = payOrderService.updatePayOrderStatus(payOrderData.getPoId(), PayOrderStatus.BANK_PAYING.value(), PayOrderStatus.PAY_SUCCESS.value(), null);
                    if (i > 0) {
                        AccountEntryRequestDTO entryRequestDTO = new AccountEntryRequestDTO();
                        entryRequestDTO.setAmount(payOrderData.getPayAmount());
                        entryRequestDTO.setBankAccountId(1); //default bank
                        entryRequestDTO.setDescription(PayOrderStatus.PAY_SUCCESS.toString());
                        entryRequestDTO.setInstId(String.valueOf(payOrderData.getPoId()));
                        entryRequestDTO.setSourceType(AccountEntrySourceType.BANK.value());
                        accountService.updateAccount(entryRequestDTO);
                    }
                } else if (responseDTO.getCode() == com.dianping.ba.finance.bankorder.api.enums.PayOrderStatus.SYSTEM_ERROR.getCode()
                        || responseDTO.getCode() == com.dianping.ba.finance.bankorder.api.enums.PayOrderStatus.REQUEST_FAILED.getCode()) {
                    payOrderService.updatePayOrderStatus(payOrderData.getPoId(), PayOrderStatus.BANK_PAYING.value(), PayOrderStatus.SYSTEM_ERROR.value(), com.dianping.ba.finance.bankorder.api.enums.PayOrderStatus.getByCode(responseDTO.getCode()).getMessage());
                } else if (responseDTO.getCode() == com.dianping.ba.finance.bankorder.api.enums.PayOrderStatus.REQUEST_SUCCESS.getCode()
                            || responseDTO.getCode() == com.dianping.ba.finance.bankorder.api.enums.PayOrderStatus.PAYING.getCode()) {
                    // ignore
                } else if (responseDTO.getCode() == com.dianping.ba.finance.bankorder.api.enums.PayOrderStatus.PAY_FAILED.getCode()){
                    payOrderService.updatePayOrderStatus(payOrderData.getPoId(), PayOrderStatus.BANK_PAYING.value(), PayOrderStatus.REFUND.value(), responseDTO.getMessage());
                }
            }
            page++;
        }
    }


    public void setPayOrderService(PayOrderService payOrderService) {
        this.payOrderService = payOrderService;
    }

    public void setFsBankQueryService(FSBankQueryService fsBankQueryService) {
        this.fsBankQueryService = fsBankQueryService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
}
