package com.dianping.ba.finance.exchange.api;

import com.dianping.ba.finance.exchange.api.dtos.ReceiveOrderMonitorDTO;

import java.util.Date;
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
     * @param startTime
     * @param endTime
     * @return
     */
    List<ReceiveOrderMonitorDTO> findReceiveOrderMonitorDataByTime(Date startTime, Date endTime);

    /**
     * 根据日期将收款单数据存入数据库
     */
    void insertReceiveOrderRecoDatas(Date startTime, Date endTime);
}
