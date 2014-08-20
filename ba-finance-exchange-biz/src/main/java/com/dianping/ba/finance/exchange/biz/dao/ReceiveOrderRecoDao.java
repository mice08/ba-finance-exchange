package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderRecoData;

import java.util.List;

/**
 * Created by will on 14-8-18.
 */
public interface ReceiveOrderRecoDao extends GenericDao {

    @DAOAction(action = DAOActionType.INSERT)
    void insertReceiveOrderRecoDatas(@DAOParam("list") List<ReceiveOrderRecoData> list);
}
