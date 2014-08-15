package com.dianping.ba.finance.exchange.receivemonitor.job.rocheck

import spock.lang.Specification

import java.text.SimpleDateFormat

/**
 * Created by IntelliJ IDEA.
 * User: yaxiong.cheng
 * Date: 2014/8/8
 * Time: 15:51
 * To change this template use File | Settings | File Templates.
 */
class ROCheckBaseTest extends Specification {
    ROCheckBase checkBaseStub

    void setup(){
        checkBaseStub = Mock()
        checkBaseStub.timeout = 5
    }

    def "checkIfTimeout"(String dateStr,boolean result) {
        given:
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = formatter.parse(dateStr)
        expect:
        result == checkBaseStub.checkIfTimeout(date)

        where:
        dateStr               ||  result
        "2014-8-8 15:19:30"   ||  false
        "2014-7-8 15:19:30"   ||  true
    }
}