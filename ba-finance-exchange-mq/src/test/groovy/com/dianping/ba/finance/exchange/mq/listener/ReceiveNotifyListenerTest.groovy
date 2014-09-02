package com.dianping.ba.finance.exchange.mq.listener
import com.dianping.ba.finance.exchange.api.ReceiveNotifyHandleService
import com.dianping.ba.finance.exchange.api.dtos.ReceiveNotifyDTO
import com.dianping.swallow.common.internal.message.SwallowMessage
import com.dianping.swallow.consumer.BackoutMessageException
import spock.lang.Specification
/**
 * Created by Administrator on 2014/7/23.
 */
class ReceiveNotifyListenerTest extends Specification {
    ReceiveNotifyListener receiveNotifyListenerStub;
    ReceiveNotifyHandleService receiveNotifyHandleServiceMock;

    void setup(){
        receiveNotifyListenerStub = [];
        receiveNotifyHandleServiceMock = Mock();
        receiveNotifyListenerStub.setReceiveNotifyHandleService(receiveNotifyHandleServiceMock);
    }

    def "onMessage"() {
        setup:
            String content = "{\"applicationId\":\"1234\","+
                                "\"businessType\":5,"+
                                "\"receiveAmount\":100.5,"+
                                "\"payChannel\":12,"+
                                "\"payTime\":1401862698,"+
                                "\"payerName\":\"asdf\","+
                                "\"bizContent\":\"622612341234\","+
                                "\"memo\":\"nothing\","+
                                "\"requestTime\":1401862698,"+
                                "\"token\":\"59E9C57D5ACCD4843D35F094BA71FB445745BD2C\"}";
            SwallowMessage swallowMessage = new SwallowMessage();
            swallowMessage.setContent(content);

        when:
            receiveNotifyListenerStub.onMessage(swallowMessage);

        then:
            1 * receiveNotifyHandleServiceMock.handleReceiveNotify(_ as ReceiveNotifyDTO);
    }

    def "onMessage with exception"(){
        setup:
            String content = "{\"applicationId\":\"1234\","+
                "\"businessType\":5,"+
                "\"receiveAmount\":100.5,"+
                "\"payChannel\":12,"+
                "\"payTime\":1401862698,"+
                "\"payerName\":\"asdf\","+
                "\"bizContent\":\"622612341234\","+
                "\"memo\":\"nothing\","+
                "\"requestTime\":1401862698,"+
                "\"token\":\"59E9C57D5ACCD4843D35F094BA71FB445745BD2C\"}";
            SwallowMessage swallowMessage = new SwallowMessage();
            swallowMessage.setContent(content);

            receiveNotifyHandleServiceMock.handleReceiveNotify(_ as ReceiveNotifyDTO) >> {
                throw new RuntimeException()
            }
        when:
            receiveNotifyListenerStub.onMessage(swallowMessage);
        then:
            thrown BackoutMessageException;
    }
}
