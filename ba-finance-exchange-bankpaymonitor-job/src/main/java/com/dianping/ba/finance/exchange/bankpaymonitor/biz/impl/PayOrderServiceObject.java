package com.dianping.ba.finance.exchange.bankpaymonitor.biz.impl;

import com.dianping.ba.finance.exchange.api.enums.PayOrderStatus;
import com.dianping.ba.finance.exchange.bankpaymonitor.api.PayOrderService;
import com.dianping.ba.finance.exchange.bankpaymonitor.api.datas.PayOrderMonitorData;
import com.dianping.ba.finance.exchange.bankpaymonitor.biz.daos.PayOrderDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by will on 15-3-13.
 */
public class PayOrderServiceObject implements PayOrderService {

    @Autowired
    private PayOrderDao payOrderDao;

    @Override
    public List<PayOrderMonitorData> findPayOrders(int start, int size, Date startTime, Date endTime) {
        return payOrderDao.findPayOrders(start, size, startTime, endTime, Arrays.asList(PayOrderStatus.BANK_PAYING.value(), PayOrderStatus.PAY_SUCCESS.value(), PayOrderStatus.PAY_FAILED.value()));
    }
}
