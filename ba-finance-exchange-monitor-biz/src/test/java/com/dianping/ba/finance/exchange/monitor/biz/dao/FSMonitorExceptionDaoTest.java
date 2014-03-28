package com.dianping.ba.finance.exchange.monitor.biz.dao;

import com.dianping.ba.finance.exchange.monitor.api.datas.ExceptionData;
import com.dianping.ba.finance.exchange.monitor.api.enums.ExceptionStatus;
import com.dianping.ba.finance.exchange.monitor.api.enums.ExceptionType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: noahshen
 * Date: 14-3-26
 * Time: 上午10:45
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/config/spring/common/appcontext-*.xml",
        "classpath*:/config/spring/local/appcontext-*.xml",
        "classpath*:/config/spring/test/appcontext-*.xml"})
public class FSMonitorExceptionDaoTest {

    @Autowired
    private FSMonitorExceptionDao fsMonitorExceptionDao;

    @Test
    public void testInsertMonitorException() throws Exception {
        ExceptionData ed = new ExceptionData();
        ed.setEoId(123);
        ed.setStatus(ExceptionStatus.INIT.value());
        ed.setExceptionType(ExceptionType.DEFAULT.value());
        ed.setAddDate(new Date());
        int i = fsMonitorExceptionDao.insertMonitorException(ed);
        System.out.println("id:" + i);
    }

    @Test
    public void testFindExceptions() throws Exception {
        int status = ExceptionStatus.INIT.value();
        List<ExceptionData> exceptionDataList = fsMonitorExceptionDao.findExceptions(status);
        System.out.println(exceptionDataList);
    }

    @Test
    public void testUpdateStatus() throws Exception {
        List<Integer> idList = Arrays.asList(1);
        int setStatus = ExceptionStatus.HANDLED.value();
        int preStatus = ExceptionStatus.INIT.value();
        int u = fsMonitorExceptionDao.updateStatus(idList, setStatus, preStatus);
        System.out.println("u:" + u);


    }
}
