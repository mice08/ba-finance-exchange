package com.dianping.ba.finance.exchange.api;


import com.dianping.ba.finance.exchange.api.beans.ReceiveOrderSearchBean;
import com.dianping.ba.finance.exchange.api.beans.ReceiveOrderUpdateBean;
import com.dianping.ba.finance.exchange.api.datas.ReceiveCalResultData;
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;
import com.dianping.ba.finance.exchange.api.enums.ReceiveOrderStatus;
import com.dianping.core.type.PageModel;

import java.math.BigDecimal;
import java.util.List;

/**
 *  处理收款款的Service类
 */
public interface ReceiveOrderService {

    /**
     * 生成收款单
     * @param receiveOrderData
     */
    int createReceiveOrder(ReceiveOrderData receiveOrderData);


    /**
     * 查询收款单
     *
     * @param receiveOrderSearchBean
     * @return
     */
    PageModel paginateReceiveOrderList(ReceiveOrderSearchBean receiveOrderSearchBean,
                                       int page,
                                       int max);

    /**
     * 查询满足条件的收款总金额
     *
     * @param receiveOrderSearchBean
     * @return
     */
    BigDecimal loadReceiveOrderTotalAmountByCondition(ReceiveOrderSearchBean receiveOrderSearchBean);

	/**
	 * 根据TradeNo获取收款单
	 * @param tradeNo
	 * @return
	 */
	ReceiveOrderData loadReceiveOrderByTradeNo(String tradeNo);

	/**
	 * 直接根据roId作废收款单
	 * @param roId
	 * @param memo
	 * @return
	 */
	boolean dropReceiveOrder(int roId, String memo);

	/**
	 * 更新收款单的冲销Id
	 * @param originRoId
	 * @param reverseRoId
	 * @return
	 */
	boolean updateReverseRoId(int originRoId, int reverseRoId);

    /**
     * 更新确认收款单对象  customerId  shopId  status  receiveTime  receiveType  bizContent
     * @param receiveOrderUpdateBean
     * @return
     */
    int updateReceiveOrderConfirm(ReceiveOrderUpdateBean receiveOrderUpdateBean);

    /**
     * 根据主键获取实体
     * @param roId
     * @return
     */
    ReceiveOrderData loadReceiveOrderDataByRoId(int roId);

    /**
     * 获取所有未匹配、未确认的收款单
     * @param status
     * @return
     */
    List<ReceiveOrderData> findUnmatchAndUnconfirmedReceiveOrder(ReceiveOrderStatus status);


    /**
     * 关联收款单及收款通知
     * @param roId
     * @param rnId
     * @return
     */
    boolean confirmReceiveOrderAndReceiveNotify(int roId, int rnId, int loginId);

    /**
     * 关联收款单及收款通知
     * @param receiveOrderUpdateBean
     * @return
     */
    boolean manuallyUpdateReceiveOrder(ReceiveOrderUpdateBean receiveOrderUpdateBean);

    /**
     * 计算并获取收款凭证
     * @param receiveOrderSearchBean
     * @return
     */
    List<ReceiveCalResultData> findCalculatedReceiveResult(ReceiveOrderSearchBean receiveOrderSearchBean);

    /**
     * 根据ID作废收款单
     * @param roId
     * @return
     */
    boolean cancelReceiveOrder(int roId);

    /**
     * 自动获取未确认收单尝试确认
     * @param receiveOrderSearchBean
     * @return
     */
    boolean fireAutoConfirm(ReceiveOrderSearchBean receiveOrderSearchBean);

    /**
     * 按条件查询所有收款单
     * @param receiveOrderSearchBean
     * @return
     */
    List<ReceiveOrderData> findReceiverOrderList(ReceiveOrderSearchBean receiveOrderSearchBean);
}
