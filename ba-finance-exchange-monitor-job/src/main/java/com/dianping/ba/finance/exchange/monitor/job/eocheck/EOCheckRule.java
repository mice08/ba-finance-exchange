package com.dianping.ba.finance.exchange.monitor.job.eocheck;

import com.dianping.ba.finance.exchange.monitor.api.datas.ExchangeOrderMonitorData;

/**
 * Created with IntelliJ IDEA.
 * User: jingzhou.yan
 * Date: 14-3-27
 * Time: 下午2:57
 * To change this template use File | Settings | File Templates.
 */
public interface EOCheckRule {
    /**
     * 过滤器，判断是否符合校验的前提
     * @param exchangeOrderMonitorData
     * @return
     */
    public boolean filter(ExchangeOrderMonitorData exchangeOrderMonitorData);

    /**
     * 执行校验
     * @param exchangeOrderMonitorData
     * @return
     */
    public EOCheckResult check(ExchangeOrderMonitorData exchangeOrderMonitorData);
}
