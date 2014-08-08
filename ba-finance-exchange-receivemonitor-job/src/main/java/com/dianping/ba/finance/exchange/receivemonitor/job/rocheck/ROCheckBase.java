package com.dianping.ba.finance.exchange.receivemonitor.job.rocheck;


import com.dianping.ba.finance.exchange.receivemonitor.api.enums.ExceptionType;
import com.dianping.ba.finance.exchange.receivemonitor.job.utils.ConstantUtils;
import com.dianping.finance.common.util.DateUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: jingzhou.yan
 * Date: 14-3-27
 * Time: 下午2:59
 * To change this template use File | Settings | File Templates.
 */
public abstract class ROCheckBase implements ROCheckRule {
    private long timeout;

    protected boolean checkIfTimeout(Date date) {
        timeout = ConstantUtils.refundTimeout;
        long diff = DateUtils.timeDifference(date, new Date(), TimeUnit.MINUTES);
        return diff > timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    protected ROCheckResult createValidResult() {
        return createResult(true ,false, ExceptionType.DEFAULT);
    }

    protected ROCheckResult createResult(boolean valid, boolean timeout, ExceptionType exceptionType) {
        ROCheckResult result = new ROCheckResult();
        result.setValid(valid);
        result.setTimeout(timeout);
        result.setExceptionType(exceptionType);
        return result;
    }
}
