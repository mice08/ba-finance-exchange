package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.ExchangeOrderService;
import com.dianping.ba.finance.exchange.api.beans.GenericResult;
import com.dianping.ba.finance.exchange.api.datas.ExchangeOrderData;
import com.dianping.ba.finance.exchange.api.enums.ExchangeType;
import com.dianping.ba.finance.exchange.biz.dao.ExchangeOrderDAO;
import com.dianping.ba.finance.exchange.biz.producer.ExchangeOrderStatusChangeNotify;
import com.dianping.ba.finance.exchange.biz.utils.BizUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 遐
 * Date: 13-12-13
 * Time: 上午10:28
*/

public class ExchangeOrderServiceObject implements ExchangeOrderService {

    private ExchangeOrderDAO exchangeOrderDAO;
    private ExchangeOrderStatusChangeNotify exchangeOrderStatusChangeNotify;
    //private ShopFundAccountService shopFundAccountService;

    private static final AvatarLogger monitorLogger = AvatarLoggerFactory.getLogger(ExchangeOrderServiceObject.class);

    @Override
    public int insertExchangeOrder(ExchangeOrderData exchangeOrderData) {
     return  exchangeOrderDAO.insertExchangeOrder(exchangeOrderData);
    }

    @Override
    public GenericResult<Integer> updateExchangeOrderToSuccess(List<Integer> orderIds) {

        GenericResult genericResult = new GenericResult<Integer>();
        List successExchangeOrders = new ArrayList<Integer>();
        List failedExchangeOrders = new ArrayList<Integer>();
        List unprocessedOrders = new ArrayList<Integer>();
        unprocessedOrders.addAll(orderIds);
        genericResult.setSuccessList(successExchangeOrders);
        genericResult.setFailList(failedExchangeOrders);
        genericResult.setUnprocessedList(unprocessedOrders);
        int processExchangeOrderId = 0;
        try {
            for(int orderId: orderIds){
                processExchangeOrderId = orderId;
                if (isOrderValid(orderId)) {
                    ExchangeOrderData exchangeOrderData = exchangeOrderDAO.loadExchangeOrderByOrderId(orderId);
                    if(exchangeOrderData != null && exchangeOrderData.getStatus() != ExchangeType.Success.getExchangeType()) {
                        Date orderDate = retrieveCurrentTime();
                        exchangeOrderDAO.updateExchangeOrderData(orderId, orderDate, ExchangeType.Success.getExchangeType());
                        exchangeOrderData.setStatus(ExchangeType.Success.ordinal());
                        exchangeOrderData.setOrderDate(orderDate);
                        //shopFundAccountService.updateShopFundAccountCausedByExchangeOrderSuccess(exchangeOrderData);
                        exchangeOrderStatusChangeNotify.exchangeOrderStatusChangeNotify(exchangeOrderData);
                    }
                    successExchangeOrders.add(orderId);
                } else {
                    failedExchangeOrders.add(orderId);
                    BizUtils.log(monitorLogger, System.currentTimeMillis(), "updateExchangeOrderToSuccess", "ignore",
                            "orderId = " + orderId,
                            null);
                }
            }
        } catch (Exception e) {
            failedExchangeOrders.add(processExchangeOrderId);
            BizUtils.log(monitorLogger, System.currentTimeMillis(), "updateExchangeOrderToSuccess", "error",
                    "orderId = " + processExchangeOrderId,
                    e);
        }
        unprocessedOrders.removeAll(successExchangeOrders);
        unprocessedOrders.removeAll(failedExchangeOrders);
        return genericResult;
    }

    private Date retrieveCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    private boolean isOrderValid(int orderId) {
        if (orderId > 0) {
            return true;
        }
        return false;
    }

    public void setExchangeOrderDAO(ExchangeOrderDAO exchangeOrderDAO) {
        this.exchangeOrderDAO = exchangeOrderDAO;
    }

    public void setExchangeOrderStatusChangeNotify(ExchangeOrderStatusChangeNotify exchangeOrderStatusChangeNotify) {
        this.exchangeOrderStatusChangeNotify = exchangeOrderStatusChangeNotify;
    }
}
