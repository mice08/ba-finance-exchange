package com.dianping.ba.finance.exchange.siteweb.action;

import com.opensymphony.xwork2.Action;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

public class RedirectActionTest {

    private RedirectAction redirectActionStub;
    @Before
    public void setUp() throws Exception {
        redirectActionStub = new RedirectAction();
    }

    @Test
    public void testWebExecute() throws Exception {
        String result = redirectActionStub.webExecute();
        Assert.assertEquals(Action.SUCCESS, result);
    }
}