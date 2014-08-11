package com.dianping.ba.finance.exchange.paymonitor.api.datas;

import java.io.Serializable;

/**
 * Created by adam.huang on 2014/8/7.
 */
public class PayOrderMonitorData implements Serializable {
    private int poId;
    private int status;

    public int getPoId() {
        return poId;
    }

    public void setPoId(int poId) {
        this.poId = poId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PayOrderMonitorData{" +
                "poId=" + poId +
                ", status=" + status +
                '}';
    }
}
