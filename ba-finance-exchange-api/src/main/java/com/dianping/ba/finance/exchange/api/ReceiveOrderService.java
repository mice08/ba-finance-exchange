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


}
