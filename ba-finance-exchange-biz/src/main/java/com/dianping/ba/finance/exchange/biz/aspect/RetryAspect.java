package com.dianping.ba.finance.exchange.biz.aspect;

import com.dianping.core.type.PageModel;
import com.site.lookup.util.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


@Aspect
/**
 * Created with IntelliJ IDEA.
 * User: bingqiu.yuan
 * Date: 14-2-27
 * Time: 上午10:13
 * To change this template use File | Settings | File Templates.
 */
public class RetryAspect {

    @AfterReturning(value="@annotation(com.dianping.ba.finance.exchange.biz.annotation.Retry)",returning="rt")
    public void afterThrowingRetry(JoinPoint joinPoint,Object rt)  {
        if (StringUtils.isEmpty(rt.toString())){
            joinPoint.getThis().getClass();
        }

    }


}
