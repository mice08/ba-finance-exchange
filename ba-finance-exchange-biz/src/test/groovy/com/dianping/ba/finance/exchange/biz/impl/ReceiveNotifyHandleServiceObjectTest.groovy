package com.dianping.ba.finance.exchange.biz.impl
import com.dianping.ba.finance.exchange.api.AccessTokenService
import com.dianping.ba.finance.exchange.api.RORNMatchFireService
import com.dianping.ba.finance.exchange.api.ReceiveNotifyRecordService
import com.dianping.ba.finance.exchange.api.ReceiveNotifyService
import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyRecordData
import com.dianping.ba.finance.exchange.api.dtos.ReceiveNotifyDTO
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

    private RORNMatchFireService rornMatchFireServiceMock;

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

        receiveNotifyResultProducerMock = Mock();
        receiveNotifyHandleServiceObjectStub.receiveNotifyResultProducer = receiveNotifyResultProducerMock;

        executorServiceMock = Executors.newSingleThreadExecutor();
        receiveNotifyHandleServiceObjectStub.executorService = executorServiceMock;

        rornMatchFireServiceMock = Mock();
        receiveNotifyHandleServiceObjectStub.rornMatchFireService = rornMatchFireServiceMock
    }

    def "HandleReceiveNotify  Normal"() {
        setup:
            Date date = new Date();

            accessTokenServiceMock.verifyAccessToken(_ as String,_ as Integer) >> {
                return true;
            }

            ReceiveNotifyDTO receiveNotifyDTO = ["applicationId":"31234","businessType":5,"receiveAmount":100.5,
                                             "payChannel":12,"payTime":date,"payerName":"asdf","bankId":123456,
                                             "bizContent":"622612341234","memo":"nothing","requestTime":date,
                                             "token":"FCBA1DF9D50FF8B4D6D435C3A5A081F24C9CB967","attachment":""];
            def conditions = new PollingConditions(timeout: 5000, initialDelay: 1.5, factor: 1.25)
        when:
            receiveNotifyHandleServiceObjectStub.handleReceiveNotify(receiveNotifyDTO);

        then:
            conditions.eventually {
                1 * receiveNotifyRecordServiceMock.insertReceiveNotifyRecord(_ as ReceiveNotifyRecordData);

                //1 * receiveNotifyResultProducerMock.fireSwallowEvent(_ as SwallowEventBean) >> null;

            }
    }

    def "HandleReceiveNotify Timeout"(){
        setup:
        Calendar requestCal = Calendar.getInstance();
        requestCal.set(Calendar.MINUTE, requestCal.get(Calendar.MINUTE) - 16);
        Date date = requestCal.getTime();

        accessTokenServiceMock.verifyAccessToken(_ as String,_ as Integer) >> {
            return true;
        }

        ReceiveNotifyDTO receiveNotifyDTO = ["applicationId":"61234","businessType":5,"receiveAmount":100.5,
                                             "payChannel":12,"payTime":date,"payerName":"asdf","bankId":123456,
                                             "bizContent":"622612341234","memo":"nothing","requestTime":date,
                                             "token":"FCBA1DF9D50FF8B4D6D435C3A5A081F24C9CB967","attachment":""];
        def conditions = new PollingConditions(timeout: 5000, initialDelay: 1.5, factor: 1.25)
        when:
        receiveNotifyHandleServiceObjectStub.handleReceiveNotify(receiveNotifyDTO);

        then:
        conditions.eventually {
            1 * receiveNotifyRecordServiceMock.insertReceiveNotifyRecord(_ as ReceiveNotifyRecordData);

            //1 * receiveNotifyResultProducerMock.fireSwallowEvent(_ as SwallowEventBean) >> null;

        }
    }

    def "HandleReceiveNotify Invalid Token"(){
        setup:
        Date date = new Date();

        accessTokenServiceMock.verifyAccessToken(_ as String,_ as Integer) >> {
            return true;
        }

        ReceiveNotifyDTO receiveNotifyDTO = ["applicationId":"51234","businessType":5,"receiveAmount":100.5,
                                             "payChannel":12,"payTime":date,"payerName":"asdf","bankId":123456,
                                             "bizContent":"622612341234","memo":"nothing","requestTime":date,
                                             "token":"AAAA","attachment":""];
        def conditions = new PollingConditions(timeout: 5000, initialDelay: 1.5, factor: 1.25)
        when:
        receiveNotifyHandleServiceObjectStub.handleReceiveNotify(receiveNotifyDTO);

        then:
        conditions.eventually {
            1 * receiveNotifyRecordServiceMock.insertReceiveNotifyRecord(_ as ReceiveNotifyRecordData);

            //1 * receiveNotifyResultProducerMock.fireSwallowEvent(_ as SwallowEventBean) >> null;

        }
    }

    def "HandleReceiveNotify Invalid ReceiveAmount"(){
        setup:
        Date date = new Date();

        accessTokenServiceMock.verifyAccessToken(_ as String,_ as Integer) >> {
            return true;
        }

        ReceiveNotifyDTO receiveNotifyDTO = ["applicationId":"41234","businessType":5,"receiveAmount":-100.5,
                                             "payChannel":12,"payTime":date,"payerName":"asdf","bankId":123456,
                                             "bizContent":"622612341234","memo":"nothing","requestTime":date,
                                             "token":"FCBA1DF9D50FF8B4D6D435C3A5A081F24C9CB967","attachment":""];
        def conditions = new PollingConditions(timeout: 5000, initialDelay: 1.5, factor: 1.25)
        when:
        receiveNotifyHandleServiceObjectStub.handleReceiveNotify(receiveNotifyDTO);

        then:
        conditions.eventually {
            1 * receiveNotifyRecordServiceMock.insertReceiveNotifyRecord(_ as ReceiveNotifyRecordData);

            //1 * receiveNotifyResultProducerMock.fireSwallowEvent(_ as SwallowEventBean) >> null;

        }
    }

    def "HandleReceiveNotify Duplicate ApplicationId"(){
        setup:
        Date date = new Date();

        accessTokenServiceMock.verifyAccessToken(_ as String,_ as Integer) >> {
            return true;
        }

        ReceiveNotifyDTO receiveNotifyDTO = ["applicationId":"1234","businessType":5,"receiveAmount":100.5,
                                             "payChannel":12,"payTime":date,"payerName":"asdf","bankId":123456,
                                             "bizContent":"622612341234","memo":"nothing","requestTime":date,
                                             "token":"FCBA1DF9D50FF8B4D6D435C3A5A081F24C9CB967","attachment":""];
        def conditions = new PollingConditions(timeout: 5000, initialDelay: 1.5, factor: 1.25)
        when:
        receiveNotifyHandleServiceObjectStub.handleReceiveNotify(receiveNotifyDTO);

        then:
        conditions.eventually {
            1 * receiveNotifyRecordServiceMock.insertReceiveNotifyRecord(_ as ReceiveNotifyRecordData);

           //1 * receiveNotifyResultProducerMock.fireSwallowEvent(_ as SwallowEventBean) >> null;

        }
    }
}
