package com.dianping.ba.finance.exchange.paymonitor.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.paymonitor.api.datas.MonitorExceptionData;

import java.util.List;

/**
 * Created by adam.huang on 2014/8/7.
 */
public interface MonitorExceptionDao extends GenericDao {
    @DAOAction(action = DAOActionType.INSERT)
    public int insertMonitorException(@DAOParam("monitorExceptionData") MonitorExceptionData monitorExceptionData);

    @DAOAction(action = DAOActionType.QUERY)
    public List<MonitorExceptionData> findMonitorExceptionDatas(@DAOParam("status") int status);

    @DAOAction(action = DAOActionType.UPDATE)
    int updateMonitorExceptionStatus(@DAOParam("monitorExceptionIdList") List<Integer> monitorExceptionIdList,
                                @DAOParam("status") int status);
}
