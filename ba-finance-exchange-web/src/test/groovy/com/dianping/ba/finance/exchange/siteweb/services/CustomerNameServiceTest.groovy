package com.dianping.ba.finance.exchange.siteweb.services

import com.dianping.ba.finance.exchange.api.datas.PayOrderData
import com.dianping.ba.finance.exchange.api.enums.BusinessType
import com.dianping.customerinfo.api.CustomerInfoService
import com.dianping.customerinfo.dto.CustomerLite
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by noahshen on 14-7-7.
 */
class CustomerNameServiceTest extends Specification {

    private CustomerNameService customerNameServiceStub;

    private CustomerInfoService customerInfoServiceMock;

    void setup() {
        customerNameServiceStub = [];

        customerInfoServiceMock = Mock();
        customerNameServiceStub.customerInfoService = customerInfoServiceMock;
    }

    @Unroll
    def "get customer name"(Integer businessType, Integer customerId, String customerName) {
        given:
        PayOrderData poDate = [businessType: businessType, customerId: customerId];
        customerInfoServiceMock.getCustomerLitesFuture(_ as List<Integer>, _ as Integer) >> { args ->
            def customerLiteList = [];
            args[0].each {
                CustomerLite customerLite = [customerID: it, customerName: "客户名称" + it];
                customerLiteList.add(customerLite)
            }
            customerLiteList
        }

        expect:
        customerName == customerNameServiceStub.getCustomerName([poDate], 8787)[customerId];

        where:
        businessType                       | customerId | customerName
        BusinessType.GROUP_PURCHASE.value()       | 87871      | "客户名称87871"
        BusinessType.ADVERTISEMENT.value() | 87871      | null
    }
}
