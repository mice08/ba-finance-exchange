package com.dianping.ba.finance.exchange.paymonitor.api;

import com.dianping.ba.finance.exchange.paymonitor.api.datas.PayPlanMonitorData;

import java.util.Date;
import java.util.List;

/**
 * Created by adam.huang on 2014/8/7.
 */
public interface PayPlanService {

    /**
     * 获取更新日期为时间范围内的付款计划
     * @param startDate
     * @param endDate
     * @return
     */
    public List<PayPlanMonitorData> findPayPlansByDate(Date startDate,Date endDate);

    /**
     * 根据ppId获取付款计划
     * @param ppId
     * @return
     */
    public PayPlanMonitorData getPayPlanById(int ppId);

    /**
     *根据ppId获取付款序列号
     * @param ppId
     * @return
     */
    public String getPaySequenceById(int ppId);
}
