package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.api.beans.ExchangeOrderSearchBean;
import com.dianping.ba.finance.exchange.api.datas.ExchangeOrderData;
import com.dianping.ba.finance.exchange.api.datas.ExchangeOrderSearchStatistics;
import com.dianping.core.type.PageModel;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: bingqiu.yuan
 * Date: 13-12-13
 * Time: 上午11:39
 * To change this template use File | Settings | File Templates.
 */
public interface ExchangeOrderDao extends GenericDao {

	/**
	 * 更新交易指令状态和时间
	 *
	 * @param orderId   交易指令主键
	 * @param orderDate 确认交易指令时间
	 * @param status    更新状态
	 * @return
	 */
	@DAOAction(action = DAOActionType.UPDATE)
	int updateExchangeOrderData(@DAOParam("exchangeOrderId") int orderId, @DAOParam("orderDate") Date orderDate, @DAOParam("status") int status);


	/**
	 * 获取对应交易指令主键的记录
	 *
	 * @param orderId 交易指令主键
	 * @return
	 */
	@DAOAction(action = DAOActionType.LOAD)
	ExchangeOrderData loadExchangeOrderByOrderId(@DAOParam("exchangeOrderId") int orderId);

	/**
	 * 插入交易指令
	 *
	 * @param exchangeOrderData
	 * @return
	 */
	@DAOAction(action = DAOActionType.INSERT)
	int insertExchangeOrder(@DAOParam("exchangeOrderData") ExchangeOrderData exchangeOrderData);

	/**
	 * 分页查询交易指令
	 *
	 * @param searchBean
	 * @param page
	 * @param max
	 * @return
	 */
	@DAOAction(action = DAOActionType.PAGE)
	PageModel paginateExchangeOrderList(@DAOParam("searchBean") ExchangeOrderSearchBean searchBean,
                                        @DAOParam("page") int page,
                                        @DAOParam("max") int max);

    /**
     * 获取付款总和的结果
     * @param searchBean   查询条件
     * @return
     */
    @DAOAction(action = DAOActionType.LOAD)
    ExchangeOrderSearchStatistics getExchangeOrderStatisticResult(@DAOParam("searchBean") ExchangeOrderSearchBean searchBean);
}
