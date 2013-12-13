package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.ExchangeOrderService;
import com.dianping.ba.finance.exchange.api.ShopFundAccountService;
import com.dianping.ba.finance.exchange.api.beans.ShopFundAccountBean;
import com.dianping.ba.finance.exchange.api.datas.ShopFundAccountData;
import com.dianping.ba.finance.exchange.api.datas.ShopFundAccountFlowData;
import com.dianping.ba.finance.exchange.api.dtos.ShopFundAccountFlowDTO;
import com.dianping.ba.finance.exchange.biz.convert.ShopFundAccountConvert;

/**
 * Created with IntelliJ IDEA.
 * User: 遐
 * Date: 13-12-13
 * Time: 上午9:28
 * To change this template use File | Settings | File Templates.
 */
public class ShopFundAccountServiceObject implements ShopFundAccountService {
    private ExchangeOrderService exchangeOrderService;

    public void setExchangeOrderService(ExchangeOrderService exchangeOrderService) {
        this.exchangeOrderService = exchangeOrderService;
    }

    @Override
    public ShopFundAccountData loadShopFundAccountData(ShopFundAccountBean shopFundAccountBean) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int insertShopFundAccountData(ShopFundAccountData shopFundAccountData) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int insertShopFundAccountFlowData(ShopFundAccountFlowData shopFundAccountFlowData) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int createShopFundAccountFlow(ShopFundAccountFlowDTO shopFundAccountFlowDTO) throws Exception {
        if (shopFundAccountFlowDTO==null)
        {
            //异常
            return -1;
        }
        //判断资金账户时候存在
        ShopFundAccountBean shopFundAccountBean = ShopFundAccountConvert.bulidShopFundAccountBeanfromShopFundAccountFlowDTO(shopFundAccountFlowDTO);
        ShopFundAccountData shopFundAccountData = loadShopFundAccountData(shopFundAccountBean);
        if (shopFundAccountData==null||shopFundAccountData.getFundAccountId()==0)
        {
             //插入资金账户
             shopFundAccountData=ShopFundAccountConvert.bulidShopFundAccountDataFromShopFundAccountFlowDTO(shopFundAccountFlowDTO);
             insertShopFundAccountData(shopFundAccountData);
        }
        //插入资金流水
        int fundAccountFlowId = insertShopFundAccountFlowData(new ShopFundAccountFlowData());

        //调用支付指令接口 插入指令

        int orderId=exchangeOrderService.createExchangeOrder();

        //回写资金流水中的exchangeOrderId

        return orderId;
    }
}
