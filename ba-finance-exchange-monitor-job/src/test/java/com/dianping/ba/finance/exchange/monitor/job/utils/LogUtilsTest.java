package com.dianping.ba.finance.exchange.monitor.job.utils;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;

public class LogUtilsTest {

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
