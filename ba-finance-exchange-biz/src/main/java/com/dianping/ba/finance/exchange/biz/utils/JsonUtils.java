package com.dianping.ba.finance.exchange.biz.utils;
/**
 * Project: avatar-biz
 *
 * File Created at 2011-8-29
 * $Id$
 *
 * Copyright 2010 dianping.com.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Dianping Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with dianping.com.
 */

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Map;

/**
 * JSON工具类
 *
 * @author tiantian.gao
 *
 */
public final class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    /**
     * 将对象转换为JSON格式
     *
     * @param model
     * @return
     * @throws java.io.IOException
     */
    public static String toStr(Object model) throws IOException {
        return MAPPER.writeValueAsString(model);
    }

    /**
     * 将JSON字符串转换为指定类实例
     *
     * @param <T>
     * @param content
     * @param clazz
     * @return
     * @throws IOException
     */
    public static <T> T fromStr(String content, Class<T> clazz) throws IOException {
        return MAPPER.readValue(content, clazz);
    }

    /**
     * 将JSON字符串转换为Map
     *
     * @param content
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> fromStrToMap(String content) throws IOException {
        return fromStr(content, Map.class);
    }

}
