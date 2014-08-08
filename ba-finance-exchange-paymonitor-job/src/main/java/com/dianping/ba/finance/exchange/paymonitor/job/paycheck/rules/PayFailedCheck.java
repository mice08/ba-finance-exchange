package com.dianping.ba.finance.exchange.paymonitor.job.paycheck.rules;

import com.dianping.ba.finance.exchange.paymonitor.api.datas.PayPlanMonitorData;
import com.dianping.ba.finance.exchange.paymonitor.api.enums.MonitorExceptionType;
import com.dianping.ba.finance.exchange.paymonitor.api.enums.PayPlanStatus;
import com.dianping.ba.finance.exchange.paymonitor.job.paycheck.BasePayCheckRule;
import com.dianping.ba.finance.exchange.paymonitor.job.paycheck.PayCheckResult;

/**
 * Created by adam.huang on 2014/8/8.
 */
public class PayFailedCheck extends BasePayCheckRule {
    @Override
    public boolean filter(PayPlanMonitorData payPlanMonitorData) {
        return payPlanMonitorData.getStatus() == PayPlanStatus.PAY_FAILED.value();
    }

    @Override
    public PayCheckResult check(PayPlanMonitorData payPlanMonitorData) {
        return createResult(false,true, MonitorExceptionType.PP_PAY_FAILED);
    }
}
