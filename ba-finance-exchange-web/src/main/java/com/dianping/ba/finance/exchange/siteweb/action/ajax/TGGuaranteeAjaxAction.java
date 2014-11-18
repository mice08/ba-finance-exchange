package com.dianping.ba.finance.exchange.siteweb.action.ajax;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.siteweb.beans.GuaranteeInfoBean;
import com.dianping.ba.finance.exchange.siteweb.services.TGGuaranteeService;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class TGGuaranteeAjaxAction extends AjaxBaseAction {

    /**
     * 记录需要监控的业务日志
     */
    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.web.monitor.TGGuaranteeAjaxAction");

    private int customerId;

    private TGGuaranteeService tgGuaranteeService;

    @Override
    protected void jsonExecute() throws Exception {

    }

    /**
     * 获取客户名称输入提示
     * @return
     * @throws Exception
     */
	public String findGuaranteeInfoByCustomerId() throws Exception {
		try {
            if (customerId <= 0) {
                msg.put("guarantee", Collections.emptyList());
                code = SUCCESS_CODE;
                return SUCCESS;
            }
            List<GuaranteeInfoBean> guaranteeInfoBeanList = tgGuaranteeService.getGuaranteeByCustomer(customerId);

            msg.put("guarantee", buildGuaranteeInfo(guaranteeInfoBeanList));
            code = SUCCESS_CODE;
            return SUCCESS;
		} catch (Exception e) {
			MONITOR_LOGGER.error("severity=[1] TGGuaranteeAjaxAction.findGuaranteeInfoByCustomerId error!", e);
            code = ERROR_CODE;
			return SUCCESS;
		}
	}

    private Map<String, String> buildGuaranteeInfo(List<GuaranteeInfoBean> guaranteeInfoBeanList) {
        if (CollectionUtils.isEmpty(guaranteeInfoBeanList)) {
            return Collections.emptyMap();
        }
        Map<String, String> guaranteeInfoMap = Maps.newHashMap();
        for (GuaranteeInfoBean guaranteeInfoBean : guaranteeInfoBeanList) {
            guaranteeInfoMap.put(guaranteeInfoBean.getGuaranteeBillId(), String.format("%s(未归还金额:%s)", guaranteeInfoBean.getGuaranteeBillId(), guaranteeInfoBean.getLeftAmount()));
        }
        return guaranteeInfoMap;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public Map<String, Object> getMsg() {
        return msg;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setTgGuaranteeService(TGGuaranteeService tgGuaranteeService) {
        this.tgGuaranteeService = tgGuaranteeService;
    }
}
