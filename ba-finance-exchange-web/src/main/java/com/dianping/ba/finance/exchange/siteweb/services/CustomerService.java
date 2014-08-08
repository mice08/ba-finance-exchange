package com.dianping.ba.finance.exchange.siteweb.services;

import com.dianping.ba.finance.exchange.api.beans.CustNameSuggestionBean;

import java.util.Arrays;
import java.util.List;

/**
 * Created by noahshen on 14-6-17.
 */
public class CustomerService {

    public List<CustNameSuggestionBean> getCustomerNameSuggestion(String customerName) throws Exception {
        CustNameSuggestionBean custNameSuggestionBean1 = new CustNameSuggestionBean();
        custNameSuggestionBean1.setCustomerId(87871);
        custNameSuggestionBean1.setCustomerName("客户名称1");

        CustNameSuggestionBean custNameSuggestionBean2 = new CustNameSuggestionBean();
        custNameSuggestionBean2.setCustomerId(87872);
        custNameSuggestionBean2.setCustomerName("客户名称2");

        return Arrays.asList(custNameSuggestionBean1, custNameSuggestionBean2);
    }
}
