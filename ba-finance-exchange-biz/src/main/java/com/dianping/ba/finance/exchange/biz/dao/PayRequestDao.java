package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.api.datas.PayRequestData;

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

}
