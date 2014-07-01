package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.api.beans.ReceiveOrderSearchBean;
import com.dianping.ba.finance.exchange.api.beans.ReceiveOrderUpdateBean;
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;
import com.dianping.core.type.PageModel;

import java.math.BigDecimal;

/**
 * 收款单Dao
 */
public interface ReceiveOrderDao extends GenericDao {

    /**
     * 添加收款单
     * @param receiveOrderData
     * @return
     */
    @DAOAction(action = DAOActionType.INSERT)
    int insertReceiveOrderData(@DAOParam("receiveOrderData") ReceiveOrderData receiveOrderData);

    /**
     * 查询收款单
     * @param receiveOrderSearchBean
     * @return
     */
    @DAOAction(action = DAOActionType.PAGE)
    PageModel paginateReceiveOrderList(@DAOParam("receiveOrderSearchBean") ReceiveOrderSearchBean receiveOrderSearchBean,
                                       @DAOParam("page") int page,
                                       @DAOParam("max") int max);

    /**
     * 查询满足条件的收款总金额
     * @param receiveOrderSearchBean
     * @return
     */
    @DAOAction(action = DAOActionType.LOAD)
    BigDecimal loadReceiveOrderTotalAmountByCondition(@DAOParam("receiveOrderSearchBean") ReceiveOrderSearchBean receiveOrderSearchBean);

	/**
	 * 根据TradeNo查询收款单
	 * @param tradeNo
	 * @return
	 */
	@DAOAction(action = DAOActionType.LOAD)
	ReceiveOrderData loadReceiveOrderByTradeNo(@DAOParam("tradeNo") String tradeNo);

	/**
	 * 更新收款单冲销Id
	 * @param updateBean
	 * @return
	 */
	@DAOAction(action = DAOActionType.UPDATE)
	int updateReceiveOrderByRoId(@DAOParam("roId") int roId, @DAOParam("updateBean") ReceiveOrderUpdateBean updateBean);

    /**
     * 更新收款单
     * @param receiveOrderData
     * @return
     */
    @DAOAction(action = DAOActionType.UPDATE)
    int updateReceiveOrder(@DAOParam("receiveOrderData") ReceiveOrderData receiveOrderData);

    /**
     * 通过主键获取实体
     * @param roId
     * @return
     */
    @DAOAction(action = DAOActionType.LOAD)
    ReceiveOrderData loadReceiveOrderDataByRoId(@DAOParam("roId") int roId);
}
