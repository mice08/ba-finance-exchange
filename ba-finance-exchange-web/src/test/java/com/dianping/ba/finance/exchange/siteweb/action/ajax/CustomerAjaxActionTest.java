package com.dianping.ba.finance.exchange.siteweb.action.ajax;

import com.dianping.ba.finance.exchange.api.beans.CustNameSuggestionBean;
import com.dianping.ba.finance.exchange.siteweb.services.CustomerService;
import com.opensymphony.xwork2.Action;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomerAjaxActionTest {

    private CustomerAjaxAction customerAjaxActionStub;

    private CustomerService customerServiceMock;

    @Before
    public void setUp() throws Exception {
        customerAjaxActionStub = new CustomerAjaxAction();

        customerServiceMock = mock(CustomerService.class);
        customerAjaxActionStub.setCustomerService(customerServiceMock);
    }


    @Test
    public void testFindCustomerNameSuggestionCustomerNameEmpty() throws Exception {
        customerAjaxActionStub.setCustomerName(null);

        String result = customerAjaxActionStub.findCustomerNameSuggestion();
        Assert.assertEquals(Action.SUCCESS, result);
        Assert.assertEquals(AjaxBaseAction.SUCCESS_CODE, customerAjaxActionStub.getCode());
        List<CustNameSuggestionBean> suggestionBeanList = (List<CustNameSuggestionBean>) customerAjaxActionStub.getMsg().get("suggestion");
        Assert.assertTrue(suggestionBeanList.isEmpty());
    }

    @Test
    public void testFindCustomerNameSuggestion() throws Exception {

        CustNameSuggestionBean custNameSuggestionBean1 = new CustNameSuggestionBean();
        custNameSuggestionBean1.setCustomerId(87871);
        custNameSuggestionBean1.setCustomerName("客户名称1");

        when(customerServiceMock.getCustomerNameSuggestion(anyString())).thenReturn(Arrays.asList(custNameSuggestionBean1));
        customerAjaxActionStub.setCustomerName("小南国");

        String result = customerAjaxActionStub.findCustomerNameSuggestion();
        Assert.assertEquals(Action.SUCCESS, result);
        Assert.assertEquals(AjaxBaseAction.SUCCESS_CODE, customerAjaxActionStub.getCode());
        List<CustNameSuggestionBean> suggestionBeanList = (List<CustNameSuggestionBean>) customerAjaxActionStub.getMsg().get("suggestion");
        Assert.assertFalse(suggestionBeanList.isEmpty());
        Assert.assertEquals(87871, suggestionBeanList.get(0).getCustomerId());
    }
}