package com.dianping.ba.finance.exchange.biz.utils;


import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

/**
 * Random Utils 随机工具，可以用于测试类的测试数据生成
 */
public class RandomUtils {

    public static final String NUMBERS_AND_LETTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String NUMBERS             = "0123456789";
    public static final String LETTERS             = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String CAPITAL_LETTERS     = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LOWER_CASE_LETTERS  = "abcdefghijklmnopqrstuvwxyz";

    /**
     * 生成随机字符串，包含数字和字母
     * @param length
     * @return
     */
    public static String getRandomNumbersAndLetters(int length) {
        return getRandom(NUMBERS_AND_LETTERS, length);
    }

    /**
     * 生成随机字符串，只包含数字
     * @param length
     * @return
     */
    public static String getRandomNumbers(int length) {
        return getRandom(NUMBERS, length);
    }

    /**
     * 生成随机字符串，只包含字母
     * @param length
     * @return
     */
    public static String getRandomLetters(int length) {
        return getRandom(LETTERS, length);
    }

    /**
     * 生成随机字符串，只包含大写字母
     * @param length
     * @return
     */
    public static String getRandomCapitalLetters(int length) {
        return getRandom(CAPITAL_LETTERS, length);
    }

    /**
     * 生成随机字符串，只包含小写字母
     * @param length
     * @return
     */
    public static String getRandomLowerCaseLetters(int length) {
        return getRandom(LOWER_CASE_LETTERS, length);
    }

    /**
     * 根据传入的字符串，生成随机字符串
     * @param source
     * @param length
     * @return
     */
    public static String getRandom(String source, int length) {
        return StringUtils.isEmpty(source) ? null : getRandom(source.toCharArray(), length);
    }

    /**
     * 根据传入的字符数组，生成随机字符串
     * @param sourceChar
     * @param length
     * @return
     */
    public static String getRandom(char[] sourceChar, int length) {
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
     * 随机生成小于max的整型
     * @param max
     * @return
     */
    public static int getRandom(int max) {
        return getRandom(0, max);
    }

    /**
     * 随机生成大于min，小于max的整型
     * @param min
     * @param max
     * @return
     */
    public static int getRandom(int min, int max) {
        if (min > max) {
            return 0;
        }
        if (min == max) {
            return min;
        }
        return min + new Random().nextInt(max - min);
    }

    /**
     * 获取UUID
     * @return
     */
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().toUpperCase();
    }

    /**
     * 随机生成大于min，小于max的BigDecimal
     * @param min
     * @param max
     * @return
     */
    public static BigDecimal getRandomBigDecimal(double min, double max) {
        double range = max - min;
        double scaled = new Random().nextDouble() * range;
        double shifted = scaled + min;
        return new BigDecimal(shifted);
    }

}