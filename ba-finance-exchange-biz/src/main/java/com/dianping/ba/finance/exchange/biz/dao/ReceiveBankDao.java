package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.ba.finance.exchange.api.datas.ReceiveBankData;

import java.util.List;

/**
 * 收款单Dao
 */
public interface ReceiveBankDao extends GenericDao {

    /**
     * 获取所有收款银行
     * @return
     */
    @DAOAction(action = DAOActionType.QUERY)
    List<ReceiveBankData> findAllReceiveBank();
}
