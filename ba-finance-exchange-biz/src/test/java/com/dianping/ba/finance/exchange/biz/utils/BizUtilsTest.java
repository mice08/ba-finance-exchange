package com.dianping.ba.finance.exchange.biz.utils;

import com.dianping.avatar.log.AvatarLogger;
import junit.framework.Assert;
import org.apache.log4j.Level;
import org.junit.Test;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Description：BizUtils对应测试类
 * User: liufeng.ren
 * Date: 13-12-12
 * Time: 下午4:25
 */
public class BizUtilsTest {

	@Test
	public void logShouldSuccessWhenParamsCommon()  {
		String actualStr = BizUtils.createLogMessage(new Long(100), new Long(200), "method", Level.ERROR, "params");
		Assert.assertEquals("100ms  method  error  params:", actualStr);
	}

	@Test
	public void logShouldSuccessWhenParamsIsNull()  {
		String actualStr = BizUtils.createLogMessage(null, new Long(200), null, Level.ERROR, "params");
		Assert.assertEquals("  null  error  params:", actualStr);
	}

    @Test
    public void testLogSuccess()  {
        AvatarLogger logger = mock(AvatarLogger.class);
        BizUtils.log(logger, new Long(100), "method", Level.ERROR, "params");
        verify(logger).error(anyString());
    }
}
