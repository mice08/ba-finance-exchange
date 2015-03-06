package com.dianping.ba.finance.exchange.api.dtos;

import java.io.Serializable;

/**
 * Created by 遐 on 2015/3/6.
 */
public class BankPayResultDTO implements Serializable {

    private String instId;
    private int status;
    private String message;

    public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
