package com.dianping.ba.finance.exchange.api;

/**
 *
 */
public interface AccessTokenService {

    /**
     * 校验token是否存在
     * @param token
     * @return
     */
    public boolean verifyAccessToken(String token);

    /**
     * 校验token是否存在，及businessType是否正确
     * @param token
     * @param businessType
     * @return
     */
    public boolean verifyAccessToken(String token, int businessType);

    /**
     * 清除缓存
     */
    public void clearCache();

}
