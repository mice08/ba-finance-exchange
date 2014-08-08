package com.dianping.ba.finance.exchange.paymonitor.biz.impl

import com.dianping.ba.finance.exchange.paymonitor.api.datas.MonitorExceptionData
import com.dianping.ba.finance.exchange.paymonitor.api.datas.MonitorTodoData
import com.dianping.ba.finance.exchange.paymonitor.biz.dao.MonitorExceptionDao
import com.dianping.ba.finance.exchange.paymonitor.biz.dao.MonitorTimeDao
import com.dianping.ba.finance.exchange.paymonitor.biz.dao.MonitorTodoDao
import spock.lang.Specification
/**
 * Created by adam.huang on 2014/8/7.
 */
class PayMonitorServiceObjectTest extends Specification {
    PayMonitorServiceObject payMonitorServiceObjectStub;
    MonitorTodoDao monitorTodoDaoMock;
    MonitorTimeDao monitorTimeDaoMock;
    MonitorExceptionDao monitorExceptionDaoMock;

    def setup(){
        payMonitorServiceObjectStub = new PayMonitorServiceObject();

        monitorTodoDaoMock = Mock();
        payMonitorServiceObjectStub.monitorTodoDao = monitorTodoDaoMock;

        monitorTimeDaoMock = Mock();
        payMonitorServiceObjectStub.monitorTimeDao = monitorTimeDaoMock;

        monitorExceptionDaoMock = Mock();
        payMonitorServiceObjectStub.monitorExceptionDao = monitorExceptionDaoMock;
    }

    def "GetLastMonitorTime"() {
        given:
        when:
        payMonitorServiceObjectStub.getLastMonitorTime();
        then:
        1 * monitorTimeDaoMock.getLastMonitorTime();
    }

    def "AddMonitorTime"() {
        given:
        Date date = new Date();
        when:
        payMonitorServiceObjectStub.addMonitorTime(date);
        then:
        1 * monitorTimeDaoMock.addMonitorTime(_ as Date);
    }

    def "AddMonitorTodo"() {
        given:
        MonitorTodoData monitorTodoData = [];
        when:
        payMonitorServiceObjectStub.addMonitorTodo(monitorTodoData);
        then:
        1 * monitorTodoDaoMock.addMonitorTodo(_ as MonitorTodoData);
    }

    def "AddMonitorException"() {
        given:
        MonitorExceptionData monitorExceptionData = [];
        when:
        payMonitorServiceObjectStub.addMonitorException(monitorExceptionData);
        then:
        1 * monitorExceptionDaoMock.addMonitorException(_ as MonitorExceptionData);
    }

    def "FindUnhandldedMonitorTodoDatas"() {
        given:
        when:
        payMonitorServiceObjectStub.findUnhandldedMonitorTodoDatas();
        then:
        1 * monitorTodoDaoMock.findMonitorTodoDatas(1);
    }

    def "FindUnhandledMonitorExceptionDatas"() {
        given:
        when:
        payMonitorServiceObjectStub.findUnhandledMonitorExceptionDatas();
        then:
        1 * monitorExceptionDaoMock.findMonitorExceptionDatas(1);
    }

    def "UpdateExceptionToHandled"() {
        given:
        when:
        payMonitorServiceObjectStub.updateExceptionToHandled([1]);
        then:
        1 * monitorExceptionDaoMock.updateMonitorExceptionStatus([1],2);
    }

    def "UpdateMonitorTodoDataToHandled"() {
        given:
        when:
        payMonitorServiceObjectStub.updateMonitorTodoDataToHandled([1]);
        then:
        1 * monitorTodoDaoMock.updateMonitorToDoStatus([1],2);
    }
}
