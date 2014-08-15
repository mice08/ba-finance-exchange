package com.dianping.ba.finance.exchange.paymonitor.biz.impl

import com.dianping.ba.finance.exchange.paymonitor.biz.dao.PayPlanDao
import spock.lang.Specification

/**
 * Created by adam.huang on 2014/8/7.
 */
class PayPlanServiceObjectTest extends Specification {
    PayPlanServiceObject payPlanServiceObjectStub;
    PayPlanDao payPlanDaoMock;

    def setup(){
        payPlanServiceObjectStub = new PayPlanServiceObject();

        payPlanDaoMock = Mock();
        payPlanServiceObjectStub.payPlanDao = payPlanDaoMock;
    }

    def "FindPayPlansByDate"() {
        given:
        Date startDate = new Date();
        Date endDate = new Date();
        when:
        payPlanServiceObjectStub.findPayPlansByDate(startDate,endDate);
        then:
        1 * payPlanDaoMock.findPayPlansByDate(_ as Date,_ as Date);
    }

    def "GetPayPlanById"() {
        given:
        int ppid = 1234;
        when:
        payPlanServiceObjectStub.getPayPlanById(ppid);
        then:
        1 * payPlanDaoMock.loadPayPlanById(_ as Integer);
    }

    def "GetPaySequenceById"() {
        given:
        int ppid = 1234;
        when:
        payPlanServiceObjectStub.getPaySequenceById(ppid);
        then:
        1 * payPlanDaoMock.loadPaySequenceById(_ as Integer);
    }
}
