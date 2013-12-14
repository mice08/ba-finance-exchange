//package com.dianping.ba.finance.exchange.api.beans;
//
//import java.io.Serializable;
//import java.math.BigDecimal;
//import java.util.Date;
//
///**
// * Created with IntelliJ IDEA.
// * User: bingqiu.yuan
// * Date: 13-12-12
// * Time: 下午6:01
// * To change this template use File | Settings | File Templates.
// */
//public class ExchangeOrderBean implements Serializable {
//    private static final long serialVersionUID = 1L;
//    /**
//     * 主键
//     */
//    private int exchangeOrderId;
//    /**
//     * 金额（都是正数）
//     */
//    private BigDecimal orderAmount;
//    /**
//     * 1:付款 2:收款
//     */
//    private int orderType;
//
//    /**
//     * 银行账号
//     */
//    private String bankAccountNo;
//
//    /**
//     * 客户开户名
//     */
//    private String bankAccountName;
//
//    /**
//     * 开户行名称
//     */
//    private String bankName;
//
//    /**
//     * 备注
//     */
//    private String memo;
//
//    public int getExchangeOrderId() {
//        return exchangeOrderId;
//    }
//
//    public void setExchangeOrderId(int exchangeOrderId) {
//        this.exchangeOrderId = exchangeOrderId;
//    }
//
//    public BigDecimal getOrderAmount() {
//        return orderAmount;
//    }
//
//    public void setOrderAmount(BigDecimal orderAmount) {
//        this.orderAmount = orderAmount;
//    }
//
//    public int getOrderType() {
//        return orderType;
//    }
//
//    public void setOrderType(int orderType) {
//        this.orderType = orderType;
//    }
//
//    public String getBankAccountNo() {
//        return bankAccountNo;
//    }
//
//    public void setBankAccountNo(String bankAccountNo) {
//        this.bankAccountNo = bankAccountNo;
//    }
//
//    public String getBankAccountName() {
//        return bankAccountName;
//    }
//
//    public void setBankAccountName(String bankAccountName) {
//        this.bankAccountName = bankAccountName;
//    }
//
//    public String getBankName() {
//        return bankName;
//    }
//
//    public void setBankName(String bankName) {
//        this.bankName = bankName;
//    }
//
//    public String getMemo() {
//        return memo;
//    }
//
//    public void setMemo(String memo) {
//        this.memo = memo;
//    }
//}
