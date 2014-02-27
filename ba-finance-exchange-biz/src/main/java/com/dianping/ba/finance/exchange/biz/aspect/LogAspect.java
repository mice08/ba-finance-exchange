//package com.dianping.ba.finance.exchange.biz.aspect;
//
//import com.dianping.avatar.log.AvatarLogger;
//import com.dianping.avatar.log.AvatarLoggerFactory;
//import com.dianping.ba.finance.exchange.biz.utils.LogUtils;
//import org.apache.log4j.Level;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.*;
//
//import java.util.Arrays;
//
//
//@Aspect
///**
// * Created with IntelliJ IDEA.
// * User: bingqiu.yuan
// * Date: 14-2-27
// * Time: 上午10:13
// * To change this template use File | Settings | File Templates.
// */
//public class LogAspect {
//
//    private long startTime;
//    private static final AvatarLogger monitorLogger = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.service.monitor");
//
//    @Before(value="execution(* com.dianping.ba.finance.exchange.biz.impl.*.*(..))")
//    public void beforeInvoke(JoinPoint joinPoint){
//        startTime = System.currentTimeMillis();
//    }
//
//    @AfterThrowing(value="execution(* com.dianping.ba.finance.exchange.biz.impl.*.*(..))",throwing="ex")
//    public void afterThrowingMonitorLog(JoinPoint joinPoint,Exception ex){
//        LogUtils.log(monitorLogger, startTime, joinPoint.getTarget().toString()+"."+joinPoint.getSignature().getName(), Level.ERROR, Arrays.toString(joinPoint.getArgs()), ex);
//    }
//}
