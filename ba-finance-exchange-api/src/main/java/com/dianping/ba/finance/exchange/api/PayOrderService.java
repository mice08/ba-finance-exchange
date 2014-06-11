package com.dianping.ba.finance.exchange.api;


import com.dianping.ba.finance.exchange.api.datas.PayOrderData;

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
     * 导出支付更新状态
     * @param poIds   付款单Ids
     * @param loginId   操作人
     * @return
     */
    int updatePayOrderToPaying(List<Integer> poIds, int loginId);

    /**
     * 确认支付成功更新状态
     * @param poIds  付款单Ids
     * @param loginId  操作人
     * @return
     */
    int updatePayOrderToPaySuccess(List<Integer> poIds, int loginId);
}
