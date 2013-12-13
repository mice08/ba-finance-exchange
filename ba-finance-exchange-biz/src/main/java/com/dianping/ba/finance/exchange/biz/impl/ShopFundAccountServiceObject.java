package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.ShopFundAccountService;
import com.dianping.ba.finance.exchange.api.datas.ShopFundAccountFlowData;
import com.dianping.ba.finance.exchange.api.dtos.ExchangeOrderDTO;
import com.dianping.ba.finance.exchange.api.enums.FlowTypeEnum;
import com.dianping.ba.finance.exchange.api.enums.SourceTypeEnum;
import com.dianping.ba.finance.exchange.biz.dao.ShopFundAccountFlowDAO;
import com.dianping.ba.finance.exchange.biz.utils.BizUtils;

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

    private ShopFundAccountFlowData buildShopFundAccountFlowData(ExchangeOrderDTO exchangeOrder){
        ShopFundAccountFlowData paymentPlanShopFundAccountFlow = shopFundAccountFlowDAO.loadShopFundAccountFlow(exchangeOrder.getExchangeOrderId(),
                FlowTypeEnum.Input.getFlowType(), SourceTypeEnum.PaymentPlan.getSourceType());
        ShopFundAccountFlowData shopFundAccountFlow= new ShopFundAccountFlowData();
        shopFundAccountFlow.setExchangeOrderId(exchangeOrder.getExchangeOrderId());
        shopFundAccountFlow.setFlowAmount(exchangeOrder.getOrderAmount());
        shopFundAccountFlow.setFlowType(FlowTypeEnum.Output.getFlowType());
        shopFundAccountFlow.setSourceType(SourceTypeEnum.ExchangeOrder.getSourceType());
        shopFundAccountFlow.setFundAccountId(paymentPlanShopFundAccountFlow.getFundAccountId());
        return shopFundAccountFlow;
    }
}
