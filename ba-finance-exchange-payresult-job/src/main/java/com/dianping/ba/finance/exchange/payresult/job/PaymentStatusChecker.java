package com.dianping.ba.finance.exchange.payresult.job;

import com.dianping.ba.finance.bankorder.api.FSBankQueryService;
import com.dianping.ba.finance.bankorder.api.dtos.BaseResponseDTO;
import com.dianping.ba.finance.bankorder.api.enums.OrderError;
import com.dianping.ba.finance.exchange.api.PayOrderService;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.ba.finance.exchange.api.enums.PayOrderStatus;
import com.dianping.core.type.PageModel;
import com.dianping.finance.common.util.LionConfigUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by noahshen on 15/3/10.
 */
public class PaymentStatusChecker {

    private PayOrderService payOrderService;

    private FSBankQueryService fsBankQueryService;

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
                BaseResponseDTO responseDTO = fsBankQueryService.queryPayResult(String.valueOf(payOrderData.getPoId()));
                if (responseDTO.getCode() == OrderError.PAY_SUCCESS.getCode()) {
                    payOrderService.updatePayOrderToPaySuccess(Arrays.asList(payOrderData.getPoId()), 0);
                } else if (responseDTO.getCode() == OrderError.SYSTEM_ERROR.getCode()) {
                    payOrderService.updatePayOrderStatus(payOrderData.getPoId(), PayOrderStatus.BANK_PAYING.value(), PayOrderStatus.SYSTEM_ERROR.value(), null);
                } else if (responseDTO.getCode() == OrderError.PAY_RESULT_PENDING.getCode()
                            || responseDTO.getCode() == OrderError.QUERY_ERROR.getCode()
                            || responseDTO.getCode() == OrderError.PAY_RESULT_UNKNOWN.getCode()) {
                    // ignore
                } else {
                    payOrderService.updatePayOrderStatus(payOrderData.getPoId(), PayOrderStatus.BANK_PAYING.value(), PayOrderStatus.PAY_FAILED.value(), null);
                }
            }
        }
    }


    public void setPayOrderService(PayOrderService payOrderService) {
        this.payOrderService = payOrderService;
    }

    public void setFsBankQueryService(FSBankQueryService fsBankQueryService) {
        this.fsBankQueryService = fsBankQueryService;
    }
}
