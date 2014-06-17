package com.dianping.ba.finance.exchange.siteweb.services;

import com.dianping.ba.finance.exchange.api.beans.CustNameSuggestionBean;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class CustomerServiceTest {

    private CustomerService customerServiceStub;
    @Before
    public void setUp() throws Exception {
        customerServiceStub = new CustomerService();
    }

    @Test
    public void testGetCustomerNameSuggestion() throws Exception {
        List<CustNameSuggestionBean> suggestionBeanList = customerServiceStub.getCustomerNameSuggestion("小南国");
        Assert.assertNotNull(suggestionBeanList);
        Assert.assertFalse(suggestionBeanList.isEmpty());
    }
}