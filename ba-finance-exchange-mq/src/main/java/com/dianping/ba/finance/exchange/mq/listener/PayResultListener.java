package com.dianping.ba.finance.exchange.mq.listener;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.PayOrderDomainService;
import com.dianping.ba.finance.exchange.api.dtos.BankPayResultDTO;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.swallow.SwallowMessageListener;
import com.dianping.swallow.common.message.Message;
import com.dianping.swallow.consumer.BackoutMessageException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 付款计划请求消息监听器
 */
public class PayResultListener extends SwallowMessageListener {

    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.mq.monitor.PayRequestResultListener");

    @Autowired
    private PayOrderDomainService payOrderDomainService;

    @Log(logBefore = true, logAfter = true, severity = 1)
    @Override
    public void onMessage(Message message) throws BackoutMessageException {
        MONITOR_LOGGER.info(String.format("BankPayResultListener.onMessage, message=%s", message));
        try {
            payOrderDomainService.handleBankPayResult(message.transferContentToBean(BankPayResultDTO.class));
        } catch (Exception e) {
            MONITOR_LOGGER.error(String.format("severity=[2] BankPayResultListener.onMessage error, message=%s", message), e);
            throw new BackoutMessageException(e);
        }
    }

    public void setPayOrderDomainService(PayOrderDomainService payOrderDomainService) {
        this.payOrderDomainService = payOrderDomainService;
    }


}
