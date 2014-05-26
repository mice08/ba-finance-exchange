package com.dianping.ba.finance.exchange.biz.impl;


public class ExampleService {

    private String serviceName;

    public String hello(String from) {
        return String.format("From=%s, ServiceName=%s", from, serviceName);
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
