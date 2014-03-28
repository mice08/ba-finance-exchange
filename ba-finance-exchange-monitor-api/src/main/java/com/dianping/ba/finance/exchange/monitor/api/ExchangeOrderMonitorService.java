package com.dianping.ba.finance.exchange.monitor.api;


import com.dianping.ba.finance.exchange.monitor.api.datas.ExchangeOrderMonitorData;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: noahshen
 * Date: 14-3-26
 * Time: 下午3:03
 * To change this template use File | Settings | File Templates.
 */
public interface ExchangeOrderMonitorService {

    /**
     * 获取更新日期为时间范围内的付款单
     * @param startDate
     * @param endDate
     * @return
     */
    List<ExchangeOrderMonitorData> findExchangeOrderData(Date startDate, Date endDate);

    /**
     *  按付款单Id获取付款单信息
     * @param eoId
     * @return
     */
    ExchangeOrderMonitorData loadExchangeOrderData(int eoId);

}
