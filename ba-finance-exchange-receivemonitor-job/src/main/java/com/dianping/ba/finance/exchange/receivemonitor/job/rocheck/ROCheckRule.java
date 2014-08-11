package com.dianping.ba.finance.exchange.receivemonitor.job.rocheck;


import com.dianping.ba.finance.exchange.receivemonitor.api.datas.ReceiveOrderMonitorData;

/**
 * Created with IntelliJ IDEA.
 * User: jingzhou.yan
 * Date: 14-3-27
 * Time: 下午2:57
 * To change this template use File | Settings | File Templates.
 */
public interface ROCheckRule {
    /**
     * 过滤器，判断是否符合校验的前提
     * @param receiveOrderMonitorData
     * @return
     */
    public boolean filter(ReceiveOrderMonitorData receiveOrderMonitorData);

    /**
     * 执行校验
     * @param receiveOrderMonitorData
     * @return
     */
    public ROCheckResult check(ReceiveOrderMonitorData receiveOrderMonitorData);
}
