package com.dianping.ba.finance.exchange.biz.impl
import com.dianping.ba.finance.exchange.api.AccessTokenService
import com.dianping.ba.finance.exchange.api.ReceiveNotifyRecordService
import com.dianping.ba.finance.exchange.api.ReceiveNotifyService
import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyRecordData
import com.dianping.ba.finance.exchange.api.dtos.ReceiveNotifyDTO
import com.dianping.finance.common.swallow.SwallowEventBean
import com.dianping.finance.common.swallow.SwallowProducer
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
/**
 * Created by Administrator on 2014/7/25.
 */
class ReceiveNotifyHandleServiceObjectTest extends Specification {

    private ReceiveNotifyHandleServiceObject receiveNotifyHandleServiceObjectStub;

    private long timeoutMock;

    private ReceiveNotifyRecordService receiveNotifyRecordServiceMock;

    private ReceiveNotifyService receiveNotifyServiceMock;

    private AccessTokenService accessTokenServiceMock;

    private SwallowProducer receiveNotifyResultProducerMock;

    private ExecutorService executorServiceMock;

    public void setup(){
        receiveNotifyHandleServiceObjectStub = new ReceiveNotifyHandleServiceObject();

        timeoutMock = TimeUnit.MINUTES.toMillis(15);
        receiveNotifyHandleServiceObjectStub.setTimeout(timeoutMock);

        receiveNotifyRecordServiceMock = Mock();
        receiveNotifyHandleServiceObjectStub.receiveNotifyRecordService = receiveNotifyRecordServiceMock;

        receiveNotifyServiceMock = Mock();
        receiveNotifyHandleServiceObjectStub.receiveNotifyService = receiveNotifyServiceMock;

        accessTokenServiceMock = Mock();
        receiveNotifyHandleServiceObjectStub.accessTokenService = accessTokenServiceMock;

        receiveNotifyResultProducerMock = Spy();
        receiveNotifyHandleServiceObjectStub.receiveNotifyResultProducer = receiveNotifyResultProducerMock;

        executorServiceMock = Executors.newSingleThreadExecutor();
        receiveNotifyHandleServiceObjectStub.setExecutorService(executorServiceMock);
    }

    def "HandleReceiveNotify"() {
        setup:
            Calendar requestCal = Calendar.getInstance();
            requestCal.set(Calendar.MINUTE, requestCal.get(Calendar.MINUTE) - 16);
            Date date = new Date();

            accessTokenServiceMock.verifyAccessToken(_ as String,_ as Integer) >> {
                return true;
            }



            ReceiveNotifyDTO receiveNotifyDTO = ["applicationId":"1234","businessType":5,"receiveAmount":100.5,
                                             "payChannel":12,"payTime":date,"payerName":"asdf",
                                             "bizContent":"622612341234","memo":"nothing","requestTime":date,
                                             "token":"FCBA1DF9D50FF8B4D6D435C3A5A081F24C9CB967","attachment":""];
            def conditions = new PollingConditions(timeout: 5000, initialDelay: 1.5, factor: 1.25)
        when:
            receiveNotifyHandleServiceObjectStub.handleReceiveNotify(receiveNotifyDTO);

        then:
            conditions.eventually {
                1 * receiveNotifyRecordServiceMock.insertReceiveNotifyRecord(_ as ReceiveNotifyRecordData);

                1 * receiveNotifyResultProducerMock.fireSwallowEvent(_ as SwallowEventBean) >> null;

            }

    }
}
