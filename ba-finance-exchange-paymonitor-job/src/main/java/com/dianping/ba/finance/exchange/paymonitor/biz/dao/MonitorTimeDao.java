package com.dianping.ba.finance.exchange.paymonitor.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;

import java.util.Date;

/**
 * Created by adam.huang on 2014/8/7.
 */
public interface MonitorTimeDao extends GenericDao {
    @DAOAction(action = DAOActionType.LOAD)
    public Date loadLastMonitorTime();

    @DAOAction(action = DAOActionType.INSERT)
    public int insertMonitorTime(@DAOParam("date") Date date);
}
