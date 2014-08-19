package com.dianping.ba.finance.exchange.midasreco.biz.impl

import com.dianping.ba.finance.exchange.midasreco.api.datas.InvoiceRecoData
import com.dianping.ba.finance.exchange.midasreco.biz.dao.InvoiceRecoDao
import spock.lang.Specification
/**
 * Created by adam.huang on 2014/8/19.
 */
class InvoiceRecoServiceObjectTest extends Specification {

    InvoiceRecoServiceObject invoiceRecoServiceObjectStub;
    InvoiceRecoDao invoiceRecoDaoMock;

    public void setup(){
        invoiceRecoServiceObjectStub = new InvoiceRecoServiceObject();
        invoiceRecoDaoMock = Mock();
        invoiceRecoServiceObjectStub.invoiceRecoDao = invoiceRecoDaoMock;
    }

    def "InsertInvoiceRecoDatas"() {
        setup:
        InvoiceRecoData invoiceRecoData = [];
        when:
        invoiceRecoServiceObjectStub.insertInvoiceRecoDatas([invoiceRecoData]);
        then:
        1 * invoiceRecoDaoMock.insertInvoiceRecoDatas(_ as List);
    }

    def "Insert Empty List"() {
        setup:
        when:
        invoiceRecoServiceObjectStub.insertInvoiceRecoDatas([]);
        then:
        0 * invoiceRecoDaoMock.insertInvoiceRecoDatas(_ as List);
    }
}
