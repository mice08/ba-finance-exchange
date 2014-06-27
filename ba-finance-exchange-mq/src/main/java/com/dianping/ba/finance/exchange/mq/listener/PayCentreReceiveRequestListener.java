package com.dianping.ba.finance.exchange.mq.listener;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.PayCentreReceiveRequestHandleService;
import com.dianping.ba.finance.exchange.api.dtos.PayCentreReceiveRequestDTO;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.swallow.SwallowMessageListener;
import com.dianping.swallow.common.message.Message;
import com.dianping.swallow.consumer.BackoutMessageException;

/**
 * 支付中心收款请求消息监听器
 */
public class PayCentreReceiveRequestListener extends SwallowMessageListener {

    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.mq.monitor。PayCentreReceiveRequestListener");

    private PayCentreReceiveRequestHandleService payCentreReceiveRequestHandleService;

    @Log(logBefore = true, logAfter = true, severity = 1)
    @Override
    public void onMessage(Message message) throws BackoutMessageException {
        MONITOR_LOGGER.info(String.format("PayCentreReceiveRequestListener.onMessage, message=%s", message));
        try {
            PayCentreReceiveRequestDTO payCentreReceiveRequestDTO = message.transferContentToBean(PayCentreReceiveRequestDTO.class);
            payCentreReceiveRequestHandleService.handleReceiveRequest(payCentreReceiveRequestDTO);
        } catch (Exception e) {
            MONITOR_LOGGER.error(String.format("severity=[2] PayOrderRequestListener.onMessage error, message=%s", message), e);
            throw new BackoutMessageException(e);
        }
    }

    public void setPayCentreReceiveRequestHandleService(PayCentreReceiveRequestHandleService payCentreReceiveRequestHandleService) {
        this.payCentreReceiveRequestHandleService = payCentreReceiveRequestHandleService;
    }
}
