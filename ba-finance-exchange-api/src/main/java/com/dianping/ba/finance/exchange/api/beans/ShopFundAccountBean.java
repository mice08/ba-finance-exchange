package com.dianping.ba.finance.exchange.api.beans;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: bingqiu.yuan
 * Date: 13-12-13
 * Time: 上午11:48
 * To change this template use File | Settings | File Templates.
 */
public class ShopFundAccountBean implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 账户类型：0默认；1团购；2预约预订; 3结婚; 4储值卡
     */
    private int businessType;

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

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

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
}
