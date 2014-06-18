package com.dianping.ba.finance.exchange.siteweb.action.ajax;

import com.dianping.ba.finance.exchange.api.ReceiveBankService;
import com.dianping.ba.finance.exchange.api.datas.ReceiveBankData;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.opensymphony.xwork2.Action;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Eric on 2014/6/11.
 */
public class LoadOptionActionTest {

    private LoadOptionAction loadOptionActionStub;

    private ReceiveBankService receiveBankServiceMock;

    @Before
    public void setUp() {
        loadOptionActionStub = new LoadOptionAction();

        receiveBankServiceMock = mock(ReceiveBankService.class);
        loadOptionActionStub.setReceiveBankService(receiveBankServiceMock);
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
    public void testLoadReceiveOrderPayChannelOption() throws Exception {
        loadOptionActionStub.loadReceiveOrderPayChannelOption();
        Assert.assertFalse(loadOptionActionStub.getOption().isEmpty());
    }

    @Test
    public void testLoadReceiveTypeOption() throws Exception {
        loadOptionActionStub.loadReceiveTypeOption();
        Assert.assertFalse(loadOptionActionStub.getOption().isEmpty());
    }

    @Test
    public void testLoadReceiveBankOptionInvalidBusinessType() throws Exception {
        loadOptionActionStub.setBusinessType(BusinessType.DEFAULT.value());
        String result = loadOptionActionStub.loadReceiveBankOption();
        Assert.assertEquals(Action.SUCCESS, result);
        Assert.assertEquals(AjaxBaseAction.ERROR_CODE, loadOptionActionStub.getCode());
    }

    @Test
    public void testLoadReceiveBankOption() throws Exception {
        ReceiveBankData rbData = new ReceiveBankData();
        rbData.setAddTime(new Date());
        rbData.setBankId(123);
        rbData.setBankName("bankName");
        rbData.setBusinessType(BusinessType.ADVERTISEMENT.value());
        rbData.setCompanyId(1);
        when(receiveBankServiceMock.findAllReceiveBank()).thenReturn(Arrays.asList(rbData));

        loadOptionActionStub.setBusinessType(BusinessType.ADVERTISEMENT.value());
        String result = loadOptionActionStub.loadReceiveBankOption();
        Assert.assertEquals(Action.SUCCESS, result);
        Assert.assertEquals(AjaxBaseAction.SUCCESS_CODE, loadOptionActionStub.getCode());
        Assert.assertEquals("bankName", loadOptionActionStub.getOption().get(123));
    }

    @Test
    public void testLoadReceiveBankOptionBankNotExist() throws Exception {
        ReceiveBankData rbData = new ReceiveBankData();
        rbData.setAddTime(new Date());
        rbData.setBankId(123);
        rbData.setBankName("bankName");
        rbData.setBusinessType(BusinessType.ADVERTISEMENT.value());
        rbData.setCompanyId(1);
        when(receiveBankServiceMock.findAllReceiveBank()).thenReturn(Arrays.asList(rbData));

        loadOptionActionStub.setBusinessType(BusinessType.GROUP_PURCHASE.value());
        String result = loadOptionActionStub.loadReceiveBankOption();
        Assert.assertEquals(Action.SUCCESS, result);
        Assert.assertEquals(AjaxBaseAction.SUCCESS_CODE, loadOptionActionStub.getCode());
        Assert.assertTrue(loadOptionActionStub.getOption().isEmpty());
    }

    @Test
    public void testJsonExecute() throws Exception {
        loadOptionActionStub.jsonExecute();
    }
}
