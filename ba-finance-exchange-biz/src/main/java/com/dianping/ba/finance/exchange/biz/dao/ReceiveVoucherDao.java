package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.api.datas.ReceiveVoucherData;

/**
 * 收款单Dao
 */
public interface ReceiveVoucherDao extends GenericDao {

    /**
     * 添加收款收款单
     * @param receiveVoucherData
     * @return
     */
    @DAOAction(action = DAOActionType.INSERT)
    int insertReceiveVoucherData(@DAOParam("receiveVoucherData") ReceiveVoucherData receiveVoucherData);

    @DAOAction(action = DAOActionType.UPDATE)
    int updateCustomerId(@DAOParam("oldCustomerId") int oldCustomerId,
                         @DAOParam("newCustomerId") int newCustomerId);
}
