package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.ExchangeOrderService;
import com.dianping.ba.finance.exchange.api.beans.ExchangeOrderSearchBean;
import com.dianping.ba.finance.exchange.api.beans.GenericResult;
import com.dianping.ba.finance.exchange.api.datas.ExchangeOrderData;
import com.dianping.ba.finance.exchange.api.datas.ExchangeOrderDisplayData;
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
        Long startTime = System.currentTimeMillis();
        GenericResult result = new GenericResult<Integer>();
        int processExchangeOrderId = 0;
        try {
            for (int orderId : orderIds) {
                processExchangeOrderId = orderId;
                boolean success = updateExchangeOrderToSuccess(orderId);
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
            BizUtils.log(monitorLogger, startTime, "updateExchangeOrderToSuccess", Level.ERROR, "Failed exchange order ids: " + result.failListToString());
        }
        return result;
    }

    @Override
    public PageModel paginateExchangeOrderList(ExchangeOrderSearchBean searchBean, int page, int pageSize) {
        long startTime = System.currentTimeMillis();
        try {
            return exchangeOrderDao.paginateExchangeOrderList(searchBean, page, pageSize);
        } catch (Exception e) {
            String message = "searchBean [exchangeOrderId: " + searchBean.getExchangeOrderId() + ", " +
                                "beginDate: " + searchBean.getBeginDate() + ", " +
                                "endDate: " + searchBean.getEndDate() + ", " +
                                "status: " + searchBean.getStatus() + "]";
            BizUtils.log(monitorLogger, startTime, "paginateExchangeOrderList", Level.ERROR, message, e);
            return new PageModel();
        }
    }

    @Override
    public BigDecimal findExchangeOrderTotalAmount(ExchangeOrderSearchBean searchBean) {
        long startTime = System.currentTimeMillis();
        try {
            return exchangeOrderDao.findExchangeOrderTotalAmount(searchBean);
        } catch (Exception e) {
            String message = "searchBean [exchangeOrderId: " + searchBean.getExchangeOrderId() + ", " +
                    "beginDate: " + searchBean.getBeginDate() + ", " +
                    "endDate: " + searchBean.getEndDate() + ", " +
                    "status: " + searchBean.getStatus() + "]";
            BizUtils.log(monitorLogger, startTime, "findExchangeOrderTotalAmount", Level.ERROR, message, e);
            return new BigDecimal(0);
        }
    }

    @Override
    public List<ExchangeOrderDisplayData> findExchangeOrderDataList(ExchangeOrderSearchBean searchBean) {
        return exchangeOrderDao.findExchangeOrderList(searchBean);
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
        int affectedRows = exchangeOrderDao.updateExchangeOrderData(orderId, orderDate, ExchangeOrderStatus.PENDING.value(),ExchangeOrderStatus.SUCCESS.value());
        if(affectedRows <= 0){
            return false;
        }
        ExchangeOrderData exchangeOrderData = exchangeOrderDao.loadExchangeOrderByOrderId(orderId);
        ExchangeOrderDTO exchangeOrderDTO = ExchangeOrderConvert.buildExchangeOrderDTO(exchangeOrderData);
        exchangeOrderStatusChangeNotify.exchangeOrderStatusChangeNotify(exchangeOrderDTO);

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
