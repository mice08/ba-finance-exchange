package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.api.datas.ExchangeOrderData;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: bingqiu.yuan
 * Date: 13-12-13
 * Time: 上午11:39
 * To change this template use File | Settings | File Templates.
 */
public interface ExchangeOrderDAO extends GenericDao{

    /**
     * 更新交易指令状态和时间
     *
     * @param orderId 交易指令主键
     * @param orderDate 确认交易指令时间
     * @param status 更新状态
     * @return
     */
    @DAOAction(action = DAOActionType.UPDATE)
    boolean updateExchangeOrderData(@DAOParam("exchangeOrderId")int orderId,@DAOParam("orderDate")Date orderDate,@DAOParam("status")int status);

    /**
     * 获取对应交易指令主键的记录
     *
     * @param orderId 交易指令主键
     * @return
     */
    @DAOAction(action = DAOActionType.LOAD)
    ExchangeOrderData loadExchangeOrderByOrderId(@DAOParam("exchangeOrderId")int orderId);

}