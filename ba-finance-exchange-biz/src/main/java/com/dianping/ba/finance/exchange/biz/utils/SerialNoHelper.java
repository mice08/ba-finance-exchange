package com.dianping.ba.finance.exchange.biz.utils;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Calendar;

/**
 * Created by will on 15-3-10.
 */
public class SerialNoHelper {

    /**
     * 生成流水号,格式：yyMM+随机数
     *
     * @param count
     *
     * @return
     */
    public static String generateSerialNo(int count) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        String monthValue = "" + month;
        if (month < 10) {
            monthValue = "0" + month;
        }
        String strTime = String.valueOf(year).substring(2) + monthValue;
        StringBuffer bf = new StringBuffer();
        bf.append(strTime).append(
                RandomStringUtils.randomNumeric(count - strTime.length()));
        return bf.toString();
    }



    /**
     * 生成流水号
     *
     * @param prefix
     * @return
     */
    public static String generateSerialNo(String prefix, int serialNoLen) {
        return generateSerialNo(prefix, null, serialNoLen);
    }

    /**
     * 生成流水号
     *
     * @param prefix
     * @param postfix
     * @return
     */
    public static String generateSerialNo(String prefix, String postfix,int serialNoLen) {
        int prefixLen = 0;
        int postfixLen = 0;
        StringBuffer serialNo = new StringBuffer();
        if (!StringUtils.isEmpty(prefix)) {
            prefixLen = prefix.getBytes().length;
            serialNo.append(prefix);
        }
        if (!StringUtils.isEmpty(postfix)) {
            postfixLen = postfix.getBytes().length;
        }
        int left = serialNoLen - prefixLen - postfixLen;
        serialNo.append(generateSerialNo(left));
        if (postfixLen != 0) {
            serialNo.append(postfix);
        }
        return serialNo.toString();
    }

}
