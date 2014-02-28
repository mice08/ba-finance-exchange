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
    /**
     * 付款单bizCode
     */
    private String bizCode;
    /**
     * 银行账号
     */
    private String bankAccountNo;
    /**
     * 退票原因
     */
    private String memo;
    /**
     * 用户登录ID
     */
    private int loginId;

    public int getLoginId() {
        return loginId;
    }

    public void setLoginId(int loginId) {
        this.loginId = loginId;
    }

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

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
