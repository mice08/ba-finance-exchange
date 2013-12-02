package com.dianping.ba.finance.fundaccount.api.datas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: huajiao.zou
 * Date: 13-9-9
 * Time: 下午5:33
 * To change this template use File | Settings | File Templates.
 */
public class ShopFundAccountInfoData implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal amount;
    private Date receiveDate;
    private int shopId;
    private String contractNo;
    private int payType;  //收款方式
    private int tradeType;    //交易类型
    private int resourceType;
    private int isPositive;

    public int getPositive() {
        return isPositive;
    }

    public void setPositive(int positive) {
        isPositive = positive;
    }

    public int getResourceType() {
        return resourceType;
    }

    public void setResourceType(int resourceType) {
        this.resourceType = resourceType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getTradeType() {
        return tradeType;
    }

    public void setTradeType(int tradeType) {
        this.tradeType = tradeType;
    }
}
