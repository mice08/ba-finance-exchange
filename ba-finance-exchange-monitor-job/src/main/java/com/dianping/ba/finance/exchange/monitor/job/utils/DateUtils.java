package com.dianping.ba.finance.exchange.monitor.job.utils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: noahshen
 * Date: 14-3-20
 * Time: 下午2:48
 * To change this template use File | Settings | File Templates.
 */
public class DateUtils {

    /**
     * 计算两个时间的时间差
     * @param d1
     * @param d2
     * @param unit
     * @return
     */
    public static long timeDifference(Date d1, Date d2, TimeUnit unit) {
        long diff = d2.getTime() - d1.getTime();
        return diff / unit.toMillis(1);
    }
}
