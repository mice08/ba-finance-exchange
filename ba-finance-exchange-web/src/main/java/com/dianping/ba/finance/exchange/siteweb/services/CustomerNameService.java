package com.dianping.ba.finance.exchange.siteweb.services;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData;
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.ba.finance.exchange.siteweb.beans.CustomerInfoBean;
import com.dianping.ba.finance.exchange.siteweb.beans.CustomerNameSuggestionBean;
import com.dianping.customerinfo.api.CustomerInfoService;
import com.dianping.customerinfo.dto.CustomerLite;
import com.dianping.customerinfo.dto.CustomerShopLite;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.aop.annotation.ReturnDefault;
import com.dianping.midas.finance.api.dto.CorporationDTO;
import com.dianping.midas.finance.api.service.CorporationService;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import org.apache.commons.collections.CollectionUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by noahshen on 14-7-7.
 */
public class CustomerNameService {

    /**
     * 阿波罗团购的客户信息Service
     */
    private CustomerInfoService customerInfoService;

    /**
     * 广告的客户信息Service
     */
    private CorporationService corporationService;

	/**
	 * 记录需要监控的业务日志
	 */
	private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.siteweb.services.CustomerNameService");

	private MonitorMailService monitorMailService;
	private MonitorSmsService monitorSmsService;

	@Log(severity = 2, logBefore = true, logAfter = true)
    @ReturnDefault
    public Map<Integer, String> getCustomerName(List<PayOrderData> payOrderDataList, int loginId) {
        Multimap<Integer, Integer> businessTypeCustomerIdMMap = groupByBusinessType(payOrderDataList);
        Map<Integer, String> customerIdNameMap = Maps.newHashMap();
        // 获取团购的客户名称
        fetchTGCustomerName(businessTypeCustomerIdMMap, customerIdNameMap, loginId);
        // 获取闪惠的客户名称
        fetchSHCustomerName(businessTypeCustomerIdMMap, customerIdNameMap, loginId);
        // 获取广告的客户名称
        fetchADCustomerName(businessTypeCustomerIdMMap, customerIdNameMap, loginId);

        return customerIdNameMap;
    }

    private void fetchADCustomerName(Multimap<Integer, Integer> businessTypeCustomerIdMMap, Map<Integer, String> customerIdNameMap, int loginId) {
        List<Integer> adCustomerIdList = Lists.newLinkedList(businessTypeCustomerIdMMap.get(BusinessType.ADVERTISEMENT.value()));
        if (CollectionUtils.isEmpty(adCustomerIdList)) {
            return;
        }
        for (int customerId : adCustomerIdList) {
			try {
				//外部接口调用，添加日志和告警
				MONITOR_LOGGER.info("CorporationService.queryCorporationById" +
						" customerId:" + customerId);
				CorporationDTO corporationDTO = corporationService.queryCorporationById(customerId);
				if (corporationDTO != null) {
					customerIdNameMap.put(corporationDTO.getId(), corporationDTO.getName());
                    //记录返回日志
					MONITOR_LOGGER.info("CorporationService.queryCorporationByBizContent" +
							"return id:" + corporationDTO.getId() + " name:"+corporationDTO.getName());
				}
			} catch (Exception e) {
				//异常日志和告警
				String exStr = convertExToStr(e);
				MONITOR_LOGGER.error("severity=[1] CorporationService.queryCorporationByBizContent error!", e);
				MONITOR_LOGGER.error("异常堆栈信息:" + exStr);
				notifyException("queryCorporationByBizContent", "roData.getBizContent()",exStr);
			}
		}

    }

    private void fetchTGCustomerName(Multimap<Integer, Integer> businessTypeCustomerIdMMap, Map<Integer, String> customerIdNameMap, int loginId) {
        List<Integer> tgCustomerIdList = Lists.newLinkedList(businessTypeCustomerIdMMap.get(BusinessType.GROUP_PURCHASE.value()));
        if (CollectionUtils.isEmpty(tgCustomerIdList)) {
            return;
        }
        List<CustomerLite> customerLiteList = customerInfoService.getCustomerLites(tgCustomerIdList, loginId);
        if (CollectionUtils.isEmpty(customerLiteList)) {
            return;
        }
        for (CustomerLite customerLite : customerLiteList) {
            customerIdNameMap.put(customerLite.getCustomerID(), customerLite.getCustomerName());
        }
    }


    /**
     * 闪惠的客户名
     *
     * @param businessTypeCustomerIdMMap
     * @param customerIdNameMap
     * @param loginId
     */
    private void fetchSHCustomerName(Multimap<Integer, Integer> businessTypeCustomerIdMMap, Map<Integer, String> customerIdNameMap, int loginId) {
        List<Integer> shCustomerIdList = Lists.newLinkedList(businessTypeCustomerIdMMap.get(BusinessType.SHAN_HUI.value()));
        if (CollectionUtils.isEmpty(shCustomerIdList)) {
            return;
        }
        List<CustomerLite> customerLiteList = customerInfoService.getCustomerLites(shCustomerIdList, loginId);
        if (CollectionUtils.isEmpty(customerLiteList)) {
            return;
        }
        for (CustomerLite customerLite : customerLiteList) {
            customerIdNameMap.put(customerLite.getCustomerID(), customerLite.getCustomerName());
        }
    }

    private Multimap<Integer, Integer> groupByBusinessType(List<PayOrderData> payOrderDataList) {
        Multimap<Integer, Integer> businessTypeCustomerIdMMap = LinkedListMultimap.create(payOrderDataList.size());
        for (PayOrderData poDate : payOrderDataList) {
            businessTypeCustomerIdMMap.put(poDate.getBusinessType(), poDate.getCustomerId());
        }
        return businessTypeCustomerIdMMap;
    }


    @Log(severity = 2, logBefore = true, logAfter = true)
    @ReturnDefault
    public Map<Integer, String> getROCustomerName(List<ReceiveOrderData> receiveOrderDataList, int loginId) {
        Multimap<Integer, Integer> businessTypeCustomerIdMMap = roGroupByBusinessType(receiveOrderDataList);
        Map<Integer, String> customerIdNameMap = Maps.newHashMap();
        // 获取团购的客户名称
        fetchTGCustomerName(businessTypeCustomerIdMMap, customerIdNameMap, loginId);
        // 获取广告的客户名称
        fetchADCustomerName(businessTypeCustomerIdMMap, customerIdNameMap, loginId);
        return customerIdNameMap;
    }

    @Log(severity = 2, logBefore = true, logAfter = true)
    @ReturnDefault
    public Map<Integer, String> getRORNCustomerName(List<ReceiveNotifyData> receiveNotifyDataList, int loginId) {
        Multimap<Integer, Integer> businessTypeCustomerIdMMap = rornGroupByBusinessType(receiveNotifyDataList);
        Map<Integer, String> customerIdNameMap = Maps.newHashMap();
        // 获取团购的客户名称
        fetchTGCustomerName(businessTypeCustomerIdMMap, customerIdNameMap, loginId);
        // 获取广告的客户名称
        fetchADCustomerName(businessTypeCustomerIdMMap, customerIdNameMap, loginId);
        return customerIdNameMap;
    }

    private Multimap<Integer, Integer> rornGroupByBusinessType(List<ReceiveNotifyData> receiveNotifyDataList) {
        Multimap<Integer, Integer> businessTypeCustomerIdMMap = LinkedListMultimap.create(receiveNotifyDataList.size());
        for (ReceiveNotifyData rnDate : receiveNotifyDataList) {
            businessTypeCustomerIdMMap.put(rnDate.getBusinessType(), rnDate.getCustomerId());
        }
        return businessTypeCustomerIdMMap;
    }

    private Multimap<Integer, Integer> roGroupByBusinessType(List<ReceiveOrderData> receiveOrderDataList) {
        Multimap<Integer, Integer> businessTypeCustomerIdMMap = LinkedListMultimap.create(receiveOrderDataList.size());
        for (ReceiveOrderData roDate : receiveOrderDataList) {
            businessTypeCustomerIdMMap.put(roDate.getBusinessType(), roDate.getCustomerId());
        }
        return businessTypeCustomerIdMMap;
    }

    /**
     * 获取客户名称的输入提示
     * @param customerName
     * @param maxSize
     * @param loginId
     * @return
     */
    @Log(severity = 2, logBefore = true, logAfter = true)
    @ReturnDefault
    public List<CustomerNameSuggestionBean> getCustomerNameSuggestion(String customerName, int maxSize, int businessType, int loginId) {
        if (businessType == BusinessType.GROUP_PURCHASE.value()) {
            return fetchTGCustomerSuggestion(customerName, maxSize, loginId);
        }
        if (businessType == BusinessType.SHAN_HUI.value()) {
            return fetchSHCustomerSuggestion(customerName, maxSize, loginId);
        }
        if (businessType == BusinessType.ADVERTISEMENT.value()) {
            return fetchADCustomerSuggestion(customerName, maxSize, loginId);
        }
        return Collections.emptyList();
    }

    private List<CustomerNameSuggestionBean> fetchADCustomerSuggestion(String customerName, int maxSize, int loginId) {
		List<CorporationDTO> corporationDTOList = null;
		try {
			//外部接口调用，添加日志和告警
			MONITOR_LOGGER.info("CorporationService.queryCorporationByName" +
					" customerName:" + customerName + " maxSize:" + maxSize);
			corporationDTOList = corporationService.queryCorporationByName(customerName, maxSize);
			//记录返回日志
			MONITOR_LOGGER.info("CorporationService.queryCorporationByName" +
					"return ListSize:" + corporationDTOList.size());
			if (CollectionUtils.isEmpty(corporationDTOList)) {
				return Collections.emptyList();
			}
		} catch (Exception e) {
			//异常日志和告警
			String exStr = convertExToStr(e);
			MONITOR_LOGGER.error("severity=[1] CorporationService.queryCorporationByName error!", e);
			MONITOR_LOGGER.error("异常堆栈信息:" + exStr);
			notifyException("queryCorporationByName",
					"customerName:" + customerName + " maxSize:" + String.valueOf(maxSize),exStr);
		}
		List<CustomerNameSuggestionBean> suggestionBeanList = Lists.newLinkedList();
        for (CorporationDTO corporationDTO : corporationDTOList) {
            CustomerNameSuggestionBean suggestionBean = new CustomerNameSuggestionBean();
            suggestionBean.setCustomerId(corporationDTO.getId());
            suggestionBean.setCustomerName(corporationDTO.getName());
            suggestionBeanList.add(suggestionBean);
        }
        return suggestionBeanList;
    }

    private List<CustomerNameSuggestionBean> fetchTGCustomerSuggestion(String customerName, int maxSize, int loginId) {
        List<CustomerShopLite> customerList = customerInfoService.searchByCustomerAndShopInfo(customerName, null, true, 0, maxSize).getY();
        if (CollectionUtils.isEmpty(customerList)) {
            return Collections.emptyList();
        }
        List<CustomerNameSuggestionBean> suggestionBeanList = Lists.newLinkedList();
        for (CustomerShopLite customer : customerList) {
            CustomerNameSuggestionBean suggestionBean = new CustomerNameSuggestionBean();
            suggestionBean.setCustomerId(customer.getCustomerID());
            suggestionBean.setCustomerName(customer.getCustomreName());
            suggestionBeanList.add(suggestionBean);
        }
        return suggestionBeanList;
    }

    private List<CustomerNameSuggestionBean> fetchSHCustomerSuggestion(String customerName, int maxSize, int loginId) {
        List<CustomerShopLite> customerList = customerInfoService.searchByCustomerAndShopInfo(customerName, null, true, 0, maxSize).getY();
        if (CollectionUtils.isEmpty(customerList)) {
            return Collections.emptyList();
        }
        List<CustomerNameSuggestionBean> suggestionBeanList = Lists.newLinkedList();
        for (CustomerShopLite customer : customerList) {
            CustomerNameSuggestionBean suggestionBean = new CustomerNameSuggestionBean();
            suggestionBean.setCustomerId(customer.getCustomerID());
            suggestionBean.setCustomerName(customer.getCustomreName());
            suggestionBeanList.add(suggestionBean);
        }
        return suggestionBeanList;
    }

    @Log(severity = 2, logBefore = true, logAfter = true)
    @ReturnDefault
    public CustomerInfoBean getCustomerInfoById(int businessType, int customerId, int loginId) {
        if (businessType == BusinessType.GROUP_PURCHASE.value()) {
            return fetchTGCustomerInfoById(customerId, loginId);
        }
        if (businessType == BusinessType.ADVERTISEMENT.value()) {
            return fetchADCustomerInfoById(customerId, loginId);
        }
        return null;
    }

    private CustomerInfoBean fetchADCustomerInfoById(int customerId, int loginId) {
		try {
			//这是一个外部接口，调用前后记录日志，发生异常记录日志还需要告警
			MONITOR_LOGGER.info("CorporationService.queryCorporationById" +
					" customerId:" + customerId);
			CorporationDTO corporationDTO = corporationService.queryCorporationById(customerId);
			if (corporationDTO != null) {
				CustomerInfoBean customerInfoBean = new CustomerInfoBean();
				customerInfoBean.setCustomerId(corporationDTO.getId());
				customerInfoBean.setCustomerName(corporationDTO.getName());
                //记录返回日志
				MONITOR_LOGGER.info("CorporationService.queryCorporationById" +
						"return id:" + corporationDTO.getId() + " name:"+corporationDTO.getName());
				return customerInfoBean;
			}
		} catch (Exception e) {
			//异常日志和告警
			String exStr = convertExToStr(e);
			MONITOR_LOGGER.error("severity=[1] CorporationService.queryCorporationById error!", e);
			MONITOR_LOGGER.error("异常堆栈信息:" + exStr);
			notifyException("queryCorporationById", String.valueOf(customerId),exStr);
		}
		return null;
    }

    private CustomerInfoBean fetchTGCustomerInfoById(int customerId, int loginId) {
        // TODO 待阿波罗团购提供接口
        return null;
    }

    @Log(severity = 2, logBefore = true, logAfter = true)
    @ReturnDefault
    public CustomerInfoBean getCustomerInfo(int businessType, String bizContent, int loginId) {
        if (businessType == BusinessType.GROUP_PURCHASE.value()) {
            return fetchTGCustomerInfo(bizContent, loginId);
        }
        if (businessType == BusinessType.ADVERTISEMENT.value()) {
            return fetchADCustomerInfo(bizContent, loginId);
        }
        return null;
    }

    private CustomerInfoBean fetchADCustomerInfo(String bizContent, int loginId) {
		try {
			//这是一个外部接口，调用前后记录日志，发生异常记录日志还需要告警
			MONITOR_LOGGER.info("CorporationService.queryCorporationByBizContent" +
					" bizContent:" + bizContent);
			CorporationDTO corporationDTO = corporationService.queryCorporationByBizContent(bizContent);
			if (corporationDTO != null) {
				CustomerInfoBean customerInfoBean = new CustomerInfoBean();
				customerInfoBean.setCustomerId(corporationDTO.getId());
				customerInfoBean.setCustomerName(corporationDTO.getName());
				//记录返回日志
				MONITOR_LOGGER.info("CorporationService.queryCorporationByBizContent" +
						"return id:" + corporationDTO.getId() + " name:"+corporationDTO.getName());
				return customerInfoBean;
			}
		} catch (Exception e) {
			//异常日志和告警
			String exStr = convertExToStr(e);
			MONITOR_LOGGER.error("severity=[1] CorporationService.queryCorporationByBizContent error!", e);
			MONITOR_LOGGER.error("异常堆栈信息:" + exStr);
			notifyException("queryCorporationByBizContent", bizContent, exStr);
		}
		return null;
    }

    private CustomerInfoBean fetchTGCustomerInfo(String bizContent, int loginId) {
        // TODO 待阿波罗团购提供接口
        return null;
    }


    public void setCustomerInfoService(CustomerInfoService customerInfoService) {
        this.customerInfoService = customerInfoService;
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
