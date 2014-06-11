package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.api.beans.PayOrderSearchBean;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.core.type.PageModel;

import java.math.BigDecimal;

/**
 * 付款单Dao
 */
public interface PayOrderDao extends GenericDao {

    /**
     * 插入付款单
     * @param payOrderData
     * @return
     */
    @DAOAction(action = DAOActionType.INSERT)
    int insertPayOrder(@DAOParam("payOrderData") PayOrderData payOrderData);

    /**
     * 根据查询条件查找应付列表
     * @param payOrderSearchBean
     * @param page
     * @param max
     * @return
     */
    @DAOAction(action = DAOActionType.PAGE)
    PageModel paginatePayOrderList(@DAOParam("payOrderSearchBean") PayOrderSearchBean payOrderSearchBean,
                                  @DAOParam("page") int page,
                                  @DAOParam("max") int max);

    /**
     * 根绝查询条件计算付款单总金额
     * @param payOrderSearchBean
     * @return
     */
    @DAOAction(action = DAOActionType.LOAD)
    BigDecimal findPayOrderTotalAmountByCondition(@DAOParam("payOrderSearchBean") PayOrderSearchBean payOrderSearchBean);
}
