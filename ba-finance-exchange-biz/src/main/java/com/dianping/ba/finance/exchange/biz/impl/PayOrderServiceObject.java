package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.PayOrderService;
import com.dianping.ba.finance.exchange.api.beans.POUpdateInfoBean;
import com.dianping.ba.finance.exchange.api.beans.PayOrderResultBean;
import com.dianping.ba.finance.exchange.api.beans.PayOrderSearchBean;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.ba.finance.exchange.api.dtos.PayOrderBankInfoDTO;
import com.dianping.ba.finance.exchange.api.dtos.RefundDTO;
import com.dianping.ba.finance.exchange.api.dtos.RefundResultDTO;
import com.dianping.ba.finance.exchange.api.enums.PayOrderStatus;
import com.dianping.ba.finance.exchange.api.enums.PayResultStatus;
import com.dianping.ba.finance.exchange.api.enums.RefundFailedReason;
import com.dianping.ba.finance.exchange.biz.dao.PayOrderDao;
import com.dianping.ba.finance.exchange.biz.producer.PayOrderResultNotify;
import com.dianping.core.type.PageModel;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.aop.annotation.ReturnDefault;
import com.dianping.finance.common.util.ConvertUtils;
import com.dianping.finance.common.util.DateUtils;
import com.dianping.finance.common.util.LionConfigUtils;
import com.dianping.finance.common.util.ListUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
                // 高并发下，payCode冲突，重试再插入
                int poId = payOrderDao.insertPayOrder(payOrderData);
                payOrderData.setPoId(poId);
                return poId;
            } catch (Exception e) {
                MONITOR_LOGGER.error(String.format("severity=[1] PayOrderServiceObject.createPayOrder error! payOrderData=%s", payOrderData), e);
                times--;
            }
        } while (times > 0);
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
        List<Integer> poidList = buildPOIDList(refundDTOList);
        List<PayOrderData> payOrderDataList = payOrderDao.findPayOrderListByPoIdList(poidList);
        Map<String, PayOrderData> poMap = buildPOMap(payOrderDataList);
        // 过滤查询不到的及状态不为支付成功的PO
        List<PayOrderData> filteredPOList = filterInvalidedPayOrder(refundResultDTO, refundDTOList, poMap);

        if (filteredPOList.isEmpty()) {
            return refundResultDTO;
        }
        // 修改状态为退票
        updateToRefund(filteredPOList, loginId);
        // 计算结果
        BigDecimal refundAmount = calRefundAmount(filteredPOList);
        refundResultDTO.addRefundAmount(refundAmount);
        refundResultDTO.setSuccessCount(filteredPOList.size());

        // 发送退票通知
        notifyPayResult(filteredPOList, PayResultStatus.PAY_REFUND, loginId);
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

    private void notifyPayResult(List<PayOrderData> payOrderDataList, PayResultStatus payResultStatus, int loginId) {
        for (PayOrderData payOrderData : payOrderDataList) {
            PayOrderResultBean payOrderResultBean = new PayOrderResultBean();
            payOrderResultBean.setLoginId(loginId);
            payOrderResultBean.setPoId(payOrderData.getPoId());
            payOrderResultBean.setPaidAmount(payOrderData.getPayAmount());
            payOrderResultBean.setPaySequence(payOrderData.getPaySequence());
            payOrderResultBean.setStatus(payResultStatus);
            payOrderResultBean.setMemo(payOrderData.getMemo());
            payOrderResultBean.setBusinessType(payOrderData.getBusinessType());
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

    private List<PayOrderData> filterInvalidedPayOrder(RefundResultDTO refundResultDTO, List<RefundDTO> refundDTOList, Map<String, PayOrderData> poMap) {
        List<PayOrderData> filteredPOList = Lists.newLinkedList();
        for (RefundDTO refundDTO : refundDTOList) {
            String payCode = refundDTO.getRefundId();
            PayOrderData poData = poMap.get(payCode);
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

    private Map<String, PayOrderData> buildPOMap(List<PayOrderData> payOrderDataList) {
        if (CollectionUtils.isEmpty(payOrderDataList)) {
            return Collections.emptyMap();
        }
        Map<String, PayOrderData> payCodePOMap = Maps.newHashMap();
        for (PayOrderData poData : payOrderDataList) {
            payCodePOMap.put(String.valueOf(poData.getPoId()), poData);
        }
        return payCodePOMap;
    }

    private List<Integer> buildPOIDList(List<RefundDTO> refundDTOList) {
        List<Integer> poIdList = Lists.newLinkedList();
        for (RefundDTO refundDTO : refundDTOList) {
            poIdList.add(Integer.parseInt((refundDTO.getRefundId())));
        }
        return poIdList;
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
                payOrderResultBean.setBusinessType(payOrderData.getBusinessType());
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
	public List<PayOrderData> findPayOrderList(PayOrderSearchBean payOrderSearchBean) {
		return payOrderDao.findPayOrderList(payOrderSearchBean);
	}

    @Log(logBefore = true, logAfter = true)
    @Override
    public BigDecimal findPayOrderTotalAmount(PayOrderSearchBean payOrderSearchBean) {
        return payOrderDao.findPayOrderTotalAmountByCondition(payOrderSearchBean);
    }

    @Log(severity = 2)
    @ReturnDefault
    @Override
    public List<Integer> findPayOrderIdList(PayOrderSearchBean payOrderSearchBean) {
        return payOrderDao.findPayOrderIdList(payOrderSearchBean);
    }

    @Log(severity = 2)
    @ReturnDefault
    @Override
    public PayOrderBankInfoDTO loadPayOrderByPaySequence(String paySequence) {
        PayOrderData poData = payOrderDao.loadPayOrderByPaySequence(paySequence);
        if (poData == null) {
            return null;
        }
        PayOrderBankInfoDTO bankInfoDTO = buildPayOrderBankInfoDTO(poData);
        return bankInfoDTO;
    }

    @Log(severity = 2)
    @ReturnDefault
    @Override
    public boolean suspendPayOrder(String paySequence) {
        PayOrderData payOrderData = payOrderDao.loadPayOrderByPaySequence(paySequence);
        if(payOrderData == null) {
            return true;
        }

        if(payOrderData.getStatus() == PayOrderStatus.INIT.value()) {
            POUpdateInfoBean poUpdateInfoBean = new POUpdateInfoBean();
            poUpdateInfoBean.setPoIdList(Arrays.asList(payOrderData.getPoId()));
            poUpdateInfoBean.setPreStatus(payOrderData.getStatus());
            poUpdateInfoBean.setUpdateStatus(PayOrderStatus.SUSPEND.value());
            poUpdateInfoBean.setLoginId(payOrderData.getAddLoginId());
            payOrderDao.updatePayOrders(poUpdateInfoBean);
            return true;
        }

        return false;
    }

    @Log(severity = 2)
    @ReturnDefault
    @Override
    public boolean resumePayOrder(String paySequence) {
        PayOrderData payOrderData = payOrderDao.loadPayOrderByPaySequence(paySequence);
        if(payOrderData == null) {
            return true;
        }

        if(payOrderData.getStatus() == PayOrderStatus.SUSPEND.value()) {
            POUpdateInfoBean poUpdateInfoBean = new POUpdateInfoBean();
            poUpdateInfoBean.setPoIdList(Arrays.asList(payOrderData.getPoId()));
            poUpdateInfoBean.setPreStatus(payOrderData.getStatus());
            poUpdateInfoBean.setUpdateStatus(PayOrderStatus.INIT.value());
            poUpdateInfoBean.setLoginId(payOrderData.getAddLoginId());
            payOrderDao.updatePayOrders(poUpdateInfoBean);
            return true;
        }

        return false;
    }

    @Log(severity = 2)
    @ReturnDefault
    @Override
    public boolean dropPayOrder(String paySequence) {
        PayOrderData payOrderData = payOrderDao.loadPayOrderByPaySequence(paySequence);
        if(payOrderData == null) {
            return true;
        }

        if(allowDropPayOrder(payOrderData)) {
            POUpdateInfoBean poUpdateInfoBean = new POUpdateInfoBean();
            poUpdateInfoBean.setPoIdList(Arrays.asList(payOrderData.getPoId()));
            poUpdateInfoBean.setPreStatus(payOrderData.getStatus());
            poUpdateInfoBean.setUpdateStatus(PayOrderStatus.INVALID.value());
            poUpdateInfoBean.setLoginId(payOrderData.getAddLoginId());
            payOrderDao.updatePayOrders(poUpdateInfoBean);
            return true;
        }

        return false;
    }

    private boolean allowDropPayOrder(PayOrderData payOrderData) {
        if (payOrderData.getStatus() == PayOrderStatus.INIT.value()
                || payOrderData.getStatus() == PayOrderStatus.SUSPEND.value()) {
            return true;
        }
        if (payOrderData.getStatus() == PayOrderStatus.EXPORT_PAYING.value()) {
            String allowStr = LionConfigUtils.getProperty("ba-finance-exchange-biz.allowDropPaying", "false");
            return Boolean.valueOf(allowStr);
        }

        return false;
    }

    @Override
    public int changeCustomer(int oldCustomerId, int newCustomerId) {
        return payOrderDao.updateCustomerId(oldCustomerId, newCustomerId);
    }

    @Override
    public PayOrderData loadPayOrderDataByPaySequence(String sequence) {
        return payOrderDao.loadPayOrderByPaySequence(sequence);
    }

    @Override
    public PayOrderData loadPayOrderDataByPOID(int poId) {
        return payOrderDao.loadPayOrderByPayPOID(poId);
    }

    @Log(logBefore = true, logAfter = true)
    @Override
    public int updatePayOrderStatus(int poId, int preStatus, int postStatus, String message) {
        try {
            if (!StringUtils.isEmpty(message)) {
                PayOrderData data = payOrderDao.loadPayOrderByPayPOID(poId);
                if (data != null) {
                    if (!StringUtils.isEmpty(data.getMemo())) {
                        message += "|" + data.getMemo();
                    }
                }
            }
            return payOrderDao.updatePayOrderStatus(poId, preStatus, postStatus, message);
        } catch (Exception e) {
            MONITOR_LOGGER.error(String.format("severity=[1], PayOrderService.updatePayOrderStatus fail!, payCode=[%s]&status=[%d]&message=[%s]", poId, postStatus, message), e);
            return 0;
        }
    }

    @Log(logBefore = true, logAfter = true)
    @Override
    public int batchUpdatePayOrderStatus(List<Integer> poIds, List<Integer> preStatusList, int postStatus, int loginId) {
        try {
            int affectedRows =  payOrderDao.updatePayOrderListStatus(poIds, preStatusList, postStatus, loginId);
            return affectedRows;
        } catch (Exception e) {
            MONITOR_LOGGER.error(String.format("severity=[1] PayOrderService.batchUpdatePayOrderStatus error! poIds=%s", poIds), e);
            return -1;
        }
    }

    @Log(logBefore = true, logAfter = true)
    @Override
    public List<PayOrderData> findPayOrderByIdList(List<Integer> poIds) {
        List<PayOrderData> dataList = payOrderDao.findPayOrderListByPoIdList(poIds);
        if (dataList == null) {
            return Collections.emptyList();
        }
        return dataList;
    }

    private PayOrderBankInfoDTO buildPayOrderBankInfoDTO(PayOrderData poData) {
        try {
            PayOrderBankInfoDTO bankInfoDTO = ConvertUtils.copy(poData, PayOrderBankInfoDTO.class);
            return bankInfoDTO;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public PageModel paginatePayOrderListByStatus(int status, int page, int max) {
        return payOrderDao.paginatePayOrderListByStatus(status, page, max);
    }

    @Log(logBefore = true, logAfter = true)
    @Override
    public int markPayOrderInvalid(List<Integer> poIdList, int loginId) {
        try {
            if(CollectionUtils.isEmpty(poIdList)){
                return 0;
            }
            int successCount = 0;
            List<PayOrderData> payOrderDataList = new ArrayList<PayOrderData>();
            for (int poId : poIdList) {
                PayOrderData payOrderData = payOrderDao.loadPayOrderByPayPOID(poId);
                int affectedRecords = payOrderDao.updatePayOrderStatus(poId, PayOrderStatus.PAY_FAILED.value(), PayOrderStatus.ACCOUNT_INVALID.value(), payOrderData.getMemo());
                if (affectedRecords == 1) {
                    successCount++;
                    payOrderDataList.add(payOrderData);
                }
            }
            notifyPayResult(payOrderDataList, PayResultStatus.ACCOUNT_INVALID, loginId);
            return successCount;
        }catch (Exception e){
            MONITOR_LOGGER.error(String.format("PayOrderServiceObject.markPayOrderInvalid fail!, poIdList=[%s]&loginId=[%d]", ListUtils.listToString(poIdList, ","), loginId), e);
            return -1;
        }
    }

    @Log(logBefore = true, logAfter = true)
    @Override
    public int updatePayCode(int poId, String payCode) {
        try {
            return payOrderDao.updatePayCode(poId, payCode);
        } catch (Exception e) {
            MONITOR_LOGGER.error(String.format("PayOrderServiceObject.updatePayCode fail!, poId=[%d]&payCode=[%s]", poId, payCode), e);
            return -1;
        }
    }

    @Log(logBefore = true, logAfter = true)
    @Override
    public Map<String,PayOrderData> findPayOrderByPayCodeList(List<String> payCodeList){
        Map<String,PayOrderData> payOrderMap=new HashMap<String, PayOrderData>();
        List<PayOrderData> dataList = payOrderDao.findPayOrderByPayCodeList(payCodeList);
        if (CollectionUtils.isEmpty(dataList)) {
            return payOrderMap;
        }
        for (PayOrderData data:dataList){
            if (!payOrderMap.containsKey(data.getPayCode())){
                payOrderMap.put(data.getPayCode(),data);
            }
        }
        return payOrderMap;
    }

    public void setPayOrderDao(PayOrderDao payOrderDao) {
        this.payOrderDao = payOrderDao;
    }

    public void setPayOrderResultNotify(PayOrderResultNotify payOrderResultNotify) {
        this.payOrderResultNotify = payOrderResultNotify;
    }
}
