package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.api.datas.AccessTokenInfoData;

import java.util.List;

/**
 *
 */
public interface AuthTokenDao extends GenericDao {

    /**
     * 获取所有Token信息
     * @return
     */
    @DAOAction(action = DAOActionType.QUERY)
    List<AccessTokenInfoData> findAllAccessTokenInfo();

    /**
     * 获取指定Token的信息
     * @param token
     * @return
     */
    @DAOAction(action = DAOActionType.LOAD)
    AccessTokenInfoData loadAccessTokenInfo(@DAOParam("token") String token);


}
