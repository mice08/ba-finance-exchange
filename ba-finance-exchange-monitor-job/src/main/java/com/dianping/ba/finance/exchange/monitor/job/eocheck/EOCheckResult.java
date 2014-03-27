package com.dianping.ba.finance.exchange.monitor.job.eocheck;

import com.dianping.ba.finance.exchange.monitor.api.enums.ExceptionType;

/**
 * Created with IntelliJ IDEA.
 * User: jingzhou.yan
 * Date: 14-3-27
 * Time: 下午2:59
 * To change this template use File | Settings | File Templates.
 */
public class EOCheckResult {
    private boolean valided;

    private boolean timeout;

    private ExceptionType exceptionType;

    public EOCheckResult() {
    }

    public boolean isValided() {
        return valided;
    }

    public void setValided(boolean valided) {
        this.valided = valided;
    }

    public boolean isTimeout() {
        return timeout;
    }

    public void setTimeout(boolean timeout) {
        this.timeout = timeout;
    }

    public ExceptionType getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }
}
