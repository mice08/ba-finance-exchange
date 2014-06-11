package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;

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
}
