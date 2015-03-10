package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.api.datas.BankAccountData;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by will on 15-3-9.
 */
public interface BankAccountDao extends GenericDao {

    @DAOAction(action = DAOActionType.LOAD)
    BankAccountData loadBankAccount(@DAOParam("id") int id);

}
