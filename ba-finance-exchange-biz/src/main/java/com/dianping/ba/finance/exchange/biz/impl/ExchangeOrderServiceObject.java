package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.ExchangeOrderService;
import com.dianping.ba.finance.exchange.api.beans.GenericResult;
import com.dianping.ba.finance.exchange.api.datas.ExchangeOrderData;
import com.dianping.ba.finance.exchange.api.enums.ExchangeOrderStatusEnum;
import com.dianping.ba.finance.exchange.biz.dao.ExchangeOrderDao;
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

    private ExchangeOrderDao exchangeOrderDao;
    private ExchangeOrderStatusChangeNotify exchangeOrderStatusChangeNotify;

    private static final AvatarLogger monitorLogger = AvatarLoggerFactory.getLogger(ExchangeOrderServiceObject.class);

    @Override
    public int insertExchangeOrder(ExchangeOrderData exchangeOrderData) {
     return  exchangeOrderDao.insertExchangeOrder(exchangeOrderData);
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
                    ExchangeOrderData exchangeOrderData = exchangeOrderDao.loadExchangeOrderByOrderId(orderId);
                    if(isExchangeOrderValid(exchangeOrderData)) {
                        Date orderDate = retrieveCurrentTime();
                        int affectedRows = exchangeOrderDao.updateExchangeOrderData(orderId, orderDate, ExchangeOrderStatusEnum.SUCCESS.getExchangeOrderStatus());
                        exchangeOrderData.setStatus(ExchangeOrderStatusEnum.SUCCESS.ordinal());
                        exchangeOrderData.setOrderDate(orderDate);
                        if(affectedRows > 0){
                            exchangeOrderStatusChangeNotify.exchangeOrderStatusChangeNotify(exchangeOrderData);
                        }
                    }
                    successExchangeOrders.add(orderId);
                } else {
                    throw new RuntimeException("Exchange order ID is not valid!");
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

    private boolean isExchangeOrderValid(ExchangeOrderData exchangeOrderData){
        if(exchangeOrderData != null && exchangeOrderData.getStatus() != ExchangeOrderStatusEnum.SUCCESS.getExchangeOrderStatus()) {
            return true;
        }
        return false;
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

    public void setExchangeOrderDao(ExchangeOrderDao exchangeOrderDao) {
        this.exchangeOrderDao = exchangeOrderDao;
    }

    public void setExchangeOrderStatusChangeNotify(ExchangeOrderStatusChangeNotify exchangeOrderStatusChangeNotify) {
        this.exchangeOrderStatusChangeNotify = exchangeOrderStatusChangeNotify;
    }
}
