package com.dianping.ba.finance.exchange.siteweb.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Eric on 2014/6/11.
 */
public class DateUtil {
    public static Date formatDate(String dateStr, boolean isEndDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(dateStr);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (!isEndDate) {
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
        } else {
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
        }
        return c.getTime();
    }

    public static Date formatDateTime(String dateTimeStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        return isValidDate(dateTimeStr) ? format.parse(dateTimeStr, pos) : null;
    }

    public static Date parseDate(String dateTimeStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(dateTimeStr);
        } catch (ParseException e) {
            // ignore
        }
        return null;
    }

    /**
     * 判断日期是否合法
     *
     * @param sDate
     * @return
     */
    public static boolean isValidDate(String sDate) {
        try {
            SimpleDateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd");
            dateFormate.parse(sDate);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * date 按照格式转换程字符串
     * @param date
     * @param formatString eg.yyyy-MM-dd
     * @return
     */
    public static String formatDateToString(Date date, String formatString){
        try {
            DateFormat dateFormat = new SimpleDateFormat(formatString);
            return dateFormat.format(date);
        }catch (Exception e){
            return null;
        }
    }
}
