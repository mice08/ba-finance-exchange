package com.dianping.ba.finance.exchange.siteweb.action.ajax;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.beans.CustNameSuggestionBean;
import com.dianping.ba.finance.exchange.siteweb.services.CustomerService;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class CustomerAjaxAction extends AjaxBaseAction {

    /**
     * 记录需要监控的业务日志
     */
    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.web.monitor.CustomerAjaxAction");

    private String customerName;

    private CustomerService customerService;
    @Override
    protected void jsonExecute() throws Exception {

    }

    /**
     * 获取客户名称输入提示
     * @return
     * @throws Exception
     */
	public String findCustomerNameSuggestion() throws Exception {
		try {
            if (StringUtils.isEmpty(customerName)) {
                msg.put("suggestion", Collections.emptyList());
                code = SUCCESS_CODE;
                return SUCCESS;
            }
            List<CustNameSuggestionBean> suggestionBeanList = customerService.getCustomerNameSuggestion(customerName);
            msg.put("suggestion", suggestionBeanList);
            code = SUCCESS_CODE;
            return SUCCESS;
		} catch (Exception e) {
			MONITOR_LOGGER.error("severity=[1] CustomerAjaxAction.findCustomerNameSuggestion error!", e);
            code = ERROR_CODE;
			return SUCCESS;
		}
	}

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }
}
