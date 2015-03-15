package com.dianping.ba.finance.exchange.api.dtos;

import java.io.Serializable;

/**
 * Created by will on 15-3-15.
 */
public class AuthMsgDTO implements Serializable {

    private String userName;
    private String token;

    public AuthMsgDTO() {}

    public AuthMsgDTO(String userName, String token) {
        this.userName = userName;
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
