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
import com.dianping.ba.finance.exchange.api.ExchangeOrderService;
import com.dianping.ba.finance.exchange.api.beans.ShopFundAccountBean;
import com.dianping.ba.finance.exchange.api.datas.ExchangeOrderData;
import com.dianping.ba.finance.exchange.api.datas.ShopFundAccountData;
import com.dianping.ba.finance.exchange.biz.convert.ShopFundAccountConvert;
import com.dianping.ba.finance.exchange.biz.dao.ShopFundAccountDao;
import com.dianping.ba.finance.exchange.biz.dao.ShopFundAccountFlowDao;
import com.dianping.ba.finance.exchange.biz.utils.BizUtils;
import com.dianping.ba.finance.exchange.biz.utils.ConvertUtils;

import java.util.Calendar;

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
    private ShopFundAccountFlowDao shopFundAccountFlowDao;
    private ShopFundAccountDao shopFundAccountDao;

    @Override
    public boolean updateShopFundAccountCausedBySuccessfulExchangeOrder(ExchangeOrderDTO exchangeOrderDTO) {
        long startTime = Calendar.getInstance().getTimeInMillis();
        try {
            if(!isExchangeOrderDTOValid(exchangeOrderDTO))
                return false;
            ShopFundAccountFlowData shopFundAccountFlowData = buildShopFundAccountFlowDataForOut(exchangeOrderDTO);
            shopFundAccountFlowDao.insertShopFundAccountFlow(shopFundAccountFlowData);
            return true;
        } catch (Exception e) {
            BizUtils.log(monitorLogger, startTime, "updateShopFundAccountCausedBySuccessfulExchangeOrder", "error",
                    "orderId = " + exchangeOrderDTO.getExchangeOrderId(),
                    e);
        }
        return false;
    }

    @Override
    public ShopFundAccountFlowDTO getPaymentPlanShopFundAccountFlow(int orderId) {
        long startTime = Calendar.getInstance().getTimeInMillis();
        try {
            if(orderId <= 0)
                return null;
            ShopFundAccountFlowData paymentPlanShopFundAccountFlow = shopFundAccountFlowDao.loadShopFundAccountFlow(orderId,
                    FlowTypeEnum.IN.getFlowType(), SourceTypeEnum.PaymentPlan.getSourceType());
            ShopFundAccountFlowDTO shopFundAccountFlowDTO =  ConvertUtils.copy(paymentPlanShopFundAccountFlow, ShopFundAccountFlowDTO.class);
            return  shopFundAccountFlowDTO;
        } catch (Exception e) {
            BizUtils.log(monitorLogger, startTime, "getPaymentPlanShopFundAccountFlow", "error",
                    "orderId = " + orderId,
                    e);
        }
        return null;
    }

    private boolean isExchangeOrderDTOValid(ExchangeOrderDTO exchangeOrderDTO){
        if(exchangeOrderDTO == null || exchangeOrderDTO.getStatus() != ExchangeOrderStatusEnum.SUCCESS.ordinal()) {
            return false;
        }
        return true;
    }

    private ShopFundAccountFlowData buildShopFundAccountFlowDataForOut(ExchangeOrderDTO exchangeOrder){
        ShopFundAccountFlowData shopFundAccountFlow= new ShopFundAccountFlowData();
        shopFundAccountFlow.setExchangeOrderId(exchangeOrder.getExchangeOrderId());
        shopFundAccountFlow.setFlowAmount(exchangeOrder.getOrderAmount());
        shopFundAccountFlow.setFlowType(FlowTypeEnum.OUT.getFlowType());
        shopFundAccountFlow.setSourceType(SourceTypeEnum.ExchangeOrder.getSourceType());
        shopFundAccountFlow.setFundAccountId(exchangeOrder.getRelevantFundAccountId());
        return shopFundAccountFlow;
    }

    @Override
    public ShopFundAccountData loadShopFundAccountData(ShopFundAccountBean shopFundAccountBean) {
        return shopFundAccountDao.loadShopFundAccountData(shopFundAccountBean);
    }

    @Override
    public int insertShopFundAccount(ShopFundAccountData shopFundAccountData) {
        return shopFundAccountDao.insertShopFundAccount(shopFundAccountData);
    }

    @Override
    public int insertShopFundAccountFlow(ShopFundAccountFlowData shopFundAccountFlowData) {
        return shopFundAccountFlowDao.insertShopFundAccountFlow(shopFundAccountFlowData);
    }

    @Override
    public int createShopFundAccountFlow(ShopFundAccountFlowDTO shopFundAccountFlowDTO){
        int exchangeOrderId=-1;
        try {
            if (shopFundAccountFlowDTO==null){
                //异常
                return -1;
            }
            int fundAccountFlowId = createShopFundAccountAndFlow(shopFundAccountFlowDTO);

            //调用支付指令接口 插入指令
            ExchangeOrderData exchangeOrderData = ShopFundAccountConvert.buildExchangeOrderData(shopFundAccountFlowDTO);
            exchangeOrderId=exchangeOrderService.insertExchangeOrder(exchangeOrderData);

            //回写资金流水中的exchangeOrderId
            shopFundAccountFlowDao.updateExchangeOrderId(exchangeOrderId, fundAccountFlowId);
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

    /**
     * 创建资金账户+流水
     * @param shopFundAccountFlowDTO
     * @return
     */
    private int createShopFundAccountAndFlow(ShopFundAccountFlowDTO shopFundAccountFlowDTO) {
        //判断资金账户时候存在
        ShopFundAccountBean shopFundAccountBean = ShopFundAccountConvert.buildShopFundAccountBeanfromShopFundAccountFlowDTO(shopFundAccountFlowDTO);
        ShopFundAccountData shopFundAccountData = loadShopFundAccountData(shopFundAccountBean);
        int shopFundAccountId;
        if (shopFundAccountData==null||shopFundAccountData.getFundAccountId()==0)
        {
             //插入资金账户  +余额
             shopFundAccountData=ShopFundAccountConvert.buildShopFundAccountDataFromShopFundAccountFlowDTO(shopFundAccountFlowDTO);
             shopFundAccountId=insertShopFundAccount(shopFundAccountData);
        }
        else{
            shopFundAccountId=shopFundAccountData.getFundAccountId();
        }
        //插入资金流水
        ShopFundAccountFlowData shopFundAccountFlowData=ShopFundAccountConvert.buildShopFundAccountFlowDataFromShopFundAccountFlowDTO(shopFundAccountFlowDTO,shopFundAccountId);
        return insertShopFundAccountFlow(shopFundAccountFlowData);
    }


    public void setShopFundAccountDao(ShopFundAccountDao shopFundAccountDao) {
        this.shopFundAccountDao = shopFundAccountDao;
    }

    public void setExchangeOrderService(ExchangeOrderService exchangeOrderService) {
        this.exchangeOrderService = exchangeOrderService;
    }

    public void setShopFundAccountFlowDao(ShopFundAccountFlowDao shopFundAccountFlowDao) {
        this.shopFundAccountFlowDao = shopFundAccountFlowDao;
    }


}
