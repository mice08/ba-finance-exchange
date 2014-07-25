package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData;

/**
 * Created by Administrator on 2014/7/24.
 */
public interface ReceiveNotifyDao extends GenericDao {
    /**
     * 添加收款通知记录
     * @param receiveNotifyData
     * @return
     */
    @DAOAction(action = DAOActionType.INSERT)
    int insertReceiveNotify(@DAOParam("receiveNotifyData")ReceiveNotifyData receiveNotifyData);
}

