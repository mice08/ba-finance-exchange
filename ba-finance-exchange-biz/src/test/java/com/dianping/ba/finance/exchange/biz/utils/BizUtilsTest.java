package com.dianping.ba.finance.exchange.biz.utils;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Description：BizUtils对应测试类
 * User: liufeng.ren
 * Date: 13-12-12
 * Time: 下午4:25
 */
public class BizUtilsTest {

	@Test
	public void logShouldSuccessWhenParamsCommon()  {
		String actualStr = BizUtils.logStr(new Long(100), new Long(200), "method", "result", "params");
		Assert.assertEquals("100ms  method  result  params:", actualStr);
	}

	@Test
	public void logShouldSuccessWhenParamsIsNull()  {
		String actualStr = BizUtils.logStr(null,new Long(200), null, null, "params");
		Assert.assertEquals("  null  null  params:", actualStr);
	}
}
