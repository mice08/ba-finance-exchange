package ba.finance.exchange.midasreco.biz.impl
import com.dianping.ba.finance.exchange.api.ReceiveOrderMonitorService
import com.dianping.ba.finance.exchange.api.dtos.ReceiveOrderMonitorSearchDTO
import com.dianping.ba.finance.exchange.midasreco.biz.impl.MidasRecoServiceObject
import com.dianping.ba.finance.settle.api.InvoiceMonitorService
import com.dianping.ba.finance.settle.api.dtos.InvoiceMonitorSearchDTO
import spock.lang.Specification
/**
 * Created by adam.huang on 2014/8/19.
 */
class MidasRecoServiceObjectTest extends Specification {

    MidasRecoServiceObject midasRecoServiceObjectStub;
    ReceiveOrderMonitorService receiveOrderMonitorServiceMock;
    InvoiceMonitorService invoiceMonitorServiceMock;

    public void setup(){
        midasRecoServiceObjectStub = new MidasRecoServiceObject();
        receiveOrderMonitorServiceMock = Mock();
        midasRecoServiceObjectStub.receiveOrderMonitorService = receiveOrderMonitorServiceMock;
        invoiceMonitorServiceMock = Mock();
        midasRecoServiceObjectStub.invoiceMonitorService = invoiceMonitorServiceMock;
    }

    def "SaveReconciliationData"() {
        setup:
        when:
        midasRecoServiceObjectStub.saveReconciliationData();
        then:
        1 * receiveOrderMonitorServiceMock.insertReceiveOrderRecoDatas(_ as ReceiveOrderMonitorSearchDTO)>>{
            []
        };
        1 * invoiceMonitorServiceMock.insertInvoiceRecoDatas(_ as InvoiceMonitorSearchDTO)>>{
            []
        };
    }
}
