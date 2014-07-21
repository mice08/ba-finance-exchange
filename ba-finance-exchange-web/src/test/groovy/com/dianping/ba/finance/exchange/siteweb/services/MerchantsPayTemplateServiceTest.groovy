package com.dianping.ba.finance.exchange.siteweb.services

import com.dianping.ba.finance.exchange.api.enums.BusinessType
import com.dianping.ba.finance.exchange.siteweb.beans.BusinessExportInfoBean
import com.dianping.ba.finance.exchange.siteweb.beans.PayOrderExportBean
import spock.lang.Specification

import javax.servlet.ServletOutputStream
import javax.servlet.http.HttpServletResponse

/**
 * Created by noahshen on 14-7-14.
 */
class MerchantsPayTemplateServiceTest extends Specification {

    MerchantsPayTemplateService merchantsPayTemplateServiceStub;

    void setup() {
        merchantsPayTemplateServiceStub = [];
        merchantsPayTemplateServiceStub.templateName = "招商银行支付模板Service";
    }

    def "Init"() {
        when:
        merchantsPayTemplateServiceStub.init();

        then:
        notThrown(NullPointerException)
    }

    def "create excel and download"() {
        setup:
        String fileName = "付款单"
        def responseMock = Mock(HttpServletResponse);

        PayOrderExportBean commonExportBean = [bankAccountType: 1, bankName: "XX银行", payAmount: 12.0G, businessType: 1]
        PayOrderExportBean sameBankExportBean = [bankAccountType: 0, bankName: "民生银行", payAmount: 87.87G, businessType: 2]
        def exportBeans = [commonExportBean, sameBankExportBean];
        BusinessExportInfoBean grouponExportInfoBean = [businessType: BusinessType.GROUP_PURCHASE, use: "团购"]
        BusinessExportInfoBean bookingExportInfoBean = [businessType: BusinessType.BOOKING, use: "预订"]
        merchantsPayTemplateServiceStub.businessExportInfoBeanMap = [1: grouponExportInfoBean, 2: bookingExportInfoBean]


        when:
        merchantsPayTemplateServiceStub.init();
        merchantsPayTemplateServiceStub.createExcelAndDownload(responseMock, fileName, exportBeans);

        then:
        1 * responseMock.getOutputStream() >> new MockServletOutputStream();
        1 * responseMock.reset();
        1 * responseMock.setHeader(_ as String, _ as String);
        1 * responseMock.setContentType(_ as String);
    }

    private class MockServletOutputStream extends ServletOutputStream {

        @Override
        void write(int b) throws IOException {
        }
    }
}
