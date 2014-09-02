package com.dianping.ba.finance.exchange.biz.service;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.beans.BizInfoBean;
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.aop.annotation.ReturnDefault;
import com.dianping.midas.finance.api.dto.CorporationDTO;
import com.dianping.midas.finance.api.service.CorporationService;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by IntelliJ IDEA.
 * User: yaxiong.cheng
 * Date: 2014/7/24
 * Time: 14:56
 * To change this template use File | Settings | File Templates.
 */
public class BizInfoService {
	private CorporationService corporationService;

	private MonitorMailService monitorMailService;
	private MonitorSmsService monitorSmsService;

	/**
	 * 记录需要监控的业务日志
	 */
	private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.biz.service.BizInfoService");

	/**
	 * 根据bizContent去调推广（广告）接口查询客户ID
	 *
	 * @param roData
	 * @return
	 */
	@Log(logBefore = true, logAfter = true, severity = 1)
	@ReturnDefault
    public BizInfoBean getBizInfo(ReceiveOrderData roData) {
        //判断businessType是不是广告
        if (roData.getBusinessType() == BusinessType.ADVERTISEMENT.value()) {
            //RPC调用
			//这是一个外部接口，调用前后记录日志，发生异常记录日志还需要告警
			MONITOR_LOGGER.info("CorporationService.queryCorporationByBizContent" +
			                    " bizContent:" + roData.getBizContent());
			try {
				CorporationDTO corporationDTO = corporationService.queryCorporationByBizContent(roData.getBizContent());
				if (corporationDTO != null) {
					BizInfoBean bean = new BizInfoBean();
					bean.setCustomerId(corporationDTO.getId());
					bean.setCustomerName(corporationDTO.getName());
					//记录返回日志
					MONITOR_LOGGER.info("CorporationService.queryCorporationByBizContent" +
							"return id:" + corporationDTO.getId() + " name:"+corporationDTO.getName());
					return bean;
				}
			} catch (Exception e) {
				//异常日志和告警
				String exStr = convertExToStr(e);
				MONITOR_LOGGER.error("severity=[1] CorporationService.queryCorporationByBizContent error!", e);
				MONITOR_LOGGER.error(exStr);
				notifyException("queryCorporationByBizContent", roData.getBizContent(), exStr);
			}
		}
        return null;
    }

	public void setCorporationService(CorporationService corporationService) {
		this.corporationService = corporationService;
	}


	/**
	 * 告警
	 */
	private void notifyException(String callMethod,String param,String e) {
		String mailInfo = "CorporationService接口调用错误详情：\n";
		mailInfo += String.format("调用接口为%s,参数为%s\n",callMethod,param);
		String smsInfo = "CorporationService接口调用异常，详情请见邮件！";
		mailInfo += String.format("异常信息为%s\n",e);
		monitorSmsService.sendSms(smsInfo);
		monitorMailService.sendMail(mailInfo);
	}

	/**
	 * 将Exception的printStackTrace信息存入字符串
	 * @param e
	 * @return
	 */
	private String convertExToStr(Exception e) {
		//将Exception的printStackTrace写入String
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		return errors.toString();
	}

	public void setMonitorMailService(MonitorMailService monitorMailService) {
		this.monitorMailService = monitorMailService;
	}

	public void setMonitorSmsService(MonitorSmsService monitorSmsService) {
		this.monitorSmsService = monitorSmsService;
	}
}
