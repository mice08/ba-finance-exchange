package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.api.beans.AccountUpdateInfoBean;
import com.dianping.ba.finance.exchange.api.datas.AccountData;

/**
 * Created by will on 15-3-6.
 */
public interface AccountDao extends GenericDao {

    @DAOAction(action = DAOActionType.UPDATE)
    int updateAccount(@DAOParam("accountUpdateInfo") AccountUpdateInfoBean accountUpdateInfo);
    @DAOAction(action = DAOActionType.LOAD)
    AccountData loadAccountByBankAccount(@DAOParam("bankAccountId") int bankAccountId);
}
