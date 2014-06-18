package com.dianping.ba.finance.exchange.api.beans;

import java.io.Serializable;

/**
 * Created by noahshen on 14-6-17.
 */
public class CustNameSuggestionBean implements Serializable{

    private int customerId;

    private String customerName;

    public CustNameSuggestionBean() {
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return "CustNameSuggestionBean{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                '}';
    }
}
