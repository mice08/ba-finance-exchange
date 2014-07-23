package com.dianping.ba.finance.exchange.siteweb.services
import com.dianping.ba.finance.exchange.api.datas.PayOrderData
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData
import com.dianping.ba.finance.exchange.api.enums.BusinessType
import com.dianping.customerinfo.api.CustomerInfoService
import com.dianping.customerinfo.dto.Customer
import com.dianping.customerinfo.dto.CustomerLite
import com.dianping.midas.finance.api.dto.CorporationDTO
import com.dianping.midas.finance.api.service.CorporationService
import spock.lang.Specification
import spock.lang.Unroll
/**
 * Created by noahshen on 14-7-7.
 */
class CustomerNameServiceTest extends Specification {

    private CustomerNameService customerNameServiceStub;

    private CustomerInfoService customerInfoServiceMock;

    private CorporationService corporationServiceMock;


    void setup() {
        customerNameServiceStub = [];

        customerInfoServiceMock = Mock();
        customerNameServiceStub.customerInfoService = customerInfoServiceMock;

        corporationServiceMock = Mock();
        customerNameServiceStub.corporationService = corporationServiceMock;

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

        corporationServiceMock.queryCorporationById(_ as Integer) >> { Integer id ->
            CorporationDTO corporationDTO = [id: id, name: "客户名称-广告-" + id]
            corporationDTO
        }

        expect:
        customerName == customerNameServiceStub.getCustomerName([poDate], 8787)[customerId];

        where:
        businessType                        | customerId | customerName
        BusinessType.GROUP_PURCHASE.value() | 87871      | "客户名称87871"
        BusinessType.ADVERTISEMENT.value()  | 87872      | "客户名称-广告-87872"
        BusinessType.PREPAID_CARD.value()   | 87872      | null
    }

    @Unroll
    def "get ro customer name"(Integer businessType, Integer customerId, String customerName) {
        given:
        ReceiveOrderData roDate = [businessType: businessType, customerId: customerId];
        customerInfoServiceMock.getCustomerLitesFuture(_ as List<Integer>, _ as Integer) >> { args ->
            def customerLiteList = [];
            args[0].each {
                CustomerLite customerLite = [customerID: it, customerName: "客户名称" + it];
                customerLiteList.add(customerLite)
            }
            customerLiteList
        }
        corporationServiceMock.queryCorporationById(_ as Integer) >> { Integer id ->
            CorporationDTO corporationDTO = [id: id, name: "客户名称-广告-" + id]
            corporationDTO
        }

        expect:
        customerName == customerNameServiceStub.getROCustomerName([roDate], 8787)[customerId];

        where:
        businessType                        | customerId | customerName
        BusinessType.GROUP_PURCHASE.value() | 87871      | "客户名称87871"
        BusinessType.ADVERTISEMENT.value()  | 87872      | "客户名称-广告-87872"
        BusinessType.PREPAID_CARD.value()   | 87872      | null
    }

    @Unroll
    def "get customer name suggestions"(String customerNameParam, Integer businessType, Integer customerId, String customerName) {
        given:
        customerInfoServiceMock.searchByCustomerName(_ as String, 0, _ as Integer, _ as Integer) >> { args ->
            Customer customer = [customerID: 123, customerName: args[0] + "-客户名称"];
            [customer]
        }
        corporationServiceMock.queryCorporationByName(_ as String) >> { String name ->
            CorporationDTO corporationDTO = [id: 125, name: name + "-客户名称-广告"]
            [corporationDTO]
        }

        expect:
        def suggestionBean = customerNameServiceStub.getCustomerNameSuggestion(customerNameParam, 10, businessType, -1)[0];
        customerId == suggestionBean?.customerId;
        customerName == suggestionBean?.customerName;

        where:
        customerNameParam | businessType                        | customerId | customerName
        "商户"              | BusinessType.GROUP_PURCHASE.value() | 123        | "商户-客户名称"
        "商户"              | BusinessType.ADVERTISEMENT.value()  | 125        | "商户-客户名称-广告"
        "商户"              | BusinessType.PREPAID_CARD.value()   | null       | null


    }
}
