package com.dianping.ba.finance.exchange.siteweb.services
import com.dianping.ba.finance.exchange.api.datas.PayOrderData
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData
import com.dianping.ba.finance.exchange.api.enums.BusinessType
import com.dianping.ba.finance.expense.api.ExpensePayeeService
import com.dianping.ba.finance.expense.api.dtos.PayeeInfoDTO
import com.dianping.customerinfo.api.CustomerInfoService
import com.dianping.customerinfo.dto.CustomerLite
import com.dianping.customerinfo.dto.CustomerShopLite
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

    private ExpensePayeeService expensePayeeServiceMock;

    void setup() {
        customerNameServiceStub = [];

        customerInfoServiceMock = Mock();
        customerNameServiceStub.customerInfoService = customerInfoServiceMock;

        corporationServiceMock = Mock();
        customerNameServiceStub.corporationService = corporationServiceMock;

        expensePayeeServiceMock = Mock();
        customerNameServiceStub.expensePayeeService = expensePayeeServiceMock;


    }

    @Unroll
    def "get customer name"(Integer businessType, Integer customerId, String customerName) {
        given:
        PayOrderData poDate = [businessType: businessType, customerId: customerId];
        customerInfoServiceMock.getCustomerLites(_ as List<Integer>, _ as Integer) >> { args ->
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
        expensePayeeServiceMock.getPayeeInfoList(_ as List<Integer>) >> { args ->
            def payeeInfoDTOList = [];
            args[0].each {
                PayeeInfoDTO payeeInfoDTO = [payeeId: it, payeeName: "客户名称" + it];
                payeeInfoDTOList << payeeInfoDTO
            }
            payeeInfoDTOList
        }

        expect:
        customerName == customerNameServiceStub.getCustomerName([poDate], 8787)[customerId];

        where:
        businessType                        | customerId | customerName
        BusinessType.GROUP_PURCHASE.value() | 87871      | "客户名称87871"
        BusinessType.ADVERTISEMENT.value()  | 87872      | "客户名称-广告-87872"
        BusinessType.SHAN_HUI.value()       | 87871      | "客户名称87871"
        BusinessType.EXPENSE.value()        | 87871      | "客户名称87871"
        BusinessType.PREPAID_CARD.value()   | 87872      | null
    }

    @Unroll
    def "get ro customer name"(Integer businessType, Integer customerId, String customerName) {
        given:
        ReceiveOrderData roDate = [businessType: businessType, customerId: customerId];
        customerInfoServiceMock.getCustomerLites(_ as List<Integer>, _ as Integer) >> { args ->
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
        customerInfoServiceMock.searchByCustomerAndShopInfo(_ as String, null, true, _ as Integer, _ as Integer) >> { args ->
            CustomerShopLite customer = [customerID: 123, customreName: args[0] + "-客户名称"];
            com.dianping.customerinfo.dto.Tuple t = new com.dianping.customerinfo.dto.Tuple(null, [customer])
            t
        }
        corporationServiceMock.queryCorporationByName(_ as String, _ as Integer) >> { String name, Integer maxSize ->
            CorporationDTO corporationDTO = [id: 125, name: name + "-客户名称-广告"]
            [corporationDTO]
        }
        expensePayeeServiceMock.searchByPayeeName(_ as String) >> { args ->
            PayeeInfoDTO payeeInfoDTO = [payeeId: 123, payeeName: args[0] + "-客户名称"];
            [payeeInfoDTO]
        }

        expect:
        def suggestionBean = customerNameServiceStub.getCustomerNameSuggestion(customerNameParam, 10, businessType, -1)[0];
        customerId == suggestionBean?.customerId;
        customerName == suggestionBean?.customerName;

        where:
        customerNameParam | businessType                        | customerId | customerName
        "商户"              | BusinessType.GROUP_PURCHASE.value() | 123        | "商户-客户名称"
        "商户"              | BusinessType.ADVERTISEMENT.value()  | 125        | "商户-客户名称-广告"
        "商户"              | BusinessType.SHAN_HUI.value()       | 123        | "商户-客户名称"
        "商户"              | BusinessType.EXPENSE.value()        | 123        | "商户-客户名称"
        "商户"              | BusinessType.PREPAID_CARD.value()   | null       | null
    }

    @Unroll
    def "get Customer Info by bizContent"(String bizContent, Integer businessType, Integer customerId, String customerName) {
        given:
        corporationServiceMock.queryCorporationByBizContent(_ as String) >> { String bizContentParam ->
            CorporationDTO corporationDTO = [id: 125, name: bizContentParam + "-客户名称-广告"]
            corporationDTO
        }

        expect:
        def customerInfoBean = customerNameServiceStub.getCustomerInfo(businessType, bizContent, -1);
        customerId == customerInfoBean?.customerId;
        customerName == customerInfoBean?.customerName;

        where:
        bizContent | businessType                        | customerId | customerName
        "AD123123" | BusinessType.ADVERTISEMENT.value()  | 125        | "AD123123-客户名称-广告"
        "TG123456" | BusinessType.GROUP_PURCHASE.value() | null       | null

    }

    @Unroll
    def "get Customer Info by Id"(Integer customerIdParam, Integer businessType, Integer customerId, String customerName) {
        given:
        corporationServiceMock.queryCorporationById(_ as Integer) >> { Integer custId ->
            CorporationDTO corporationDTO = [id: custId, name: "客户名称-广告"]
            corporationDTO
        }

        expect:
        def customerInfoBean = customerNameServiceStub.getCustomerInfoById(businessType, customerIdParam, -1);
        customerId == customerInfoBean?.customerId;
        customerName == customerInfoBean?.customerName;

        where:
        customerIdParam | businessType                        | customerId | customerName
        123             | BusinessType.ADVERTISEMENT.value()  | 123        | "客户名称-广告"
        789             | BusinessType.GROUP_PURCHASE.value() | null       | null

    }
}
