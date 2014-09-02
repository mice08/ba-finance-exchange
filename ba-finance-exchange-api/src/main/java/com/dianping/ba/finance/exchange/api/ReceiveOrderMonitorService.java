package com.dianping.ba.finance.exchange.api;

import com.dianping.ba.finance.exchange.api.dtos.ReceiveOrderMonitorDTO;
import com.dianping.ba.finance.exchange.api.dtos.ReceiveOrderMonitorSearchDTO;

import java.util.List;

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

    /**
     * 根据日期获取收款单数据
     * @param receiveOrderMonitorSearchDTO
     * @return
     */
    List<ReceiveOrderMonitorDTO> findReceiveOrderMonitorDataByTime(ReceiveOrderMonitorSearchDTO receiveOrderMonitorSearchDTO);

    /**
     * 根据日期将收款单数据存入数据库
     */
    void insertReceiveOrderRecoDatas(ReceiveOrderMonitorSearchDTO receiveOrderMonitorSearchDTO);
}
