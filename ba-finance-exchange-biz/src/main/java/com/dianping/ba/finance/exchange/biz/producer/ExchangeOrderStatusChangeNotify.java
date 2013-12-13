package com.dianping.ba.finance.exchange.biz.producer;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.datas.ExchangeOrderData;
import com.dianping.ba.finance.exchange.biz.utils.BizUtils;
import com.dianping.combiz.util.JsonUtils;
import com.dianping.swallow.producer.Producer;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: jinyu.yin
 * Date: 13-12-13
 * Time: 下午2:57
 * To change this template use File | Settings | File Templates.
 */
public class ExchangeOrderStatusChangeNotify {

    private static final AvatarLogger monitorLogger = AvatarLoggerFactory
            .getLogger("com.dianping.ba.finance.exchange.biz.producer.ExchangeOrderStatusChangeNotify");
    Producer producerClient;

    public void exchangeOrderStatusChangeNotify(ExchangeOrderData exchangeOrderData) {
        String message = null;
        try {
            message = createJson(exchangeOrderData);
            producerClient.sendMessage(message);
            monitorLogger.info("ExchangeOrderStatusChangeNotify invoked!!!");
        } catch (Exception ex) {
            BizUtils.log(monitorLogger, System.currentTimeMillis(), "exchangeOrderStatusChangeNotify", "error", "ExchangeOrderId=" + exchangeOrderData.getExchangeOrderId() + "&OrderStatus=" +
                    exchangeOrderData.getStatus() + "&message=" + message, ex);
        }
    }

    public String createJson(ExchangeOrderData exchangeOrderData) throws JSONException, IOException {
        return JsonUtils.toStr(exchangeOrderData);
//        java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String changeDate = format1.format(exchangeOrderData.getOrderDate());
//        JSONObject jsonObject=new JSONObject();
//        jsonObject.put("exchangeOrderId",exchangeOrderData.getExchangeOrderId());
//        jsonObject.put("changeDate",changeDate);
//        jsonObject.put("status",exchangeOrderData.getStatus());
//        jsonObject.put("orderType",exchangeOrderData.getOrderType());
//        jsonObject.put("orderAmount",exchangeOrderData.getOrderAmount());
//        jsonObject.put("bankAccountNo",exchangeOrderData.getBankAccountNo());
//        jsonObject.put("bankAccountName",exchangeOrderData.getBankAccountName());
//        jsonObject.put("bankName",exchangeOrderData.getBankName());
//        jsonObject.put("lastUpdateDate",format1.format(exchangeOrderData.getLastUpdateDate()));
//        jsonObject.put("memo",exchangeOrderData.getMemo());
//        jsonObject.put("addDate",format1.format(exchangeOrderData.getAddDate()));
//        return jsonObject.toString();
    }

    public void setProducerClient(Producer producerClient) {
        this.producerClient = producerClient;
    }
}
