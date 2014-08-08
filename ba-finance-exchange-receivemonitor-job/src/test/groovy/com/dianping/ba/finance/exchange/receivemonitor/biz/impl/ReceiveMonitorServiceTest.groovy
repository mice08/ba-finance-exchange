package com.dianping.ba.finance.exchange.receivemonitor.biz.impl

import com.dianping.ba.finance.exchange.receivemonitor.api.ReceiveMonitorService
import com.dianping.ba.finance.exchange.receivemonitor.api.datas.ExceptionData
import com.dianping.ba.finance.exchange.receivemonitor.api.datas.MonitorTimeData
import com.dianping.ba.finance.exchange.receivemonitor.api.datas.TodoData
import com.dianping.ba.finance.exchange.receivemonitor.biz.dao.FSMonitorExceptionDao
import com.dianping.ba.finance.exchange.receivemonitor.biz.dao.FSMonitorTimeDao
import com.dianping.ba.finance.exchange.receivemonitor.biz.dao.FSMonitorTodoDao
import spock.lang.Specification

/**
 * Created by IntelliJ IDEA.
 * User: yaxiong.cheng
 * Date: 2014/8/8
 * Time: 16:48
 * To change this template use File | Settings | File Templates.
 */
class ReceiveMonitorServiceTest extends Specification {
    ReceiveMonitorServiceObject serviceStub
    FSMonitorTimeDao timeDaoMock
    FSMonitorTodoDao todoDaoMock
    FSMonitorExceptionDao exceptionDaoMock

    void setup() {
        serviceStub = []
        timeDaoMock = Mock()
        todoDaoMock = Mock()
        exceptionDaoMock = Mock()
        serviceStub.fsMonitorExceptionDao = exceptionDaoMock
        serviceStub.fsMonitorTimeDao = timeDaoMock
        serviceStub.fsMonitorTodoDao = todoDaoMock
    }

    def "addTodo"(){
        given:
        TodoData data = [roId:1,
                         status:2]
        when:
        serviceStub.addTodo(data)

        then:
        1*todoDaoMock.insertTodoData(_ as TodoData)
    }

    def "addMonitorException"(){
        given:
        ExceptionData data = [roId:1,
                         status:2]
        when:
        serviceStub.addMonitorException(data)

        then:
        1*exceptionDaoMock.insertMonitorException(_ as ExceptionData)
    }

    def "addMonitorTime"(){
        given:
        Date date = new Date()

        when:
        serviceStub.addMonitorTime(date)

        then:
        1*timeDaoMock.insertMonitorTime(_ as MonitorTimeData)
    }
}