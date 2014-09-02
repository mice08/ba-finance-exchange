package com.dianping.ba.finance.exchange.paymonitor.job.paycheck;

import com.dianping.ba.finance.exchange.paymonitor.api.enums.MonitorExceptionType;
import com.dianping.ba.finance.exchange.paymonitor.job.utils.ConstantUtils;
import com.dianping.finance.common.util.DateUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;


public abstract class BasePayCheckRule implements PayCheckRule {

    private long timeout;

	protected boolean checkIfTimeout(Date d) {
		long diff = DateUtils.timeDifference(d, new Date(), TimeUnit.MINUTES);
		this.timeout = ConstantUtils.Timeout;
		return diff > timeout;
	}

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    protected PayCheckResult createValidResult() {
        return createResult(true ,false, null);
    }

    protected PayCheckResult createResult(boolean valided, boolean timeout, MonitorExceptionType exceptionType) {
        PayCheckResult result = new PayCheckResult();
        result.setValided(valided);
        result.setTimeout(timeout);
        result.setMonitorExceptionType(exceptionType);
        return result;
    }
}
