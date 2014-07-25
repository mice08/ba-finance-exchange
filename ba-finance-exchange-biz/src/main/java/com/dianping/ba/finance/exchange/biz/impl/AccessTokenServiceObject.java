package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.AccessTokenService;
import com.dianping.ba.finance.exchange.api.datas.AccessTokenInfoData;
import com.dianping.ba.finance.exchange.biz.dao.AuthTokenDao;
import com.google.common.collect.Maps;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by noahshen on 14-6-3.
 */
public class AccessTokenServiceObject implements AccessTokenService {

    private Map<String, AccessTokenInfoData> tokenCache = Maps.newConcurrentMap();

    private boolean useCache = true; // 默认开启缓存

    private boolean loadAllWhenInitial;

    private AuthTokenDao authTokenDao;

    public void init() {
        if (useCache && loadAllWhenInitial) {
            List<AccessTokenInfoData> tokenInfoDataList =  authTokenDao.findAllAccessTokenInfo();
            for (AccessTokenInfoData token : tokenInfoDataList) {
                tokenCache.put(token.getAccessToken(), token);
            }
        }
    }

    @Override
    public boolean verifyAccessToken(String token) {
        AccessTokenInfoData tokenInfoData = loadAccessTokenInfoData(token);
        if (tokenInfoData == null) {
            return false;
        }
        return checkExpireDate(tokenInfoData);

    }

    /**
     * 校验权限是否过期
     * @param tokenInfoData
     * @return
     */
    private boolean checkExpireDate(AccessTokenInfoData tokenInfoData) {
        Date current = new Date();
        Date expireDate = tokenInfoData.getExpireDate();
        return expireDate == null || expireDate.compareTo(current) > 0;
    }

    @Override
    public boolean verifyAccessToken(String token, int businessType) {
        AccessTokenInfoData tokenInfoData = loadAccessTokenInfoData(token);
        if (tokenInfoData == null) {
            return false;
        }
        if (!checkExpireDate(tokenInfoData)) {
            return false;
        }
        return tokenInfoData.getBusinessType() == businessType;
    }

    private AccessTokenInfoData loadAccessTokenInfoData(String token) {
        if (useCache) {
            AccessTokenInfoData accessTokenInfoData = tokenCache.get(token);
            if (accessTokenInfoData == null) {
                accessTokenInfoData = authTokenDao.loadAccessTokenInfo(token);
                if (accessTokenInfoData != null) {
                    tokenCache.put(token, accessTokenInfoData);
                }
            }
            return accessTokenInfoData;
        }
        return authTokenDao.loadAccessTokenInfo(token);
    }

    @Override
    public void clearCache() {
        if (useCache) {
            tokenCache.clear();
        }
    }

    public void setLoadAllWhenInitial(boolean loadAllWhenInitial) {
        this.loadAllWhenInitial = loadAllWhenInitial;
    }

    public void setAuthTokenDao(AuthTokenDao authTokenDao) {
        this.authTokenDao = authTokenDao;
    }

    public void setUseCache(boolean useCache) {
        this.useCache = useCache;
    }
}
