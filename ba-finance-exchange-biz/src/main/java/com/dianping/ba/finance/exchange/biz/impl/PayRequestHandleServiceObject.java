package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.PayOrderService;
import com.dianping.ba.finance.exchange.api.PayRequestHandleService;
import com.dianping.ba.finance.exchange.api.PayRequestService;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.ba.finance.exchange.api.datas.PayRequestData;
import com.dianping.ba.finance.exchange.api.dtos.PayRequestDTO;
import com.dianping.ba.finance.exchange.api.dtos.PayRequestResultDTO;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.ba.finance.exchange.api.enums.PayOrderStatus;
import com.dianping.ba.finance.exchange.api.enums.PayRequestStatus;
import com.dianping.ba.finance.exchange.api.enums.PayResultStatus;
import com.dianping.ba.finance.exchange.biz.constants.EventConstant;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.aop.annotation.ReturnDefault;
import com.dianping.finance.common.swallow.SwallowEventBean;
import com.dianping.finance.common.swallow.SwallowProducer;
import com.dianping.finance.common.util.CopyUtils;
import com.dianping.finance.common.util.DateUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.ExecutorService;

/**
 * 处理付款请求的Service类
 */
public class PayRequestHandleServiceObject implements PayRequestHandleService {

    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.biz.monitor.PayRequestHandleServiceObject");

    private long timeout;

    private PayRequestService payRequestService;

    private PayOrderService payOrderService;

    private SwallowProducer payRequestProducer;

    private ExecutorService executorService;

    @Log(logBefore = true, logAfter = true, severity = 1)
    @ReturnDefault
    @Override
    public boolean handleNewPayRequest(final PayRequestDTO payRequestDTO) {
        final PayRequestData payRequestData = buildPayRequestData(payRequestDTO);
        payRequestService.insertPayRequest(payRequestData);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                doHandle(payRequestData, payRequestDTO);
            }
        });
        return true;
    }

    /**
     * 处理新提交的付款请求
     *
     * @param payRequestData
     * @param payRequestDTO
     */
    private void doHandle(PayRequestData payRequestData, PayRequestDTO payRequestDTO) {
        PayRequestResultDTO payRequestResultDTO = new PayRequestResultDTO();
        payRequestResultDTO.setPaySequence(payRequestDTO.getPaySequence());
        payRequestResultDTO.setPayAmount(payRequestDTO.getPayAmount());

        // 校验该付款请求
        if (!isValidRequest(payRequestData, payRequestDTO, payRequestResultDTO)) {
            payResultNotify(payRequestResultDTO, payRequestDTO.getBusinessType());
            return;
        }

        // 创建付款计划
        PayOrderData payOrderData = buildPayOrderData(payRequestDTO);
        int result = payOrderService.createPayOrder(payOrderData);
        if (result == -1) {
            payRequestResultDTO.setStatus(PayResultStatus.REQUEST_FAIL.value());
            payRequestResultDTO.setMemo("付款单生成异常");
            payRequestService.updatePayRequest(payRequestData.getPrId(), PayRequestStatus.HANDLE_REQUEST_FAILED, PayRequestStatus.HANDLE_REQUEST_FAILED.toString());
        } else {
            payRequestResultDTO.setPoId(payOrderData.getPoId());
            payRequestResultDTO.setStatus(PayResultStatus.PAY_ORDER_GENERATED.value());
            payRequestResultDTO.setMemo(PayResultStatus.PAY_ORDER_GENERATED.toString());
            payRequestService.updatePayRequest(payRequestData.getPrId(), PayRequestStatus.SUCCESS, PayRequestStatus.SUCCESS.toString());

        }
        payResultNotify(payRequestResultDTO, payRequestDTO.getBusinessType());
    }

    private boolean isValidRequest(PayRequestData payRequestData, PayRequestDTO payRequestDTO, PayRequestResultDTO payRequestResultDTO) {
        // 校验超时
        if (isTimeout(payRequestDTO)) {
            payRequestService.updatePayRequest(payRequestData.getPrId(), PayRequestStatus.TIMEOUT, PayRequestStatus.TIMEOUT.toString());
            payRequestResultDTO.setStatus(PayResultStatus.REQUEST_FAIL.value());
            payRequestResultDTO.setMemo(PayRequestStatus.TIMEOUT.toString());
            return false;
        }
        // 校验金额
        if (isInvalidPayAmount(payRequestDTO)) {
            payRequestService.updatePayRequest(payRequestData.getPrId(), PayRequestStatus.INVALID_AMOUNT, PayRequestStatus.INVALID_AMOUNT.toString());
            payRequestResultDTO.setStatus(PayResultStatus.REQUEST_FAIL.value());
            payRequestResultDTO.setMemo(PayRequestStatus.INVALID_AMOUNT.toString());
            return false;
        }
        return true;
    }

    private void payResultNotify(PayRequestResultDTO payRequestResultDTO, int businessType) {
        SwallowEventBean eventBean = null;
        if (businessType == BusinessType.EXPENSE.value()) {
            eventBean = new SwallowEventBean(EventConstant.EXPENSE_PAY_RESULT_EVENT_KEY, payRequestResultDTO);
        }
        if (eventBean != null) {
            payRequestProducer.fireSwallowEvent(eventBean);
        }
    }


    private boolean isTimeout(PayRequestDTO payRequestDTO) {
        Date current = new Date();
        Date requestTime = payRequestDTO.getRequestTime();
        if (requestTime == null) {
            MONITOR_LOGGER.error(String.format("severity=[1] NewPayRequestServiceObject.checkTimeout error, requestTime null, PayRequestDTO=%s", payRequestDTO));
            return false;
        }
        return current.getTime() - requestTime.getTime() > timeout;
    }

    private boolean isInvalidPayAmount(PayRequestDTO payRequestDTO) {
        BigDecimal planAmount = payRequestDTO.getPayAmount() != null ? payRequestDTO.getPayAmount() : BigDecimal.ZERO;
        return planAmount.compareTo(BigDecimal.ZERO) <= 0;
    }

    private PayRequestData buildPayRequestData(PayRequestDTO payRequestDTO) {
        PayRequestData payRequestData = CopyUtils.copy(payRequestDTO, PayRequestData.class);
        payRequestData.setAddLoginId(0);
        payRequestData.setAddTime(new Date());
        payRequestData.setUpdateLoginId(0);
        payRequestData.setUpdateTime(new Date());
        payRequestData.setMemo(null);
        payRequestData.setRequestMemo(payRequestDTO.getMemo());
        payRequestData.setStatus(PayRequestStatus.INIT.value());
        return payRequestData;
    }

    private PayOrderData buildPayOrderData(PayRequestDTO payRequestDTO) {
        PayOrderData payOrderData = new PayOrderData();
        payOrderData.setPaySequence(payRequestDTO.getPaySequence());
        payOrderData.setBusinessType(payRequestDTO.getBusinessType());
        payOrderData.setPayAmount(payRequestDTO.getPayAmount());
        payOrderData.setStatus(PayOrderStatus.INIT.value());
        payOrderData.setCustomerId(0);
        payOrderData.setCustomerBankId(0);
        payOrderData.setBankFullBranchName(payRequestDTO.getBankFullBranchName());
        payOrderData.setBankAccountName(payRequestDTO.getBankAccountName());
        payOrderData.setBankProvince(payRequestDTO.getBankProvince());
        payOrderData.setBankCity(payRequestDTO.getBankCity());
        payOrderData.setBankAccountNo(payRequestDTO.getBankAccountNo());
        payOrderData.setBankName(payRequestDTO.getBankName());
        payOrderData.setBankCode(payRequestDTO.getBankCode());
        payOrderData.setBankBranchName(payRequestDTO.getBankBranchName());
        payOrderData.setBankAccountType(payRequestDTO.getBankAccountType());
        //1系统
        payOrderData.setAddType(1);
        payOrderData.setAddTime(DateUtils.getCurrentTime());
        payOrderData.setAddLoginId(0);
        payOrderData.setUpdateTime(DateUtils.getCurrentTime());
        payOrderData.setUpdateLoginId(0);
        payOrderData.setMemo(payRequestDTO.getMemo());

        return payOrderData;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public void setPayRequestService(PayRequestService payRequestService) {
        this.payRequestService = payRequestService;
    }

    public void setPayOrderService(PayOrderService payOrderService) {
        this.payOrderService = payOrderService;
    }

    public void setPayRequestProducer(SwallowProducer payRequestProducer) {
        this.payRequestProducer = payRequestProducer;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }
}
