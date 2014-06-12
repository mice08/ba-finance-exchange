package com.dianping.ba.finance.exchange.api;


import com.dianping.ba.finance.exchange.api.beans.PayOrderSearchBean;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.core.type.PageModel;

import java.math.BigDecimal;

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

     /** 根据查询条件返回付款单列表
     *
     * @param payOrderSearchBean
     * @param page
     * @param pageSize
     * @return
     */
    PageModel paginatePayOrderList(PayOrderSearchBean payOrderSearchBean, int page, int pageSize);

	/**
	 * 根据查询条件返回付款单列表
	 *
	 * @param payOrderSearchBean
	 * @return
	 */
	List<PayOrderData> findPayOrderList(PayOrderSearchBean payOrderSearchBean);

    /**
     * 根据搜索条件计算付款单总金额
     *
     * @param payOrderSearchBean
     * @return
     */
    public BigDecimal findPayOrderTotalAmount(PayOrderSearchBean payOrderSearchBean);

}
