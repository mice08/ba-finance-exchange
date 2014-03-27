package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.ExchangeOrderService;
import com.dianping.ba.finance.exchange.api.beans.ExchangeOrderSearchBean;
import com.dianping.ba.finance.exchange.api.datas.*;
import com.dianping.ba.finance.exchange.api.dtos.*;
import com.dianping.ba.finance.exchange.api.enums.ExchangeOrderStatus;
import com.dianping.ba.finance.exchange.api.enums.FlowType;
import com.dianping.ba.finance.exchange.api.enums.RefundFailedReason;
import com.dianping.ba.finance.exchange.api.enums.SourceType;
import com.dianping.ba.finance.exchange.biz.dao.ExchangeOrderDao;
import com.dianping.ba.finance.exchange.biz.producer.ExchangeOrderStatusChangeNotify;
import com.dianping.ba.finance.exchange.biz.utils.*;
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
    public int updateExchangeOrderToSuccess(List<Integer> orderIds, int loginId) {
        long startTime = System.currentTimeMillis();
        Date orderDate = getCurrentTime();
        try {
            int affectedRows = exchangeOrderDao.updateExchangeOrderDataByOrderIdList(orderIds, orderDate, ExchangeOrderStatus.PENDING.value(), ExchangeOrderStatus.SUCCESS.value(), loginId);
            if (affectedRows != orderIds.size()) {
                LogUtils.log(monitorLogger, startTime, "updateExchangeOrderToSuccess.updateExchangeOrderDataByOrderIdList", Level.ERROR, "orderIds:" + ListUtils.convertIntegerListToString(orderIds) + ",affectedRows not equal orderIds size,affectedRows:" + affectedRows);
            }
            List<ExchangeOrderData> exchangeOrderDataList = exchangeOrderDao.findExchangeOrderListByOrderIdList(orderIds);
            for(ExchangeOrderData exchangeOrderData: exchangeOrderDataList){
                if(exchangeOrderData.getStatus() == ExchangeOrderStatus.SUCCESS.value()){
                    final ExchangeOrderDTO exchangeOrderDTO = ConvertUtils.copy(exchangeOrderData, ExchangeOrderDTO.class);
                    exchangeOrderDTO.setLoginId(loginId);
                    exchangeOrderStatusChangeNotify.exchangeOrderStatusChangeNotify(exchangeOrderDTO);
                }
            }
            return affectedRows;
        } catch (Exception ex) {
            LogUtils.log(monitorLogger, startTime, "updateExchangeOrderToSuccess", Level.ERROR, "orderIds:" + ListUtils.convertIntegerListToString(orderIds), ex);
            return 0;
        }
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

    @Override
    public RefundResultDTO refundExchangeOrder(List<RefundDTO> refundDTOList, int loginId) throws Exception {
        long startTime = System.currentTimeMillis();
        if (CollectionUtils.isEmpty(refundDTOList)) {
            return new RefundResultDTO();
        }
        List<String> bizCodeList = new ArrayList<String>();
        Map<String,String> refundDTOMap = new HashMap<String, String>();
        for (RefundDTO item : refundDTOList) {
            bizCodeList.add(item.getRefundId());
            refundDTOMap.put(item.getRefundId(),item.getRefundReason());
        }
        List<ExchangeOrderData> exchangeOrderDataList = findExchangeOrderDataByRefundId(bizCodeList);
        RefundResultDTO refundResultDTO = checkExchangeOrderStatus(bizCodeList,exchangeOrderDataList);

        if (!refundResultDTO.getRefundFailedMap().isEmpty()) {
            return refundResultDTO;
        }

        updateExchangeOrderToRefund(refundDTOList, loginId);

        for(ExchangeOrderData data: exchangeOrderDataList){
            data.setStatus(ExchangeOrderStatus.FAIL.value());
            String memo = refundDTOMap.get(data.getBizCode());
            data.setMemo(memo);
        }
        try {
            sendMessage(loginId, exchangeOrderDataList);
        } catch (Exception e) {
            LogUtils.log(monitorLogger, startTime, "refundExchangeOrder", Level.ERROR, "RefundIDs:" + bizCodeList.toString(), e);
        }
        return refundResultDTO;
    }

    @Override
    public EOAndFlowIdSummaryDTO loadExchangeOrderDataAndPositiveFlow(int exchangeOrderId) throws Exception {
        EOAndFlowIdSummaryData summaryData =  exchangeOrderDao.loadExchangeOrderAndPositiveFlow(exchangeOrderId,
                FlowType.IN.value(),
                SourceType.PaymentPlan.value());
        EOAndFlowIdSummaryDTO summaryDTO = ConvertUtils.copy(summaryData, EOAndFlowIdSummaryDTO.class);
        return summaryDTO;
    }

    @Override
    public List<ExchangeOrderSummaryDTO> getExchangeOrderSummaryInfo(List<Integer> flowIdList) throws Exception {
        List<ExchangeOrderSummaryDTO> summaryDTOList = new ArrayList<ExchangeOrderSummaryDTO>();
        List<ExchangeOrderSummaryData> summaryDataList = exchangeOrderDao.findExchangeOrderSummaryDataListByFlowIdList(flowIdList);
        if(summaryDataList == null){
            return summaryDTOList;
        }
        for(ExchangeOrderSummaryData data: summaryDataList) {
            summaryDTOList.add(ConvertUtils.copy(data, ExchangeOrderSummaryDTO.class));
        }
        return summaryDTOList;
    }

    private void sendMessage(int loginId, List<ExchangeOrderData> exchangeOrderDataList) throws Exception {
        for (ExchangeOrderData data : exchangeOrderDataList) {
            ExchangeOrderDTO exchangeOrderDTO = ConvertUtils.copy(data, ExchangeOrderDTO.class);
            exchangeOrderDTO.setLoginId(loginId);
            exchangeOrderStatusChangeNotify.exchangeOrderStatusChangeNotify(exchangeOrderDTO);
        }
    }

    private RefundResultDTO checkExchangeOrderStatus(List<String> bizCodeList,List<ExchangeOrderData> exchangeOrderDataList) {
        RefundResultDTO refundResultDTO = new RefundResultDTO();

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
            for (ExchangeOrderData data : exchangeOrderDataList) {
                if (data.getStatus() == ExchangeOrderStatus.SUCCESS.value()) {
                    refundResultDTO.setRefundTotalAmount(refundResultDTO.getRefundTotalAmount().add(data.getOrderAmount()));
                } else {
                     refundFailedMap.put(data.getBizCode(),RefundFailedReason.STATUS_ERROR);
                }
            }
        }
        refundResultDTO.setRefundFailedMap(refundFailedMap);
        return refundResultDTO;
    }

    private List<ExchangeOrderData> findExchangeOrderDataByRefundId(List<String> bizCodeList) {
        return exchangeOrderDao.findExchangeOrderByBizCode(bizCodeList);
    }

    @Override
    public List<EOMonitorDTO> findEOMonitorDataByFlowIdList(List<Integer> flowIdList) throws Exception {
        List<EOMonitorData> eoMonitorDataList = exchangeOrderDao.findEOMonitorDataByFlowIdList(flowIdList);
        List<EOMonitorDTO> eoMonitorDTOList = new ArrayList<EOMonitorDTO>(eoMonitorDataList.size());
        for (EOMonitorData data : eoMonitorDataList) {
            EOMonitorDTO dto = ConvertUtils.copy(data, EOMonitorDTO.class);
            eoMonitorDTOList.add(dto);
        }
        return eoMonitorDTOList;
    }

    public boolean updateExchangeOrderToRefund(List<RefundDTO> refundDTOList, int loginId) throws Exception {
        int preStatus = ExchangeOrderStatus.SUCCESS.value();
        int setStatus = ExchangeOrderStatus.FAIL.value();
        Date todayDate = new Date();

        for (RefundDTO item : refundDTOList) {
            int affectedRows = exchangeOrderDao.updateExchangeOrderToRefund(item, preStatus, setStatus,todayDate, loginId);
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
