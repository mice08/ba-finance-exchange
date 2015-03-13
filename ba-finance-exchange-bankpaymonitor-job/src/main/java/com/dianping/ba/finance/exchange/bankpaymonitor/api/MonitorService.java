package com.dianping.ba.finance.exchange.bankpaymonitor.api;

import com.dianping.ba.finance.exchange.bankpaymonitor.api.enums.BankPayException;

import java.util.Date;

/**
 * Created by will on 15-3-13.
 */
public interface MonitorService {

    Date loadLastMonitorTime();

    boolean insertMonitorTime(Date time);

    boolean insertMonitorResult(int poId, BankPayException bankPayException);

}
