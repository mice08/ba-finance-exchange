package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.api.datas.AccountEntryData;

import java.util.List;

/**
 * Created by will on 15-3-6.
 */
public interface AccountEntryDao extends GenericDao {

    @DAOAction(action = DAOActionType.INSERT)
    int insertAccountEntry(@DAOParam("accountEntry") AccountEntryData accountEntry);

}
