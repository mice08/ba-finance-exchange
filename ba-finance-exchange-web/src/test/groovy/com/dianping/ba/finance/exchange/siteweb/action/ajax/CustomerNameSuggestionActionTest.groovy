package com.dianping.ba.finance.exchange.siteweb.action.ajax

import com.dianping.ba.finance.exchange.api.enums.BusinessType
import com.dianping.ba.finance.exchange.siteweb.beans.CustomerNameSuggestionBean
import com.dianping.ba.finance.exchange.siteweb.services.CustomerNameService
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by noahshen on 14-7-8.
 */
class CustomerNameSuggestionActionTest extends Specification {

    private CustomerNameSuggestionAction customerNameSuggestionActionStub;

    private CustomerNameService customerNameServiceMock;

    void setup() {
        customerNameSuggestionActionStub = new CustomerNameSuggestionAction();

        customerNameServiceMock = Mock();
        customerNameSuggestionActionStub.customerNameService = customerNameServiceMock;
    }

    @Unroll
    def "FetchCustomerNameSuggestion"(String customerNamePrefix, String businessType, Integer customerId, String customerName) {
        given:
        customerNameSuggestionActionStub.businessType = businessType;
        customerNameSuggestionActionStub.q = customerNamePrefix;
        customerNameServiceMock.getCustomerNameSuggestion(_ as String, _ as Integer, _ as Integer, _ as Integer) >> { args ->
            CustomerNameSuggestionBean suggestionBean = [customerId: 123, customerName: args[0] + "-客户名称"];
            [suggestionBean]
        }

        expect:
        customerNameSuggestionActionStub.fetchCustomerNameSuggestion();
        customerId == customerNameSuggestionActionStub.msg["suggestion"][0]?.customerId;
        customerName == customerNameSuggestionActionStub.msg["suggestion"][0]?.customerName;

        where:
        customerNamePrefix | businessType                        | customerId | customerName
        null               | null                                | null       | null
        "ABC"              | null                                | null       | null
        "ABC"              | BusinessType.ADVERTISEMENT.value()  | 123        | "ABC-客户名称"
        "商户"              | BusinessType.GROUP_PURCHASE.value() | 123        | "商户-客户名称"
    }
}
