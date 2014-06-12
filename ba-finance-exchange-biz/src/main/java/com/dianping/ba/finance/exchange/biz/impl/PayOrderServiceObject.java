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
import com.dianping.ba.finance.exchange.api.enums.RefundFailedReason;
import com.dianping.ba.finance.exchange.biz.dao.PayOrderDao;
import com.dianping.ba.finance.exchange.biz.producer.PayOrderResultNotify;
import com.dianping.ba.finance.exchange.biz.utils.BizUtils;
import com.dianping.core.type.PageModel;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.aop.annotation.ReturnDefault;
import com.dianping.finance.common.util.DateUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
        // 获取PO实体
        List<String> payCodeList = buildPayCodeList(refundDTOList);
        List<PayOrderData> payOrderDataList = payOrderDao.findPayOrderListByPayCode(payCodeList);
        Map<String, PayOrderData> toRefundPayCodePOMap = buildPayCodePOMap(payOrderDataList);
        // 过滤查询不到的及状态不为支付成功的PO
        filterInvalidedPayOrder(refundResultDTO, refundDTOList, toRefundPayCodePOMap);

        if (toRefundPayCodePOMap.isEmpty()) {
            return refundResultDTO;
        }
        // 修改状态为退票
        List<Integer> poIdList = buildPOIDList(toRefundPayCodePOMap);
        payOrderDao.updatePayOrders(poIdList, PayOrderStatus.PAY_SUCCESS.value(), PayOrderStatus.REFUND.value(), null, loginId);
        BigDecimal refundAmount = calRefundAmount(toRefundPayCodePOMap);
        refundResultDTO.addRefundAmount(refundAmount);

        // 发送退票通知
        notifyRefund(Lists.newLinkedList(toRefundPayCodePOMap.values()), loginId);
        return refundResultDTO;
    }

    private void notifyRefund(List<PayOrderData> payOrderDataList, int loginId) {
        for (PayOrderData payOrderData : payOrderDataList) {
            PayOrderResultBean payOrderResultBean = new PayOrderResultBean();
            payOrderResultBean.setLoginId(loginId);
            payOrderResultBean.setPoId(payOrderData.getPoId());
            payOrderResultBean.setPaidAmount(payOrderData.getPayAmount());
            payOrderResultBean.setPaySequence(payOrderData.getPaySequence());
            payOrderResultBean.setStatus(PayResultStatus.PAY_REFUND);
            payOrderResultNotify.payResultNotify(payOrderResultBean);
        }
    }

    private BigDecimal calRefundAmount(Map<String, PayOrderData> toRefundPayCodePOMap) {
        BigDecimal amount = new BigDecimal(0);
        for (PayOrderData poData : toRefundPayCodePOMap.values()) {
            BigDecimal payAmount = poData.getPayAmount();
            if (payAmount == null) {
                payAmount = BigDecimal.ZERO;
            }
            amount = amount.add(payAmount);
        }
        return amount;
    }

    private List<Integer> buildPOIDList(Map<String, PayOrderData> toRefundPayCodePOMap) {
        List<Integer> poIdList = Lists.newLinkedList();
        for (PayOrderData poData : toRefundPayCodePOMap.values()) {
            poIdList.add(poData.getPoId());
        }
        return poIdList;
    }

    private void filterInvalidedPayOrder(RefundResultDTO refundResultDTO, List<RefundDTO> refundDTOList, Map<String, PayOrderData> payCodePOMap) {
        for (RefundDTO refundDTO : refundDTOList) {
            String payCode = refundDTO.getRefundId();
            PayOrderData poData = payCodePOMap.get(payCode);
            if (poData == null) {
                refundResultDTO.addFailedRefund(payCode, RefundFailedReason.INFO_EMPTY);
                continue;
            }
            int status = poData.getStatus();
            if (status != PayOrderStatus.PAY_SUCCESS.value()) {
                refundResultDTO.addFailedRefund(payCode, RefundFailedReason.STATUS_ERROR);
                payCodePOMap.remove(payCode);
            }
        }
    }

    private Map<String, PayOrderData> buildPayCodePOMap(List<PayOrderData> payOrderDataList) {
        if (CollectionUtils.isEmpty(payOrderDataList)) {
            return Collections.emptyMap();
        }
        Map<String, PayOrderData> payCodePOMap = Maps.newHashMap();
        for (PayOrderData poData : payOrderDataList) {
            payCodePOMap.put(poData.getPayCode(), poData);
        }
        return payCodePOMap;
    }

    private List<String> buildPayCodeList(List<RefundDTO> refundDTOList) {
        List<String> payCodeList = Lists.newLinkedList();
        for (RefundDTO refundDTO : refundDTOList) {
            payCodeList.add(refundDTO.getRefundId());
        }
        return payCodeList;
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
