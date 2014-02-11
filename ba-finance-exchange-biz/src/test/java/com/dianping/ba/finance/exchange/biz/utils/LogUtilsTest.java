package com.dianping.ba.finance.exchange.biz.utils;

import com.dianping.avatar.log.AvatarLogger;
import junit.framework.Assert;
import org.apache.log4j.Level;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created with IntelliJ IDEA.
 * User: 遐
 * Date: 14-1-26
 * Time: 下午2:07
 * To change this template use File | Settings | File Templates.
 */
public class LogUtilsTest {
    @Test
    public void logShouldSuccessWhenParamsCommon()  {
        String actualStr = LogUtils.createLogMessage(new Long(100), new Long(200), "method", Level.ERROR, "params");
        Assert.assertEquals("100ms  method  error  params:", actualStr);
    }

    @Test
    public void logShouldSuccessWhenParamsIsNull()  {
        String actualStr = LogUtils.createLogMessage(null, new Long(200), null, Level.ERROR, "params");
        Assert.assertEquals("  null  error  params:", actualStr);
    }

    @Test
    public void testLogSuccess()  {
        AvatarLogger logger = mock(AvatarLogger.class);
        LogUtils.log(logger, new Long(100), "method", Level.ERROR, "params");
        verify(logger).error(anyString());
    }

    @Test
    public void testCreateLogMessage() {
        long startTime = System.currentTimeMillis();
        long endTime = System.currentTimeMillis();

        String actual = LogUtils.createLogMessage(startTime, endTime,"method name", Level.ERROR,"param1=1&param2=2");

        Assert.assertEquals("0ms  method name  error  param1=1&param2=2:",actual);
    }

    @Test
    public void testCreateLogParams() {
        List list = new ArrayList<String>();
        list.add("abc");
        list.add("cbd");

        String actual = LogUtils.createLogParams(list);

        Assert.assertEquals("java.util.ArrayList{\"abc\",\"cbd\"}",actual);
    }
}
