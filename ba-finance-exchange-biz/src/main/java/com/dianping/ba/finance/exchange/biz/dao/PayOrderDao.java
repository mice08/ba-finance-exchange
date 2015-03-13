package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.api.beans.POUpdateInfoBean;
import com.dianping.ba.finance.exchange.api.beans.PayOrderSearchBean;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.core.type.PageModel;

import java.math.BigDecimal;
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
     *
     * @param updateInfoBean
     * @return
     */
    @DAOAction(action = DAOActionType.UPDATE)
    int updatePayOrders(@DAOParam("updateInfoBean") POUpdateInfoBean updateInfoBean);

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

    /**
     * 根据查询条件查找付款单Id
     * @param payOrderSearchBean
     * @return
     */
    @DAOAction(action = DAOActionType.QUERY)
    List<Integer> findPayOrderIdList(@DAOParam("payOrderSearchBean") PayOrderSearchBean payOrderSearchBean);

    /**
     * 根据PaySequence查找付款单
     * @param paySequence
     * @return
     */
    @DAOAction(action = DAOActionType.LOAD)
    PayOrderData loadPayOrderByPaySequence(@DAOParam("paySequence") String paySequence);

    @DAOAction(action = DAOActionType.UPDATE)
    int updateCustomerId(@DAOParam("oldCustomerId") int oldCustomerId,
                         @DAOParam("newCustomerId") int newCustomerId);

    /**
     * 根据付款单号更新付款单状态
     * @param poId
     * @param preStatus
     * @param postStatus
     * @param memo
     * @return
     */
    @DAOAction(action = DAOActionType.UPDATE)
    int updatePayOrderStatus(@DAOParam("poId") int poId, @DAOParam("preStatus") int preStatus, @DAOParam("postStatus") int postStatus,@DAOParam("memo") String memo);

    /**
     * 根据付款单号查找付款单
     * @param poId
     * @return
     */
    @DAOAction(action = DAOActionType.LOAD)
    PayOrderData loadPayOrderByPayPOID(@DAOParam("poId") int poId);

    /**
     *
     * @param
     * @return
     */
    @DAOAction(action = DAOActionType.UPDATE)
    int updatePayOrderListStatus(@DAOParam("poIds") List<Integer> poIds, @DAOParam("preStatusList") List<Integer> preStatusList, @DAOParam("postStatus") int postStatus,@DAOParam("loginId") int loginId);

    /**
     * 根据状态查询PO
     *
     * @param status
     * @return
     */
    @DAOAction(action = DAOActionType.PAGE)
    PageModel paginatePayOrderListByStatus(@DAOParam("status") int status,
                                           @DAOParam("page") int page,
                                           @DAOParam("max") int max);

    @DAOAction(action = DAOActionType.UPDATE)
    int updatePayCode(@DAOParam("poId") int poId, @DAOParam("payCode") String payCode);

}
