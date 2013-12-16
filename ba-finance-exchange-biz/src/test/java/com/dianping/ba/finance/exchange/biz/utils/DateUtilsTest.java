package com.dianping.ba.finance.exchange.biz.utils;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: huajiao.zou
 * Date: 13-12-6
 * Time: 下午20:42
 * To change this template use File | Settings | File Templates.
 */
public class DateUtilsTest {

    @Test
    public void dateSubtractTest() throws ParseException {
        Calendar calA = Calendar.getInstance();
        calA.set(2012,11,8);
        Date dateA = calA.getTime();
        Calendar calB = Calendar.getInstance();
        calB.set(2012,11,5);
        Date dateB = calB.getTime();
        long day = DateUtils.dateSubtract(dateA,dateB);
        Assert.assertEquals(3,day);
    }

    @Test
    public void compareToDateBiggerTest(){
        Calendar calA = Calendar.getInstance();
        calA.set(2012,11,5);
        Date dateA = calA.getTime();
        Calendar calB = Calendar.getInstance();
        calB.set(2011,10,5);
        Date dateB = calB.getTime();
        Assert.assertEquals(false,DateUtils.compareToDate(dateA,dateB));
    }

    @Test
    public void compareToDateSmallerTest(){
        Calendar calA = Calendar.getInstance();
        calA.set(2012,10,5);
        Date dateA = calA.getTime();
        Calendar calB = Calendar.getInstance();
        calB.set(2012,11,5);
        Date dateB = calB.getTime();
        Assert.assertEquals(true,DateUtils.compareToDate(dateA,dateB));
    }

    @Test
    public void addDateTest() throws ParseException {
        Calendar calA = Calendar.getInstance();
        calA.set(2012,11,5);
        Date date = calA.getTime();
        Calendar calExpect = Calendar.getInstance();
        calExpect.set(2012,11,8);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dateExpect = format.parse(format.format(calExpect.getTime()));
        int day = 3;
        try {
            Assert.assertEquals(dateExpect,DateUtils.addDate(date,day));
        } catch (ParseException e) {
            Assert.assertNull(e);
        }
    }


    @Test
    public void extractTimeTest() throws ParseException {
        Calendar calender = Calendar.getInstance();
        calender.set(2012,11,5,8,8,8);
        Date date = calender.getTime();
        Calendar calExpect = Calendar.getInstance();
        calExpect.set(2012,11,5,0,0,0);
        Date dateExpect = calExpect.getTime();
        Assert.assertEquals(dateExpect.toString(), DateUtils.removeTime(date).toString());

    }
}
