package com.dianping.ba.finance.exchange.api;


import com.dianping.ba.finance.exchange.api.beans.ReceiveOrderSearchBean;
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;
import com.dianping.core.type.PageModel;

import java.math.BigDecimal;

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
}
