package com.dianping.ba.finance.exchange.paymonitor.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.paymonitor.api.datas.PayPlanMonitorData;

import java.util.Date;
import java.util.List;

/**
 * Created by adam.huang on 2014/8/7.
 */
public interface PayPlanDao extends GenericDao {

    @DAOAction(action = DAOActionType.QUERY)
    public List<PayPlanMonitorData> findPayPlansByDate(@DAOParam("startDate") Date startDate,
                                                       @DAOParam("endDate") Date endDate);

    @DAOAction(action = DAOActionType.LOAD)
    public PayPlanMonitorData getPayPlanById(@DAOParam("ppId") int ppId);

    @DAOAction(action = DAOActionType.LOAD)
    public  String getPaySequenceById(@DAOParam("ppId") int ppId);

}
