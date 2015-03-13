package com.dianping.ba.finance.exchange.bankpaymonitor.biz.daos;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.bankpaymonitor.api.datas.BankPayMonitorExceptionData;

/**
 * Created by will on 15-3-13.
 */
public interface BankPayMonitorExceptionDao extends GenericDao {

    @DAOAction(action = DAOActionType.INSERT)
    int insertBankPayResult(@DAOParam("result") BankPayMonitorExceptionData result);
}
