package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.ReceiveNotifyService;
import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData;
import com.dianping.ba.finance.exchange.api.enums.ReceiveNotifyStatus;
import com.dianping.ba.finance.exchange.biz.dao.ReceiveNotifyDao;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.aop.annotation.ReturnDefault;

import java.util.List;

/**
 * Created by Administrator on 2014/7/24.
 */
public class ReceiveNotifyServiceObject implements ReceiveNotifyService {
    private ReceiveNotifyDao receiveNotifyDao;

    @Log(severity = 1, logBefore = true, logAfter = false)
    @ReturnDefault
    @Override
    public int insertReceiveNotify(ReceiveNotifyData receiveNotifyData) {
        return receiveNotifyDao.insertReceiveNotify(receiveNotifyData);
    }

    @Log(severity = 1, logBefore = true, logAfter = false)
    @ReturnDefault
    @Override
    public boolean updateReceiveNotifyMatchId(int setStatus, int roId, int preStatus, int rnId) {
        int u = receiveNotifyDao.updateReceiveNotifyMatchId(setStatus, roId, preStatus, rnId);
        return u == 1;
    }

    @Log(severity = 3, logBefore = true, logAfter = false)
    @ReturnDefault
    @Override
    public List<ReceiveNotifyData> getUnMatchedReceiveNotify(ReceiveNotifyStatus status) {
        return receiveNotifyDao.getUnMatchedReceiveNotify(status.value());
    }

    public void setReceiveNotifyDao(ReceiveNotifyDao receiveNotifyDao) {
        this.receiveNotifyDao = receiveNotifyDao;
    }
}
