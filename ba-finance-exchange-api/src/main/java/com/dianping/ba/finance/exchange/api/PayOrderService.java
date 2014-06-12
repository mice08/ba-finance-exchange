package com.dianping.ba.finance.exchange.api;


import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.ba.finance.exchange.api.dtos.RefundDTO;
import com.dianping.ba.finance.exchange.api.dtos.RefundResultDTO;

import java.util.List;

/**
 *  处理付款单的Service类
 */
public interface PayOrderService {

    /**
     * 生成付款单
     * @param payOrderData
     */
    int createPayOrder(PayOrderData payOrderData);

    /**
     * 退票
     * @param refundDTOList
     * @param loginId
     * @return
     */
    RefundResultDTO refundPayOrder(List<RefundDTO> refundDTOList, int loginId);

}
