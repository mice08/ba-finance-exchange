package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.api.beans.ExchangeOrderSearchBean;
import com.dianping.ba.finance.exchange.api.datas.ExchangeOrderData;
import com.dianping.ba.finance.exchange.api.datas.ExchangeOrderDisplayData;
import com.dianping.ba.finance.exchange.api.datas.ExchangeOrderSummaryData;
import com.dianping.ba.finance.exchange.api.dtos.RefundDTO;
import com.dianping.core.type.PageModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
     * @param orderId    交易指令主键
     * @param orderDate  确认交易指令时间
     * @param preStatus  更新前状态
     * @param postStatus 更新状态
     * @param loginId
     * @return
     */
    @DAOAction(action = DAOActionType.UPDATE)
    int updateExchangeOrderData(@DAOParam("exchangeOrderId") int orderId, @DAOParam("orderDate") Date orderDate, @DAOParam("preStatus") int preStatus, @DAOParam("postStatus") int postStatus, @DAOParam("loginId") int loginId);

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
     * 批量更新交易订单到处理中
     *
     * @param orderIds
     * @param whereStatus where条件
     * @param setStatus   更新条件
     * @param loginId
     * @return
     */
    @DAOAction(action = DAOActionType.UPDATE)
    int updateExchangeOrderToPending(@DAOParam("orderIds") List<Integer> orderIds, @DAOParam("whereStatus") int whereStatus, @DAOParam("setStatus") int setStatus, @DAOParam("loginId") int loginId);

    /**
     * 获取付款单总额
     *
     * @param searchBean
     * @return
     */
    @DAOAction(action = DAOActionType.LOAD)
    BigDecimal findExchangeOrderTotalAmount(@DAOParam("searchBean") ExchangeOrderSearchBean searchBean);

    /**
     * 查询交易指令
     *
     * @param searchBean
     * @return
     */
    @DAOAction(action = DAOActionType.QUERY)
    List<ExchangeOrderDisplayData> findExchangeOrderList(@DAOParam("searchBean") ExchangeOrderSearchBean searchBean);

    /**
     * 查询交易指令主键
     *
     * @param searchBean
     * @return
     */

    @DAOAction(action = DAOActionType.QUERY)
    List<Integer> findExchangeOrderIdList(@DAOParam("searchBean") ExchangeOrderSearchBean searchBean);

    /**
     * 更新交易指令为退票状态
     * @param refundDTO
     * @param preStatus
     * @param setStatus
     * @param loginId
     * @return
     */
    @DAOAction(action = DAOActionType.UPDATE)
    int updateExchangeOrderToRefund(@DAOParam("refundDTO") RefundDTO refundDTO, @DAOParam("preStatus") int preStatus, @DAOParam("setStatus") int setStatus, @DAOParam("loginId") int loginId);

    /**
     * 根据bizCode返回总金额
     * @param bizCodeList
     * @return
     */
    @DAOAction(action = DAOActionType.LOAD)
    BigDecimal findExchangeOrderTotalAmountByBizCode(@DAOParam("bizCodeList") List<String> bizCodeList);

    /**
     * 根据bizCode返回付款单
     * @param bizCodeList
     * @return
     */
    @DAOAction(action = DAOActionType.QUERY)
    List<ExchangeOrderData> findExchangeOrderByBizCode(@DAOParam("bizCodeList")List<String> bizCodeList);

    /**
     * 根据资金账户流水逐渐获取关联付款单概要信息
     *
     * @return
     */
    @DAOAction(action = DAOActionType.LOAD)
    ExchangeOrderSummaryData loadExchangeOrderSummaryDataByShopFundAccountFlowId(@DAOParam("flowId") int flowId);
}
