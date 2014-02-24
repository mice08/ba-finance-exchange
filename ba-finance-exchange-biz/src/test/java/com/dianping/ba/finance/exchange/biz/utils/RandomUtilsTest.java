package com.dianping.ba.finance.exchange.biz.utils;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: noahshen
 * Date: 14-2-23
 * Time: 0:49
 * To change this template use File | Settings | File Templates.
 */
public class RandomUtilsTest {

    private static final String numberRegex = "^[0-9]+$";

    private static final String uuidRegex = "^[A-Z0-9]{8}-[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{12}$";

    @Test
    public void testGetRandomNumbersAndLetters() throws Exception {
        String str = RandomUtils.getRandomNumbersAndLetters(10);
        Assert.assertEquals(10, str.length());
    }

    @Test
    public void testGetRandomNumbers() throws Exception {
        String str = RandomUtils.getRandomNumbers(12);
        boolean ok = Pattern.matches(numberRegex, str);
        Assert.assertTrue(ok);

    }

    @Test
    public void testGetRandom() throws Exception {
        int i = RandomUtils.getRandom(20);
        Assert.assertTrue(i < 20);
    }

    @Test
    public void testGetUUID() throws Exception {
        String uuid = RandomUtils.getUUID();
        boolean ok = Pattern.matches(uuidRegex, uuid);
        Assert.assertTrue(ok);
    }

    @Test
    public void testGetRandomBigDecimal() throws Exception {
        double min = 10.87;
        double max = 187.00;
        BigDecimal bd = RandomUtils.getRandomBigDecimal(min, max);
        Assert.assertTrue(bd.compareTo(new BigDecimal(min)) > 0);
        Assert.assertTrue(bd.compareTo(new BigDecimal(max)) < 0);
    }
}
