package com.dianping.ba.finance.exchange.siteweb.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 */
public class ListUtil {

    /**
     * Convert an array of Strings to a list of Integers.
     *
     * @param strArr
     * @return
     */
    public static List<Integer> convertStringArrayToIntegerList(String[] strArr) {
        List integerList = new ArrayList<Integer>();
        for (String str : strArr) {
            String strInt = StringUtils.isNotBlank(str) ? str.trim() : "";
            if (isNumeric(strInt)) {
                integerList.add(Integer.valueOf(strInt));
            }
        }
        return integerList;
    }

    /**
     * 将List集合用输入字符连接转成字符串
     * @param list
     * @param splitChar
     * @return
     */
    public static String listToString(List<Integer> list, String splitChar) {
        if(CollectionUtils.isEmpty(list)){
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer item : list) {
            stringBuilder.append(item + splitChar);
        }
        return stringBuilder.toString();
    }

    /**
     * 判断字符串是否为数字
     * @param str
     * @return
     */
    private static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]+");
        return pattern.matcher(str).matches();
    }
}
