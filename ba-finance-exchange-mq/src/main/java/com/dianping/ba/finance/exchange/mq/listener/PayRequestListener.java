package com.dianping.ba.finance.exchange.mq.listener;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.PayRequestHandleService;
import com.dianping.ba.finance.exchange.api.dtos.PayRequestDTO;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.swallow.SwallowMessageListener;
import com.dianping.swallow.common.message.Message;
import com.dianping.swallow.consumer.BackoutMessageException;

/**
 * 付款计划请求消息监听器
 */
public class PayRequestListener extends SwallowMessageListener {

    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.mq.monitor.PayRequestListener");

    private PayRequestHandleService payRequestHandleService;

    @Log(logBefore = true, logAfter = true, severity = 1)
    @Override
    public void onMessage(Message message) throws BackoutMessageException {
        MONITOR_LOGGER.info(String.format("PayRequestListener.onMessage, message=%s", message));
        try {
            PayRequestDTO payRequestDTO = message.transferContentToBean(PayRequestDTO.class);
            payRequestHandleService.handleNewPayRequest(payRequestDTO);
        } catch (Exception e) {
            MONITOR_LOGGER.error(String.format("severity=[2] PayRequestListener.onMessage error, message=%s", message), e);
            throw new BackoutMessageException(e);
        }
    }

    public void setPayRequestHandleService(PayRequestHandleService payRequestHandleService) {
        this.payRequestHandleService = payRequestHandleService;
    }
}
