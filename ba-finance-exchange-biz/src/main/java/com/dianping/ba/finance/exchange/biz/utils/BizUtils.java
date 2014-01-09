package com.dianping.ba.finance.exchange.biz.utils;

import com.dianping.avatar.log.AvatarLogger;
import org.apache.log4j.Level;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jingzhou.yan
 * Date: 13-12-6
 * Time: 下午3:16
 * To change this template use File | Settings | File Templates.
 */
public class BizUtils {

    @Deprecated
    public static void log(AvatarLogger logger, Long startTime, String method, String result, String params, Throwable e) {
        String logStr=logStr(startTime,System.currentTimeMillis(),method,result,params);
        if (e == null) {
            logger.info(logStr);
        } else {
            logger.error(logStr, e);
        }
    }

    @Deprecated
    public static String logStr(Long startTime,Long endTime, String method, String result, String params) {
        StringBuilder sb = new StringBuilder();
        if (startTime != null) {
            sb.append((endTime - startTime) + "ms");
        }
        sb.append("  " + method + "  " + result + "  " + params+":");
        return sb.toString();
    }

    /**
     * 记录日志
     *
     * @param logger    日志类型
     * @param startTime 开始时间 ，为 null则不记录方法执行时间
     * @param method    具体的方法
     * @param logLevel  日志级别
     * @param params    方法参数
     * @param e         异常。如为null则记录info日志；如不为null，则记录error日志。
     */
    public static void log(AvatarLogger logger, Long startTime, String method, Level logLevel, String params, Throwable e){
        try{
            String logMsg = createLogMessage(startTime, System.currentTimeMillis(), method, logLevel, params);
            String levelStr = logLevel.toString().toLowerCase();
            Class clz = logger.getClass();
            Method methodWithoutExceptionInfo = clz.getMethod(levelStr, Object.class);
            Method methodWithinExceptionInfo = clz.getMethod(levelStr, new Class[]{Object.class, Throwable.class});
            if (e == null) {
                methodWithoutExceptionInfo.invoke(logger,logMsg);
            } else {
                methodWithinExceptionInfo.invoke(logger,logMsg,e);
            }
        }catch(Exception ex){
            //ignore log exception
        }
    }

    /**
     * 记录不包含异常详细信息的日志
     *
     * @param logger    日志类型
     * @param startTime 开始时间 ，为 null则不记录方法执行时间
     * @param method    具体的方法
     * @param logLevel  日志级别
     * @param params    方法参数
     */
    public static void log(AvatarLogger logger, Long startTime, String method, Level logLevel, String params){
        log(logger,startTime,method,logLevel,params,null);
    }

    /**
     * 根据参数条件构建日志信息
     *
     * @param startTime
     * @param endTime
     * @param method
     * @param logLevel
     * @param params
     * @return
     */
    public static String createLogMessage(Long startTime, Long endTime, String method, Level logLevel, String params) {
        final String period = "  ";
        StringBuilder sb = new StringBuilder();
        if (startTime != null) {
            sb.append((endTime - startTime)).append("ms");
        }
        sb.append(period).append(method).append(period).append(logLevel.toString().toLowerCase()).append(period).append(params).append(":");
        return sb.toString();
    }

    /**
     * 将参数集转换为日志参数字符串
     *
     * @param listToBeLogged
     * @return
     */
    public static String createLogParams(List<? extends Object> listToBeLogged){
        StringBuilder sb = new StringBuilder(listToBeLogged.getClass().getName());
        try{
            for(Object o: listToBeLogged){
                sb.append("{").append(JsonUtils.toStr(o)).append(",");
            }
            sb.deleteCharAt(sb.toString().lastIndexOf(",")).append("}");
        } catch (Exception e) {
            // ignore log exception
        }
        return sb.toString();
    }
}
