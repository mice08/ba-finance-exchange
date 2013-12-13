package com.dianping.ba.finance.exchange.api.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jinyu.yin
 * Date: 13-12-12
 * Time: 下午6:00
 * To change this template use File | Settings | File Templates.
 */
public class ShopFundAccountFlowDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 资金账户流水ID
     */
    private int fundAccountFlowId;
    /**
     * 资金账户号
     */
    private int fundAccountId;
    /**
     * 金额（都是正数）
     */
    private BigDecimal flowAmount;
    /**
     * 1:正向 2:负向
     */
    private int flowType;
    /**
     * 源头类型：1.应付2.应收3.交易指令
     */
    private int sourceType;
    /**
     * 交易指令ID
     */
    private int exchangeOrderId;
    /**
     * 添加时间
     */
    private Date addDate;
    /**
     * 更新时间
     */
    private Date lastUpdateDate;
    /**
     * 备注
     */
    private String memo;

    public int getFundAccountFlowId() {
        return fundAccountFlowId;
    }

    public void setFundAccountFlowId(int fundAccountFlowId) {
        this.fundAccountFlowId = fundAccountFlowId;
    }

    public int getFundAccountId() {
        return fundAccountId;
    }

    public void setFundAccountId(int fundAccountId) {
        this.fundAccountId = fundAccountId;
    }

    public BigDecimal getFlowAmount() {
        return flowAmount;
    }

    public void setFlowAmount(BigDecimal flowAmount) {
        this.flowAmount = flowAmount;
    }

    public int getFlowType() {
        return flowType;
    }

    public void setFlowType(int flowType) {
        this.flowType = flowType;
    }

    public int getSourceType() {
        return sourceType;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }

    public int getExchangeOrderId() {
        return exchangeOrderId;
    }

    public void setExchangeOrderId(int exchangeOrderId) {
        this.exchangeOrderId = exchangeOrderId;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
