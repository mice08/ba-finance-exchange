package com.dianping.ba.finance.exchange.api.datas;

import java.io.Serializable;

/**
 * 付款单
 */
public class PayOrderForSuccessData implements Serializable {

    private static final long serialVersionUID = -1L;
    /**
     * 主键
     */
    private int poId;
    /**
     * 业务类型
     */
    private int businessType;

    public int getPoId() {
        return poId;
    }

    public void setPoId(int poId) {
        this.poId = poId;
    }

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    @Override
    public String toString() {
        return "PayOrderForSuccessData{" +
                "poId=" + poId +
                ", businessType=" + businessType +
                '}';
    }
}
