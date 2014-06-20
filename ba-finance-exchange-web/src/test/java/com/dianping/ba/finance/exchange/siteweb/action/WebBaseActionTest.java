package com.dianping.ba.finance.exchange.siteweb.action;

import org.junit.Before;
import org.junit.Test;

public class WebBaseActionTest {

    private WebBaseAction webBaseActionStub;

    @Before
    public void setUp() throws Exception {
        webBaseActionStub = new WebBaseActionMock();
    }

    @Test
    public void testPrepare() throws Exception {
        webBaseActionStub.prepare();
    }

    @Test
    public void testWebExecute() throws Exception {
        webBaseActionStub.webExecute();
    }


    private class WebBaseActionMock extends WebBaseAction {

        @Override
        protected String webExecute() throws Exception {
            return "success";
        }
    }
}