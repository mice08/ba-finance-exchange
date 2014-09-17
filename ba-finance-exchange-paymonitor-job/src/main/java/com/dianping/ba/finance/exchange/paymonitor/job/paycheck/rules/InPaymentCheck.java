package com.dianping.ba.finance.exchange.paymonitor.job.paycheck.rules;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.paymonitor.api.PayOrderService;
import com.dianping.ba.finance.exchange.paymonitor.api.PayPlanService;
import com.dianping.ba.finance.exchange.paymonitor.api.datas.PayOrderMonitorData;
import com.dianping.ba.finance.exchange.paymonitor.api.datas.PayPlanMonitorData;
import com.dianping.ba.finance.exchange.paymonitor.api.enums.MonitorExceptionType;
import com.dianping.ba.finance.exchange.paymonitor.api.enums.PayOrderStatus;
import com.dianping.ba.finance.exchange.paymonitor.api.enums.PayPlanStatus;
import com.dianping.ba.finance.exchange.paymonitor.job.paycheck.BasePayCheckRule;
import com.dianping.ba.finance.exchange.paymonitor.job.paycheck.PayCheckResult;

/**
 * Created by adam.huang on 2014/8/8.
 */
public class InPaymentCheck extends BasePayCheckRule {
    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.paymonitor.job.PayPlanDataChecker");

    private PayPlanService payPlanService;
    private PayOrderService payOrderService;

    @Override
    public boolean filter(PayPlanMonitorData payPlanMonitorData) {
        return payPlanMonitorData.getStatus() == PayPlanStatus.IN_PAYMENT.value();
    }

    @Override
    public PayCheckResult check(PayPlanMonitorData payPlanMonitorData) {
        MONITOR_LOGGER.info("============InPaymentCheck===============");

        String sequence = payPlanService.getPaySequenceById(payPlanMonitorData.getPpId());
        if( sequence == null ){
            MONITOR_LOGGER.info("sequence is null");
            return createResult(false, true, MonitorExceptionType.PP_PAY_SEQUENCE_NOT_FOUND);
        }
        MONITOR_LOGGER.info("sequence: " + sequence);

        PayOrderMonitorData payOrderMonitorData = payOrderService.getPayOrderBySequence(sequence);
        if( payOrderMonitorData == null){
            MONITOR_LOGGER.info("payOrderMonitorData is null");
            return createResult(false, true, MonitorExceptionType.PP_PAY_ORDER_NOT_FOUND);
        }


        if(payOrderMonitorData.getStatus() == PayOrderStatus.INIT.value() ||
                payOrderMonitorData.getStatus() == PayOrderStatus.EXPORT_PAYING.value()){
            return createValidResult();
        }

        if(payOrderMonitorData.getStatus() == PayOrderStatus.PAY_SUCCESS.value()){
            return createResult(false,checkIfTimeout(payPlanMonitorData.getUpdateTime()),
                    MonitorExceptionType.PP_IN_PAYMENT_TIMEOUT);
        }

        return createResult(false,true,
                MonitorExceptionType.PP_IN_PAYMENT_INVALID_STATE);
    }

    public void setPayPlanService(PayPlanService payPlanService) {
        this.payPlanService = payPlanService;
    }

    public void setPayOrderService(PayOrderService payOrderService) {
        this.payOrderService = payOrderService;
    }
}
