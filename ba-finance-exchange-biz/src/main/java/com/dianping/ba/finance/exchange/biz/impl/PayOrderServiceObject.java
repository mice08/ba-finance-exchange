package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.PayOrderService;
import com.dianping.ba.finance.exchange.api.beans.PayOrderResultBean;
import com.dianping.ba.finance.exchange.api.beans.PayOrderSearchBean;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.ba.finance.exchange.api.dtos.RefundDTO;
import com.dianping.ba.finance.exchange.api.dtos.RefundResultDTO;
import com.dianping.ba.finance.exchange.api.enums.PayOrderStatus;
import com.dianping.ba.finance.exchange.api.enums.PayResultStatus;
import com.dianping.ba.finance.exchange.biz.dao.PayOrderDao;
import com.dianping.ba.finance.exchange.biz.producer.PayOrderResultNotify;
import com.dianping.ba.finance.exchange.biz.utils.BizUtils;
import com.dianping.core.type.PageModel;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.aop.annotation.ReturnDefault;
import com.dianping.finance.common.util.DateUtils;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 处理付款单的Service类
 */
public class PayOrderServiceObject implements PayOrderService {

    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.biz.monitor.PayOrderServiceObject");

    private static final int DUPLICATE_RETRY_TIMES = 5;

    private PayOrderDao payOrderDao;

    private PayOrderResultNotify payOrderResultNotify;

    @Log(logBefore = true, logAfter = true)
    @ReturnDefault
    @Override
    public int createPayOrder(PayOrderData payOrderData) {
        int times = DUPLICATE_RETRY_TIMES;
        do {
            try {
                // 主键冲突，重新生成PayCode再插入
                payOrderData.setPayCode(BizUtils.generatePayCode());
                int poId = payOrderDao.insertPayOrder(payOrderData);
                payOrderData.setPoId(poId);
                return poId;
            } catch (Exception e) {
                MONITOR_LOGGER.error(String.format("severity=[1] PayOrderServiceObject.createPayOrder error! payOrderData=%s", payOrderData), e);
                times--;
            }
        } while(times > 0);
        return -1;
    }

    @Log(severity = 1, logBefore = true, logAfter = true)
    @ReturnDefault
    @Override
    public RefundResultDTO refundPayOrder(List<RefundDTO> refundDTOList, int loginId) {
        RefundResultDTO refundResultDTO = new RefundResultDTO();
        if (CollectionUtils.isEmpty(refundDTOList)) {
            return refundResultDTO;
        }
        // TODO

        return refundResultDTO;
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
        List<PayOrderData> payOrderDataList = payOrderDao.findPayOrderListByPoIdList(poIds);
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

    public void setPayOrderResultNotify(PayOrderResultNotify payOrderResultNotify) {
        this.payOrderResultNotify = payOrderResultNotify;
    }
}
