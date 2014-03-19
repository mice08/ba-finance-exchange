//package com.dianping.ba.finance.exchange.biz.aspect;
//
//import com.dianping.avatar.log.AvatarLogger;
//import com.dianping.avatar.log.AvatarLoggerFactory;
//import com.dianping.ba.finance.exchange.biz.utils.LogUtils;
//import com.dianping.core.type.PageModel;
//import com.site.lookup.util.StringUtils;
//import org.apache.log4j.Level;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.reflect.MethodSignature;
//
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.Collection;
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
//public class RetryAspect {
//    private static final AvatarLogger monitorLogger = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.service.monitor");
//
//
//    @Around(value="@annotation(com.dianping.ba.finance.exchange.biz.annotation.Retry)")
//    public Object afterThrowingRetry(ProceedingJoinPoint proceedingJoinPoint) throws Exception {
//        Object rt;
//        int retryTimes=3;
//        for(int i=1;i<=retryTimes;i++){
//            try {
//                rt = process(proceedingJoinPoint);
//                if (rt!=null) {
//                    return rt;
//                }
//            } catch (Throwable throwable) {
//                LogUtils.log(monitorLogger, System.currentTimeMillis(), "retry", Level.ERROR, proceedingJoinPoint.getArgs().toString(), throwable);
//            }
//        }
//        return getReturnObject(proceedingJoinPoint);
//    }
//
//    private Object getReturnObject(ProceedingJoinPoint proceedingJoinPoint) throws Exception {
//        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
//        return signature.getReturnType().newInstance();
//    }
//
//    private Object process(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
//    }
//}
