package com.dianping.ba.finance.exchange.monitor.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.monitor.api.datas.ExchangeOrderMonitorData;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: noahshen
 * Date: 14-3-26
 * Time: 下午3:10
 * To change this template use File | Settings | File Templates.
 */
public interface ExchangeOrderMonitorDao extends GenericDao {

    @DAOAction(action = DAOActionType.QUERY)
    public List<ExchangeOrderMonitorData> findExchangeOrderData( @DAOParam("startDate") Date startDate,@DAOParam("endDate") Date endDate);


}
