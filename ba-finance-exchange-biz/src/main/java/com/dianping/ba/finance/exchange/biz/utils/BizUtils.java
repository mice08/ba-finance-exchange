package com.dianping.ba.finance.exchange.biz.utils;

import com.dianping.finance.common.util.DateUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: 遐
 * Date: 14-1-26
 * Time: 下午2:17
 * To change this template use File | Settings | File Templates.
 */
public class BizUtils {

    public static final String NUMBERS_AND_LETTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
	 * 创建sequence
	 *
	 * @param clientNo 客户no
	 * @param bizId    id
	 * @return
	 */
	public static String createSequence(String clientNo, String bizId) {
		Date today = Calendar.getInstance().getTime();
		String todayHour;
		try {
			todayHour = DateUtils.format("yyyyMMddHH", today);
		} catch (ParseException e) {
			todayHour ="";
		}
		return clientNo + "|" + bizId + "|" + todayHour;
	}

    /**
     * 随机生成字符串
     * @param sourceChar
     * @param length
     * @return
     */
    public static String random(char[] sourceChar, int length) {
        if (sourceChar == null || sourceChar.length == 0 || length < 0) {
            return null;
        }

        StringBuilder str = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            str.append(sourceChar[random.nextInt(sourceChar.length)]);
        }
        return str.toString();
    }

    /**
     * 随机生成PayCode
     * @return
     */
    public static String generatePayCode() {
        return random(NUMBERS_AND_LETTERS.toCharArray(), 8);
    }

}
