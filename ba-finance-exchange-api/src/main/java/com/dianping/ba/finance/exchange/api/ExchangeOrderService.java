package com.dianping.ba.finance.exchange.api;

import com.dianping.ba.finance.exchange.api.beans.ExchangeOrderSearchBean;
import com.dianping.ba.finance.exchange.api.beans.GenericResult;
import com.dianping.ba.finance.exchange.api.datas.ExchangeOrderData;
import com.dianping.ba.finance.exchange.api.datas.ExchangeOrderDisplayData;
import com.dianping.ba.finance.exchange.api.dtos.*;
import com.dianping.core.type.PageModel;

import java.math.BigDecimal;
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
     * @param loginId
     * @return 更新结果集
     */
    int updateExchangeOrderToSuccess(List<Integer> orderIds, int loginId);

    /**
     * 分页获取支付订单
     *
     * @param searchBean 查询条件
     * @param page       第几页
     * @param pageSize   分页大小
     * @return
     */
    PageModel paginateExchangeOrderList(ExchangeOrderSearchBean searchBean, int page, int pageSize);

    /**
     * 批量更新支付订单到处理中
     *
     * @param orderIds
     * @param loginId
     * @return
     */
    int updateExchangeOrderToPending(List<Integer> orderIds, int loginId);

    /**
     * 根据查询条件获取交易指令集总金额
     *
     * @param searchBean
     * @return
     */
    BigDecimal findExchangeOrderTotalAmount(ExchangeOrderSearchBean searchBean);

    /**
     * 根据查询条件获取交易指令集详细数据
     *
     * @param searchBean
     * @return
     */
    List<ExchangeOrderDisplayData> findExchangeOrderDataList(ExchangeOrderSearchBean searchBean);

    /**
     * 根据查询条件获取交易指令集主键
     *
     * @param searchBean
     * @return
     */
    List<Integer> findExchangeOrderIdList(ExchangeOrderSearchBean searchBean);

    /**
     * 退票更新状态
     *
     * @param refundDTOList
     * @param loginId
     * @return
     */
    RefundResultDTO refundExchangeOrder(List<RefundDTO> refundDTOList, int loginId) throws Exception;

    /**
     * 根据exchangeOrderId获取ExchangeOrderData及其关联的正向FlowId
     * @param exchangeOrderId
     * @return
     */
    EOAndFlowIdSummaryDTO loadExchangeOrderDataAndPositiveFlow(int exchangeOrderId) throws Exception;

    /**
     * 根绝资金账户流水号集获取付款单的概要信息集
     *
     * @param flowIdList
     * @return
     */
    List<ExchangeOrderSummaryDTO> getExchangeOrderSummaryInfo(List<Integer> flowIdList) throws Exception;

    /**
     * 根据flowId获取EO
     * @param flowIdList
     * @return
     * @throws Exception
     */
    List<EOMonitorDTO> findEOMonitorDataByFlowIdList(List<Integer> flowIdList) ;

}
