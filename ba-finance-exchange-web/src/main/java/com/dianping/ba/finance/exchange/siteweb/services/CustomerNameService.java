package com.dianping.ba.finance.exchange.siteweb.services;

import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.customerinfo.api.CustomerInfoService;
import com.dianping.customerinfo.dto.CustomerLite;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.aop.annotation.ReturnDefault;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by noahshen on 14-7-7.
 */
public class CustomerNameService {

    private CustomerInfoService customerInfoService;

    @Log(severity = 2, logBefore = true, logAfter = true)
    @ReturnDefault
    public Map<Integer, String> getCustomerName(List<PayOrderData> payOrderDataList, int loginId) {
        Multimap<Integer, Integer> businessTypeCustomerIdMMap = groupByBusinessType(payOrderDataList);
        Map<Integer, String> customerIdNameMap = Maps.newHashMap();
        // 获取团购的客户名称
        fetchTGCustomerName(businessTypeCustomerIdMMap, customerIdNameMap, loginId);

        return customerIdNameMap;
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

    public void setCustomerInfoService(CustomerInfoService customerInfoService) {
        this.customerInfoService = customerInfoService;
    }
}
