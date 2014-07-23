package com.dianping.ba.finance.exchange.siteweb.services;

import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.ba.finance.exchange.siteweb.beans.CustomerInfoBean;
import com.dianping.ba.finance.exchange.siteweb.beans.CustomerNameSuggestionBean;
import com.dianping.customerinfo.api.CustomerInfoService;
import com.dianping.customerinfo.dto.Customer;
import com.dianping.customerinfo.dto.CustomerLite;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.aop.annotation.ReturnDefault;
import com.dianping.midas.finance.api.dto.CorporationDTO;
import com.dianping.midas.finance.api.service.CorporationService;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import org.apache.commons.collections.CollectionUtils;

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

    @Log(severity = 2, logBefore = true, logAfter = true)
    @ReturnDefault
    public Map<Integer, String> getCustomerName(List<PayOrderData> payOrderDataList, int loginId) {
        Multimap<Integer, Integer> businessTypeCustomerIdMMap = groupByBusinessType(payOrderDataList);
        Map<Integer, String> customerIdNameMap = Maps.newHashMap();
        // 获取团购的客户名称
        fetchTGCustomerName(businessTypeCustomerIdMMap, customerIdNameMap, loginId);
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
            CorporationDTO corporationDTO = corporationService.queryCorporationById(customerId);
            customerIdNameMap.put(corporationDTO.getId(), corporationDTO.getName());
        }

    }


    private void fetchTGCustomerName(Multimap<Integer, Integer> businessTypeCustomerIdMMap, Map<Integer, String> customerIdNameMap, int loginId) {
        List<Integer> tgCustomerIdList = Lists.newLinkedList(businessTypeCustomerIdMMap.get(BusinessType.GROUP_PURCHASE.value()));
        if (CollectionUtils.isEmpty(tgCustomerIdList)) {
            return;
        }
        List<CustomerLite> customerLiteList = customerInfoService.getCustomerLitesFuture(tgCustomerIdList, loginId);
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
        if (businessType == BusinessType.ADVERTISEMENT.value()) {
            return fetchADCustomerSuggestion(customerName, maxSize, loginId);
        }
        return Collections.emptyList();
    }

    private List<CustomerNameSuggestionBean> fetchADCustomerSuggestion(String customerName, int maxSize, int loginId) {
        List<CorporationDTO> corporationDTOList = corporationService.queryCorporationByName(customerName, maxSize);
        if (CollectionUtils.isEmpty(corporationDTOList)) {
            return Collections.emptyList();
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
        List<Customer> customerList = customerInfoService.searchByCustomerName(customerName, 0, maxSize, loginId);
        if (CollectionUtils.isEmpty(customerList)) {
            return Collections.emptyList();
        }
        List<CustomerNameSuggestionBean> suggestionBeanList = Lists.newLinkedList();
        for (Customer customer : customerList) {
            CustomerNameSuggestionBean suggestionBean = new CustomerNameSuggestionBean();
            suggestionBean.setCustomerId(customer.getCustomerID());
            suggestionBean.setCustomerName(customer.getCustomerName());
            suggestionBeanList.add(suggestionBean);
        }
        return suggestionBeanList;
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
        CorporationDTO corporationDTO = corporationService.queryCorporationByBizContent(bizContent);
        if (corporationDTO != null) {
            CustomerInfoBean customerInfoBean = new CustomerInfoBean();
            customerInfoBean.setCustomerId(corporationDTO.getId());
            customerInfoBean.setCustomerName(corporationDTO.getName());
            return customerInfoBean;
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
}
