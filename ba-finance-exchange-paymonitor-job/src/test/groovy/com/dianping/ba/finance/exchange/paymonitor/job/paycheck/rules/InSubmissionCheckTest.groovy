package com.dianping.ba.finance.exchange.paymonitor.job.paycheck.rules

import com.dianping.ba.finance.exchange.paymonitor.api.datas.PayPlanMonitorData
import com.dianping.ba.finance.exchange.paymonitor.api.enums.MonitorExceptionType
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by adam.huang on 2014/8/8.
 */
class InSubmissionCheckTest extends Specification {
    InSubmissionCheck inSubmissionCheckStub;

    void setup(){
        inSubmissionCheckStub = new InSubmissionCheck();
    }

    @Unroll
    def "check filter"(int paramStatus,boolean expectResult){
        setup:
        PayPlanMonitorData payPlanMonitorData = ["status":paramStatus];
        expect:
        expectResult == inSubmissionCheckStub.filter(payPlanMonitorData)
        where:
        paramStatus || expectResult
        1           || false
        2           || true
        3           || false
        4           || false
        5           || false
        6           || false
        7           || false
        8           || false
    }

    def "check timeout"(){
        setup:
        PayPlanMonitorData payPlanMonitorData = [];
        payPlanMonitorData.setUpdateTime(new Date());
        expect:
        MonitorExceptionType.PP_IN_SUBMISSION_TIMEOUT == inSubmissionCheckStub.check(payPlanMonitorData).getMonitorExceptionType()

    }
}
