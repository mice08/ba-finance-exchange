package ba.finance.exchange.midasreco.biz.impl

import com.dianping.ba.finance.exchange.api.ReceiveOrderMonitorService
import com.dianping.ba.finance.exchange.midasreco.api.InvoiceRecoService
import com.dianping.ba.finance.exchange.midasreco.api.ReceiveOrderRecoService
import com.dianping.ba.finance.exchange.midasreco.biz.impl.MidasRecoServiceObject
import com.dianping.ba.finance.settle.api.InvoiceMonitorService
import spock.lang.Specification

/**
 * Created by adam.huang on 2014/8/19.
 */
class MidasRecoServiceObjectTest extends Specification {

    MidasRecoServiceObject midasRecoServiceObjectStub;
    ReceiveOrderMonitorService receiveOrderMonitorServiceMock;
    InvoiceMonitorService invoiceMonitorServiceMock;
    InvoiceRecoService invoiceRecoServiceMock;
    ReceiveOrderRecoService receiveOrderRecoServiceMock;

    public void setup(){
        midasRecoServiceObjectStub = new MidasRecoServiceObject();
        receiveOrderMonitorServiceMock = Mock();
        midasRecoServiceObjectStub.receiveOrderMonitorService = receiveOrderMonitorServiceMock;
        invoiceMonitorServiceMock = Mock();
        midasRecoServiceObjectStub.invoiceMonitorService = invoiceMonitorServiceMock;
        invoiceRecoServiceMock = Mock();
        midasRecoServiceObjectStub.invoiceRecoService = invoiceRecoServiceMock;
        receiveOrderRecoServiceMock = Mock();
        midasRecoServiceObjectStub.receiveOrderRecoService = receiveOrderRecoServiceMock;
    }

    def "SaveReconciliationData"() {
        setup:
        when:
        midasRecoServiceObjectStub.saveReconciliationData();
        then:
        1 * receiveOrderMonitorServiceMock.findReceiveOrderMonitorDataByTime(_ as Date, _ as Date)>>{
            []
        };
        1 * invoiceMonitorServiceMock.findInvoiceMonitorDataByTime(_ as Date, _ as Date)>>{
            []
        };
        1 * receiveOrderRecoServiceMock.insertReceiveOrderRecoDatas(_ as List);
        1 * invoiceRecoServiceMock.insertInvoiceRecoDatas(_ as List);
    }
}
