package com.dianping.ba.finance.exchange.receivemonitor.job.rocheck;


import com.dianping.ba.finance.exchange.receivemonitor.api.enums.ExceptionType;

/**
 * Created with IntelliJ IDEA.
 * User: jingzhou.yan
 * Date: 14-3-27
 * Time: 下午2:59
 * To change this template use File | Settings | File Templates.
 */
public class ROCheckResult {
    private boolean valid;

    private boolean timeout;

    private ExceptionType exceptionType;

    public ROCheckResult() {
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
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
