package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.api.datas.PayCentreReceiveRequestData;

/**
 *
 */
public interface PayCentreReceiveRequestDao extends GenericDao {

    /**
     * 添加支付中心收款请求记录
     * @param payCentreReceiveRequestData
     * @return
     */
    @DAOAction(action = DAOActionType.INSERT)
    int insertPayCentreReceiveRequest(@DAOParam("payCentreReceiveRequestData") PayCentreReceiveRequestData payCentreReceiveRequestData);

}
