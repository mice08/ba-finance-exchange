package com.dianping.ba.finance.exchange.paymonitor.job.paycheck.rules;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.paymonitor.api.datas.PayPlanMonitorData;
import com.dianping.ba.finance.exchange.paymonitor.api.enums.MonitorExceptionType;
import com.dianping.ba.finance.exchange.paymonitor.api.enums.PayPlanStatus;
import com.dianping.ba.finance.exchange.paymonitor.job.paycheck.BasePayCheckRule;
import com.dianping.ba.finance.exchange.paymonitor.job.paycheck.PayCheckResult;

/**
 * Created by adam.huang on 2014/8/8.
 */
public class PayFailedCheck extends BasePayCheckRule {
    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.paymonitor.job.PayPlanDataChecker");

    @Override
    public boolean filter(PayPlanMonitorData payPlanMonitorData) {
        return payPlanMonitorData.getStatus() == PayPlanStatus.PAY_FAILED.value();
    }

    @Override
    public PayCheckResult check(PayPlanMonitorData payPlanMonitorData) {
        MONITOR_LOGGER.info("============PayFailedCheck===============");
        return createResult(false,true, MonitorExceptionType.PP_PAY_FAILED);
    }
}
