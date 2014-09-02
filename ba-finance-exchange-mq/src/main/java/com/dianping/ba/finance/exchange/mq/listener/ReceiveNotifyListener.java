package com.dianping.ba.finance.exchange.mq.listener;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.ReceiveNotifyHandleService;
import com.dianping.ba.finance.exchange.api.dtos.ReceiveNotifyDTO;
import com.dianping.finance.common.swallow.SwallowMessageListener;
import com.dianping.swallow.common.message.Message;
import com.dianping.swallow.consumer.BackoutMessageException;

/**
 * Created by Administrator on 2014/7/23.
 */
public class ReceiveNotifyListener extends SwallowMessageListener {
    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.settle.mq.monitor.PayRequestListener");

    private ReceiveNotifyHandleService receiveNotifyHandleService;

    @Override
    public void onMessage(Message msg) throws BackoutMessageException {
        MONITOR_LOGGER.info(String.format("PayRequestListener.onMessage, message=%s", msg));
        try {
            ReceiveNotifyDTO receiveNotifyDTO = msg.transferContentToBean(ReceiveNotifyDTO.class);
            receiveNotifyHandleService.handleReceiveNotify(receiveNotifyDTO);
        } catch (Exception e) {
            MONITOR_LOGGER.error(String.format("severity=[2] PayRequestListener.onMessage error, message=%s", msg), e);
            throw new BackoutMessageException(e);
        }
    }

    public void setReceiveNotifyHandleService(ReceiveNotifyHandleService receiveNotifyHandleService) {
        this.receiveNotifyHandleService = receiveNotifyHandleService;
    }
}
