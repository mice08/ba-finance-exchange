package com.dianping.ba.finance.exchange.monitor.api;

import com.dianping.ba.finance.exchange.monitor.api.datas.ShopFundAccountFlowMonitorData;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jingzhou.yan
 * Date: 14-3-27
 * Time: 下午4:08
 * To change this template use File | Settings | File Templates.
 */
public interface ShopFundAccountFlowMonitorService {
    /**
     * 根据eoid获取对应的流水记录
     * @param eoIdList
     * @return
     */
    List<ShopFundAccountFlowMonitorData> findShopFundAccountFlowData(Integer eoIdList);
}
