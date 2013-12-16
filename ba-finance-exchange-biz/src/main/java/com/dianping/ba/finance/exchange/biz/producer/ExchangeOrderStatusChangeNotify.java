package com.dianping.ba.finance.exchange.biz.producer;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.dtos.ExchangeOrderDTO;
import com.dianping.ba.finance.exchange.biz.utils.BizUtils;
import com.dianping.ba.finance.exchange.biz.utils.JsonUtils;
import com.dianping.swallow.producer.Producer;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: jinyu.yin
 * Date: 13-12-13
 * Time: 下午2:57
 * To change this template use File | Settings | File Templates.
 */
public class ExchangeOrderStatusChangeNotify {

    private static final AvatarLogger monitorLogger = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.biz.producer.ExchangeOrderStatusChangeNotify");
    private Producer producerClient;

    public void exchangeOrderStatusChangeNotify(ExchangeOrderDTO exchangeOrderDTO) {
        long startTime = Calendar.getInstance().getTimeInMillis();
        String message = null;
        try {
            message = JsonUtils.toStr(exchangeOrderDTO);
            producerClient.sendMessage(message);
            monitorLogger.info("ExchangeOrderStatusChangeNotify invoked!!!");
        } catch (Exception ex) {
            BizUtils.log(monitorLogger, startTime, "exchangeOrderStatusChangeNotify", "error", "ExchangeOrderId=" + exchangeOrderDTO.getExchangeOrderId() + "&OrderStatus=" +
                    exchangeOrderDTO.getStatus() + "&message=" + message, ex);
        }
    }

    public void setProducerClient(Producer producerClient) {
        this.producerClient = producerClient;
    }
}
