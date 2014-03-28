package com.dianping.ba.finance.exchange.monitor.job.utils;

import com.dianping.combiz.spring.util.PropertiesLoaderSupportUtils;

/**
 * Created with IntelliJ IDEA.
 * User: qucong
 * Date: 14-3-4
 * Time: 下午6:22
 * To change this template use File | Settings | File Templates.
 */
public class ConstantUtils {
    public static String monitorMailAddress = PropertiesLoaderSupportUtils.getProperty("ba-finance-exchange-monitor-job.MonitorMailAddress");
    public static String monitorMobileNo = PropertiesLoaderSupportUtils.getProperty("ba-finance-exchange-monitor-job.MonitorMobileNo");
    public static int refundTimeout = PropertiesLoaderSupportUtils.getIntProperty("ba-finance-exchange-monitor-job.RefundTimeout",5);
    public static int paySuccessTimeout = PropertiesLoaderSupportUtils.getIntProperty("ba-finance-exchange-monitor-job.paySuccessTimeout",5);

}
