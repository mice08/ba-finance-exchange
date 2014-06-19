package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.PayCentreReceiveRequestService;
import com.dianping.ba.finance.exchange.api.datas.PayCentreReceiveRequestData;
import com.dianping.ba.finance.exchange.biz.dao.PayCentreReceiveRequestDao;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.aop.annotation.ReturnDefault;

/**
 *
 */
public class PayCentreReceiveRequestServiceObject implements PayCentreReceiveRequestService {

    private PayCentreReceiveRequestDao payCentreReceiveRequestDao;

    @Log(severity = 1)
    @ReturnDefault
    @Override
    public boolean insertPayCentreReceiveRequest(PayCentreReceiveRequestData payCentreReceiveRequestData) {
        int requestId = payCentreReceiveRequestDao.insertPayCentreReceiveRequest(payCentreReceiveRequestData);
        if (requestId <= 0) {
            return false;
        }
        payCentreReceiveRequestData.setRequestId(requestId);
        return true;
    }

    public void setPayCentreReceiveRequestDao(PayCentreReceiveRequestDao payCentreReceiveRequestDao) {
        this.payCentreReceiveRequestDao = payCentreReceiveRequestDao;
    }
}
