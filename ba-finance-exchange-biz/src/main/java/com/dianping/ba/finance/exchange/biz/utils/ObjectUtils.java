package com.dianping.ba.finance.exchange.biz.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: noahshen
 * Date: 14-2-19
 * Time: 下午7:30
 * To change this template use File | Settings | File Templates.
 */
public class ObjectUtils {

    /**
     * 输出对象的所有字段值
     * @param object
     * @return
     */
    public static String toString(Object object) {
        Class<?> classType = object.getClass();

        StringBuilder sb = new StringBuilder(classType.getName());
        sb.append("{\n");

        Field fields[] = classType.getDeclaredFields();
        for(Field field : fields){
            try{
                String name = field.getName();
                String firstLetter = name.substring(0, 1).toUpperCase();

                //获得属性的set、get的方法名
                String getMethodName = "get" + firstLetter + name.substring(1);

                Method getMethod = classType.getMethod(getMethodName, new Class<?>[]{});

                //获得copy对象的属性值
                Object value = getMethod.invoke(object, new Object[]{});
                sb.append(name)
                        .append("=")
                        .append(value)
                        .append(",\n");
            } catch(Exception e) {
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
