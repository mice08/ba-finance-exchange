package com.dianping.ba.finance.exchange.biz.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 * User: bingqiu.yuan
 * Date: 14-2-27
 * Time: 下午3:24
 * To change this template use File | Settings | File Templates.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Retry {

}
