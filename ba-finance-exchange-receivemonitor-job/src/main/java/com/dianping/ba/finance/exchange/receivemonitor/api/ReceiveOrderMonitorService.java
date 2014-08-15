package com.dianping.ba.finance.exchange.receivemonitor.api;


import com.dianping.ba.finance.exchange.receivemonitor.api.datas.ReceiveOrderMonitorData;

import java.util.Date;
import java.util.List;

/**
 * 监控收款单的service接口
 */
public interface ReceiveOrderMonitorService {

    /**
     * 获取更新日期为时间范围内的收款单
     * @param startDate
     * @param endDate
     * @return
     */
    List<ReceiveOrderMonitorData> findReceiveOrderData(Date startDate, Date endDate);

    /**
     *  按收款单Id获取收款单信息
     * @param roId
     * @return
     */
    ReceiveOrderMonitorData loadReceiveOrderData(int roId);

}
