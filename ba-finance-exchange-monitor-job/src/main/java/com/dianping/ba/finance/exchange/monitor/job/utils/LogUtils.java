package com.dianping.ba.finance.exchange.monitor.job.utils;

/**
 * Log工具类
 */
public class LogUtils {

    /**
     * 构建错误日志的日志信息
     * @param startTime
     * @param method
     * @param params
     * @return
     */
    public static String formatErrorLogMsg(Long startTime, String method, String params) {
        StringBuilder sb = new StringBuilder();
        String separator = "  ";
        Long endTime = System.currentTimeMillis();
        sb.append((endTime - startTime)+"ms").append(separator).append(method)
                .append(separator).append("error").append(separator).append(params).append(":");
        return sb.toString();
    }

    /**
     * 构建业务日志的日志信息
     * @param startTime
     * @param method
     * @param params
     * @return
     */
    public static String formatInfoLogMsg(Long startTime, String method, String params) {
        StringBuilder sb = new StringBuilder();
        String separator = "  ";
        Long endTime = System.currentTimeMillis();
        sb.append("action="+method).append(separator).append("period="+(endTime - startTime)+"ms")
                .append(separator).append(params).append(":");
        return sb.toString();
    }
}
