package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.PayOrderRequestHandleService;
import com.dianping.ba.finance.exchange.api.PayOrderService;
import com.dianping.ba.finance.exchange.api.beans.PayOrderResultBean;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.ba.finance.exchange.api.dtos.PayOrderRequestDTO;
import com.dianping.ba.finance.exchange.api.enums.PayOrderStatus;
import com.dianping.ba.finance.exchange.api.enums.PayResultStatus;
import com.dianping.ba.finance.exchange.biz.producer.PayOrderResultNotify;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.aop.annotation.ReturnDefault;
import com.dianping.finance.common.util.DateUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.ExecutorService;

/**
 * 处理付款计划请求的Service类
 */
public class PayOrderRequestHandleServiceObject implements PayOrderRequestHandleService {

    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.biz.monitor.PayOrderRequestHandleServiceObject");

    private long timeout;

    private PayOrderService payOrderService;

    private PayOrderResultNotify payOrderResultNotify;

    private ExecutorService executorService;

    @Log(logBefore = true, logAfter = true, severity = 1)
    @ReturnDefault
    @Override
    public boolean handlePayOrderRequest(final PayOrderRequestDTO payOrderRequestDTO) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                doHandle(payOrderRequestDTO);
            }
        });
        return true;
    }

    /**
     * 处理提交的付款计划请求
     * @param payOrderRequestDTO
     */
    private void doHandle(PayOrderRequestDTO payOrderRequestDTO) {
        PayOrderResultBean payOrderResultBean = new PayOrderResultBean();
        payOrderResultBean.setPaySequence(payOrderRequestDTO.getPaySequence());
        payOrderResultBean.setPaidAmount(BigDecimal.ZERO);
        payOrderResultBean.setLoginId(payOrderRequestDTO.getLoginId());

        // 校验该付款请求
        if (!checkRequest(payOrderRequestDTO, payOrderResultBean)) {
            payOrderResultNotify.payResultNotify(payOrderResultBean);
            return;
        }

        // 创建付款计划
        PayOrderData payOrderData = buildPayOrderData(payOrderRequestDTO);
        int result = payOrderService.createPayOrder(payOrderData);
        if (result == -1) {
            payOrderResultBean.setStatus(PayResultStatus.REQUEST_FAIL);
            payOrderResultBean.setMemo("付款单生成异常");
        } else {
            payOrderResultBean.setPoId(payOrderData.getPoId());
            payOrderResultBean.setStatus(PayResultStatus.PAY_ORDER_GENERATED);
        }
        payOrderResultNotify.payResultNotify(payOrderResultBean);
    }

    private boolean checkRequest(PayOrderRequestDTO payOrderRequestDTO, PayOrderResultBean payOrderResultBean) {
        // 校验超时
        if (!checkTimeout(payOrderRequestDTO)) {
            payOrderResultBean.setStatus(PayResultStatus.REQUEST_FAIL);
            payOrderResultBean.setMemo("接收超时");
            return false;
        }

        // 校验金额
        if (!checkPayAmount(payOrderRequestDTO)) {
            payOrderResultBean.setStatus(PayResultStatus.REQUEST_FAIL);
            payOrderResultBean.setMemo("付款金额为0");
            return false;
        }
        return true;
    }

    private boolean checkTimeout(PayOrderRequestDTO payOrderRequestDTO) {
        Date current = new Date();
        Date requestDate = payOrderRequestDTO.getRequestDate();
        if (requestDate == null) {
            MONITOR_LOGGER.error(String.format("severity=[1] PayOrderRequestHandleServiceObject.checkTimeout error, requestDate null, PayOrderRequestDTO=%s", payOrderRequestDTO));
            return false;
        }
        return current.getTime() - requestDate.getTime() <= timeout;
    }

    private boolean checkPayAmount(PayOrderRequestDTO payRequestDTO) {
        BigDecimal payAmount = payRequestDTO.getPayAmount() != null ? payRequestDTO.getPayAmount() : BigDecimal.ZERO;
        return payAmount.compareTo(BigDecimal.ZERO) > 0;
    }

    private PayOrderData buildPayOrderData(PayOrderRequestDTO payOrderRequestDTO) {
        PayOrderData payOrderData = new PayOrderData();
        payOrderData.setPaySequence(payOrderRequestDTO.getPaySequence());
        payOrderData.setBusinessType(payOrderRequestDTO.getBusinessType());
        payOrderData.setPayAmount(payOrderRequestDTO.getPayAmount());
        payOrderData.setStatus(PayOrderStatus.INIT.value());
        payOrderData.setCustomerId(payOrderRequestDTO.getCustomerId());

        payOrderData.setCustomerBankId(payOrderRequestDTO.getCustomerBankId());
        payOrderData.setBankFullBranchName(payOrderRequestDTO.getBankFullBranchName());
        payOrderData.setBankAccountName(payOrderRequestDTO.getBankAccountName());
        payOrderData.setBankProvince(payOrderRequestDTO.getBankProvince());
        payOrderData.setBankCity(payOrderRequestDTO.getBankCity());
        payOrderData.setBankAccountNo(payOrderRequestDTO.getBankAccountNo());
        payOrderData.setBankName(payOrderRequestDTO.getBankName());
        payOrderData.setBankCode(payOrderRequestDTO.getBankCode());
        payOrderData.setBankBranchName(payOrderRequestDTO.getBankBranchName());
        payOrderData.setBankAccountType(payOrderRequestDTO.getBankAccountType());
        //1系统
        payOrderData.setAddType(1);
        payOrderData.setAddTime(DateUtils.getCurrentTime());
        payOrderData.setAddLoginId(payOrderRequestDTO.getLoginId());
        payOrderData.setUpdateTime(DateUtils.getCurrentTime());
        payOrderData.setUpdateLoginId(payOrderRequestDTO.getLoginId());
        return payOrderData;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public void setPayOrderService(PayOrderService payOrderService) {
        this.payOrderService = payOrderService;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void setPayOrderResultNotify(PayOrderResultNotify payOrderResultNotify) {
        this.payOrderResultNotify = payOrderResultNotify;
    }
}
