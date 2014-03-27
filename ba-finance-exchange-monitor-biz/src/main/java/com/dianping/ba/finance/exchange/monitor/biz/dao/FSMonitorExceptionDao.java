package com.dianping.ba.finance.exchange.monitor.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.monitor.api.datas.ExceptionData;

import java.util.List;


public interface FSMonitorExceptionDao extends GenericDao {

    @DAOAction(action= DAOActionType.INSERT)
    int insertMonitorException(@DAOParam("exceptionData") ExceptionData exceptionData);

    @DAOAction(action = DAOActionType.QUERY)
    List<ExceptionData> findExceptions(@DAOParam("status") int status);

    @DAOAction(action = DAOActionType.UPDATE)
    int updateStatus(@DAOParam("exceptionIdList") List<Integer> exceptionIdList,@DAOParam("setStatus") int setStatus,@DAOParam("preStatus") int preStatus);
}
