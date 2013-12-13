package com.dianping.ba.finance.exchange.api.datas;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jinyu.yin
 * Date: 13-12-12
 * Time: 下午6:00
 * To change this template use File | Settings | File Templates.
 */
public class ShopFundAccountData {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private int fundAccountId;
    /**
     * CustomerGlobalId
     */
    private String customerGlobalId;
    /**
     * CompanyGlobalId
     */
    private String companyGlobalId;
    /**
     * 分店号
     */
    private int shopId;
    /**
     * 账户类型：0默认；1团购；2预约预订; 3结婚; 4储值卡
     */
    private int businessType;
    /**
     * 收入
     */
    private BigDecimal credit;
    /**
     * 支出
     */
    private BigDecimal debit;
    /**
     * 余额
     */
    private BigDecimal balanceTotal;
    /**
     * 余额
     */
    private BigDecimal balanceFrozen;
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

    public int getFundAccountId() {
        return fundAccountId;
    }

    public void setFundAccountId(int fundAccountId) {
        this.fundAccountId = fundAccountId;
    }

    public String getCustomerGlobalId() {
        return customerGlobalId;
    }

    public void setCustomerGlobalId(String customerGlobalId) {
        this.customerGlobalId = customerGlobalId;
    }

    public String getCompanyGlobalId() {
        return companyGlobalId;
    }

    public void setCompanyGlobalId(String companyGlobalId) {
        this.companyGlobalId = companyGlobalId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getBalanceTotal() {
        return balanceTotal;
    }

    public void setBalanceTotal(BigDecimal balanceTotal) {
        this.balanceTotal = balanceTotal;
    }

    public BigDecimal getBalanceFrozen() {
        return balanceFrozen;
    }

    public void setBalanceFrozen(BigDecimal balanceFrozen) {
        this.balanceFrozen = balanceFrozen;
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
