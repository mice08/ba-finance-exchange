package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.ReceiveNotifyService;
import com.dianping.ba.finance.exchange.api.beans.ReceiveNotifySearchBean;
import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData;
import com.dianping.ba.finance.exchange.api.enums.ReceiveNotifyStatus;
import com.dianping.ba.finance.exchange.biz.dao.ReceiveNotifyDao;
import com.dianping.core.type.PageModel;
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

    @Log(severity = 3, logBefore = true, logAfter = false)
    @ReturnDefault
    @Override
    public List<ReceiveNotifyData> findUnmatchedLeftReceiveNotify(ReceiveNotifyStatus status, String excludeApplicationId) {
        return receiveNotifyDao.findUnmatchedLeftReceiveNotify(status.value(), excludeApplicationId);
    }

    @Log(severity = 1, logBefore = true, logAfter = false)
    @ReturnDefault
    @Override
    public int clearReceiveNotifyMatchInfo(ReceiveNotifyStatus status, List<Integer> rnIdList) {
        return receiveNotifyDao.clearReceiveNotifyMatchInfo(status.value(), rnIdList);
    }

    @Log(severity = 3, logBefore = true, logAfter = false)
    @ReturnDefault
    @Override
    public ReceiveNotifyData loadUnmatchedReceiveNotifyByApplicationId(ReceiveNotifyStatus status, int businessType, String applicationId) {
        return receiveNotifyDao.loadUnmatchedReceiveNotifyByApplicationId(status.value(), businessType, applicationId);
    }

    @Log(severity = 3, logBefore = true, logAfter = false)
    @ReturnDefault
    @Override
    public PageModel paginateReceiveNotifyList(ReceiveNotifySearchBean receiveNotifySearchBean, int page, int max) {
        return receiveNotifyDao.paginateReceiveNotifyList(receiveNotifySearchBean,page,max);
    }

    public void setReceiveNotifyDao(ReceiveNotifyDao receiveNotifyDao) {
        this.receiveNotifyDao = receiveNotifyDao;
    }
}
