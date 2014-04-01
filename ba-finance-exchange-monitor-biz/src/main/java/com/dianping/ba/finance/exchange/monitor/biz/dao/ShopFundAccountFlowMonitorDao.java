package com.dianping.ba.finance.exchange.monitor.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.monitor.api.datas.ShopFundAccountFlowMonitorData;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jingzhou.yan
 * Date: 14-3-27
 * Time: 下午4:01
 * To change this template use File | Settings | File Templates.
 */
public interface ShopFundAccountFlowMonitorDao extends GenericDao {

    @DAOAction(action = DAOActionType.QUERY)
    public List<ShopFundAccountFlowMonitorData> findShopFundAccountFlowData(@DAOParam("eoId") int eoId);
}
