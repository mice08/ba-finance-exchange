package com.dianping.ba.finance.exchange.monitor.biz.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;


/**
 * Created with IntelliJ IDEA.
 * User: huajiao.zou
 * Date: 13-12-6
 * Time: 下午20:42
 * To change this template use File | Settings | File Templates.
 */
public class LogUtilsTest {

    @Before
    public void runBeforeTest() {
        //todo 初始化可以放在这里
    }

    @Test
    public void formatErrorLogMsg() throws ParseException {
        String actual = LogUtils.formatErrorLogMsg(System.currentTimeMillis(), "test", "");

        Assert.assertTrue(actual.contains("test"));
    }

    @Test
    public void formatInfoLogMsg() throws ParseException {
        String actual = LogUtils.formatErrorLogMsg(System.currentTimeMillis(), "test", "");

        Assert.assertTrue(actual.contains("test"));
    }
}
