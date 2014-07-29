package com.dianping.ba.finance.exchange.api.datas;

import java.io.Serializable;

/**
 * Created by noahshen on 14-6-16.
 */
public class ReceiveOrderPaginateData extends ReceiveOrderData implements Serializable{

    private int matchedCount;

    public ReceiveOrderPaginateData() {
    }

    public int getMatchedCount() {
        return matchedCount;
    }

    public void setMatchedCount(int matchedCount) {
        this.matchedCount = matchedCount;
    }

    @Override
    public String toString() {
        return super.toString() + "  ReceiveOrderPaginateData{" +
                "matchedCount=" + matchedCount +
                '}';
    }
}
