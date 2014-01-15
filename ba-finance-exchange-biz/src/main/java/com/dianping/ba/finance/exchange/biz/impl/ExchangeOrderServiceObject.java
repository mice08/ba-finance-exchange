package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.ExchangeOrderService;
import com.dianping.ba.finance.exchange.api.beans.ExchangeOrderSearchBean;
import com.dianping.ba.finance.exchange.api.beans.GenericResult;
import com.dianping.ba.finance.exchange.api.datas.ExchangeOrderData;
import com.dianping.ba.finance.exchange.api.dtos.ExchangeOrderDTO;
import com.dianping.ba.finance.exchange.api.enums.ExchangeOrderStatus;
import com.dianping.ba.finance.exchange.biz.convert.ExchangeOrderConvert;
import com.dianping.ba.finance.exchange.biz.dao.ExchangeOrderDao;
import com.dianping.ba.finance.exchange.biz.producer.ExchangeOrderStatusChangeNotify;
import com.dianping.ba.finance.exchange.biz.utils.BizUtils;
import com.dianping.ba.finance.exchange.biz.utils.JsonUtils;
import com.dianping.core.type.PageModel;
import org.apache.log4j.Level;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: 遐
 * Date: 13-12-13
 * Time: 上午10:28
 */

public class ExchangeOrderServiceObject implements ExchangeOrderService {

    private ExchangeOrderDao exchangeOrderDao;
    private ExchangeOrderStatusChangeNotify exchangeOrderStatusChangeNotify;

    private static final AvatarLogger monitorLogger = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.service.monitor.ExchangeOrderServiceObject");

    @Override
    public int insertExchangeOrder(ExchangeOrderData exchangeOrderData) {
        //TODO: 增加唯一性校验
        return exchangeOrderDao.insertExchangeOrder(exchangeOrderData);
    }

    @Override
    public GenericResult<Integer> updateExchangeOrderToSuccess(List<Integer> orderIds) {
        Long startTime = Calendar.getInstance().getTimeInMillis();

        GenericResult genericResult = new GenericResult<Integer>();
        int processExchangeOrderId = 0;
        try {
            for (int orderId : orderIds) {
                processExchangeOrderId = orderId;
                boolean success = updateExchangeOrderToSuccess(orderId);
                if (success) {
                    genericResult.addSuccess(orderId);
                } else {
                    genericResult.addFail(orderId);
                }
            }
        } catch (Exception e) {
            genericResult.addFail(processExchangeOrderId);
        }
        if (genericResult.hasFailResult()) {
            BizUtils.log(monitorLogger, startTime, "updateExchangeOrderToSuccess error", "error", "Failed exchange order ids: " + genericResult.failListToString(), new Exception("updateExchangeOrderToSuccess error"));
        }
        return genericResult;
    }

    @Override
    public PageModel paginateExchangeOrderList(ExchangeOrderSearchBean searchBean, int page, int pageSize) {
        long startTime = System.currentTimeMillis();
        try {
            return exchangeOrderDao.paginateExchangeOrderList(searchBean, page, pageSize);
        } catch (Exception e) {
            try {
                BizUtils.log(monitorLogger, startTime, "paginateExchangeOrderList", Level.ERROR, JsonUtils.toStr(searchBean), e);
            } catch (Exception ex) {
                //ignore
            }
            return new PageModel();
        }
    }

    @Override
    public BigDecimal findExchangeOrderTotalAmount(ExchangeOrderSearchBean searchBean) {
        long startTime = System.currentTimeMillis();
        try {
            return exchangeOrderDao.findExchangeOrderTotalAmount(searchBean);
        } catch (Exception e) {
            try {
                BizUtils.log(monitorLogger, startTime, "findExchangeOrderTotalAmount", Level.ERROR, JsonUtils.toStr(searchBean), e);
            } catch (Exception ex) {
                //ignore
            }
            return new BigDecimal(0);
        }
    }

    @Override
    public int updateExchangeOrderToPending(List<Integer> orderIds){
        long startTime = System.currentTimeMillis();
        try{
            ExchangeOrderStatus whereStatus=ExchangeOrderStatus.INIT;
            ExchangeOrderStatus setStatus=ExchangeOrderStatus.PENDING;
            return exchangeOrderDao.updateExchangeOrderToPending(orderIds,whereStatus.value(),setStatus.value());
        }catch(Exception e){
            BizUtils.log(monitorLogger,startTime,"updateExchangeOrderToPending", Level.ERROR, BizUtils.createLogParams(orderIds),e);
        }
        return -1;
    }
    private boolean updateExchangeOrderToSuccess(int orderId) {
        if (orderId <= 0) {
            return false;
        }
        Date orderDate = getCurrentTime();
        int affectedRows = exchangeOrderDao.updateExchangeOrderData(orderId, orderDate, ExchangeOrderStatus.SUCCESS.getExchangeOrderStatus());
        if (affectedRows > 0) {
            ExchangeOrderData exchangeOrderData = exchangeOrderDao.loadExchangeOrderByOrderId(orderId);
            ExchangeOrderDTO exchangeOrderDTO = ExchangeOrderConvert.buildExchangeOrderDTO(exchangeOrderData);
            exchangeOrderStatusChangeNotify.exchangeOrderStatusChangeNotify(exchangeOrderDTO);
        } else {
            return false;
        }
        return true;
    }

    private Date getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public void setExchangeOrderDao(ExchangeOrderDao exchangeOrderDao) {
        this.exchangeOrderDao = exchangeOrderDao;
    }

    public void setExchangeOrderStatusChangeNotify(ExchangeOrderStatusChangeNotify exchangeOrderStatusChangeNotify) {
        this.exchangeOrderStatusChangeNotify = exchangeOrderStatusChangeNotify;
    }
}
