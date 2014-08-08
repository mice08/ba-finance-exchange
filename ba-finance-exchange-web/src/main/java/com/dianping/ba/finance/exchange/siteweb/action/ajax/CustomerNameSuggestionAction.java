package com.dianping.ba.finance.exchange.siteweb.action.ajax;

import com.dianping.ba.finance.exchange.siteweb.beans.CustomerInfoBean;
import com.dianping.ba.finance.exchange.siteweb.beans.CustomerNameSuggestionBean;
import com.dianping.ba.finance.exchange.siteweb.services.CustomerNameService;
import com.dianping.finance.common.util.LionConfigUtils;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Eric on 2014/5/29.
 */
public class CustomerNameSuggestionAction extends AjaxBaseAction {

    private Map<String, Object> msg = Maps.newHashMap();

    private int code;

    /**
     * autocomplete插件的参数名为q
     */
    private String q;

    private String businessType;

    private String bizContent;

    private CustomerNameService customerNameService;

    @Override
    protected void jsonExecute() throws Exception {
    }

    public String fetchCustomerNameSuggestion() {
        if (StringUtils.isBlank(q)) {
            msg.put("suggestion", Collections.emptyList());
            code = SUCCESS_CODE;
            return SUCCESS;
        }
        if (StringUtils.isBlank(businessType)) {
            msg.put("suggestion", Collections.emptyList());
            code = SUCCESS_CODE;
            return SUCCESS;
        }
        int maxSize = Integer.parseInt(LionConfigUtils.getProperty("ba-finance-settle-web.suggestion.maxSize", "10"));

        List<CustomerNameSuggestionBean> suggestionBeanList = customerNameService.getCustomerNameSuggestion(q, maxSize, Integer.parseInt(businessType), getLoginId());
        if (CollectionUtils.isNotEmpty(suggestionBeanList)) {
            msg.put("suggestion", suggestionBeanList);
        }
        code = SUCCESS_CODE;
        return SUCCESS;
    }

    public String fetchCustomerInfo() {
        if (StringUtils.isBlank(bizContent)) {
            code = SUCCESS_CODE;
            return SUCCESS;
        }
        if (StringUtils.isBlank(businessType)) {
            code = SUCCESS_CODE;
            return SUCCESS;
        }

        CustomerInfoBean customerInfoBean = customerNameService.getCustomerInfo(Integer.parseInt(businessType), bizContent, getLoginId());
        if (customerInfoBean != null && customerInfoBean.getCustomerId() > 0) {
            msg.put("customerInfoBean", customerInfoBean);
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

    public void setQ(String q) {
        this.q = q;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public void setBizContent(String bizContent) {
        this.bizContent = bizContent;
    }

    public void setCustomerNameService(CustomerNameService customerNameService) {
        this.customerNameService = customerNameService;
    }
}
