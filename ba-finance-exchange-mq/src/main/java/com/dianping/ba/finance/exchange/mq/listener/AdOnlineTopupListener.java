package com.dianping.ba.finance.exchange.mq.listener;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.dtos.AdOnlineTopupDTO;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.swallow.SwallowMessageListener;
import com.dianping.swallow.common.message.Message;
import com.dianping.swallow.consumer.BackoutMessageException;

/**
 * 推广在线重置消息
 */
public class AdOnlineTopupListener extends SwallowMessageListener {

    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger(AdOnlineTopupListener.class);

    @Log(logBefore = true, logAfter = true, severity = 1)
    @Override
    public void onMessage(Message message) throws BackoutMessageException {
        MONITOR_LOGGER.info(String.format("AdOnlineTopupListener.onMessage, message=%s", message));
        try {
            AdOnlineTopupDTO adOnlineTopupDTO = message.transferContentToBean(AdOnlineTopupDTO.class);

        } catch (Exception e) {
            MONITOR_LOGGER.error(String.format("severity=[2] AdOnlineTopupListener.onMessage error, message=%s", message), e);
            throw new BackoutMessageException(e);
        }
    }

}
