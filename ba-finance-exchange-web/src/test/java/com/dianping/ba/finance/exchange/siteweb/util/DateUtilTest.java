package com.dianping.ba.finance.exchange.siteweb.util;

import junit.framework.Assert;
import org.junit.Test;

import java.text.ParseException;
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
	public void testFormatDateToString() {
		Calendar cal = Calendar.getInstance();
		cal.set(2013, 1, 1);
		Date date = cal.getTime();
		String actual = DateUtil.formatDateToString(date, "yyyyMMdd");
		Assert.assertEquals("20130201", actual);
	}

    @Test
    public void testFormatDateTime(){
        String dateStr="2014-03-03 11:11:11";
        Date date=DateUtil.formatDateTime(dateStr);
        Assert.assertNotNull(date);
    }

    @Test
    public void testFormatDate() throws ParseException {
        String dateStr="2014-03-03";
        Date date=DateUtil.formatDate(dateStr,false);
        date=DateUtil.formatDate(dateStr,true);
    }

    @Test
    public void testIsValidDate(){
        String sDate="2014-03-02";
        Boolean result=DateUtil.isValidDate(sDate);
        Assert.assertTrue(result);

        sDate="a";
        result=DateUtil.isValidDate(sDate);
        Assert.assertTrue(!result);

        sDate=null;
        result=DateUtil.isValidDate(sDate);
        Assert.assertTrue(!result);
    }

    @Test
    public void testParseDate() throws Exception {
        Date d = DateUtil.parseDate("20140731131313", "yyyyMMddhhmmss");
        Assert.assertNotNull(d);

    }
    @Test
    public void testParseDateError() throws Exception {
        Date d = DateUtil.parseDate("201403ABC", "yyyyMMddhhmmss");
        Assert.assertNull(d);

    }
}
