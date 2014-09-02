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
class InPaymentCheckTest extends Specification {
    InPaymentCheck inPaymentCheckStub;
    PayPlanService payPlanServiceMock;
    PayOrderService payOrderServiceMock;

    void setup(){
        inPaymentCheckStub = new InPaymentCheck();
        payPlanServiceMock = Mock();
        inPaymentCheckStub.payPlanService = payPlanServiceMock;
        payOrderServiceMock = Mock();
        inPaymentCheckStub.payOrderService = payOrderServiceMock;
    }

    @Unroll
    def "check filter"(int paramStatus,boolean expectResult){
        setup:
        PayPlanMonitorData payPlanMonitorData = ["status":paramStatus];
        expect:
        expectResult == inPaymentCheckStub.filter(payPlanMonitorData)
        where:
        paramStatus || expectResult
        1           || false
        2           || false
        3           || false
        4           || true
        5           || false
        6           || false
        7           || false
        8           || false
    }

    def "check sequence not found"(){
        setup:
        PayPlanMonitorData payPlanMonitorData = [];
        payPlanServiceMock.getPaySequenceById(_ as Integer)>>{null};
        expect:
        MonitorExceptionType.PP_PAY_SEQUENCE_NOT_FOUND == inPaymentCheckStub.check(payPlanMonitorData).monitorExceptionType
    }

    def "check payorder not found"(){
        setup:
        PayPlanMonitorData payPlanMonitorData = [];
        payPlanServiceMock.getPaySequenceById(_ as Integer)>>{" "};
        payOrderServiceMock.getPayOrderBySequence(_ as String)>>{null};
        expect:
        MonitorExceptionType.PP_PAY_ORDER_NOT_FOUND == inPaymentCheckStub.check(payPlanMonitorData).monitorExceptionType
    }

    @Unroll
    def "check valid"(int paramStatus,boolean expectValid){
        setup:
        PayPlanMonitorData payPlanMonitorData = ["updateTime":new Date()];
        PayOrderMonitorData payOrderMonitorData = ["status":paramStatus];
        payPlanServiceMock.getPaySequenceById(_ as Integer)>>{" "};
        payOrderServiceMock.getPayOrderBySequence(_ as String)>>{payOrderMonitorData};
        expect:
        expectValid == inPaymentCheckStub.check(payPlanMonitorData).isValided()
        where:
        paramStatus |  expectValid
        1           |  true
        2           |  true
        3           |  false
        4           |  false
    }

    def "check timeout"(){
        setup:
        PayPlanMonitorData payPlanMonitorData = ["updateTime":new Date()];
        PayOrderMonitorData payOrderMonitorData = ["status":3];
        payPlanServiceMock.getPaySequenceById(_ as Integer)>>{" "};
        payOrderServiceMock.getPayOrderBySequence(_ as String)>>{payOrderMonitorData};
        expect:
        MonitorExceptionType.PP_IN_PAYMENT_TIMEOUT == inPaymentCheckStub.check(payPlanMonitorData).monitorExceptionType
    }

    def "check invalid state"(){
        setup:
        PayPlanMonitorData payPlanMonitorData = ["updateTime":new Date()];
        PayOrderMonitorData payOrderMonitorData = ["status":4];
        payPlanServiceMock.getPaySequenceById(_ as Integer)>>{" "};
        payOrderServiceMock.getPayOrderBySequence(_ as String)>>{payOrderMonitorData};
        expect:
        MonitorExceptionType.PP_IN_PAYMENT_INVALID_STATE == inPaymentCheckStub.check(payPlanMonitorData).monitorExceptionType
    }
}
