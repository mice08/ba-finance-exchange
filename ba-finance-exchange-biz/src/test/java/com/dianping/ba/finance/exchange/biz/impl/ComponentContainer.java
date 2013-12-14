package com.dianping.ba.finance.exchange.biz.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: jinyu.yin
 * Date: 13-12-14
 * Time: 下午6:14
 * To change this template use File | Settings | File Templates.
 */
public enum ComponentContainer {
    SPRING;

    private final String[]				SPRING_CONFIG	= new String[] {"classpath*:config/spring/local/appcontext-*.xml","classpath*:config/spring/common/appcontext-*.xml"};

    private transient ApplicationContext ctx;

    private ComponentContainer() {
        this.ctx = new ClassPathXmlApplicationContext(SPRING_CONFIG);
    }

    @SuppressWarnings("unchecked")
    public <T> T lookup(String id) {
        return (T) this.ctx.getBean(id);
    }

    public <T> T lookup(String id, Class<T> type) {
        return (T) this.ctx.getBean(id, type);
    }
}