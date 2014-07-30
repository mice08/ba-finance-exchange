package com.dianping.ba.finance.exchange.biz.service;

import com.dianping.ba.finance.exchange.api.beans.BizInfoBean;
import com.dianping.ba.finance.exchange.api.dtos.PayCentreReceiveRequestDTO;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.aop.annotation.ReturnDefault;
import com.dianping.midas.finance.api.dto.CorporationDTO;
import com.dianping.midas.finance.api.service.CorporationService;

/**
 * Created by IntelliJ IDEA.
 * User: yaxiong.cheng
 * Date: 2014/7/24
 * Time: 14:56
 * To change this template use File | Settings | File Templates.
 */
public class BizInfoService {
	private CorporationService corporationService;
	/**
	 * 根据bizContent去调推广（广告）接口查询客户ID
	 *
	 * @param requestDTO
	 * @return
	 */
	@Log(logBefore = true, logAfter = true, severity = 1)
	@ReturnDefault
    public BizInfoBean getBizInfo(PayCentreReceiveRequestDTO requestDTO) {
        //判断businessType是不是广告
        if (requestDTO.getBusinessType() == BusinessType.ADVERTISEMENT.value()) {
            //RPC调用
            CorporationDTO corporationDTO = corporationService.queryCorporationByBizContent(requestDTO.getBizContent());
            if (corporationDTO != null) {
                BizInfoBean bean = new BizInfoBean();
                bean.setCustomerId(corporationDTO.getId());
                bean.setCustomerName(corporationDTO.getName());
                return bean;
            }
        }
        return null;
    }

	public void setCorporationService(CorporationService corporationService) {
		this.corporationService = corporationService;
	}
}
