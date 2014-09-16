package com.dianping.ba.finance.exchange.siteweb.action.ajax
import com.dianping.ba.finance.exchange.api.ReceiveBankService
import com.dianping.ba.finance.exchange.api.ReceiveOrderService
import com.dianping.ba.finance.exchange.api.datas.ReceiveBankData
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData
import com.dianping.ba.finance.exchange.api.enums.BusinessType
import spock.lang.Specification
/**
 * Created by will on 14-7-1.
 */
class ImportTelTransferAjaxActionTest extends Specification {

    ImportTelTransferAjaxAction importTelTransferAjaxActionStub;

    ReceiveOrderService receiveOrderServiceMock;

    ReceiveBankService receiveBankServiceMock;

    void setup(){
        importTelTransferAjaxActionStub = [] as ImportTelTransferAjaxAction;
        receiveBankServiceMock = Mock();
        receiveOrderServiceMock = Mock();

        importTelTransferAjaxActionStub.receiveBankService = receiveBankServiceMock;
        importTelTransferAjaxActionStub.receiveOrderService = receiveOrderServiceMock;
    }

    def "ImportTelTransfer for Empty Excel"(String invalidFileMsg, String fileName) {
        given:
        importTelTransferAjaxActionStub.telTransferFile = new File(getClass().getResource(fileName) == null ? "" : getClass().getResource(fileName).getPath());

        expect:
        importTelTransferAjaxActionStub.importTelTransfer();
        invalidFileMsg == importTelTransferAjaxActionStub.invalidFileMsg;

        where:
        invalidFileMsg         | fileName
        "导入收款文件格式错误！"  | "telTrans_Null.xls"
        "导入收款文件格式错误！"  | "Null.xls"
    }

    def "ImportTelTransfer for Normal Excel"(boolean isNormal, String fileName) {
        given:
        importTelTransferAjaxActionStub.telTransferFile = new File(getClass().getResource(fileName).getPath());
        receiveBankServiceMock.loadReceiveBankByBankId(_ as Integer) >> {
            [] as ReceiveBankData;
        }
        receiveOrderServiceMock.createReceiveOrder(_ as ReceiveOrderData) >> 1

        expect:
        importTelTransferAjaxActionStub.importTelTransfer();
        isNormal == (importTelTransferAjaxActionStub.msg["totalCount"] == 2);

        where:
        isNormal | fileName
        true     | "telTrans_Normal.xls"
    }

    def "ImportTelTransfer for Normal Excel AD recieve"(boolean isNormal, String fileName) {
        given:
        importTelTransferAjaxActionStub.telTransferFile = new File(getClass().getResource(fileName).getPath());
        receiveBankServiceMock.loadReceiveBankByBankId(_ as Integer) >> {
            [businessType: BusinessType.ADVERTISEMENT.value()] as ReceiveBankData;
        }
        receiveOrderServiceMock.createReceiveOrder(_ as ReceiveOrderData) >> 1

        expect:
        importTelTransferAjaxActionStub.importTelTransfer();
        isNormal == (importTelTransferAjaxActionStub.msg["totalCount"] == 2);

        where:
        isNormal | fileName
        true     | "telTrans_Normal.xls"
    }

    def "ImportTelTransfer for Invalid Excel"(boolean isNormal, String fileName) {
        given:
        importTelTransferAjaxActionStub.telTransferFile = new File(getClass().getResource(fileName).getPath());

        expect:
        importTelTransferAjaxActionStub.importTelTransfer();
        isNormal == (importTelTransferAjaxActionStub.msg == null);

        where:
        isNormal | fileName
        false    | "telTrans_Wrong.xls"
        false    | "telTrans_Duplicate.xls"
    }

}
