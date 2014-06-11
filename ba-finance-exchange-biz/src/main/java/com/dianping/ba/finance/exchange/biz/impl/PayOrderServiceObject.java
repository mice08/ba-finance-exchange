package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.PayOrderService;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.ba.finance.exchange.api.enums.PayOrderStatus;
import com.dianping.ba.finance.exchange.biz.dao.PayOrderDao;
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
            //发消息
            return affectedRows;
        } catch (Exception e) {
            MONITOR_LOGGER.error(String.format("severity=[1] PayOrderServiceObject.updatePayOrderToPaySuccess error! poIds=%s", poIds), e);
            return -1;
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
}
