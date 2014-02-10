package com.dianping.ba.finance.exchange.biz.utils;

import junit.framework.Assert;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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

        Assert.assertEquals("PP|0001", actual);
    }

}
