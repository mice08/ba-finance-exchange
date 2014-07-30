package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.AccessTokenService;
import com.dianping.ba.finance.exchange.api.ReceiveNotifyRecordService;
import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyRecordData;
import com.dianping.ba.finance.exchange.api.dtos.ReceiveNotifyDTO;
import com.dianping.finance.common.swallow.SwallowEventBean;
import com.dianping.finance.common.swallow.SwallowProducer;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by Administrator on 2014/7/25.
 */
public class ReceiveNotifyHandlerServiceObjectTest {
    private ReceiveNotifyHandleServiceObject receiveNotifyHandleServiceObjectStub;

    private long timeoutMock;

    private ReceiveNotifyRecordService receiveNotifyRecordServiceMock;

    private AccessTokenService accessTokenServiceMock;

    private SwallowProducer receiveNotifyResultProducerMock;

    private ExecutorService executorServiceMock;

    @Before
    public void setUp(){
        receiveNotifyHandleServiceObjectStub = new ReceiveNotifyHandleServiceObject();

        timeoutMock = TimeUnit.MINUTES.toMillis(15);
        receiveNotifyHandleServiceObjectStub.setTimeout(timeoutMock);

        receiveNotifyRecordServiceMock = mock(ReceiveNotifyRecordService.class);
        receiveNotifyHandleServiceObjectStub.setReceiveNotifyRecordService(receiveNotifyRecordServiceMock);

        accessTokenServiceMock = mock(AccessTokenService.class);
        receiveNotifyHandleServiceObjectStub.setAccessTokenService(accessTokenServiceMock);

        receiveNotifyResultProducerMock = mock(SwallowProducer.class);
        receiveNotifyHandleServiceObjectStub.setReceiveNotifyResultProducer(receiveNotifyResultProducerMock);

        executorServiceMock = Executors.newSingleThreadExecutor();
        receiveNotifyHandleServiceObjectStub.setExecutorService(executorServiceMock);
    }

    @Test
    public void testHandleReceiveNotify(){
        ReceiveNotifyDTO receiveNotifyDTO = new ReceiveNotifyDTO();
        receiveNotifyDTO.setApplicationId("12345");
        receiveNotifyDTO.setBizContent("11111111111");
        receiveNotifyDTO.setMemo("");
        receiveNotifyDTO.setRequestTime(new Date());
        receiveNotifyDTO.setBusinessType(5);
        receiveNotifyDTO.setPayChannel(1);
        receiveNotifyDTO.setPayerName("1523");
        receiveNotifyDTO.setPayTime(new Date());
        receiveNotifyDTO.setReceiveAmount(BigDecimal.ZERO);
        receiveNotifyDTO.setToken("59E9C57D5ACCD4843D35F094BA71FB445745BD2C");
        receiveNotifyDTO.setAttachment("");
        receiveNotifyDTO.setCustomerId(1);

        receiveNotifyHandleServiceObjectStub.handleReceiveNotify(receiveNotifyDTO);

        verify(receiveNotifyRecordServiceMock,timeout(5000).times(1)).insertReceiveNotifyRecord(any(ReceiveNotifyRecordData.class));

        when(accessTokenServiceMock.verifyAccessToken(anyString(), anyInt())).thenReturn(true);

        verify(receiveNotifyResultProducerMock,timeout(5000).times(1)).fireSwallowEvent(any(SwallowEventBean.class));
    }
}
