package com.dianping.ba.finance.exchange.api.dtos;

import com.dianping.ba.finance.exchange.api.enums.BusinessTypeEnum;
import com.dianping.ba.finance.exchange.api.enums.FlowTypeEnum;
import com.dianping.ba.finance.exchange.api.enums.SourceTypeEnum;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: bingqiu.yuan
 * Date: 13-12-13
 * Time: 下午2:36
 * To change this template use File | Settings | File Templates.
 */
public class ShopFundAccountFlowDTO implements Serializable {
    private static final long serialVersionUID = 1L;

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
    private BusinessTypeEnum businessType;
    /**
     * 金额（都是正数）
     */
    private BigDecimal flowAmount;
    /**
     * 1:正向 2:负向
     */
    private FlowTypeEnum flowType;
    /**
     * 源头类型：1.应付2.应收3.交易指令
     */
    private SourceTypeEnum sourceType;

    /**
     * 银行账号
     */
    private String bankAccountNo;

    /**
     * 客户开户名
     */
    private String bankAccountName;

    /**
     * 开户行名称
     */
    private String bankName;

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

    public BusinessTypeEnum getBusinessType() {
        return businessType;
    }

    public void setBusinessType(BusinessTypeEnum businessType) {
        this.businessType = businessType;
    }

    public BigDecimal getFlowAmount() {
        return flowAmount;
    }

    public void setFlowAmount(BigDecimal flowAmount) {
        this.flowAmount = flowAmount;
    }

    public FlowTypeEnum getFlowType() {
        return flowType;
    }

    public void setFlowType(FlowTypeEnum flowType) {
        this.flowType = flowType;
    }

    public SourceTypeEnum getSourceType() {
        return sourceType;
    }

    public void setSourceType(SourceTypeEnum sourceType) {
        this.sourceType = sourceType;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
