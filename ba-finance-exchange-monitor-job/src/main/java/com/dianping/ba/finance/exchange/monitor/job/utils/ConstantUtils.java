package com.dianping.ba.finance.exchange.monitor.job.utils;

import com.dianping.finance.common.util.LionConfigUtils;

/**
 * Created with IntelliJ IDEA.
 * User: qucong
 * Date: 14-3-4
 * Time: 下午6:22
 * To change this template use File | Settings | File Templates.
 */
public class ConstantUtils {
	public static String monitorMailAddress = LionConfigUtils.getProperty("ba-finance-exchange-monitor-job.MonitorMailAddress", "fs.ba@dianping.com");
	public static String monitorMobileNo = LionConfigUtils.getProperty("ba-finance-exchange-monitor-job.MonitorMobileNo", "");
	public static int refundTimeout = Integer.parseInt(LionConfigUtils.getProperty("ba-finance-exchange-monitor-job.RefundTimeout", "5"));
	public static int paySuccessTimeout = Integer.parseInt(LionConfigUtils.getProperty("ba-finance-exchange-monitor-job.paySuccessTimeout", "5"));
}
