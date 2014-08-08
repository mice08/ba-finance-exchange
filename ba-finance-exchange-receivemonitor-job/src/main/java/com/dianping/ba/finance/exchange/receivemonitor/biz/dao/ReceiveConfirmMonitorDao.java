package com.dianping.ba.finance.exchange.receivemonitor.biz.dao;



import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.receivemonitor.api.datas.ReceiveConfirmMonitorData;
import com.dianping.ba.finance.exchange.receivemonitor.api.datas.ReceiveOrderMonitorData;

import java.util.Date;
import java.util.List;

/**
 * 收款确认的DAO层
 */
public interface ReceiveConfirmMonitorDao extends GenericDao {

    @DAOAction(action = DAOActionType.LOAD)
    public ReceiveConfirmMonitorData loadReceiveConfirmData(@DAOParam("rcId") int rcId);


}
