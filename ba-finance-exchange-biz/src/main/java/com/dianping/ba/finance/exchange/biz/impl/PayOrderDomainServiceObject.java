package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.PayOrderDomainService;
import com.dianping.ba.finance.exchange.api.PayOrderService;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.ba.finance.exchange.api.dtos.BankPayRequestDTO;
import com.dianping.ba.finance.exchange.api.enums.PayOrderStatus;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.swallow.producer.Producer;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by 遐 on 2015/3/9.
 */
public class PayOrderDomainServiceObject implements PayOrderDomainService {

    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.biz.monitor.PayOrderDomainServiceObject");

    private static final Set<Integer> ALLOWED_BANK_PAY_STATUS = Sets.newHashSet(PayOrderStatus.SUBMIT_FOR_PAY.value());

    @Autowired
    private PayOrderService payOrderService;
    @Autowired
    private Producer bankPayProducer;

    @Override
    @Log(logBefore = true, logAfter = true)
    public void pay(List<Integer> poIds, int loginId) {
        MONITOR_LOGGER.info(String.format("PayOrderDomainServiceObject.pay request size [%d]", poIds.size()));
        try {
            List<PayOrderData> payOrderDataList = payOrderService.findPayOrderByIdList(poIds);
            List<Integer> idList = buildPayOrderListForBankPay(payOrderDataList);
            payOrderService.batchUpdatePayOrderStatus(idList, PayOrderStatus.SUBMIT_FOR_PAY.value(), PayOrderStatus.BANK_PAYING.value(), loginId);
            for(PayOrderData data: payOrderDataList){
                if(idList.contains(data.getPoId())){

                }
            }
        } catch (Exception e) {
            MONITOR_LOGGER.error(String.format("severity=[1],PayOrderDomainServiceObject.pay fail!, poIds=[%s]&loginId=[%d]", poIds, loginId), e);
        }
    }

    private List<Integer> buildPayOrderListForBankPay(List<PayOrderData> dataList) {
        if (CollectionUtils.isEmpty(dataList)) {
            return new ArrayList<Integer>();
        }
        List<Integer> idList = new ArrayList<Integer>();
        for (PayOrderData data : dataList) {
            if (orderCanBankPay(data)) {
                idList.add(data.getPoId());
            }
        }
        return idList;
    }

    private boolean orderCanBankPay(PayOrderData order) {
        //todo check business type
        return order.getPayAmount().compareTo(BigDecimal.ZERO) > 0 && ALLOWED_BANK_PAY_STATUS.contains(order.getStatus());
    }

    private BankPayRequestDTO buildBankPayRequest(PayOrderData payOrderData){
        BankPayRequestDTO requestDTO = new BankPayRequestDTO();
        requestDTO.setInsId(String.valueOf(payOrderData.getPoId()));
        //todo call service to get account no and name
        requestDTO.setAccountNo("");
        requestDTO.setAccountName("");
        requestDTO.setAccountToNo(payOrderData.getBankAccountNo());
        requestDTO.setAccountToName(payOrderData.getBankAccountName());
        requestDTO.setAccountType(1);
        requestDTO.setAccountToType(payOrderData.getBankAccountType());
        requestDTO.setBankBranchCode(payOrderData.getBankCode());
        requestDTO.setBankCode(payOrderData.getMasterBankCode());
        requestDTO.setBankName(payOrderData.getBankName());
        requestDTO.setAmount(payOrderData.getPayAmount());
        requestDTO.setDescription(payOrderData.getMemo());
        return requestDTO;
    }
}
