package com.dianping.ba.finance.exchange.biz.utils;

/**
 * Created with IntelliJ IDEA.
 * User: 遐
 * Date: 14-1-26
 * Time: 下午2:17
 * To change this template use File | Settings | File Templates.
 */
public class BizUtils {

    public static String createSequence(String clientNo, String bizId) {
        return clientNo + "|" + bizId;
    }
}
