package com.dianping.ba.finance.exchange.siteweb.action.ajax;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Eric on 2014/6/11.
 */
public class LoadOptionActionTest {

    private LoadOptionAction loadOptionActionStub;

    @Before
    public void setUp() {
        loadOptionActionStub = new LoadOptionAction();
    }

    @Test
    public void testLoadPPStatusOption() {
        loadOptionActionStub.loadPOStatusOption();
        Assert.assertNotNull(loadOptionActionStub.getOption());
    }

    @Test
    public void testLoadBusinessTypeOption() {
        loadOptionActionStub.loadBusinessTypeOption();
        Assert.assertNotNull(loadOptionActionStub.getOption());
    }

    @Test
    public void testJsonExecute() throws Exception {
        loadOptionActionStub.jsonExecute();
    }
}
