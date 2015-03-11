package com.dianping.ba.finance.exchange.biz.impl

import com.dianping.ba.finance.exchange.api.datas.ExpensePayRequestData
import com.dianping.ba.finance.exchange.biz.dao.PayRequestDao
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by adam.huang on 2014/9/29.
 */
class ExpenseServiceObjectTest extends Specification {

    ExpenseServiceObject expenseServiceObjectStub;
    PayRequestDao payRequestDaoMock;

    void setup() {
        expenseServiceObjectStub = new ExpenseServiceObject();
        payRequestDaoMock = Mock();
        expenseServiceObjectStub.payRequestDao = payRequestDaoMock;
    }

    @Unroll
    def "FindExpenseDataByDate"(String paramSeq, String expectSeq) {
        given:
        payRequestDaoMock.findExpensePayDataByDate(_ as Integer, _ as Date, _ as Date) >> {
            ExpensePayRequestData expensePayRequestData = ["paySequence":paramSeq];
            [expensePayRequestData];
        }

        expect:
        expectSeq == expenseServiceObjectStub.findExpenseDataByDate(new Date(), new Date()).get(0).paySequence

        where:
        paramSeq  ||  expectSeq
        "a"       ||  "a"
        ""        ||  ""
        "asfd"    ||  "asfd"

    }
}
