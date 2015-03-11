package com.dianping.ba.finance.exchange.api.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 */
public class AdOnlineTopupDTO implements Serializable {

    private BigDecimal accountAmount;

    private BigDecimal thirdpartyAmount;

    private BigDecimal giftCardAmount;

    private String thirdpartySerialNo;

    private BigDecimal couponAmount;

    private int sourceCode;

    private int productCode;

    private int status;

    private String outBizId;

    private BigDecimal discountAmount;

    private String thirdPaymentAccount;

    private String payplatform;

    private BigDecimal eventAmount;

    private BigDecimal redEnvelopeAmount;

    private String discountDetail;

    private Date actualTime;

    private String userId;

    private int paymentBizType;

    private String paymenthirdPartyOrderIdtBizType;

    private int paymethod;

    private int paychannel;

    private BigDecimal actualAmount;

    private String receiptId;

    public BigDecimal getAccountAmount() {
        return accountAmount;
    }

    public void setAccountAmount(BigDecimal accountAmount) {
        this.accountAmount = accountAmount;
    }

    public BigDecimal getThirdpartyAmount() {
        return thirdpartyAmount;
    }

    public void setThirdpartyAmount(BigDecimal thirdpartyAmount) {
        this.thirdpartyAmount = thirdpartyAmount;
    }

    public BigDecimal getGiftCardAmount() {
        return giftCardAmount;
    }

    public void setGiftCardAmount(BigDecimal giftCardAmount) {
        this.giftCardAmount = giftCardAmount;
    }

    public String getThirdpartySerialNo() {
        return thirdpartySerialNo;
    }

    public void setThirdpartySerialNo(String thirdpartySerialNo) {
        this.thirdpartySerialNo = thirdpartySerialNo;
    }

    public BigDecimal getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
    }

    public int getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(int sourceCode) {
        this.sourceCode = sourceCode;
    }

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOutBizId() {
        return outBizId;
    }

    public void setOutBizId(String outBizId) {
        this.outBizId = outBizId;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getThirdPaymentAccount() {
        return thirdPaymentAccount;
    }

    public void setThirdPaymentAccount(String thirdPaymentAccount) {
        this.thirdPaymentAccount = thirdPaymentAccount;
    }

    public String getPayplatform() {
        return payplatform;
    }

    public void setPayplatform(String payplatform) {
        this.payplatform = payplatform;
    }

    public BigDecimal getEventAmount() {
        return eventAmount;
    }

    public void setEventAmount(BigDecimal eventAmount) {
        this.eventAmount = eventAmount;
    }

    public BigDecimal getRedEnvelopeAmount() {
        return redEnvelopeAmount;
    }

    public void setRedEnvelopeAmount(BigDecimal redEnvelopeAmount) {
        this.redEnvelopeAmount = redEnvelopeAmount;
    }

    public String getDiscountDetail() {
        return discountDetail;
    }

    public void setDiscountDetail(String discountDetail) {
        this.discountDetail = discountDetail;
    }

    public Date getActualTime() {
        return actualTime;
    }

    public void setActualTime(Date actualTime) {
        this.actualTime = actualTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getPaymentBizType() {
        return paymentBizType;
    }

    public void setPaymentBizType(int paymentBizType) {
        this.paymentBizType = paymentBizType;
    }

    public String getPaymenthirdPartyOrderIdtBizType() {
        return paymenthirdPartyOrderIdtBizType;
    }

    public void setPaymenthirdPartyOrderIdtBizType(String paymenthirdPartyOrderIdtBizType) {
        this.paymenthirdPartyOrderIdtBizType = paymenthirdPartyOrderIdtBizType;
    }

    public int getPaymethod() {
        return paymethod;
    }

    public void setPaymethod(int paymethod) {
        this.paymethod = paymethod;
    }

    public int getPaychannel() {
        return paychannel;
    }

    public void setPaychannel(int paychannel) {
        this.paychannel = paychannel;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    @Override
    public String toString() {
        return "AdOnlineTopupDTO{" +
                "accountAmount=" + accountAmount +
                ", thirdpartyAmount=" + thirdpartyAmount +
                ", giftCardAmount=" + giftCardAmount +
                ", thirdpartySerialNo='" + thirdpartySerialNo + '\'' +
                ", couponAmount=" + couponAmount +
                ", sourceCode=" + sourceCode +
                ", productCode=" + productCode +
                ", status=" + status +
                ", outBizId='" + outBizId + '\'' +
                ", discountAmount=" + discountAmount +
                ", thirdPaymentAccount='" + thirdPaymentAccount + '\'' +
                ", payplatform='" + payplatform + '\'' +
                ", eventAmount=" + eventAmount +
                ", redEnvelopeAmount=" + redEnvelopeAmount +
                ", discountDetail='" + discountDetail + '\'' +
                ", actualTime=" + actualTime +
                ", userId='" + userId + '\'' +
                ", paymentBizType=" + paymentBizType +
                ", paymenthirdPartyOrderIdtBizType='" + paymenthirdPartyOrderIdtBizType + '\'' +
                ", paymethod=" + paymethod +
                ", paychannel=" + paychannel +
                ", actualAmount=" + actualAmount +
                ", receiptId='" + receiptId + '\'' +
                '}';
    }
}
