package com.dianping.ba.finance.exchange.monitor.job.utils;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: qucong
 * Date: 14-3-5
 * Time: 上午10:58
 * To change this template use File | Settings | File Templates.
 */
public class ListUtils {

    /**
     * 将List转成String
     * @param inputList
     * @param separator
     * @return
     */
    public static String listToString(List<Integer> inputList, String separator) {
        if (inputList == null || inputList.size() == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (Integer item : inputList) {
            if (sb.length() <= 0) {
                sb.append(item.toString());
            } else {
                sb.append(separator).append(item.toString());
            }
        }

        return sb.toString();
    }

    /**
     * 将字符串转换为List
     * @param str
     * @param separator
     * @return
     */
    public static List<Integer> stringToList(String str, String separator) {
        if (StringUtils.isEmpty(str)) {
            return Collections.emptyList();
        }
        String[] strArr = str.split(separator);
        List<Integer> list = new ArrayList<Integer>();
        for(String s : strArr) {
            list.add(Integer.valueOf(s));
        }
        return list;
    }
}
