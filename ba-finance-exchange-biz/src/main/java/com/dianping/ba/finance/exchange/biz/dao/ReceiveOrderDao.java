package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.api.beans.ReceiveOrderSearchBean;
import com.dianping.ba.finance.exchange.api.beans.ReceiveOrderUpdateBean;
import com.dianping.ba.finance.exchange.api.datas.ReceiveCalResultData;
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;
import com.dianping.core.type.PageModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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

    /**
     * 获取所有未匹配、未确认的收款单
     * @param status
     * @return
     */
    @DAOAction(action = DAOActionType.QUERY)
    List<ReceiveOrderData> findUnmatchAndUnconfirmedReceiveOrder(@DAOParam("status") int status);

    /**
     * 根据时间查询数据
     * @param startTime
     * @param endTime
     * @return
     */
    @DAOAction(action = DAOActionType.QUERY)
    List<ReceiveOrderData> findReceiveOrderDataByTime(@DAOParam("startTime") Date startTime,
                                                      @DAOParam("endTime") Date endTime,
                                                      @DAOParam("businessType") int businessType,
                                                      @DAOParam("status") int status);


    /**
     * 计算并获取收款凭证
     * @param receiveOrderSearchBean
     * @return
     */
    @DAOAction(action = DAOActionType.QUERY)
    List<ReceiveCalResultData> findCalculatedReceiveResult(@DAOParam("receiveOrderSearchBean") ReceiveOrderSearchBean receiveOrderSearchBean,
                                                           @DAOParam("voucherDate")String voucherDate);

    /**
     * 根据条件查询收款记录
     * @param receiveOrderSearchBean
     * @return
     */
    @DAOAction(action = DAOActionType.QUERY)
    List<ReceiveOrderData> findReceiveOrderBySearchBean(@DAOParam("receiveOrderSearchBean") ReceiveOrderSearchBean receiveOrderSearchBean);
}
