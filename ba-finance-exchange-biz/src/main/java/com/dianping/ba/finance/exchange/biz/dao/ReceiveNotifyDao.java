package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData;

import java.util.List;

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

    @DAOAction(action = DAOActionType.UPDATE)
    int updateReceiveNotifyMatchId(@DAOParam("setStatus") int setStatus,
                                   @DAOParam("roId") int roId,
                                   @DAOParam("preStatus") int preStatus,
                                   @DAOParam("rnId") int rnId);


    @DAOAction(action = DAOActionType.QUERY)
    List<ReceiveNotifyData> getUnMatchedReceiveNotify(@DAOParam("status") int status);




}

