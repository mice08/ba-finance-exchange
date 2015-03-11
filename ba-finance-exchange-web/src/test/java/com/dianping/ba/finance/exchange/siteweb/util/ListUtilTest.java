package com.dianping.ba.finance.exchange.siteweb.util;

import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ListUtilTest {

    @Test
    public void testConvertStringArrayToIntegerList() {
        String[] strArr = {"1", "2", "3"};
        List<Integer> intList = ListUtil.convertStringArrayToIntegerList(strArr);

        Assert.assertNotNull(intList);
        Assert.assertEquals(3, intList.size());

    }

    @Test
    public void listToStringTest() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        String splitChar = ",";
        String exceptString = "1,2,";
        ListUtil listUtils = new ListUtil();
        String actualStr = listUtils.listToString(list, splitChar);
        Assert.assertEquals(exceptString, actualStr);
    }

    @Test
    public void listToStringTestListIsEmpty() {
        List<Integer> list = new ArrayList<Integer>();
        String splitChar = ",";
        String exceptString = "";
        ListUtil listUtils = new ListUtil();
        String actualStr = listUtils.listToString(list, splitChar);
        Assert.assertEquals(exceptString, actualStr);
    }
}
