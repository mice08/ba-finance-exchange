package com.dianping.ba.finance.exchange.biz.utils;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Description：BizUtils对应测试类
 * User: liufeng.ren
 * Date: 13-12-12
 * Time: 下午4:25
 */
public class BizUtilsTest {

    @Test
    public void testCreateSequence(){
        String actual = BizUtils.createSequence("PP", "0001");
        Assert.assertEquals("PP|0001", actual.substring(0,7));     //ignore 当日
    }

    @Test
    public void testGeneratePayCode() throws Exception {
        String payCode = BizUtils.generatePayCode();
        System.out.println(payCode);
        Assert.assertNotNull(payCode);
    }
}
