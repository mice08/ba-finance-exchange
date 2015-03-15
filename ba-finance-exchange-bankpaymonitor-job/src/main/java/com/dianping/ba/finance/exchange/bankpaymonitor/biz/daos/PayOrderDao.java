package com.dianping.ba.finance.exchange.bankpaymonitor.biz.daos;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.bankpaymonitor.api.datas.PayOrderMonitorData;

import java.util.Date;
import java.util.List;

/**
 * Created by adam.huang on 2014/8/7.
 */
public interface PayOrderDao extends GenericDao{

    @DAOAction(action = DAOActionType.QUERY)
    public List<PayOrderMonitorData> findPayOrders(@DAOParam("start") int start, @DAOParam("size") int size, @DAOParam("startTime") Date startTime, @DAOParam("endTime") Date endTime, @DAOParam("statusList") List<Integer> statusList);
}
