package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;

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
}
