package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;

/**
 * 收款单Dao
 */
public interface ReceiveOrderDao extends GenericDao {

    /**
     * 添加收款单
     * @param receiveOrderData
     * @return
     */
    @DAOAction(action = DAOActionType.INSERT)
    int insertReceiveOrderData(@DAOParam("receiveOrderData") ReceiveOrderData receiveOrderData);
}
