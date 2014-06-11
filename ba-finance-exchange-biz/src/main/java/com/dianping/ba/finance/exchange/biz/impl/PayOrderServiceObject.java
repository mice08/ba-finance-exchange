package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.PayOrderService;
import com.dianping.ba.finance.exchange.api.beans.PayOrderResultBean;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.ba.finance.exchange.api.enums.PayOrderStatus;
import com.dianping.ba.finance.exchange.api.enums.PayResultStatus;
import com.dianping.ba.finance.exchange.biz.dao.PayOrderDao;
import com.dianping.ba.finance.exchange.biz.producer.PayOrderResultNotify;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.aop.annotation.ReturnDefault;
import com.dianping.finance.common.util.DateUtils;

import java.util.Date;
import java.util.List;

/**
 * 处理付款单的Service类
 */
public class PayOrderServiceObject implements PayOrderService {

    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.biz.monitor.PayOrderServiceObject");

    private PayOrderDao payOrderDao;

    private PayOrderResultNotify payOrderResultNotify;

    @Log(logBefore = true, logAfter = true)
    @ReturnDefault
    @Override
    public int createPayOrder(PayOrderData payOrderData){
        try {
            int poId = payOrderDao.insertPayOrder(payOrderData);
            payOrderData.setPoId(poId);
            return poId;
        } catch (Exception e) {// 直接插入，如果主键冲突会抛异常
            MONITOR_LOGGER.error(String.format("severity=[1] PayOrderServiceObject.createPayOrder error! payOrderData=%s", payOrderData), e);
            return -1;
        }
    }

    @Log(logBefore = true, logAfter = true)
    @ReturnDefault
    @Override
    public int updatePayOrderToPaySuccess(List<Integer> poIds, int loginId){
        try {
            PayOrderStatus whereStatus = PayOrderStatus.EXPORT_PAYING;
            PayOrderStatus setStatus = PayOrderStatus.PAY_SUCCESS;
            Date paidDate= DateUtils.getCurrentTime();
            int affectedRows = payOrderDao.updatePayOrders(poIds, whereStatus.value(), setStatus.value(), paidDate, loginId);
            if (affectedRows != poIds.size()) {
                MONITOR_LOGGER.error(String.format("severity=[1] PayOrderServiceObject.updatePayOrderToPaySuccess error! affectedRows not equal poIds size,affectedRows:=%s", affectedRows));
            }
            payOrderResultNotify(poIds, loginId);
            return affectedRows;
        } catch (Exception e) {
            MONITOR_LOGGER.error(String.format("severity=[1] PayOrderServiceObject.updatePayOrderToPaySuccess error! poIds=%s", poIds), e);
            return -1;
        }
    }

    private void payOrderResultNotify(List<Integer> poIds, int loginId) {
        List<PayOrderData> payOrderDataList = payOrderDao.findPayOrderListBypoIdList(poIds);
        for (PayOrderData payOrderData : payOrderDataList) {
            if (payOrderData.getStatus() == PayOrderStatus.PAY_SUCCESS.value()) {
                PayOrderResultBean payOrderResultBean = new PayOrderResultBean();
                payOrderResultBean.setLoginId(loginId);
                payOrderResultBean.setPoId(payOrderData.getPoId());
                payOrderResultBean.setPaidAmount(payOrderData.getPayAmount());
                payOrderResultBean.setPaySequence(payOrderData.getPaySequence());
                payOrderResultBean.setStatus(PayResultStatus.PAY_SUCCESS);
                payOrderResultNotify.payResultNotify(payOrderResultBean);
            }
        }
    }

    @Log(logBefore = true, logAfter = true)
    @ReturnDefault
    @Override
    public int updatePayOrderToPaying(List<Integer> poIds, int loginId){
        try {
            PayOrderStatus whereStatus = PayOrderStatus.INIT;
            PayOrderStatus setStatus = PayOrderStatus.EXPORT_PAYING;
            return payOrderDao.updatePayOrders(poIds, whereStatus.value(), setStatus.value(),null,loginId);
        } catch (Exception e) {
            MONITOR_LOGGER.error(String.format("severity=[1] PayOrderServiceObject.updatePayOrderToPaying error! poIds=%s", poIds), e);
            return -1;
        }
    }
    public void setPayOrderDao(PayOrderDao payOrderDao) {
        this.payOrderDao = payOrderDao;
    }

    public void setPayOrderResultNotify(PayOrderResultNotify payOrderResultNotify) {
        this.payOrderResultNotify = payOrderResultNotify;
    }
}
