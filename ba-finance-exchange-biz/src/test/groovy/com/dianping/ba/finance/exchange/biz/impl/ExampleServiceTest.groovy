package com.dianping.ba.finance.exchange.biz.impl

/**
 * Created by noahshen on 14-5-26.
 */
class ExampleServiceTest extends GroovyTestCase {

    def exampleServiceStub = new ExampleService();
    void setUp() {
        super.setUp()
        exampleServiceStub.setServiceName("exampleService");
    }

    void testHello() {
        def result = exampleServiceStub.hello("noah");
        assert result == "From=noah, ServiceName=exampleService"
    }
}
