package com.dianping.ba.finance.exchange.bankpaymonitor.api;

import com.dianping.ba.finance.exchange.bankpaymonitor.api.datas.PayOrderMonitorData;

import java.util.Date;
import java.util.List;

/**
 * Created by will on 15-3-13.
 */
public interface PayOrderService {

    List<PayOrderMonitorData> findPayOrders(int start, int size, Date startTime, Date endTime);
}
