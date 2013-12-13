package com.dianping.ba.finance.exchange.biz.convert;

import com.dianping.ba.finance.exchange.api.beans.ExchangeOrderBean;
import com.dianping.ba.finance.exchange.api.beans.ShopFundAccountBean;
import com.dianping.ba.finance.exchange.api.datas.ShopFundAccountData;
import com.dianping.ba.finance.exchange.api.datas.ShopFundAccountFlowData;
import com.dianping.ba.finance.exchange.api.dtos.ShopFundAccountFlowDTO;
import com.dianping.ba.finance.exchange.biz.utils.ConvertUtils;

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
     public static ShopFundAccountBean bulidShopFundAccountBeanfromShopFundAccountFlowDTO(ShopFundAccountFlowDTO shopFundAccountFlowDTO) throws Exception {
         ShopFundAccountBean shopFundAccountBean = new ShopFundAccountBean();
         shopFundAccountBean=ConvertUtils.copy(shopFundAccountFlowDTO, ShopFundAccountBean.class);
         return shopFundAccountBean;
     }

    /**
     * 转换成插入资金账户需要的Data
     * @param shopFundAccountFlowDTO
     * @return
     */
    public static ShopFundAccountData bulidShopFundAccountDataFromShopFundAccountFlowDTO(ShopFundAccountFlowDTO shopFundAccountFlowDTO) throws Exception {
        ShopFundAccountData shopFundAccountData = new ShopFundAccountData();
        shopFundAccountData=ConvertUtils.copy(shopFundAccountFlowDTO, ShopFundAccountData.class);
        return shopFundAccountData;
    }

    /**
     * 转换成插入资金流水的Data
     * @param shopFundAccountFlowDTO
     * @return
     */
    public static ShopFundAccountFlowData bulidShopFundAccountFlowDataFromShopFundAccountFlowDTO(ShopFundAccountFlowDTO shopFundAccountFlowDTO) throws Exception {
        ShopFundAccountFlowData shopFundAccountFlowData = new ShopFundAccountFlowData();
        shopFundAccountFlowData=ConvertUtils.copy(shopFundAccountFlowDTO, ShopFundAccountFlowData.class);
        return shopFundAccountFlowData;
    }

    /**
     * 构建交易指令bean
     * @return
     */
    public static ExchangeOrderBean bulidExchangeOrderBean(ShopFundAccountFlowDTO shopFundAccountFlowDTO) {
        ExchangeOrderBean exchangeOrderBean=new ExchangeOrderBean();
        exchangeOrderBean.setOrderAmount(shopFundAccountFlowDTO.getFlowAmount());
        exchangeOrderBean.setOrderType(shopFundAccountFlowDTO.getFlowType().ordinal());
        return exchangeOrderBean;
    }
}
