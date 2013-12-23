package com.dianping.ba.finance.exchange.biz.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: jinyu.yin
 * Date: 13-12-11
 * Time: 下午4:13
 * To change this template use File | Settings | File Templates.
 */
public class ConvertUtils {
    public static <TI extends Object> TI copy(TI object) throws Exception {
        return copy(object, (Class<TI>)object.getClass());
    }

    public static <TI extends Object,TO extends Object> TO copy(TI object, Class<TO> retClassType) throws Exception {
        Class<?> classType = object.getClass();

        Constructor<TO> constructor = retClassType.getConstructor(new Class<?>[]{});
        TO objectCopy = constructor.newInstance(new Object[]{});

        Field fields[] = classType.getDeclaredFields();
        for(Field field : fields){
            try{
                String name = field.getName();
                String firstLetter = name.substring(0, 1).toUpperCase();

                //获得属性的set、get的方法名
                String getMethodName = "get" + firstLetter + name.substring(1);
                String setMethodName = "set" + firstLetter + name.substring(1);

                Method getMethod = classType.getMethod(getMethodName, new Class<?>[]{});
                Method setMethod = retClassType.getMethod(setMethodName, new Class<?>[]{field.getType()});

                //获得copy对象的属性值
                Object value = getMethod.invoke(object, new Object[]{});

                //设置被copy对象的属性值
                setMethod.invoke(objectCopy, value);
            }catch(Exception e){
            }
        }

        return objectCopy;
    }
}