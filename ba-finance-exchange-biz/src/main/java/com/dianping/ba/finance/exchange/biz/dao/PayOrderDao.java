package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.api.beans.PayOrderSearchBean;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.core.type.PageModel;

import java.math.BigDecimal;

import java.util.Date;
import java.util.List;

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
     * 批量更新付款单toSetStatus
     * @param poIds   付款单Ids
     * @param whereStatus 前置条件
     * @param setStatus 更新状态
     * @param paidDate  付款日期  支付成功才有
     * @param loginId 操作人
     * @return
     */
    @DAOAction(action = DAOActionType.UPDATE)
    int updatePayOrders(@DAOParam("poIds")List<Integer> poIds,@DAOParam("whereStatus") int whereStatus,@DAOParam("setStatus") int setStatus,@DAOParam("paidDate") Date paidDate,@DAOParam("loginId") int loginId);

    /**
     * 根据主键列表查询PayOrder集合
     * @param poIds 主键
     * @return
     */
    @DAOAction(action = DAOActionType.QUERY)
    List<PayOrderData> findPayOrderListByPoIdList(@DAOParam("poIds") List<Integer> poIds);

     /** 根据查询条件查找付款单列表
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

	/**
	 * 根据查询条件查找付款单列表
	 * @param payOrderSearchBean
	 * @return
	 */
	@DAOAction(action = DAOActionType.QUERY)
	List<PayOrderData> findPayOrderList(@DAOParam("payOrderSearchBean") PayOrderSearchBean payOrderSearchBean);
}
