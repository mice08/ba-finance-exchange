package com.dianping.ba.finance.exchange.siteweb.action.ajax

import com.dianping.ba.finance.exchange.api.ReceiveBankService
import com.dianping.ba.finance.exchange.api.ReceiveNotifyService
import com.dianping.ba.finance.exchange.api.beans.ReceiveNotifySearchBean
import com.dianping.ba.finance.exchange.api.datas.ReceiveBankData
import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData
import com.dianping.ba.finance.exchange.api.enums.BusinessType
import com.dianping.ba.finance.exchange.api.enums.ReceiveNotifyStatus
import com.dianping.ba.finance.exchange.api.enums.ReceiveType
import com.dianping.ba.finance.exchange.siteweb.beans.CustomerInfoBean
import com.dianping.ba.finance.exchange.siteweb.services.CustomerNameService
import com.dianping.core.type.PageModel
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by noahshen on 14-7-28.
 */
class ReceiveNotifyAjaxActionTest extends Specification {

    private ReceiveNotifyAjaxAction receiveNotifyAjaxActionStub;

    private ReceiveNotifyService receiveNotifyServiceMock;

    private CustomerNameService customerNameServiceMock;

    private ReceiveBankService receiveBankServiceMock;


    void setup() {
        receiveNotifyAjaxActionStub = new ReceiveNotifyAjaxActionMock()

        receiveNotifyServiceMock = Mock();
        receiveNotifyAjaxActionStub.receiveNotifyService = receiveNotifyServiceMock;

        customerNameServiceMock = Mock();
        receiveNotifyAjaxActionStub.customerNameService = customerNameServiceMock;

        receiveBankServiceMock = Mock();
        receiveNotifyAjaxActionStub.receiveBankService = receiveBankServiceMock;
    }

    @Unroll
    def "testRornCancelLink" (Integer rnId, Integer roMatcherId, Integer actionCode) {
        given:
        receiveNotifyAjaxActionStub.rnId = rnId;
        receiveNotifyAjaxActionStub.roMatcherId = roMatcherId;
        receiveNotifyServiceMock.removeReceiveNotifyMatchRelation(1, 2) >> {
            true;
        };
        receiveNotifyServiceMock.removeReceiveNotifyMatchRelation(1, 1) >> {
            false;
        };
        receiveNotifyServiceMock.removeReceiveNotifyMatchRelation(2, 2) >> {
            throw new Exception();
            false;
        };

        expect:
        receiveNotifyAjaxActionStub.rornCancelLink();
        actionCode == receiveNotifyAjaxActionStub.code;

        where:
        rnId | roMatcherId | actionCode
        1    | 2           | ReceiveNotifyAjaxAction.SUCCESS_CODE
        1    | 1           | ReceiveNotifyAjaxAction.SUCCESS_CODE
        2    | 2           | ReceiveNotifyAjaxAction.ERROR_CODE
    }

    @Unroll
    def "testFindNotifiesByROId" (Integer roMatcherId, Integer actionCode) {
        given:
        receiveNotifyAjaxActionStub.roMatcherId = roMatcherId;
        receiveNotifyServiceMock.findMatchedReceiveNotify(1) >> {
            ReceiveNotifyData rnData = [receiveNotifyId: 123, payTime: new Date(), customerId: 123]
            [rnData];
        }
        receiveNotifyServiceMock.findMatchedReceiveNotify(2) >> {
            throw new Exception();
            [];
        }
        customerNameServiceMock.getRORNCustomerName(_ as ArrayList, _ as Integer) >> {
            def map = new HashMap()
            map.put(123, "12")
            map;
        }

        expect:
        receiveNotifyAjaxActionStub.findNotifiesByROId();
        actionCode == receiveNotifyAjaxActionStub.code;

        where:
        roMatcherId | actionCode
        1           | ReceiveNotifyAjaxAction.SUCCESS_CODE
        2           | ReceiveNotifyAjaxAction.ERROR_CODE
    }

    @Unroll
    def "LoadReceiveOrderInfo"(String applicationId, Integer businessType, Integer actionCode, Integer resultCustomerId) {
        given:
        receiveNotifyAjaxActionStub.applicationId = applicationId;
        receiveNotifyAjaxActionStub.businessType = businessType;
        receiveNotifyServiceMock.loadUnmatchedReceiveNotifyByApplicationId(_ as ReceiveNotifyStatus, _ as Integer, _ as String) >> { args ->
            ReceiveNotifyData rnData = [receiveNotifyId: 123]
            rnData
        }
        customerNameServiceMock.getCustomerInfoById(_ as Integer, _ as Integer, _ as Integer) >> {
            CustomerInfoBean customerInfoBean = [customerId: 123, customerName: "客户名"]
            customerInfoBean
        }

        expect:
        receiveNotifyAjaxActionStub.loadReceiveOrderInfo();
        actionCode == receiveNotifyAjaxActionStub.code
        resultCustomerId == receiveNotifyAjaxActionStub.msg["receiveInfo"]?.customerId;

        where:
        applicationId | businessType                       | actionCode                           | resultCustomerId
        ""            | BusinessType.ADVERTISEMENT.value() | ReceiveNotifyAjaxAction.SUCCESS_CODE | null
        null          | BusinessType.ADVERTISEMENT.value() | ReceiveNotifyAjaxAction.SUCCESS_CODE | null
        "APP123"      | BusinessType.ADVERTISEMENT.value() | ReceiveNotifyAjaxAction.SUCCESS_CODE | 123
    }

    @Unroll
    def "jsonExecute"(Integer businessType, String receiveAmount, Integer resultCode) {
        given:
        receiveNotifyAjaxActionStub.businessType = businessType
        receiveNotifyServiceMock.paginateReceiveNotifyList(_ as ReceiveNotifySearchBean, _ as Integer, _ as Integer) >> {
            PageModel pm = []
            ReceiveNotifyData rnData = [receiveNotifyId: 123,
                                        receiveType: ReceiveType.AD_FEE.value(),
                                        status: ReceiveNotifyStatus.INIT.value()]
            pm.records = [rnData]
            pm
        }
        customerNameServiceMock.getRORNCustomerName(_ as List, _ as Integer) >> {
            [:]
        }
        receiveBankServiceMock.loadReceiveBankByBankId(_ as Integer) >> {
            ReceiveBankData bankData = [bankId: 1]
            bankData
        }
        receiveNotifyServiceMock.loadTotalReceiveAmountByCondition(_ as ReceiveNotifySearchBean) >> BigDecimal.TEN

        expect:
        receiveNotifyAjaxActionStub.jsonExecute()
        resultCode == receiveNotifyAjaxActionStub.code

        where:
        businessType | receiveAmount| resultCode
        BusinessType.DEFAULT.value() | null | 0
        BusinessType.ADVERTISEMENT.value() | null | ReceiveNotifyAjaxAction.SUCCESS_CODE
        BusinessType.ADVERTISEMENT.value() | "10.87" | ReceiveNotifyAjaxAction.SUCCESS_CODE

    }

    private class ReceiveNotifyAjaxActionMock extends ReceiveNotifyAjaxAction {
        @Override
        int getLoginId() {
            return 0
        }
    }

}
