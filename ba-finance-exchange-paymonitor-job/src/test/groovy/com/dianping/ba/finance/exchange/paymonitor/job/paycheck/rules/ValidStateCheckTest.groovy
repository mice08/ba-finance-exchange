package com.dianping.ba.finance.exchange.paymonitor.job.paycheck.rules

import com.dianping.ba.finance.exchange.paymonitor.api.datas.PayPlanMonitorData
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by adam.huang on 2014/8/8.
 */
class ValidStateCheckTest extends Specification {
    ValidStateCheck validStateCheckStub;

    void setup(){
        validStateCheckStub = new ValidStateCheck();
    }

    @Unroll
    def "check filter"(int paramStatus,boolean expectResult){
        setup:
        PayPlanMonitorData payPlanMonitorData = ["status":paramStatus];
        expect:
        expectResult == validStateCheckStub.filter(payPlanMonitorData)
        where:
        paramStatus || expectResult
        1           || true
        2           || false
        3           || true
        4           || false
        5           || false
        6           || false
        7           || true
        8           || false
    }
}
