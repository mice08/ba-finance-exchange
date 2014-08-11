package com.dianping.ba.finance.exchange.paymonitor.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.paymonitor.api.datas.PayOrderMonitorData;

/**
 * Created by adam.huang on 2014/8/7.
 */
public interface PayOrderDao extends GenericDao{

    @DAOAction(action = DAOActionType.LOAD)
    public PayOrderMonitorData getPayOrderBySequence(@DAOParam("paySequence")String paySequence);
}
