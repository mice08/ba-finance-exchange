package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.PayRequestService;
import com.dianping.ba.finance.exchange.api.datas.PayRequestData;
import com.dianping.ba.finance.exchange.api.enums.PayRequestStatus;
import com.dianping.ba.finance.exchange.biz.dao.PayRequestDao;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.aop.annotation.ReturnDefault;

/**
 *
 */
public class PayRequestServiceObject implements PayRequestService {

    private PayRequestDao payRequestDao;

    @Log(severity = 1)
    @ReturnDefault
    @Override
    public boolean insertPayRequest(PayRequestData payRequestData) {
        int prId = payRequestDao.insertPayRequest(payRequestData);
        if (prId <= 0) {
            return false;
        }
        payRequestData.setPrId(prId);
        return true;
    }

    @Log(severity = 1)
    @ReturnDefault
    @Override
    public boolean updatePayRequest(int requestId, PayRequestStatus status, String memo) {
        int count = payRequestDao.updatePayRequest(requestId, status.value(), memo);
        return count == 1;
    }

    public void setPayRequestDao(PayRequestDao payRequestDao) {
        this.payRequestDao = payRequestDao;
    }
}
