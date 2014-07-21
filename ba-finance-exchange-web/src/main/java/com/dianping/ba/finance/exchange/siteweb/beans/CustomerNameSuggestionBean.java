package com.dianping.ba.finance.exchange.siteweb.beans;

/**
 * Created by noahshen on 14-7-8.
 */
public class CustomerNameSuggestionBean {

    private int customerId;

    private String customerName;

    public CustomerNameSuggestionBean() {
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
        return "CustomerNameSuggestionBean{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                '}';
    }
}
