package com.dianping.ba.finance.exchange.api.dtos;

import java.io.Serializable;

/**
 * Created by 遐 on 2015/3/6.
 */
public class BankPayResultDTO implements Serializable {

    private String instId;
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
