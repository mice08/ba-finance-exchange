package com.dianping.ba.finance.exchange.siteweb.services;

import com.dianping.ba.finance.exchange.siteweb.beans.GuaranteeInfoBean;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.aop.annotation.ReturnDefault;
import com.dianping.ts.treasure.pool.api.DeductionRuleService;
import com.dianping.ts.treasure.pool.api.dtos.guaranteeform.GuaranteeWholeInfoDTO;
import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 *
 */
public class TGGuaranteeService {

    private DeductionRuleService deductionRuleService;

    @Log(severity = 2, logBefore = true)
    @ReturnDefault
    public List<GuaranteeInfoBean> getGuaranteeByCustomer(int customerId) {
        List<GuaranteeWholeInfoDTO> guaranteeWholeInfoDTOList = deductionRuleService.getGuaranteeByCustomer(customerId);
        if (CollectionUtils.isEmpty(guaranteeWholeInfoDTOList)) {
            return Collections.emptyList();
        }

        List<GuaranteeInfoBean> guaranteeInfoBeanList = Lists.newLinkedList();
        for (GuaranteeWholeInfoDTO guaranteeWholeInfoDTO : guaranteeWholeInfoDTOList) {
            GuaranteeInfoBean guaranteeInfoBean = new GuaranteeInfoBean();
            guaranteeInfoBean.setAmount(guaranteeWholeInfoDTO.getAmount());
            guaranteeInfoBean.setCustomerId(guaranteeWholeInfoDTO.getCustomerId());
            guaranteeInfoBean.setGuaranteeBillId(guaranteeWholeInfoDTO.getGuaranteeBillId());
            guaranteeInfoBean.setGuaranteeId(guaranteeWholeInfoDTO.getGuaranteeId());
            guaranteeInfoBean.setLeftAmount(guaranteeWholeInfoDTO.getLeftAmount());
            guaranteeInfoBean.setReturnAmount(guaranteeWholeInfoDTO.getReturnAmount());
            guaranteeInfoBeanList.add(guaranteeInfoBean);
        }
        return guaranteeInfoBeanList;
    }

    public void setDeductionRuleService(DeductionRuleService deductionRuleService) {
        this.deductionRuleService = deductionRuleService;
    }
}
