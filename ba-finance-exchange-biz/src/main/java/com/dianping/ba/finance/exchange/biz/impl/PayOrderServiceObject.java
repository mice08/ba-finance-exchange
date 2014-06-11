package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.PayOrderService;
import com.dianping.ba.finance.exchange.api.beans.PayOrderSearchBean;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.ba.finance.exchange.biz.dao.PayOrderDao;
import com.dianping.core.type.PageModel;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.aop.annotation.ReturnDefault;

import java.math.BigDecimal;

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
    @Override
    public PageModel paginatePayOrderList(PayOrderSearchBean payOrderSearchBean, int page, int pageSize) {
        return payOrderDao.paginatePayOrderList(payOrderSearchBean,page,pageSize);
    }

    @Log(logBefore = true, logAfter = true)
    @Override
    public BigDecimal findPayOrderTotalAmount(PayOrderSearchBean payOrderSearchBean) {
        return payOrderDao.findPayOrderTotalAmountByCondition(payOrderSearchBean);
    }

    public void setPayOrderDao(PayOrderDao payOrderDao) {
        this.payOrderDao = payOrderDao;
    }
}
