package com.dianping.ba.finance.exchange.api;

/**
 * Created by will on 15-3-10.
 */
public interface AuthStatusInfoService {

    boolean isRSALock(String workNo);
    boolean updateRetryTimes(String workNo, int type);
    boolean removeAuthStatus(String workNo, int type);
}
