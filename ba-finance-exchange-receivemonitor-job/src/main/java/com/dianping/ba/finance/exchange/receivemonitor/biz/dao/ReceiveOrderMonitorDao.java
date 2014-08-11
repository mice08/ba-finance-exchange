package com.dianping.ba.finance.exchange.receivemonitor.biz.dao;



import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.receivemonitor.api.datas.ReceiveOrderMonitorData;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: noahshen
 * Date: 14-3-26
 * Time: 下午3:10
 * To change this template use File | Settings | File Templates.
 */
public interface ReceiveOrderMonitorDao extends GenericDao {

    @DAOAction(action = DAOActionType.QUERY)
    public List<ReceiveOrderMonitorData> findReceiveOrderData(@DAOParam("startDate") Date startDate, @DAOParam("endDate") Date endDate);

    @DAOAction(action = DAOActionType.LOAD)
    public ReceiveOrderMonitorData loadReceiveOrderData(@DAOParam("roId") int roId);


}
