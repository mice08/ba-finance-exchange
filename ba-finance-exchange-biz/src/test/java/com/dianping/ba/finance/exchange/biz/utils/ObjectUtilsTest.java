package com.dianping.ba.finance.exchange.biz.utils;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: noahshen
 * Date: 14-2-19
 * Time: 下午7:36
 * To change this template use File | Settings | File Templates.
 */
public class ObjectUtilsTest {

    @Test
    public void testToString() throws Exception {
        TestObject to = new TestObject();
        to.setProperty("value");
        String expectString = "com.dianping.ba.finance.exchange.biz.utils.ObjectUtilsTest$TestObject{\nproperty=value,\n}";
        String s = ObjectUtils.toString(to);
        Assert.assertEquals(expectString, s);

    }

    private class TestObject {

        private String property;

        private TestObject() {
        }

        public String getProperty() {
            return property;
        }

        public void setProperty(String property) {
            this.property = property;
        }
    }
}
