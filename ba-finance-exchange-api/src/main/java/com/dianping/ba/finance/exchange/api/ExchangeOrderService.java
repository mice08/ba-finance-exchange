package com.dianping.ba.finance.exchange.api;

import com.dianping.ba.finance.exchange.api.beans.ExchangeOrderSearchBean;
import com.dianping.ba.finance.exchange.api.beans.GenericResult;
import com.dianping.ba.finance.exchange.api.datas.ExchangeOrderData;
import com.dianping.core.type.PageModel;

import java.util.Date;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: 遐
 * Date: 13-12-13
 * Time: 上午10:28
 * To change this template use File | Settings | File Templates.
 */
public interface ExchangeOrderService {
	/**
	 * 创建交易订单
	 *
	 * @param exchangeOrderData
	 * @return
	 */
	int insertExchangeOrder(ExchangeOrderData exchangeOrderData);

	/**
	 * 更新交易指令成功
	 *
	 * @param orderIds 交易指令集
	 * @return 更新结果集
	 */
	GenericResult<Integer> updateExchangeOrderToSuccess(List<Integer> orderIds);

	/**
	 * 分页获取支付订单
	 *
	 * @param searchBean   查询条件
	 * @param page         第几页
	 * @param pageSize     分页大小
	 * @return
	 */
	PageModel paginateExchangeOrderList(ExchangeOrderSearchBean searchBean, int page, int pageSize);
}
