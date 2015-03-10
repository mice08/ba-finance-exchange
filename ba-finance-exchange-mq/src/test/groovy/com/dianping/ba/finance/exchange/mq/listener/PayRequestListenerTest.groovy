package com.dianping.ba.finance.exchange.mq.listener
import com.dianping.ba.finance.exchange.api.PayRequestHandleService
import com.dianping.ba.finance.exchange.api.dtos.PayRequestDTO
import com.dianping.swallow.common.internal.message.SwallowMessage
import com.dianping.swallow.consumer.BackoutMessageException
import spock.lang.Specification
/**
 *
 */
class PayRequestListenerTest extends Specification {

    PayRequestListener payRequestListenerStub;

    PayRequestHandleService payRequestHandleServiceMock;

    void setup() {
        payRequestListenerStub = [];
        payRequestHandleServiceMock = Mock();
        payRequestListenerStub.payRequestHandleService = payRequestHandleServiceMock;
    }

    def "onMessage"() {
        setup:
        String content = "{\n" +
                "\"paySequence\":\"EP_226169_1410750503960\",\n" +
                "\"businessType\":7,\n" +
                "\"payeeName\":\"张三_0001234\",\n" +
                "\"payAmount\":100.00,\n" +
                "\"bankAccountNo\":\"102424981067\",\n" +
                "\"bankName\":\"中国银行\",\n" +
                "\"bankAccountName\":\"西安万达国际电影城有限公司\",\n" +
                "\"bankCity\":\"西安市\",\n" +
                "\"bankProvince\":\"陕西省\",\n" +
                "\"bankBranchName\":\"西安长安路支行\",\n" +
                "\"bankFullBranchName\":\"中国银行西安长安路支行\",\n" +
                "\"bankCode\":\"104791004502\",\n" +
                "\"bankAccountType\":2,\n" +
                "\"payBankName\": \"招商银行\",\n" +
                "\"payBankAccountNo\": \"121909245310505\",\n" +
                "\"requestTime\":\"2014-08-27T17:25:00+08\",\n" +
                "\"memo\":\"加班报销|XXX\"\n" +
                "}\n" +
                "";
        SwallowMessage swallowMessage = new SwallowMessage();
        swallowMessage.setContent(content);

        when:
        payRequestListenerStub.onMessage(swallowMessage);

        then:
        1 * payRequestHandleServiceMock.handleNewPayRequest(_ as PayRequestDTO);
    }

    def "onMessage with exception"() {
        setup:
        String content = "{\n" +
                "\"paySequence\":\"EP_226169_1410750503960\",\n" +
                "\"businessType\":7,\n" +
                "\"payeeName\":\"张三_0001234\",\n" +
                "\"payAmount\":100.00,\n" +
                "\"bankAccountNo\":\"102424981067\",\n" +
                "\"bankName\":\"中国银行\",\n" +
                "\"bankAccountName\":\"西安万达国际电影城有限公司\",\n" +
                "\"bankCity\":\"西安市\",\n" +
                "\"bankProvince\":\"陕西省\",\n" +
                "\"bankBranchName\":\"西安长安路支行\",\n" +
                "\"bankFullBranchName\":\"中国银行西安长安路支行\",\n" +
                "\"bankCode\":\"104791004502\",\n" +
                "\"bankAccountType\":2,\n" +
                "\"payBankName\": \"招商银行\",\n" +
                "\"payBankAccountNo\": \"121909245310505\",\n" +
                "\"requestTime\":\"2014-08-27T17:25:00+08\",\n" +
                "\"memo\":\"加班报销|XXX\"\n" +
                "}\n" +
                "";
        SwallowMessage swallowMessage = new SwallowMessage();
        swallowMessage.setContent(content);

        when:
        payRequestListenerStub.onMessage(swallowMessage);

        then:
        1 * payRequestHandleServiceMock.handleNewPayRequest(_ as PayRequestDTO) >> {
            throw new RuntimeException("123");
        };
        thrown BackoutMessageException;
    }
}
