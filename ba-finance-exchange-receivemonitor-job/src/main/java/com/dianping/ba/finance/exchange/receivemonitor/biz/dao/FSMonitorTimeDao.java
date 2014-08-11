package com.dianping.ba.finance.exchange.receivemonitor.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.receivemonitor.api.datas.MonitorTimeData;

public interface FSMonitorTimeDao extends GenericDao {

    @DAOAction(action = DAOActionType.LOAD)
	MonitorTimeData loadLastMonitorTime();

    @DAOAction(action = DAOActionType.INSERT)
    int insertMonitorTime(@DAOParam("monitorTimeData") MonitorTimeData monitorTimeData);
}
