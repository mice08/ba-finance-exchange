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
class RefundCheckTest extends Specification {
    RefundCheck refundCheckStub;
    PayPlanService payPlanServiceMock;
    PayOrderService payOrderServiceMock;

    void setup(){
        refundCheckStub = new RefundCheck();
        payPlanServiceMock = Mock();
        refundCheckStub.payPlanService = payPlanServiceMock;
        payOrderServiceMock = Mock();
        refundCheckStub.payOrderService = payOrderServiceMock;
    }

    @Unroll
    def "check filter"(int paramStatus,boolean expectResult){
        setup:
        PayPlanMonitorData payPlanMonitorData = ["status":paramStatus];
        expect:
        expectResult == refundCheckStub.filter(payPlanMonitorData)
        where:
        paramStatus || expectResult
        1           || false
        2           || false
        3           || false
        4           || false
        5           || false
        6           || true
        7           || false
        8           || false
    }

    def "check sequence not found"(){
        setup:
        PayPlanMonitorData payPlanMonitorData = [];
        payPlanServiceMock.getPaySequenceById(_ as Integer)>>{null};
        expect:
        MonitorExceptionType.PP_PAY_SEQUENCE_NOT_FOUND == refundCheckStub.check(payPlanMonitorData).monitorExceptionType
    }

    def "check payorder not found"(){
        setup:
        PayPlanMonitorData payPlanMonitorData = [];
        payPlanServiceMock.getPaySequenceById(_ as Integer)>>{" "};
        payOrderServiceMock.getPayOrderBySequence(_ as String)>>{null};
        expect:
        MonitorExceptionType.PP_PAY_ORDER_NOT_FOUND == refundCheckStub.check(payPlanMonitorData).monitorExceptionType
    }

    @Unroll
    def "check valid"(int paramStatus,boolean expectValid){
        setup:
        PayPlanMonitorData payPlanMonitorData = ["updateTime":new Date()];
        PayOrderMonitorData payOrderMonitorData = ["status":paramStatus];
        payPlanServiceMock.getPaySequenceById(_ as Integer)>>{" "};
        payOrderServiceMock.getPayOrderBySequence(_ as String)>>{payOrderMonitorData};
        expect:
        expectValid == refundCheckStub.check(payPlanMonitorData).isValided()
        where:
        paramStatus |  expectValid
        1           |  false
        2           |  false
        3           |  false
        4           |  true
    }

    def "check invalid state"(int paramStatus,int expect){
        setup:
        PayPlanMonitorData payPlanMonitorData = ["updateTime":new Date()];
        PayOrderMonitorData payOrderMonitorData = ["status":paramStatus];
        payPlanServiceMock.getPaySequenceById(_ as Integer)>>{" "};
        payOrderServiceMock.getPayOrderBySequence(_ as String)>>{payOrderMonitorData};
        expect:
        MonitorExceptionType.PP_REFUND_INVALID_STATE == refundCheckStub.check(payPlanMonitorData).monitorExceptionType
        where:
        paramStatus || expect
        1           || 0
        2           || 0
        3           || 0

    }
}
