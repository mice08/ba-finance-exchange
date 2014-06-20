package com.dianping.ba.finance.exchange.api.dtos;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: bingqiu.yuan
 * Date: 14-6-4
 * Time: 上午11:44
 * To change this template use File | Settings | File Templates.
 */
public class PayResultNotifyDTO implements Serializable {

    private static final long serialVersionUID = -1L;

    private String paySequence;
    private int status;
    private int poId;
    private int loginId;
    private BigDecimal paidAmount;
    private String memo;

    public String getPaySequence() {
        return paySequence;
    }

    public void setPaySequence(String paySequence) {
        this.paySequence = paySequence;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPoId() {
        return poId;
    }

    public void setPoId(int poId) {
        this.poId = poId;
    }

    public int getLoginId() {
        return loginId;
    }

    public void setLoginId(int loginId) {
        this.loginId = loginId;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "PayResultNotifyDTO{" +
                "paySequence='" + paySequence + '\'' +
                ", status=" + status +
                ", poId=" + poId +
                ", paidAmount=" + paidAmount +
                ", memo='" + memo + '\'' +
                '}';
    }
}
