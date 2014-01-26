package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.ShopFundAccountService;
import com.dianping.ba.finance.exchange.api.datas.ShopFundAccountFlowData;
import com.dianping.ba.finance.exchange.api.dtos.ExchangeOrderDTO;
import com.dianping.ba.finance.exchange.api.dtos.ShopFundAccountFlowDTO;
import com.dianping.ba.finance.exchange.api.enums.ExchangeOrderStatus;
import com.dianping.ba.finance.exchange.api.enums.FlowType;
import com.dianping.ba.finance.exchange.api.enums.SourceType;
import com.dianping.ba.finance.exchange.api.ExchangeOrderService;
import com.dianping.ba.finance.exchange.api.beans.ShopFundAccountBean;
import com.dianping.ba.finance.exchange.api.datas.ExchangeOrderData;
import com.dianping.ba.finance.exchange.api.datas.ShopFundAccountData;
import com.dianping.ba.finance.exchange.biz.dao.ShopFundAccountDao;
import com.dianping.ba.finance.exchange.biz.dao.ShopFundAccountFlowDao;
import com.dianping.ba.finance.exchange.biz.utils.BizUtils;
import com.dianping.ba.finance.exchange.biz.utils.ConvertUtils;
import com.dianping.ba.finance.exchange.biz.utils.LogUtils;
import org.apache.log4j.Level;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: 遐
 * Date: 13-12-13
 * Time: 上午9:28
 * To change this template use File | Settings | File Templates.
 */
public class ShopFundAccountServiceObject implements ShopFundAccountService {

    private static final AvatarLogger monitorLogger = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.service.monitor.ShopFundAccountServiceObject");

    private ExchangeOrderService exchangeOrderService;
    private ShopFundAccountFlowDao shopFundAccountFlowDao;
    private ShopFundAccountDao shopFundAccountDao;

    @Override
    public int createShopFundAccountFlow(ShopFundAccountFlowDTO shopFundAccountFlowDTO){
        int fundAccountFlowId = -1;
        long startTime = System.currentTimeMillis();
        try {
            if (shopFundAccountFlowDTO == null){
                LogUtils.log(monitorLogger, startTime, "createShopFundAccountFlow", Level.ERROR, "argument is null");
                return -1;
            }
            fundAccountFlowId = createShopFundAccountAndFlow(shopFundAccountFlowDTO);
            //调用支付指令接口 插入指令
            ExchangeOrderData exchangeOrderData = buildExchangeOrderDataFromShopFundAccountFlowDTO(shopFundAccountFlowDTO, fundAccountFlowId);
            int exchangeOrderId = exchangeOrderService.insertExchangeOrder(exchangeOrderData);

            //回写资金流水中的exchangeOrderId
            shopFundAccountFlowDao.updateExchangeOrderId(exchangeOrderId, fundAccountFlowId);
        }catch (Exception e){
            LogUtils.log(monitorLogger, startTime, "createShopFundAccountFlow", Level.ERROR,
                    "BusinessType = " + shopFundAccountFlowDTO.getBusinessType()
                            + "CustomerGlobalId = "+ shopFundAccountFlowDTO.getCustomerGlobalId()
                            + "CompanyGlobalId = "+ shopFundAccountFlowDTO.getCompanyGlobalId()
                            + "ShopId = "+ shopFundAccountFlowDTO.getShopId()
                            + "FlowAmount = "+ shopFundAccountFlowDTO.getFlowAmount()
                            + "FlowType = "+ shopFundAccountFlowDTO.getFlowType()
                            + "SourceType = "+ shopFundAccountFlowDTO.getSourceType().value()
                    ,e);
        }
        return fundAccountFlowId;
    }

    @Override
    public boolean updateShopFundAccountCausedBySuccessfulExchangeOrder(ExchangeOrderDTO exchangeOrderDTO) {
        long startTime = System.currentTimeMillis();
        try {
            //TODO: 更新资金账户之后要做
            if(!isExchangeOrderDTOValid(exchangeOrderDTO))
                return false;
            ShopFundAccountFlowData shopFundAccountFlowData = buildShopFundAccountFlowDataForOut(exchangeOrderDTO);
            shopFundAccountFlowDao.insertShopFundAccountFlow(shopFundAccountFlowData);
            return true;
        } catch (Exception e) {
            LogUtils.log(monitorLogger, startTime, "updateShopFundAccountCausedBySuccessfulExchangeOrder", Level.ERROR,
                    "orderId = " + exchangeOrderDTO.getExchangeOrderId(),
                    e);
        }
        return false;
    }

    @Override
    public ShopFundAccountFlowDTO getPaymentPlanShopFundAccountFlow(int orderId) {
        long startTime = System.currentTimeMillis();
        try {
            if(orderId <= 0)
                return null;
            ShopFundAccountFlowData paymentPlanShopFundAccountFlow = shopFundAccountFlowDao.loadShopFundAccountFlow(orderId,
                    FlowType.IN.getFlowType(), SourceType.PaymentPlan.getSourceType());
            if(paymentPlanShopFundAccountFlow == null){
                LogUtils.log(monitorLogger, startTime, "getPaymentPlanShopFundAccountFlow", Level.ERROR,
                        "orderId = " + orderId);
            }
            ShopFundAccountFlowDTO shopFundAccountFlowDTO =  ConvertUtils.copy(paymentPlanShopFundAccountFlow, ShopFundAccountFlowDTO.class);
            return  shopFundAccountFlowDTO;
        } catch (Exception e) {
            LogUtils.log(monitorLogger, startTime, "getPaymentPlanShopFundAccountFlow",Level.ERROR,
                    "orderId = " + orderId,
                    e);
        }
        return null;
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

    /**
     * 创建资金账户+流水
     * @param shopFundAccountFlowDTO
     * @return
     */
    private int createShopFundAccountAndFlow(ShopFundAccountFlowDTO shopFundAccountFlowDTO) throws Exception{
        //判断资金账户时候存在
        ShopFundAccountBean shopFundAccountBean = ConvertUtils.copy(shopFundAccountFlowDTO, ShopFundAccountBean.class);
        ShopFundAccountData shopFundAccountData = loadShopFundAccountData(shopFundAccountBean);
        int shopFundAccountId;
        if (!isShopFundAccountDataExist(shopFundAccountData)) {
            shopFundAccountData = buildShopFundAccountDataFromShopFundAccountFlowDTO(shopFundAccountFlowDTO);
            shopFundAccountId = insertShopFundAccount(shopFundAccountData);
        } else {
            shopFundAccountId = shopFundAccountData.getFundAccountId();
        }
        //插入资金流水
        ShopFundAccountFlowData shopFundAccountFlowData = buildShopFundAccountFlowDataFromShopFundAccountFlowDTO(shopFundAccountFlowDTO, shopFundAccountId);
        return insertShopFundAccountFlow(shopFundAccountFlowData);
    }

    /**
     * 转换成插入资金账户需要的Data
     *
     * @param shopFundAccountFlowDTO
     * @return
     */
    private ShopFundAccountData buildShopFundAccountDataFromShopFundAccountFlowDTO(ShopFundAccountFlowDTO shopFundAccountFlowDTO)  {
        ShopFundAccountData shopFundAccountData = new ShopFundAccountData();
        shopFundAccountData.setShopId(shopFundAccountFlowDTO.getShopId());
        shopFundAccountData.setBusinessType(shopFundAccountFlowDTO.getBusinessType().value());
        shopFundAccountData.setCompanyGlobalId(shopFundAccountFlowDTO.getCompanyGlobalId());
        shopFundAccountData.setCustomerGlobalId(shopFundAccountFlowDTO.getCustomerGlobalId());
        // 账户先不加金额
        shopFundAccountData.setBalanceFrozen(BigDecimal.ZERO);
        shopFundAccountData.setBalanceTotal(BigDecimal.ZERO);
        shopFundAccountData.setCredit(BigDecimal.ZERO);
        shopFundAccountData.setDebit(BigDecimal.ZERO);
        return shopFundAccountData;
    }

    /**
     * 转换成插入资金流水的Data
     *
     * @param shopFundAccountFlowDTO
     * @param shopFundAccountId
     * @return
     */
    private ShopFundAccountFlowData buildShopFundAccountFlowDataFromShopFundAccountFlowDTO(ShopFundAccountFlowDTO shopFundAccountFlowDTO,int shopFundAccountId) {
        ShopFundAccountFlowData shopFundAccountFlowData = new ShopFundAccountFlowData();
        shopFundAccountFlowData.setFundAccountId(shopFundAccountId);
        shopFundAccountFlowData.setFlowAmount(shopFundAccountFlowDTO.getFlowAmount());
        shopFundAccountFlowData.setFlowType(shopFundAccountFlowDTO.getFlowType().value());
        shopFundAccountFlowData.setSourceType(shopFundAccountFlowDTO.getSourceType().value());
        shopFundAccountFlowData.setSequence(BizUtils.createSequence(shopFundAccountFlowDTO.getSourceType().clientNo(),String.valueOf(shopFundAccountFlowDTO.getBizId())));
        //exchangeOrderId 先为0 后面回写
        shopFundAccountFlowData.setExchangeOrderId(0);
        return shopFundAccountFlowData;
    }

    /**
     * 构建交易指令bean
     *
     * @return
     */
    private ExchangeOrderData buildExchangeOrderDataFromShopFundAccountFlowDTO(ShopFundAccountFlowDTO shopFundAccountFlowDTO, int fundAccountFlowId) {
        ExchangeOrderData exchangeOrderData=new ExchangeOrderData();
        exchangeOrderData.setOrderAmount(shopFundAccountFlowDTO.getFlowAmount());
        exchangeOrderData.setOrderType(shopFundAccountFlowDTO.getFlowType().value());
        exchangeOrderData.setBankAccountNo(shopFundAccountFlowDTO.getBankAccountNo());
        exchangeOrderData.setBankAccountName(shopFundAccountFlowDTO.getBankAccountName());
        exchangeOrderData.setBankName(shopFundAccountFlowDTO.getBankName());
        exchangeOrderData.setBankCity(shopFundAccountFlowDTO.getBankCity());
        exchangeOrderData.setBankProvince(shopFundAccountFlowDTO.getBankProvince());
        exchangeOrderData.setBizCode("P" + fundAccountFlowId);       //TODO BizCode P开头
        return exchangeOrderData;
    }

    private ShopFundAccountFlowData buildShopFundAccountFlowDataForOut(ExchangeOrderDTO exchangeOrder){
        ShopFundAccountFlowData shopFundAccountFlow= new ShopFundAccountFlowData();
        shopFundAccountFlow.setExchangeOrderId(exchangeOrder.getExchangeOrderId());
        shopFundAccountFlow.setFlowAmount(exchangeOrder.getOrderAmount());
        shopFundAccountFlow.setFlowType(FlowType.OUT.getFlowType());
        shopFundAccountFlow.setSourceType(SourceType.ExchangeOrder.value());
        shopFundAccountFlow.setFundAccountId(exchangeOrder.getRelevantFundAccountId());
        shopFundAccountFlow.setSequence(BizUtils.createSequence(SourceType.ExchangeOrder.clientNo(),String.valueOf(exchangeOrder.getExchangeOrderId())));
        return shopFundAccountFlow;
    }

    private boolean isShopFundAccountDataExist(ShopFundAccountData shopFundAccountData){
        if (shopFundAccountData == null|| shopFundAccountData.getFundAccountId() == 0) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isExchangeOrderDTOValid(ExchangeOrderDTO exchangeOrderDTO){
        if(exchangeOrderDTO == null || exchangeOrderDTO.getStatus() != ExchangeOrderStatus.SUCCESS.value()) {
            return false;
        }
        return true;
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
