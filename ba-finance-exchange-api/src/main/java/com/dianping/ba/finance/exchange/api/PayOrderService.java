package com.dianping.ba.finance.exchange.api;


import com.dianping.ba.finance.exchange.api.beans.PayOrderSearchBean;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.ba.finance.exchange.api.dtos.PayOrderBankInfoDTO;
import com.dianping.ba.finance.exchange.api.dtos.RefundDTO;
import com.dianping.ba.finance.exchange.api.dtos.RefundResultDTO;
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
     * 退票
     * @param refundDTOList
     * @param loginId
     * @return
     */
    RefundResultDTO refundPayOrder(List<RefundDTO> refundDTOList, int loginId);

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


    /**
     * 根据查询条件返回付款单Id
     *
     * @param payOrderSearchBean
     * @return
     */
    List<Integer> findPayOrderIdList(PayOrderSearchBean payOrderSearchBean);


    /**
     * 根据PaySequence获取付款单银行相关信息
     * @param paySequence
     * @return
     */
    PayOrderBankInfoDTO loadPayOrderByPaySequence(String paySequence);

    /**
     * 根据付款序列号暂停付款单
     * @param paySequence
     * @return
     */
    boolean suspendPayOrder(String paySequence);

    /**
     * 根据付款序列号恢复付款单
     * @param paySequence
     * @return
     */
    boolean resumePayOrder(String paySequence);

    /**
     * 根据付款序列号作废付款单
     * @param paySequence
     * @return
     */
    boolean dropPayOrder(String paySequence);

    /**
     * 修改customerId
     * @param oldCustomerId
     * @param newCustomerId
     */
    int changeCustomer(int oldCustomerId, int newCustomerId);

    /**
     * 根据PaySequence获取付款单
     * @param sequence
     * @return
     */
    PayOrderData loadPayOrderDataByPaySequence(String sequence);

    /**
     * 根据付款单号更新状态
     * @param poId
     * @param preStatus
     * @param postStatus
     * @return
     */
    int updatePayOrderStatus(int poId, int preStatus, int postStatus, String message);

    /**
     * 批量更新付款单状态
     * @param poIds
     * @param loginId
     * @return
     */
    int batchUpdatePayOrderStatus(List<Integer> poIds, int preStatus,int postStatus, int loginId);

    /**
     * 根据付款单号获取付款单信息
     * @param poIds
     * @return
     */
    List<PayOrderData> findPayOrderByIdList(List<Integer> poIds);

}
