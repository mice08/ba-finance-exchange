package com.dianping.ba.finance.exchange.siteweb.services
import com.dianping.ba.finance.exchange.api.datas.PayOrderData
import com.dianping.ba.finance.exchange.api.enums.BusinessType
import com.dianping.customerinfo.api.CustomerInfoService
import com.dianping.customerinfo.dto.Customer
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
        BusinessType.GROUP_PURCHASE.value()| 87871      | "客户名称87871"
        BusinessType.ADVERTISEMENT.value() | 87871      | null
    }

    @Unroll
    def "get customer name suggestions"(String customerNameParam, Integer businessType, Integer customerId, String customerName) {
        given:
        customerInfoServiceMock.searchByCustomerName(_ as String, 0, _ as Integer, _ as Integer) >> { args ->
            Customer customer = [customerID: 123, customerName: args[0] + "-客户名称"];
            [customer]
        }

        expect:
        def suggestionBean = customerNameServiceStub.getCustomerNameSuggestion(customerNameParam, 10, businessType, -1)[0];
        customerId == suggestionBean?.customerId;
        customerName == suggestionBean?.customerName;

        where:
        customerNameParam  | businessType                        | customerId | customerName
        "商户"              | BusinessType.ADVERTISEMENT.value()  | null       | null
        "商户"              | BusinessType.GROUP_PURCHASE.value() | 123        | "商户-客户名称"

    }
}
