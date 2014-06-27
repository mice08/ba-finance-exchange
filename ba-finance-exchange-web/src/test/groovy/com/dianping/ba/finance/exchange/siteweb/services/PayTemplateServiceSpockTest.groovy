package com.dianping.ba.finance.exchange.siteweb.services
import com.dianping.ba.finance.exchange.siteweb.beans.PayOrderExportBean
import spock.lang.Specification

import javax.servlet.ServletOutputStream
import javax.servlet.http.HttpServletResponse
/**
 * Created by noahshen on 14-6-24.
 */
class PayTemplateServiceSpockTest extends Specification {

    PayTemplateService payTemplateServiceStub;


    void setup() {
        payTemplateServiceStub = [];
        payTemplateServiceStub.templateName = "民生银行支付模板Service";
    }

    def "Init"() {
        when:
        payTemplateServiceStub.init();

        then:
        notThrown(NullPointerException)
    }

    def "create excel and download"() {
        setup:
        String fileName = "付款单"
        def responseMock = Mock(HttpServletResponse);

        PayOrderExportBean commonExportBean = [bankAccountType: 1, bankName: "XX银行", payAmount: 12.0G]
        PayOrderExportBean sameBankExportBean = [bankAccountType: 0, bankName: "民生银行", payAmount: 87.87G]
        def exportBeans = [commonExportBean, sameBankExportBean];

        when:
        payTemplateServiceStub.init();
        payTemplateServiceStub.createExcelAndDownload(responseMock, fileName, exportBeans);

        then:
        1 * responseMock.getOutputStream() >> new MockServletOutputStream();
        1 * responseMock.reset();
        1 * responseMock.setHeader(_ as String, _ as String) ;
        1 * responseMock.setContentType(_ as String);
    }

    private class MockServletOutputStream extends ServletOutputStream {

        @Override
        void write(int b) throws IOException {
        }
    }
}
