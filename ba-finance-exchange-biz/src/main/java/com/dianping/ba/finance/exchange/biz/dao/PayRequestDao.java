package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.api.datas.ExpensePayRequestData;
import com.dianping.ba.finance.exchange.api.datas.PayRequestData;

import java.util.Date;
import java.util.List;

/**
 * 付款单Dao
 */
public interface PayRequestDao extends GenericDao {

    /**
     * 插入付款请求
     * @param payRequestData
     * @return
     */
    @DAOAction(action = DAOActionType.INSERT)
    int insertPayRequest(@DAOParam("payRequestData") PayRequestData payRequestData);

    /**
     * 更新付款请求
     * @param prId
     * @param status
     * @param memo
     * @return
     */
    @DAOAction(action = DAOActionType.UPDATE)
    int updatePayRequest(@DAOParam("prId") int prId,
                        @DAOParam("status") int status,
                        @DAOParam("memo") String memo);

    /**
     *
     * @param businessType
     * @param startTime
     * @param endTime
     * @return
     */
    @DAOAction(action = DAOActionType.QUERY)
    List<ExpensePayRequestData> findExpensePayDataByDate(@DAOParam("businessType") int businessType,
                                                         @DAOParam("startTime")Date startTime,
                                                         @DAOParam("endTime")Date endTime);

}
