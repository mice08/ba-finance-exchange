package com.dianping.ba.finance.exchange.monitor.biz.impl;

import com.dianping.ba.finance.exchange.monitor.api.ShopFundAccountFlowMonitorService;
import com.dianping.ba.finance.exchange.monitor.api.datas.ShopFundAccountFlowMonitorData;
import com.dianping.ba.finance.exchange.monitor.biz.dao.ShopFundAccountFlowMonitorDao;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jingzhou.yan
 * Date: 14-3-27
 * Time: 下午4:07
 * To change this template use File | Settings | File Templates.
 */
public class ShopFundAccountFlowMonitorServiceObject implements ShopFundAccountFlowMonitorService {

    ShopFundAccountFlowMonitorDao shopFundAccountFlowMonitorDao;

    @Override
    public List<ShopFundAccountFlowMonitorData> findPendIngAndInitExchangeOrderDatas(List<Integer> eoIdList) {
        return shopFundAccountFlowMonitorDao.findShopFundAccountFlowDatas(eoIdList);
    }

    public void setShopFundAccountFlowMonitorDao(ShopFundAccountFlowMonitorDao shopFundAccountFlowMonitorDao) {
        this.shopFundAccountFlowMonitorDao = shopFundAccountFlowMonitorDao;
    }
}
