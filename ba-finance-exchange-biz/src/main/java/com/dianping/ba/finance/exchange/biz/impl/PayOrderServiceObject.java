package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.PayOrderService;
import com.dianping.ba.finance.exchange.api.beans.POUpdateInfoBean;
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
import java.util.*;

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
        Map<String, PayOrderData> payCodePOMap = buildPayCodePOMap(payOrderDataList);
        // 过滤查询不到的及状态不为支付成功的PO
        List<PayOrderData> filteredPOList = filterInvalidedPayOrder(refundResultDTO, refundDTOList, payCodePOMap);

        if (filteredPOList.isEmpty()) {
            return refundResultDTO;
        }
        // 修改状态为退票
        updateToRefund(filteredPOList, loginId);
        BigDecimal refundAmount = calRefundAmount(filteredPOList);
        refundResultDTO.addRefundAmount(refundAmount);

        // 发送退票通知
        notifyRefund(filteredPOList, loginId);
        return refundResultDTO;
    }

    private void updateToRefund(List<PayOrderData> poList, int loginId) {
        for (PayOrderData poData : poList) {
            POUpdateInfoBean poUpdateInfoBean = new POUpdateInfoBean();
            poUpdateInfoBean.setPoIdList(Arrays.asList(poData.getPoId()));
            poUpdateInfoBean.setLoginId(loginId);
            poUpdateInfoBean.setMemo(poData.getMemo());
            poUpdateInfoBean.setPreStatus(PayOrderStatus.PAY_SUCCESS.value());
            poUpdateInfoBean.setUpdateStatus(PayOrderStatus.REFUND.value());
            payOrderDao.updatePayOrders(poUpdateInfoBean);

        }
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

    private BigDecimal calRefundAmount(List<PayOrderData> payOrderDataList) {
        BigDecimal amount = new BigDecimal(0);
        for (PayOrderData poData : payOrderDataList) {
            BigDecimal payAmount = poData.getPayAmount();
            if (payAmount == null) {
                payAmount = BigDecimal.ZERO;
            }
            amount = amount.add(payAmount);
        }
        return amount;
    }

    private List<PayOrderData> filterInvalidedPayOrder(RefundResultDTO refundResultDTO, List<RefundDTO> refundDTOList, Map<String, PayOrderData> payCodePOMap) {
        List<PayOrderData> filteredPOList = Lists.newLinkedList();
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
                continue;
            }
            poData.setMemo(refundDTO.getRefundReason());
            filteredPOList.add(poData);
        }
        return filteredPOList;
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
    public int updatePayOrderToPaySuccess(List<Integer> poIdList, int loginId){
        try {
            POUpdateInfoBean poUpdateInfoBean = new POUpdateInfoBean();
            poUpdateInfoBean.setPoIdList(poIdList);
            poUpdateInfoBean.setLoginId(loginId);
            poUpdateInfoBean.setPreStatus(PayOrderStatus.EXPORT_PAYING.value());
            poUpdateInfoBean.setUpdateStatus(PayOrderStatus.PAY_SUCCESS.value());
            poUpdateInfoBean.setPaidDate(DateUtils.getCurrentTime());
            int affectedRows = payOrderDao.updatePayOrders(poUpdateInfoBean);
            if (affectedRows != poIdList.size()) {
                MONITOR_LOGGER.error(String.format("severity=[1] PayOrderServiceObject.updatePayOrderToPaySuccess error! affectedRows not equal poIds size,affectedRows:=%s", affectedRows));
            }
            payOrderResultNotify(poIdList, loginId);
            return affectedRows;
        } catch (Exception e) {
            MONITOR_LOGGER.error(String.format("severity=[1] PayOrderServiceObject.updatePayOrderToPaySuccess error! poIdList=%s", poIdList), e);
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
    public int updatePayOrderToPaying(List<Integer> poIdList, int loginId){
        try {
            POUpdateInfoBean poUpdateInfoBean = new POUpdateInfoBean();
            poUpdateInfoBean.setPoIdList(poIdList);
            poUpdateInfoBean.setLoginId(loginId);
            poUpdateInfoBean.setPreStatus(PayOrderStatus.INIT.value());
            poUpdateInfoBean.setUpdateStatus(PayOrderStatus.EXPORT_PAYING.value());
            return payOrderDao.updatePayOrders(poUpdateInfoBean);
        } catch (Exception e) {
            MONITOR_LOGGER.error(String.format("severity=[1] PayOrderServiceObject.updatePayOrderToPaying error! poIdList=%s", poIdList), e);
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
