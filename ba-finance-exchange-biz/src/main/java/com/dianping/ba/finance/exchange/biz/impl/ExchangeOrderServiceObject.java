package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.ExchangeOrderService;
import com.dianping.ba.finance.exchange.api.beans.ExchangeOrderSearchBean;
import com.dianping.ba.finance.exchange.api.beans.GenericResult;
import com.dianping.ba.finance.exchange.api.datas.ExchangeOrderData;
import com.dianping.ba.finance.exchange.api.datas.ExchangeOrderDisplayData;
import com.dianping.ba.finance.exchange.api.dtos.ExchangeOrderDTO;
import com.dianping.ba.finance.exchange.api.dtos.RefundDTO;
import com.dianping.ba.finance.exchange.api.dtos.RefundResultDTO;
import com.dianping.ba.finance.exchange.api.enums.ExchangeOrderStatus;
import com.dianping.ba.finance.exchange.api.enums.RefundFailedReason;
import com.dianping.ba.finance.exchange.biz.dao.ExchangeOrderDao;
import com.dianping.ba.finance.exchange.biz.producer.ExchangeOrderStatusChangeNotify;
import com.dianping.ba.finance.exchange.biz.utils.ConvertUtils;
import com.dianping.ba.finance.exchange.biz.utils.JsonUtils;
import com.dianping.ba.finance.exchange.biz.utils.LogUtils;
import com.dianping.ba.finance.exchange.biz.utils.ObjectUtils;
import com.dianping.core.type.PageModel;
import org.apache.log4j.Level;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: 遐
 * Date: 13-12-13
 * Time: 上午10:28
 */

public class ExchangeOrderServiceObject implements ExchangeOrderService {

    private static final AvatarLogger monitorLogger = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.service.monitor.ExchangeOrderServiceObject");
    private ExchangeOrderDao exchangeOrderDao;
    private ExchangeOrderStatusChangeNotify exchangeOrderStatusChangeNotify;

    @Override
    public int insertExchangeOrder(ExchangeOrderData exchangeOrderData) {
        //TODO: 增加唯一性校验
        return exchangeOrderDao.insertExchangeOrder(exchangeOrderData);
    }

    @Override
    public GenericResult<Integer> updateExchangeOrderToSuccess(List<Integer> orderIds, int loginId) {
        Long startTime = System.currentTimeMillis();
        GenericResult result = new GenericResult<Integer>();
        int processExchangeOrderId = 0;
        try {
            for (int orderId : orderIds) {
                processExchangeOrderId = orderId;
                boolean success = updateExchangeOrderToSuccess(orderId, loginId);
                if (success) {
                    result.addSuccess(orderId);
                } else {
                    result.addFail(orderId);
                }
            }
        } catch (Exception e) {
            result.addFail(processExchangeOrderId);
        }
        if (result.hasFailResult()) {
            LogUtils.log(monitorLogger, startTime, "updateExchangeOrderToSuccess", Level.ERROR, "Failed exchange order ids: " + result.failListToString());
        }
        return result;
    }

    @Override
    public PageModel paginateExchangeOrderList(ExchangeOrderSearchBean searchBean, int page, int pageSize) {
        long startTime = System.currentTimeMillis();
        try {
            return exchangeOrderDao.paginateExchangeOrderList(searchBean, page, pageSize);
        } catch (Exception e) {
            try {
                LogUtils.log(monitorLogger, startTime, "paginateExchangeOrderList", Level.ERROR, JsonUtils.toStr(searchBean), e);
                return new PageModel();
            } catch (Exception ex) {
                //ignore
            }
            LogUtils.log(monitorLogger, startTime, "paginateExchangeOrderList", Level.ERROR, ObjectUtils.toString(searchBean), e);
        }
        return new PageModel();
    }

    @Override
    public BigDecimal findExchangeOrderTotalAmount(ExchangeOrderSearchBean searchBean) {
        long startTime = System.currentTimeMillis();
        try {
            return exchangeOrderDao.findExchangeOrderTotalAmount(searchBean);
        } catch (Exception e) {
            try {
                LogUtils.log(monitorLogger, startTime, "findExchangeOrderTotalAmount", Level.ERROR, JsonUtils.toStr(searchBean), e);
                return BigDecimal.ZERO;
            } catch (Exception ex) {
                //ignore
            }
            LogUtils.log(monitorLogger, startTime, "findExchangeOrderTotalAmount", Level.ERROR, ObjectUtils.toString(searchBean), e);
        }
        return BigDecimal.ZERO;
    }

    @Override
    public List<ExchangeOrderDisplayData> findExchangeOrderDataList(ExchangeOrderSearchBean searchBean) {
        return exchangeOrderDao.findExchangeOrderList(searchBean);
    }

    @Override
    public List<Integer> findExchangeOrderIdList(ExchangeOrderSearchBean searchBean) {
        return exchangeOrderDao.findExchangeOrderIdList(searchBean);
    }

    @Override
    public int updateExchangeOrderToPending(List<Integer> orderIds, int loginId) {
        long startTime = System.currentTimeMillis();
        try {
            ExchangeOrderStatus whereStatus = ExchangeOrderStatus.INIT;
            ExchangeOrderStatus setStatus = ExchangeOrderStatus.PENDING;
            return exchangeOrderDao.updateExchangeOrderToPending(orderIds, whereStatus.value(), setStatus.value(), loginId);
        } catch (Exception e) {
            LogUtils.log(monitorLogger, startTime, "updateExchangeOrderToPending", Level.ERROR, LogUtils.createLogParams(orderIds), e);
        }
        return -1;
    }

    private boolean updateExchangeOrderToSuccess(int orderId, int loginId) throws Exception {
        if (orderId <= 0) {
            return false;
        }
        Date orderDate = getCurrentTime();
        int affectedRows = exchangeOrderDao.updateExchangeOrderData(orderId, orderDate, ExchangeOrderStatus.PENDING.value(), ExchangeOrderStatus.SUCCESS.value(), loginId);
        if (affectedRows <= 0) {
            return false;
        }
        ExchangeOrderData exchangeOrderData = exchangeOrderDao.loadExchangeOrderByOrderId(orderId);

        ExchangeOrderDTO exchangeOrderDTO = ConvertUtils.copy(exchangeOrderData, ExchangeOrderDTO.class);
        exchangeOrderDTO.setLoginId(loginId);
        exchangeOrderStatusChangeNotify.exchangeOrderStatusChangeNotify(exchangeOrderDTO);

        return true;
    }

    @Override
    public RefundResultDTO refundExchangeOrder(List<RefundDTO> refundDTOList, int loginId) throws Exception {
        long startTime = System.currentTimeMillis();
        if (CollectionUtils.isEmpty(refundDTOList)) {
            return new RefundResultDTO();
        }
        List<String> bizCodeList = new ArrayList<String>();
        for (RefundDTO item : refundDTOList) {
            bizCodeList.add(item.getRefundId());
        }

        RefundResultDTO refundResultDTO = checkExchangeOrderStatus(bizCodeList);

        if (!refundResultDTO.getRefundFailedMap().isEmpty()) {
            return refundResultDTO;
        }

        updateExchangeOrderToRefund(refundDTOList, loginId);

        try {
            refundResultDTO.setRefundTotalAmount(findExchangeOrderTotalAmountByRefundId(bizCodeList));
            sendMessage(loginId, bizCodeList);
        } catch (Exception e) {
            LogUtils.log(monitorLogger, startTime, "refundExchangeOrder", Level.ERROR, "RefundIDs:"+bizCodeList.toString(), e);
        }
        return refundResultDTO;
    }

    private void sendMessage(int loginId, List<String> bizCodeList) throws Exception {
        List<ExchangeOrderData> exchangeOrderDataList;
        exchangeOrderDataList = findExchangeOrderDataByRefundId(bizCodeList);
        for (ExchangeOrderData data : exchangeOrderDataList) {
            ExchangeOrderDTO exchangeOrderDTO = ConvertUtils.copy(data, ExchangeOrderDTO.class);
            exchangeOrderDTO.setLoginId(loginId);
            exchangeOrderStatusChangeNotify.exchangeOrderStatusChangeNotify(exchangeOrderDTO);
        }
    }

    private RefundResultDTO checkExchangeOrderStatus(List<String> bizCodeList) {
        RefundResultDTO refundResultDTO = new RefundResultDTO();

        List<ExchangeOrderData> exchangeOrderDataList = findExchangeOrderDataByRefundId(bizCodeList);
        Map<String, Integer> bizCodeMap = new HashMap<String, Integer>();
        Map<String, RefundFailedReason> refundFailedMap = new HashMap<String, RefundFailedReason>();
        for (ExchangeOrderData data : exchangeOrderDataList) {
            bizCodeMap.put(data.getBizCode(), data.getStatus());
        }
        if (bizCodeList.size() != exchangeOrderDataList.size()) {
            for (String str : bizCodeList) {
                if (bizCodeMap.containsKey(str)) {
                    continue;
                } else {
                    refundFailedMap.put(str, RefundFailedReason.INFO_EMPTY);
                }
            }
        } else {
            for (Map.Entry<String, Integer> entry : bizCodeMap.entrySet()) {
                int status = entry.getValue();
                if(status !=  ExchangeOrderStatus.SUCCESS.value()){
                    refundFailedMap.put(entry.getKey(),RefundFailedReason.STATUS_ERROR);
                }
            }
        }
        refundResultDTO.setRefundFailedMap(refundFailedMap);
        return refundResultDTO;
    }

    private List<ExchangeOrderData> findExchangeOrderDataByRefundId(List<String> bizCodeList) {
        return exchangeOrderDao.findExchangeOrderByBizCode(bizCodeList);
    }

    private BigDecimal findExchangeOrderTotalAmountByRefundId(List<String> bizCodeList) {
        return exchangeOrderDao.findExchangeOrderTotalAmountByBizCode(bizCodeList);
    }

    public boolean updateExchangeOrderToRefund(List<RefundDTO> refundDTOList, int loginId) throws Exception {
        int preStatus = ExchangeOrderStatus.SUCCESS.value();
        int setStatus = ExchangeOrderStatus.FAIL.value();

        for (RefundDTO item : refundDTOList) {
            int affectedRows = exchangeOrderDao.updateExchangeOrderToRefund(item, preStatus, setStatus, loginId);
            if (affectedRows <= 0) {
                throw new Exception("System is abnormal");
            }
        }
        return true;
    }

    private Date getCurrentTime() {
        return Calendar.getInstance().getTime();
    }

    public void setExchangeOrderDao(ExchangeOrderDao exchangeOrderDao) {
        this.exchangeOrderDao = exchangeOrderDao;
    }

    public void setExchangeOrderStatusChangeNotify(ExchangeOrderStatusChangeNotify exchangeOrderStatusChangeNotify) {
        this.exchangeOrderStatusChangeNotify = exchangeOrderStatusChangeNotify;
    }
}
