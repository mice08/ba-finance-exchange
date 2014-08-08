package com.dianping.ba.finance.exchange.api.datas;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by noahshen on 14-6-3.
 */
public class AccessTokenInfoData implements Serializable {

    private int tokenId;

    private int authBusinessId;

    private String accessToken;

    private String businessName;

    private int businessType;

    private int authCode;

    private Date expireDate;

    private Date addDate;

    private String memo;

    public AccessTokenInfoData() {
    }

    public int getTokenId() {
        return tokenId;
    }

    public void setTokenId(int tokenId) {
        this.tokenId = tokenId;
    }

    public int getAuthBusinessId() {
        return authBusinessId;
    }

    public void setAuthBusinessId(int authBusinessId) {
        this.authBusinessId = authBusinessId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public int getAuthCode() {
        return authCode;
    }

    public void setAuthCode(int authCode) {
        this.authCode = authCode;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "AccessTokenInfoData{" +
                "tokenId=" + tokenId +
                ", authBusinessId=" + authBusinessId +
                ", accessToken='" + accessToken + '\'' +
                ", businessName='" + businessName + '\'' +
                ", businessType=" + businessType +
                ", authCode=" + authCode +
                ", expireDate=" + expireDate +
                ", addDate=" + addDate +
                ", memo='" + memo + '\'' +
                '}';
    }
}
