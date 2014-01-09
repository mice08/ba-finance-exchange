package com.dianping.ba.finance.exchange.biz.convert;

import com.dianping.ba.finance.exchange.api.beans.ShopFundAccountBean;
import com.dianping.ba.finance.exchange.api.datas.ExchangeOrderData;
import com.dianping.ba.finance.exchange.api.datas.ShopFundAccountData;
import com.dianping.ba.finance.exchange.api.datas.ShopFundAccountFlowData;
import com.dianping.ba.finance.exchange.api.dtos.ShopFundAccountFlowDTO;
import com.dianping.ba.finance.exchange.biz.utils.BizUtils;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: bingqiu.yuan
 * Date: 13-12-13
 * Time: 下午3:22
 * To change this template use File | Settings | File Templates.
 */
public class ShopFundAccountConvert {
    /**
     * 转换成查询资金账户的对象
     * @param shopFundAccountFlowDTO
     * @return
     * @throws Exception
     */
     public static ShopFundAccountBean buildShopFundAccountBeanfromShopFundAccountFlowDTO(ShopFundAccountFlowDTO shopFundAccountFlowDTO) {
         ShopFundAccountBean shopFundAccountBean = new ShopFundAccountBean();
         shopFundAccountBean.setBusinessType(shopFundAccountFlowDTO.getBusinessType().ordinal());
         shopFundAccountBean.setCustomerGlobalId(shopFundAccountFlowDTO.getCustomerGlobalId());
         shopFundAccountBean.setCompanyGlobalId(shopFundAccountFlowDTO.getCompanyGlobalId());
         shopFundAccountBean.setShopId(shopFundAccountFlowDTO.getShopId());
         return shopFundAccountBean;
     }

    /**
     * 转换成插入资金账户需要的Data
     * @param shopFundAccountFlowDTO
     * @return
     */
    public static ShopFundAccountData buildShopFundAccountDataFromShopFundAccountFlowDTO(ShopFundAccountFlowDTO shopFundAccountFlowDTO)  {
        ShopFundAccountData shopFundAccountData = new ShopFundAccountData();
        shopFundAccountData.setShopId(shopFundAccountFlowDTO.getShopId());
        shopFundAccountData.setBusinessType(shopFundAccountFlowDTO.getBusinessType().ordinal());
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
     * @param shopFundAccountFlowDTO
     * @param shopFundAccountId
     * @return
     */
    public static ShopFundAccountFlowData buildShopFundAccountFlowDataFromShopFundAccountFlowDTO(ShopFundAccountFlowDTO shopFundAccountFlowDTO,int shopFundAccountId) {
        ShopFundAccountFlowData shopFundAccountFlowData = new ShopFundAccountFlowData();
        shopFundAccountFlowData.setFundAccountId(shopFundAccountId);
        shopFundAccountFlowData.setFlowAmount(shopFundAccountFlowDTO.getFlowAmount());
        shopFundAccountFlowData.setFlowType(shopFundAccountFlowDTO.getFlowType().value());
        shopFundAccountFlowData.setSourceType(shopFundAccountFlowDTO.getSourceType().value());
        shopFundAccountFlowData.setSequence(BizUtils.createSequence(shopFundAccountFlowDTO.getSourceType().clientNo(),shopFundAccountFlowDTO.getBizId()));
        //exchangeOrderId 先为0 后面回写
        shopFundAccountFlowData.setExchangeOrderId(0);
        return shopFundAccountFlowData;
    }

    /**
     * 构建交易指令bean
     * @return
     */
    public static ExchangeOrderData buildExchangeOrderData(ShopFundAccountFlowDTO shopFundAccountFlowDTO) {
        ExchangeOrderData exchangeOrderData=new ExchangeOrderData();
        exchangeOrderData.setOrderAmount(shopFundAccountFlowDTO.getFlowAmount());
        exchangeOrderData.setOrderType(shopFundAccountFlowDTO.getFlowType().ordinal());
        exchangeOrderData.setBankAccountNo(shopFundAccountFlowDTO.getBankAccountNo());
        exchangeOrderData.setBankAccountName(shopFundAccountFlowDTO.getBankAccountName());
        exchangeOrderData.setBankName(shopFundAccountFlowDTO.getBankName());
        return exchangeOrderData;
    }
}
