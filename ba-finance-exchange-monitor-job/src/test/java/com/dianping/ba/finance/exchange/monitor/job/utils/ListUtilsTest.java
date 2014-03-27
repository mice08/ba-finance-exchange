package com.dianping.ba.finance.exchange.monitor.job.utils;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ListUtilsTest {


    @Test
    public void listToStringForEmptyInput() throws ParseException {
        String actual = ListUtils.listToString(null, ",");
        Assert.assertEquals("", actual);
    }

    @Test
    public void listToStringForInputListContainOneItem() throws ParseException {
        List<Integer> inputList = new ArrayList<Integer>();
        inputList.add(1);
        String actual = ListUtils.listToString(inputList, ",");
        Assert.assertEquals("1", actual);
    }

    @Test
    public void listToStringForInputListContainMoreThanOneItem() throws ParseException {
        List<Integer> inputList = new ArrayList<Integer>();
        inputList.add(1);
        inputList.add(2);
        String actual = ListUtils.listToString(inputList, ",");
        Assert.assertEquals("1,2", actual);
    }
}
