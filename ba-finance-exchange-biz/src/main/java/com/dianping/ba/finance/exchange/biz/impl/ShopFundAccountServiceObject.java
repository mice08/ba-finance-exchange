package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.ShopFundAccountService;
import com.dianping.ba.finance.exchange.api.datas.ShopFundAccountFlowData;
import com.dianping.ba.finance.exchange.api.dtos.ExchangeOrderDTO;
import com.dianping.ba.finance.exchange.api.dtos.ShopFundAccountFlowDTO;
import com.dianping.ba.finance.exchange.api.enums.ExchangeOrderStatusEnum;
import com.dianping.ba.finance.exchange.api.enums.FlowTypeEnum;
import com.dianping.ba.finance.exchange.api.enums.SourceTypeEnum;
import com.dianping.ba.finance.exchange.biz.dao.ShopFundAccountFlowDAO;
import com.dianping.ba.finance.exchange.biz.utils.BizUtils;
import com.dianping.ba.finance.exchange.biz.utils.ConvertUtils;

/**
 * Created with IntelliJ IDEA.
 * User: 遐
 * Date: 13-12-13
 * Time: 上午9:28
 * To change this template use File | Settings | File Templates.
 */
public class ShopFundAccountServiceObject implements ShopFundAccountService {

    private static final AvatarLogger monitorLogger = AvatarLoggerFactory.getLogger(ShopFundAccountServiceObject.class);

    private ShopFundAccountFlowDAO shopFundAccountFlowDAO;

    public void setShopFundAccountFlowDAO(ShopFundAccountFlowDAO shopFundAccountFlowDAO) {
        this.shopFundAccountFlowDAO = shopFundAccountFlowDAO;
    }
    @Override
    public boolean updateShopFundAccountCausedByExchangeOrderSuccess(ExchangeOrderDTO exchangeOrder) {
        try {
            if(exchangeOrder == null || exchangeOrder.getStatus() != ExchangeOrderStatusEnum.Success.ordinal())
                return false;
            ShopFundAccountFlowData shopFundAccountFlowData = buildShopFundAccountFlowData(exchangeOrder);
            shopFundAccountFlowDAO.insertShopFundAccountFlow(shopFundAccountFlowData);
            return true;
        } catch (Exception e) {
            BizUtils.log(monitorLogger, System.currentTimeMillis(), "updateShopFundAccountCausedByExchangeOrderSuccess", "error",
                    "orderId = " + exchangeOrder.getExchangeOrderId(),
                    e);
        }
        return false;
    }

    @Override
    public ShopFundAccountFlowDTO getPaymentPlanShopFundAccountFlow(ExchangeOrderDTO exchangeOrder) {
        try {
            ShopFundAccountFlowData paymentPlanShopFundAccountFlow = shopFundAccountFlowDAO.loadShopFundAccountFlow(exchangeOrder.getExchangeOrderId(),
                    FlowTypeEnum.Input.getFlowType(), SourceTypeEnum.PaymentPlan.getSourceType());
            ShopFundAccountFlowDTO shopFundAccountFlowDTO =  ConvertUtils.copy(paymentPlanShopFundAccountFlow, ShopFundAccountFlowDTO.class);
            return  shopFundAccountFlowDTO;
        } catch (Exception e) {
            BizUtils.log(monitorLogger, System.currentTimeMillis(), "getPaymentPlanShopFundAccountFlow", "error",
                    "orderId = " + exchangeOrder.getExchangeOrderId(),
                    e);
        }
        return null;
    }

    private ShopFundAccountFlowData buildShopFundAccountFlowData(ExchangeOrderDTO exchangeOrder){
        ShopFundAccountFlowData shopFundAccountFlow= new ShopFundAccountFlowData();
        shopFundAccountFlow.setExchangeOrderId(exchangeOrder.getExchangeOrderId());
        shopFundAccountFlow.setFlowAmount(exchangeOrder.getOrderAmount());
        shopFundAccountFlow.setFlowType(FlowTypeEnum.Output.getFlowType());
        shopFundAccountFlow.setSourceType(SourceTypeEnum.ExchangeOrder.getSourceType());
        shopFundAccountFlow.setFundAccountId(exchangeOrder.getRelevantFundAccountId());
        return shopFundAccountFlow;
    }
}
