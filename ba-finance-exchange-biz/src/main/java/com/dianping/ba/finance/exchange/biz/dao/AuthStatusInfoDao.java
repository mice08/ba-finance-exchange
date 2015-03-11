package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.api.datas.AuthStatusInfoData;

/**
 * Created by will on 15-3-10.
 */
public interface AuthStatusInfoDao extends GenericDao {

    @DAOAction(action = DAOActionType.INSERT)
    int insertAuthStatusInfo(@DAOParam("workNo") String workNo, @DAOParam("times") int times, @DAOParam("type") int type);

    @DAOAction(action = DAOActionType.LOAD)
    AuthStatusInfoData loadStatusByWorkNoAndType(@DAOParam("workNo") String workNo, @DAOParam("type") int type);

    @DAOAction(action = DAOActionType.UPDATE)
    int updateAuthTimes(@DAOParam("id") int id, @DAOParam("times") int times);

    @DAOAction(action = DAOActionType.UPDATE)
    int updateStatusInvalid(@DAOParam("workNo") String workNo, @DAOParam("type") int type);
}
