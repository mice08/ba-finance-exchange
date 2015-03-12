package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.AuthStatusInfoService;
import com.dianping.ba.finance.exchange.api.datas.AuthStatusInfoData;
import com.dianping.ba.finance.exchange.api.enums.AuthType;
import com.dianping.ba.finance.exchange.biz.dao.AuthStatusInfoDao;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.util.LionConfigUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by will on 15-3-10.
 */
public class AuthStatusInfoServiceObject implements AuthStatusInfoService {

    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.biz.impl.monitor.AuthStatusInfoServiceObject");

    @Autowired
    private AuthStatusInfoDao authStatusInfoDao;

    @Override
    @Log(logBefore = true, logAfter = true)
    public boolean isRSALock(String workNo) {
        try {
            AuthStatusInfoData data = authStatusInfoDao.loadStatusByWorkNoAndType(workNo, AuthType.RSA.getType());
            return data != null && data.getTimes() > Integer.parseInt(LionConfigUtils.getProperty("ba-finance-expense-service.rsa.retry.times", "3"));
        } catch (Exception e) {
            MONITOR_LOGGER.error("severity=[2] AuthStatusInfoServiceObject.isRSALock error", e);
        }
        return false;
    }

    @Override
    @Log(logBefore = true, logAfter = true)
    public synchronized boolean updateRetryTimes(String workNo, int type) {
        try {
            AuthStatusInfoData data = authStatusInfoDao.loadStatusByWorkNoAndType(workNo, type);
            if (data == null) {
                return authStatusInfoDao.insertAuthStatusInfo(workNo, 1, type) > 0;
            } else {
                return authStatusInfoDao.updateAuthTimes(data.getId()) > 0;
            }
        } catch (Exception e) {
            MONITOR_LOGGER.error("severity=[2] AuthStatusInfoServiceObject.updateRetryTimes fail", e);
        }
        return false;
    }

    @Override
    public boolean removeAuthStatus(String workNo, int type) {
        try {
            return authStatusInfoDao.updateStatusInvalid(workNo, type) > 0;
        } catch (Exception e) {
            MONITOR_LOGGER.error("severity=[2] AuthStatusInfoServiceObject.removeAuthStatus fail", e);
        }
        return false;
    }

    public void setAuthStatusInfoDao(AuthStatusInfoDao authStatusInfoDao) {
        this.authStatusInfoDao = authStatusInfoDao;
    }
}
