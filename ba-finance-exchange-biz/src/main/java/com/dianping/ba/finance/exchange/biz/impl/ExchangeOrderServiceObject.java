package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.ExchangeOrderService;
import com.dianping.ba.finance.exchange.api.beans.GenericResult;
import com.dianping.ba.finance.exchange.api.datas.ExchangeOrderData;
import com.dianping.ba.finance.exchange.api.datas.ShopFundAccountFlowData;
import com.dianping.ba.finance.exchange.api.enums.FlowTypeEnum;
import com.dianping.ba.finance.exchange.api.enums.SourceTypeEnum;
import com.dianping.ba.finance.exchange.biz.dao.ExchangeOrderDAO;
import com.dianping.ba.finance.exchange.biz.dao.ShopFundAccountFlowDao;
import com.dianping.ba.finance.exchange.biz.utils.BizUtils;
import com.dianping.ba.finance.exchange.enums.ExchangeType;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: 遐
 * Date: 13-12-13
 * Time: 上午10:28
*/

public class ExchangeOrderServiceObject implements ExchangeOrderService {

    private ExchangeOrderDAO exchangeOrderDao;
    private ShopFundAccountFlowDao shopFundAccountFlowDao;

    private static final AvatarLogger monitorLogger = AvatarLoggerFactory.getLogger(ExchangeOrderServiceObject.class);

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
                    if(exchangeOrderData.getStatus() != ExchangeType.Success.getExchangeType()) {
                        Date orderDate = retrieveCurrentTime();
                        exchangeOrderDao.updateExchangeOrderData(orderId, orderDate, ExchangeType.Success.getExchangeType());
                        exchangeOrderData.setStatus(ExchangeType.Success.ordinal());
                        exchangeOrderData.setOrderDate(orderDate);
                        buildShopFundAccountFlowData(exchangeOrderData);
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

    private ShopFundAccountFlowData buildShopFundAccountFlowData(ExchangeOrderData exchangeOrder){
        ShopFundAccountFlowData paymentPlanShopFundAccountFlow = shopFundAccountFlowDao.loadShopFundAccountFlow(exchangeOrder.getExchangeOrderId(),
                FlowTypeEnum.Input.getFlowType(), SourceTypeEnum.PaymentPlan.getSourceType());
        ShopFundAccountFlowData shopFundAccountFlow= new ShopFundAccountFlowData();
        shopFundAccountFlow.setExchangeOrderId(exchangeOrder.getExchangeOrderId());
        shopFundAccountFlow.setFlowAmount(exchangeOrder.getOrderAmount());
        shopFundAccountFlow.setFlowType(FlowTypeEnum.Output.getFlowType());
        shopFundAccountFlow.setSourceType(SourceTypeEnum.ExchangeOrder.getSourceType());
        shopFundAccountFlow.setFundAccountId(paymentPlanShopFundAccountFlow.getFundAccountId());
        return shopFundAccountFlow;
    }

    public void setExchangeOrderDao(ExchangeOrderDAO exchangeOrderDao) {
        this.exchangeOrderDao = exchangeOrderDao;
    }

    public void setShopFundAccountFlowDao(ShopFundAccountFlowDao shopFundAccountFlowDao) {
        this.shopFundAccountFlowDao = shopFundAccountFlowDao;
    }
}
