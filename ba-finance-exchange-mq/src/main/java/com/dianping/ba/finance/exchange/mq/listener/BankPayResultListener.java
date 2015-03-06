package com.dianping.ba.finance.exchange.mq.listener;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.PayOrderService;
import com.dianping.ba.finance.exchange.api.dtos.BankPayResultDTO;
import com.dianping.ba.finance.exchange.api.enums.PayOrderStatus;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.swallow.SwallowMessageListener;
import com.dianping.swallow.common.message.Message;
import com.dianping.swallow.consumer.BackoutMessageException;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 付款计划请求消息监听器
 */
public class BankPayResultListener extends SwallowMessageListener {

    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.mq.monitor.PayRequestResultListener");

    @Autowired
    private PayOrderService payOrderService;

    @Log(logBefore = true, logAfter = true, severity = 1)
    @Override
    public void onMessage(Message message) throws BackoutMessageException {
        MONITOR_LOGGER.info(String.format("BankPayResultListener.onMessage, message=%s", message));
        try {
            BankPayResultDTO bankPayResultDTO = message.transferContentToBean(BankPayResultDTO.class);
            PayOrderStatus payOrderStatus = PayOrderStatus.valueOf(bankPayResultDTO.getStatus());
            if(payOrderStatus != PayOrderStatus.BANK_PAYING){
                int result = payOrderService.updatePayOrderStatus(bankPayResultDTO.getInstId(), payOrderStatus.value(), bankPayResultDTO.getMessage());
                if(result != 1){
                    MONITOR_LOGGER.error(String.format("severity=1], update pay order status failed!. bankPayResultDTO=[%s]", ToStringBuilder.reflectionToString(bankPayResultDTO)));
                }
            }
        } catch (Exception e) {
            MONITOR_LOGGER.error(String.format("severity=[2] BankPayResultListener.onMessage error, message=%s", message), e);
            throw new BackoutMessageException(e);
        }
    }

}
