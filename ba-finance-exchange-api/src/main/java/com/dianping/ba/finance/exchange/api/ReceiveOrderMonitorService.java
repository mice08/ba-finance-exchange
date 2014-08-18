package com.dianping.ba.finance.exchange.api;

import com.dianping.ba.finance.exchange.api.dtos.ReceiveOrderMonitorDTO;

/**
 * Created by adam.huang on 2014/8/18.
 */
public interface ReceiveOrderMonitorService {

    /**
     * 根据主键获取收款单数据
     * @param roId
     * @return
     */
    ReceiveOrderMonitorDTO loadReceiveOrderMonitorDTOById(int roId);
}
