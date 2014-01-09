package com.dianping.ba.finance.exchange.biz.utils;

import com.dianping.avatar.log.AvatarLogger;

/**
 * Created with IntelliJ IDEA.
 * User: jingzhou.yan
 * Date: 13-12-6
 * Time: 下午3:16
 * To change this template use File | Settings | File Templates.
 */
public class BizUtils {
    /**
     * 记录监控日志
     *
     * @param logger    监控日志类型
     * @param startTime 开始时间 ，为 null则不记录方法执行时间
     * @param method    具体的方法
     * @param result    执行结果
     * @param params    方法参数
     * @param e         异常。如为null则记录info日志；如不为null，则记录error日志。
     */
    public static void log(AvatarLogger logger, Long startTime, String method, String result, String params, Throwable e) {
        String logStr=logStr(startTime,System.currentTimeMillis(),method,result,params);
        if (e == null) {
            logger.info(logStr);
        } else {
            logger.error(logStr, e);
        }
    }

    public static String logStr(Long startTime,Long endTime, String method, String result, String params) {
        StringBuilder sb = new StringBuilder();
        if (startTime != null) {
            sb.append((endTime - startTime) + "ms");
        }
        sb.append("  " + method + "  " + result + "  " + params+":");
        return sb.toString();
    }

    public static String createSequence(String clientNo, String bizId) {
        return clientNo + "|" + bizId;
    }

}
