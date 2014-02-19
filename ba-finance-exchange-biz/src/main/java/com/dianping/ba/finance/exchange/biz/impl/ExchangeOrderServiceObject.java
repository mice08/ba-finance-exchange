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
import com.dianping.ba.finance.exchange.biz.dao.ExchangeOrderDao;
import com.dianping.ba.finance.exchange.biz.producer.ExchangeOrderStatusChangeNotify;
import com.dianping.ba.finance.exchange.biz.utils.ConvertUtils;
import com.dianping.ba.finance.exchange.biz.utils.JsonUtils;
import com.dianping.ba.finance.exchange.biz.utils.LogUtils;
import com.dianping.core.type.PageModel;
import org.apache.log4j.Level;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    public RefundResultDTO refundExchangeOrder(List<RefundDTO> refundDTOList, int loginId) {
        long startTime = System.currentTimeMillis();
        RefundResultDTO refundResultDTO = new RefundResultDTO();
        String processRefundId = "";
        try {
            for (RefundDTO data : refundDTOList) {
                processRefundId = data.getRefundId();
                boolean success = updateExchangeOrderToRefund(data, loginId);
                if (success) {
                    refundResultDTO.addSuccess(data.getRefundId());
                } else {
                    refundResultDTO.addFail(data.getRefundId());
                }
            }
        } catch (Exception e) {
            refundResultDTO.addFail(processRefundId);
        }
        if (refundResultDTO.hasFailResult()) {
            LogUtils.log(monitorLogger, startTime, "updateExchangeOrderToRefund", Level.ERROR, "Fail Refund RefundIds:" + refundResultDTO.failListToString());
        }
        return findExchangeOrderTotalAmountByRefundId(refundResultDTO);
    }

    private RefundResultDTO findExchangeOrderTotalAmountByRefundId(RefundResultDTO refundResultDTO) {
        if(!CollectionUtils.isEmpty(refundResultDTO.getSuccessList())) {
            refundResultDTO.setSucceedTotalAmount(exchangeOrderDao.findExchangeOrderTotalAmountByBizCode(refundResultDTO.getSuccessList()));
        }
        if(!CollectionUtils.isEmpty(refundResultDTO.getFailList())){
            refundResultDTO.setFailedTotalAmount(exchangeOrderDao.findExchangeOrderTotalAmountByBizCode(refundResultDTO.getFailList()));
        }
        return refundResultDTO;
    }

    private boolean updateExchangeOrderToRefund(RefundDTO refundDTO, int loginId) {
        int preStatus = ExchangeOrderStatus.SUCCESS.value();
        int setStatus = ExchangeOrderStatus.FAIL.value();
        int affectedRows = exchangeOrderDao.updateExchangeOrderToRefund(refundDTO, preStatus, setStatus, loginId);
        if (affectedRows <= 0) {
            return false;
        }
        //TODO 发送mq消息更新付款计划和结算单，插入流水
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
