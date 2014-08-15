package com.dianping.ba.finance.exchange.paymonitor.job.utils;

import com.dianping.finance.common.util.LionConfigUtils;

public class ConstantUtils {
	public static String monitorMailAddress = LionConfigUtils.getProperty("ba-finance-exchange-paymonitor-job.MonitorMailAddress", "fs.ba@dianping.com");
	public static String monitorMobileNo = LionConfigUtils.getProperty("ba-finance-exchange-paymonitor-job.MonitorMobileNo", "");
	public static int Timeout = Integer.parseInt(LionConfigUtils.getProperty("ba-finance-exchange-paymonitor-job.Timeout", "5"));
}
