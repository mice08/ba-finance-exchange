package com.dianping.ba.finance.exchange.biz.aspect;

import com.dianping.core.type.PageModel;
import com.site.lookup.util.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;


@Aspect
/**
 * Created with IntelliJ IDEA.
 * User: bingqiu.yuan
 * Date: 14-2-27
 * Time: 上午10:13
 * To change this template use File | Settings | File Templates.
 */
public class RetryAspect {
    @Around(value="@annotation(com.dianping.ba.finance.exchange.biz.annotation.Retry)")
    public Object afterThrowingRetry(ProceedingJoinPoint proceedingJoinPoint)  {
        Object rt;
        int retryTimes=3;
        for(int i=1;i<=retryTimes;i++){
            try {
                rt = process(proceedingJoinPoint);
                if (rt!=null) {
                    return rt;
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return getReturnObject(proceedingJoinPoint);
    }

    private Object getReturnObject(ProceedingJoinPoint proceedingJoinPoint)  {
        Object rt=null;
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        try {
            rt = signature.getReturnType().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return rt;
    }

    private Object process(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
    }
}
