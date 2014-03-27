package com.dianping.ba.finance.exchange.monitor.job.eocheck;

import com.dianping.ba.finance.exchange.monitor.api.enums.ExceptionType;
import com.dianping.ba.finance.exchange.monitor.job.utils.DateUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: jingzhou.yan
 * Date: 14-3-27
 * Time: 下午2:59
 * To change this template use File | Settings | File Templates.
 */
public abstract class EOCheckBase implements EOCheckRule {
    private long timeout;

    protected boolean checkIfTimeout(Date d) {
        long diff = DateUtils.timeDifference(d, new Date(), TimeUnit.MINUTES);
        return diff > timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    protected EOCheckResult createValidResult() {
        return createResult(true ,false, null);
    }

    protected EOCheckResult createResult(boolean valided, boolean timeout, ExceptionType exceptionType) {
        EOCheckResult result = new EOCheckResult();
        result.setValided(valided);
        result.setTimeout(timeout);
        result.setExceptionType(exceptionType);
        return result;
    }
}
