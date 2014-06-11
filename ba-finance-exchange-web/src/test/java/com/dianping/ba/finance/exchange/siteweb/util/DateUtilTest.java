package com.dianping.ba.finance.exchange.siteweb.util;

import junit.framework.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * Description：DateUtil对应Test
 * User: liufeng.ren
 * Date: 14-1-15
 * Time: 下午6:06
 */
public class DateUtilTest {

	@Test
	public void formatDateToString() {
		Calendar cal = Calendar.getInstance();
		cal.set(2013, 1, 1);
		Date date = cal.getTime();
		String actual = DateUtil.formatDateToString(date, "yyyyMMdd");
		Assert.assertEquals("20130201", actual);
	}
}
