package com.dianping.ba.finance.exchange.siteweb.action.ajax;

import com.dianping.ba.finance.exchange.siteweb.beans.CustomerNameSuggestionBean;
import com.dianping.ba.finance.exchange.siteweb.services.CustomerNameService;
import com.dianping.finance.common.util.LionConfigUtils;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by Eric on 2014/5/29.
 */
public class CustomerNameSuggestionAction extends AjaxBaseAction {

    private Map<String, Object> msg = Maps.newHashMap();

    private int code;

    private String customerName;

    private CustomerNameService customerNameService;

    @Override
    protected void jsonExecute() throws Exception {
    }

    public String fetchCustomerNameSuggestion() {
        int maxSize = Integer.parseInt(LionConfigUtils.getProperty("ba-finance-settle-web.suggestion.maxSize", "10"));
        List<CustomerNameSuggestionBean> suggestionBeanList = customerNameService.getCustomerNameSuggestion(customerName, maxSize, getLoginId());
        if (CollectionUtils.isNotEmpty(suggestionBeanList)) {
            msg.put("suggestion", suggestionBeanList);
        }
        code = SUCCESS_CODE;
        return SUCCESS;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public Map<String, Object> getMsg() {
        return msg;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerNameService(CustomerNameService customerNameService) {
        this.customerNameService = customerNameService;
    }
}
