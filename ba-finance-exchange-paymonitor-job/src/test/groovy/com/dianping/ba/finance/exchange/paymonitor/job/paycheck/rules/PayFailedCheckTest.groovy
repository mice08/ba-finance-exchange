package com.dianping.ba.finance.exchange.paymonitor.job.paycheck.rules

import com.dianping.ba.finance.exchange.paymonitor.api.datas.PayPlanMonitorData
import com.dianping.ba.finance.exchange.paymonitor.api.enums.MonitorExceptionType
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by adam.huang on 2014/8/8.
 */
class PayFailedCheckTest extends Specification {
    PayFailedCheck payFailedCheckStub;

    void setup(){
        payFailedCheckStub = new PayFailedCheck();
    }

    @Unroll
    def "check filter"(int paramStatus,boolean expectResult){
        setup:
        PayPlanMonitorData payPlanMonitorData = ["status":paramStatus];
        expect:
        expectResult == payFailedCheckStub.filter(payPlanMonitorData)
        where:
        paramStatus || expectResult
        1           || false
        2           || false
        3           || false
        4           || false
        5           || false
        6           || false
        7           || false
        8           || true
    }

    def "check pay failed"(){
        setup:
        PayPlanMonitorData payPlanMonitorData = [];
        expect:
        MonitorExceptionType.PP_PAY_FAILED == payFailedCheckStub.check(payPlanMonitorData).monitorExceptionType
    }
}
