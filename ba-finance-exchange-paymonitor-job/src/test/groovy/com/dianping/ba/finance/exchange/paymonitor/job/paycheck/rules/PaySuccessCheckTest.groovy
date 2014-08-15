package com.dianping.ba.finance.exchange.paymonitor.job.paycheck.rules

import com.dianping.ba.finance.exchange.paymonitor.api.PayOrderService
import com.dianping.ba.finance.exchange.paymonitor.api.PayPlanService
import com.dianping.ba.finance.exchange.paymonitor.api.datas.PayOrderMonitorData
import com.dianping.ba.finance.exchange.paymonitor.api.datas.PayPlanMonitorData
import com.dianping.ba.finance.exchange.paymonitor.api.enums.MonitorExceptionType
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by adam.huang on 2014/8/8.
 */
class PaySuccessCheckTest extends Specification {
    PaySuccessCheck paySuccessCheckStub;

    PayPlanService payPlanServiceMock;
    PayOrderService payOrderServiceMock;

    void setup(){
        paySuccessCheckStub = new PaySuccessCheck();
        payPlanServiceMock = Mock();
        paySuccessCheckStub.payPlanService = payPlanServiceMock;
        payOrderServiceMock = Mock();
        paySuccessCheckStub.payOrderService = payOrderServiceMock;
    }

    @Unroll
    def "check filter"(int paramStatus,boolean expectResult){
        setup:
        PayPlanMonitorData payPlanMonitorData = ["status":paramStatus];
        expect:
        expectResult == paySuccessCheckStub.filter(payPlanMonitorData)
        where:
        paramStatus || expectResult
        1           || false
        2           || false
        3           || false
        4           || false
        5           || true
        6           || false
        7           || false
        8           || false
    }

    def "check sequence not found"(){
        setup:
        PayPlanMonitorData payPlanMonitorData = [];
        payPlanServiceMock.getPaySequenceById(_ as Integer)>>{null};
        expect:
        MonitorExceptionType.PP_PAY_SEQUENCE_NOT_FOUND == paySuccessCheckStub.check(payPlanMonitorData).monitorExceptionType
    }

    def "check payorder not found"(){
        setup:
        PayPlanMonitorData payPlanMonitorData = [];
        payPlanServiceMock.getPaySequenceById(_ as Integer)>>{" "};
        payOrderServiceMock.getPayOrderBySequence(_ as String)>>{null};
        expect:
        MonitorExceptionType.PP_PAY_ORDER_NOT_FOUND == paySuccessCheckStub.check(payPlanMonitorData).monitorExceptionType
    }

    @Unroll
    def "check valid"(int paramStatus,boolean expectValid){
        setup:
        PayPlanMonitorData payPlanMonitorData = ["updateTime":new Date()];
        PayOrderMonitorData payOrderMonitorData = ["status":paramStatus];
        payPlanServiceMock.getPaySequenceById(_ as Integer)>>{" "};
        payOrderServiceMock.getPayOrderBySequence(_ as String)>>{payOrderMonitorData};
        expect:
        expectValid == paySuccessCheckStub.check(payPlanMonitorData).isValided()
        where:
        paramStatus |  expectValid
        1           |  false
        2           |  false
        3           |  true
        4           |  false
    }

    @Unroll
    def "check invalid state"(int paramStatus,int expect){
        setup:
        PayPlanMonitorData payPlanMonitorData = ["updateTime":new Date()];
        PayOrderMonitorData payOrderMonitorData = ["status":paramStatus];
        payPlanServiceMock.getPaySequenceById(_ as Integer)>>{" "};
        payOrderServiceMock.getPayOrderBySequence(_ as String)>>{payOrderMonitorData};
        expect:
        MonitorExceptionType.PP_PAY_SUCCESS_INVALID_STATE == paySuccessCheckStub.check(payPlanMonitorData).monitorExceptionType
        where:
        paramStatus || expect
        1           || 0
        2           || 0
        4           || 0
    }
}
