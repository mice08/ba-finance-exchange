package com.dianping.ba.finance.exchange.biz.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: huajiao.zou
 * Date: 13-12-5
 * Time: 下午4:52
 * To change this template use File | Settings | File Templates.
 */
public class DateUtils {
    /**
     * 比较两个日期的大小
     *
     * @param dateA
     * @param dateB
     * @return
     */
    public static boolean compareToDate(Date dateA, Date dateB) {
        int result = dateA.compareTo(dateB);
        //dateA<dateB return true
        if (result < 0) {
            return true;
        }
        return false;
    }

    /**
     * 返回2个Date型日期相差的天数
     *
     * @param dateA
     * @param dateB
     * @return
     */
    public static long dateSubtract(Date dateA, Date dateB) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return (format.parse(format.format(dateA)).getTime() - format.parse(format.format(dateB)).getTime()) / (24 * 60 * 60 * 1000);
    }

    public static Date getTodayDate() throws ParseException {
        Calendar calToday = Calendar.getInstance();
        Date todayDate = calToday.getTime();
        return todayDate;
    }

    /**
     * 返回日期加day天数的日期  eg:2013-11-01
     *
     * @param date
     * @param day
     * @return
     */
    public static Date addDate(Date date, int day) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dateAfterFormat = format.parse(format.format(date));
        calendar.setTime(dateAfterFormat);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    /**
     * 根据时间和格式化返回String
     * @param date   时间
     * @param formatString 格式化
     * @return  日期转字符串
     */
    public static String getFormatDateString(Date date,String formatString) {
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        return format.format(date);
    }

    /**
     * 去除日期中的时分秒
     *
     * @param date 含时分秒的日期
     * @return 不含时分秒的日期
     * @throws java.text.ParseException
     */
    public static Date removeTime(Date date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

}
