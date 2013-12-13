package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.ShopFundAccountService;
import com.dianping.ba.finance.exchange.api.datas.ShopFundAccountFlowData;
import com.dianping.ba.finance.exchange.api.dtos.ExchangeOrderDTO;
import com.dianping.ba.finance.exchange.api.enums.ExchangeType;
import com.dianping.ba.finance.exchange.api.enums.FlowTypeEnum;
import com.dianping.ba.finance.exchange.api.enums.SourceTypeEnum;
import com.dianping.ba.finance.exchange.api.ExchangeOrderService;
import com.dianping.ba.finance.exchange.api.beans.ShopFundAccountBean;
import com.dianping.ba.finance.exchange.api.datas.ExchangeOrderData;
import com.dianping.ba.finance.exchange.api.datas.ShopFundAccountData;
import com.dianping.ba.finance.exchange.api.dtos.ShopFundAccountFlowDTO;
import com.dianping.ba.finance.exchange.biz.convert.ShopFundAccountConvert;
import com.dianping.ba.finance.exchange.biz.dao.ShopFundAccountDAO;
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

    private ExchangeOrderService exchangeOrderService;
    private ShopFundAccountFlowDAO shopFundAccountFlowDAO;
    private ShopFundAccountDAO shopFundAccountDAO;

    public void setExchangeOrderService(ExchangeOrderService exchangeOrderService) {
        this.exchangeOrderService = exchangeOrderService;
    }


    public void setShopFundAccountFlowDAO(ShopFundAccountFlowDAO shopFundAccountFlowDAO) {
        this.shopFundAccountFlowDAO = shopFundAccountFlowDAO;
    }

    public void setShopFundAccountDAO(ShopFundAccountDAO shopFundAccountDAO) {
        this.shopFundAccountDAO = shopFundAccountDAO;
    }

    @Override
    public boolean updateShopFundAccountCausedByExchangeOrderSuccess(ExchangeOrderDTO exchangeOrder) {
        try {
            if(exchangeOrder == null || exchangeOrder.getStatus() != ExchangeType.Success.ordinal())
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


    @Override
    public ShopFundAccountData loadShopFundAccountData(ShopFundAccountBean shopFundAccountBean) {
        return shopFundAccountDAO.loadShopFundAccountData(shopFundAccountBean);
    }

    @Override
    public int insertShopFundAccountData(ShopFundAccountData shopFundAccountData) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int insertShopFundAccountFlowData(ShopFundAccountFlowData shopFundAccountFlowData) {
        return shopFundAccountFlowDAO.insertShopFundAccountFlow(shopFundAccountFlowData);
    }

    @Override
    public int createShopFundAccountFlow(ShopFundAccountFlowDTO shopFundAccountFlowDTO){
        int exchangeOrderId=-1;
        try {
            if (shopFundAccountFlowDTO==null){
                //异常
                return -1;
            }
            //判断资金账户时候存在
            ShopFundAccountBean shopFundAccountBean = ShopFundAccountConvert.bulidShopFundAccountBeanfromShopFundAccountFlowDTO(shopFundAccountFlowDTO);
            ShopFundAccountData shopFundAccountData = loadShopFundAccountData(shopFundAccountBean);
            if (shopFundAccountData==null||shopFundAccountData.getFundAccountId()==0)
            {
                 //插入资金账户  +余额
                 shopFundAccountData=ShopFundAccountConvert.bulidShopFundAccountDataFromShopFundAccountFlowDTO(shopFundAccountFlowDTO);
                 insertShopFundAccountData(shopFundAccountData);
            }
            //插入资金流水
            ShopFundAccountFlowData shopFundAccountFlowData=ShopFundAccountConvert.bulidShopFundAccountFlowDataFromShopFundAccountFlowDTO(shopFundAccountFlowDTO);
            int fundAccountFlowId = insertShopFundAccountFlowData(shopFundAccountFlowData);

            //调用支付指令接口 插入指令
            ExchangeOrderData exchangeOrderData = ShopFundAccountConvert.bulidExchangeOrderData(shopFundAccountFlowDTO);
            exchangeOrderId=exchangeOrderService.insertExchangeOrder(exchangeOrderData);

            //回写资金流水中的exchangeOrderId
            shopFundAccountFlowDAO.updateExchangeOrderId(exchangeOrderId, fundAccountFlowId);
        }catch (Exception e){
            BizUtils.log(monitorLogger, System.currentTimeMillis(), "createShopFundAccountFlow", "error",
                    "BusinessType = " + shopFundAccountFlowDTO.getBusinessType()
                            + "CustomerGlobalId = "+ shopFundAccountFlowDTO.getCustomerGlobalId()
                            + "CompanyGlobalId = "+ shopFundAccountFlowDTO.getCompanyGlobalId()
                            + "ShopId = "+ shopFundAccountFlowDTO.getShopId()
                            + "FlowAmount = "+ shopFundAccountFlowDTO.getFlowAmount()
                            + "FlowType = "+ shopFundAccountFlowDTO.getFlowType()
                            + "SourceType = "+ shopFundAccountFlowDTO.getSourceType().ordinal()
                    ,e);
        }
        return exchangeOrderId;
    }
}
