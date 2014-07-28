package com.dianping.ba.finance.exchange.siteweb.action.ajax

import com.dianping.ba.finance.exchange.api.ReceiveNotifyService
import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData
import com.dianping.ba.finance.exchange.api.enums.BusinessType
import com.dianping.ba.finance.exchange.api.enums.ReceiveNotifyStatus
import com.dianping.ba.finance.exchange.siteweb.beans.CustomerInfoBean
import com.dianping.ba.finance.exchange.siteweb.services.CustomerNameService
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by noahshen on 14-7-28.
 */
class ReceiveNotifyAjaxActionTest extends Specification {

    private ReceiveNotifyAjaxAction receiveNotifyAjaxActionStub;

    private ReceiveNotifyService receiveNotifyServiceMock;

    private CustomerNameService customerNameServiceMock;

    void setup() {
        receiveNotifyAjaxActionStub = []

        receiveNotifyServiceMock = Mock();
        receiveNotifyAjaxActionStub.receiveNotifyService = receiveNotifyServiceMock;

        customerNameServiceMock = Mock();
        receiveNotifyAjaxActionStub.customerNameService = customerNameServiceMock;
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
}
