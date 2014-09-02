package com.dianping.ba.finance.exchange.paymonitor.job.paycheck;

import com.dianping.ba.finance.exchange.paymonitor.api.datas.PayPlanMonitorData;

public interface PayCheckRule {

    /**
     * 过滤器，判断是否符合校验的前提
     * @param payPlanMonitorData
     * @return
     */
    public boolean filter(PayPlanMonitorData payPlanMonitorData);

    /**
     * 执行校验
     * @param payPlanMonitorData
     * @return
     */
    public PayCheckResult check(PayPlanMonitorData payPlanMonitorData);
}
