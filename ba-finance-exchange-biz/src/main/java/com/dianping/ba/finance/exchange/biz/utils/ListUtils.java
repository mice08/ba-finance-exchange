package com.dianping.ba.finance.exchange.biz.utils;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 遐
 * Date: 14-3-12
 * Time: 下午2:44
 * To change this template use File | Settings | File Templates.
 */
public class ListUtils {

    public static String convertIntegerListToString(List<Integer> intList ){
        StringBuilder sb = new StringBuilder("[");
        for(int i: intList){
            sb.append(i).append(",");
        }
        sb.deleteCharAt(sb.lastIndexOf(",")).append("]");
        return sb.toString();
    }
}
