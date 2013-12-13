package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.api.datas.ExchangeOrderData;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 遐
 * Date: 13-12-13
 * Time: 上午10:31
 * To change this template use File | Settings | File Templates.
 */
public interface ExchangeOrderDao extends GenericDao {

    @DAOAction(action = DAOActionType.UPDATE)
    boolean updateExchangeOrderData(@DAOParam("exchangeOrderId")int orderId,@DAOParam("orderDate")Date orderDate,@DAOParam("status")int status);

    @DAOAction(action = DAOActionType.LOAD)
    ExchangeOrderData loadExchangeOrderByOrderId(@DAOParam("exchangeOrderId")int orderId);
}
