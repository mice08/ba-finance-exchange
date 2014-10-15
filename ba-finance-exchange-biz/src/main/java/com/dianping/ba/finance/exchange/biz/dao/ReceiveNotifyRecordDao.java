package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyRecordData;

/**
 * Created by Administrator on 2014/7/23.
 */
public interface ReceiveNotifyRecordDao extends GenericDao {

    /**
     * 添加收款通知记录
     * @param receiveNotifyRecordData
     * @return
     */
    @DAOAction(action = DAOActionType.INSERT)
    int insertReceiveNotifyRecord(@DAOParam("receiveNotifyRecordData")ReceiveNotifyRecordData receiveNotifyRecordData);

    @DAOAction(action = DAOActionType.UPDATE)
    int updateCustomerId(@DAOParam("oldCustomerId") int oldCustomerId,
                         @DAOParam("newCustomerId") int newCustomerId);
}
