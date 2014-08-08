package com.dianping.ba.finance.exchange.paymonitor.job.paycheck.rules;

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
public class RefundCheck extends BasePayCheckRule {

    private PayPlanService payPlanService;
    private PayOrderService payOrderService;

    @Override
    public boolean filter(PayPlanMonitorData payPlanMonitorData) {
        return payPlanMonitorData.getStatus() == PayPlanStatus.REFUND.value();
    }

    @Override
    public PayCheckResult check(PayPlanMonitorData payPlanMonitorData) {
        String sequence = payPlanService.getPaySequenceById(payPlanMonitorData.getPpId());
        if( sequence == null ){
            return createResult(false, true, MonitorExceptionType.PP_PAY_SEQUENCE_NOT_FOUND);
        }

        PayOrderMonitorData payOrderMonitorData = payOrderService.getPayOrderBySequence(sequence);
        if( payOrderMonitorData == null){
            return createResult(false, true, MonitorExceptionType.PP_PAY_ORDER_NOT_FOUND);
        }

        if(payOrderMonitorData.getStatus() == PayOrderStatus.REFUND.value()){
           return createValidResult();
        }
        return createResult(false,true,
                MonitorExceptionType.PP_REFUND_INVALID_STATE);
    }

    public void setPayPlanService(PayPlanService payPlanService) {
        this.payPlanService = payPlanService;
    }

    public void setPayOrderService(PayOrderService payOrderService) {
        this.payOrderService = payOrderService;
    }
}
