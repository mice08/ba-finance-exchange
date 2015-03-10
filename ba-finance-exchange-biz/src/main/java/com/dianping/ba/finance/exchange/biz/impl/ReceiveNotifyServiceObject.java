package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.ReceiveNotifyService;
import com.dianping.ba.finance.exchange.api.beans.ReceiveNotifyResultBean;
import com.dianping.ba.finance.exchange.api.beans.ReceiveNotifySearchBean;
import com.dianping.ba.finance.exchange.api.beans.ReceiveNotifyUpdateBean;
import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData;
import com.dianping.ba.finance.exchange.api.enums.ReceiveNotifyResultStatus;
import com.dianping.ba.finance.exchange.api.enums.ReceiveNotifyStatus;
import com.dianping.ba.finance.exchange.biz.dao.ReceiveNotifyDao;
import com.dianping.ba.finance.exchange.biz.producer.ReceiveNotifyResultNotify;
import com.dianping.core.type.PageModel;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.aop.annotation.ReturnDefault;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2014/7/24.
 */
public class ReceiveNotifyServiceObject implements ReceiveNotifyService {

    private ReceiveNotifyResultNotify receiveNotifyResultNotify;

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
    public List<ReceiveNotifyData> findUnmatchedLeftReceiveNotify(ReceiveNotifyStatus status, int roId, String excludeApplicationId) {
        return receiveNotifyDao.findUnmatchedLeftReceiveNotify(status.value(), roId, excludeApplicationId);
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
		return receiveNotifyDao.paginateReceiveNotifyList(receiveNotifySearchBean, page, max);
	}

    @Override
    public BigDecimal loadTotalReceiveAmountByCondition(ReceiveNotifySearchBean receiveNotifySearchBean) {
        return receiveNotifyDao.loadTotalReceiveAmountByCondition(receiveNotifySearchBean);
    }

    @Log(severity = 3, logBefore = true, logAfter = true)
    @ReturnDefault
    @Override
    public List<ReceiveNotifyData> findMatchedReceiveNotify(int roId) {
        return receiveNotifyDao.findMatchedReceiveNotify(ReceiveNotifyStatus.MATCHED.value(), roId);
    }

    @Log(severity = 1, logBefore = true, logAfter = true)
    @ReturnDefault
    @Override
    public boolean removeReceiveNotifyMatchRelation(int rnId, int roMatcherId) {
        int u = receiveNotifyDao.removeReceiveNotifyMatchRelation(rnId, roMatcherId, ReceiveNotifyStatus.MATCHED.value());
        return u == 1;
    }

    @Log(severity = 3, logBefore = true, logAfter = true)
    @ReturnDefault
    @Override
    public ReceiveNotifyData loadMatchedReceiveNotify(int rnId, int roId) {
        return receiveNotifyDao.loadMatchedReceiveNotify(ReceiveNotifyStatus.MATCHED.value(), rnId, roId);
    }

    @Log(severity = 3, logBefore = true, logAfter = true)
    @ReturnDefault
    @Override
    public boolean updateReceiveNotifyConfirm(int roId, int rnId) {
        int u = receiveNotifyDao.updateReceiveNotifyConfirm(ReceiveNotifyStatus.CONFIRMED.value(), roId, rnId);
        return u == 1;
    }

    @Log(severity = 3, logBefore = true, logAfter = true)
    @ReturnDefault
    @Override
    public ReceiveNotifyData loadReceiveNotifyByApplicationId(String applicationId) {
        return receiveNotifyDao.loadReceiveNotifyByApplicationId(applicationId);
    }

    @Log(severity = 3, logBefore = true, logAfter = true)
    @ReturnDefault
    @Override
    public int updateReceiveNotifyStatus(int rnId, ReceiveNotifyStatus preStatus, ReceiveNotifyStatus setStatus, String memo) {
        ReceiveNotifyUpdateBean receiveNotifyUpdateBean = new ReceiveNotifyUpdateBean();
        receiveNotifyUpdateBean.setRnId(rnId);
        receiveNotifyUpdateBean.setPreStatus(preStatus.value());
        receiveNotifyUpdateBean.setSetStatus(setStatus.value());
        receiveNotifyUpdateBean.setMemo(memo);
        int u = receiveNotifyDao.updateReceiveNotifyStatus(receiveNotifyUpdateBean);
        if (u > 0
                && (setStatus == ReceiveNotifyStatus.CONFIRMED
                || setStatus == ReceiveNotifyStatus.REJECT)) {
            ReceiveNotifyData rnData = receiveNotifyDao.loadReceiveNotifyByRNID(rnId);
            if (rnData != null) {
                ReceiveNotifyResultStatus status = ReceiveNotifyResultStatus.valueOf(rnData.getStatus());
                ReceiveNotifyResultBean receiveNotifyResultBean = buildReceiveNotifyResultBean(rnData, status);
                receiveNotifyResultNotify.resultNotify(receiveNotifyResultBean);
            }
        }
        return u;
    }

    private ReceiveNotifyResultBean buildReceiveNotifyResultBean(ReceiveNotifyData rnData, ReceiveNotifyResultStatus status) {
        ReceiveNotifyResultBean receiveNotifyResultBean = new ReceiveNotifyResultBean();
        receiveNotifyResultBean.setApplicationId(rnData.getApplicationId());
        receiveNotifyResultBean.setBusinessType(rnData.getBusinessType());
        receiveNotifyResultBean.setMemo(rnData.getMemo());
        receiveNotifyResultBean.setReceiveNotifyId(rnData.getReceiveNotifyId());
        receiveNotifyResultBean.setStatus(status);
        return receiveNotifyResultBean;
    }

    public void setReceiveNotifyResultNotify(ReceiveNotifyResultNotify receiveNotifyResultNotify) {
        this.receiveNotifyResultNotify = receiveNotifyResultNotify;
    }

    public void setReceiveNotifyDao(ReceiveNotifyDao receiveNotifyDao) {
        this.receiveNotifyDao = receiveNotifyDao;
    }

}
