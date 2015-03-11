package com.dianping.ba.finance.exchange.api.beans;

import java.math.BigDecimal;

/**
 * Created by will on 15-3-6.
 */
public class AccountUpdateInfoBean {

    private int id;
    private BigDecimal balance;
    private BigDecimal credit;
    private BigDecimal debit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
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
}
