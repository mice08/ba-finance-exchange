package com.dianping.ba.finance.exchange.bankpaymonitor.biz.impl;

import com.dianping.ba.finance.exchange.bankpaymonitor.api.MonitorService;
import com.dianping.ba.finance.exchange.bankpaymonitor.api.datas.BankPayMonitorExceptionData;
import com.dianping.ba.finance.exchange.bankpaymonitor.api.enums.BankPayException;
import com.dianping.ba.finance.exchange.bankpaymonitor.biz.daos.BankPayMonitorResultDao;
import com.dianping.ba.finance.exchange.bankpaymonitor.biz.daos.MonitorTimeDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by will on 15-3-13.
 */
public class MonitorServiceObject implements MonitorService {

    @Autowired
    private MonitorTimeDao monitorTimeDao;
    @Autowired
    private BankPayMonitorResultDao bankPayMonitorResultDao;

    @Override
    public Date loadLastMonitorTime() {
        return monitorTimeDao.loadLastMonitorTime();
    }

    @Override
    public boolean insertMonitorTime(Date time) {
        return monitorTimeDao.insertMonitorTime(time) > 0;
    }

    @Override
    public boolean insertMonitorResult(int poId, BankPayException bankPayException) {
        BankPayMonitorExceptionData data = new BankPayMonitorExceptionData();
        data.setCheckStatus(bankPayException.getCode());
        data.setMemo(bankPayException.getMessage());
        data.setPoId(poId);
        return bankPayMonitorResultDao.insertBankPayResult(data) > 0;
    }
}
