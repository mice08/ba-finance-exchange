package com.dianping.ba.finance.exchange.api.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: sally.zhu
 * Date: 13-12-12
 * Time: 下午6:01
 * To change this template use File | Settings | File Templates.
 */
public class ExchangeOrderDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private int exchangeOrderId;
    /**
     * 1:付款 2:收款
     */
    private int orderType;
    /**
     * 交易状态；1初始；2支付中; 3成功；4失败
     */
    private int status;
    /**
     * 对应的源资金账户流水ID
     */
    private int relevantFundAccountFlowId;
    /**
     * 对应的资金账户ID
     */
    private int relevantFundAccountId;
    /**
     * 交易金额
     */
    private BigDecimal orderAmount;

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public int getExchangeOrderId() {
        return exchangeOrderId;
    }

    public void setExchangeOrderId(int exchangeOrderId) {
        this.exchangeOrderId = exchangeOrderId;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRelevantFundAccountFlowId() {
        return relevantFundAccountFlowId;
    }

    public void setRelevantFundAccountFlowId(int relevantFundAccountFlowId) {
        this.relevantFundAccountFlowId = relevantFundAccountFlowId;
    }

    public int getRelevantFundAccountId() {
        return relevantFundAccountId;
    }

    public void setRelevantFundAccountId(int relevantFundAccountId) {
        this.relevantFundAccountId = relevantFundAccountId;
    }


}
