package com.dianping.ba.finance.exchange.paymonitor.job.paycheck.rules;

import com.dianping.ba.finance.exchange.paymonitor.api.datas.PayPlanMonitorData;
import com.dianping.ba.finance.exchange.paymonitor.api.enums.PayPlanStatus;
import com.dianping.ba.finance.exchange.paymonitor.job.paycheck.BasePayCheckRule;
import com.dianping.ba.finance.exchange.paymonitor.job.paycheck.PayCheckResult;

/**
 * Created by adam.huang on 2014/8/8.
 */
public class ValidStateCheck extends BasePayCheckRule {
    @Override
    public boolean filter(PayPlanMonitorData payPlanMonitorData) {
        if(payPlanMonitorData.getStatus() == PayPlanStatus.INIT.value() ||
                payPlanMonitorData.getStatus() == PayPlanStatus.INVALID_BANK_ACCOUNT.value() ||
                payPlanMonitorData.getStatus() == PayPlanStatus.SUSPEND.value()){
            return true;
        }
        return false;
    }

    @Override
    public PayCheckResult check(PayPlanMonitorData payPlanMonitorData) {
        return createValidResult();
    }
}
