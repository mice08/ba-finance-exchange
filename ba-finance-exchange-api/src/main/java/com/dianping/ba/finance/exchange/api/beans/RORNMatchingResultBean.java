package com.dianping.ba.finance.exchange.api.beans;

import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;

import java.util.List;
import java.util.LinkedList;

/**
 * Created by noahshen on 14-7-26.
 */
public class RORNMatchingResultBean {

    private List<ReceiveOrderData> matchUpdateFailedROList = new LinkedList<ReceiveOrderData>();

    public RORNMatchingResultBean() {
    }

    public List<ReceiveOrderData> getMatchUpdateFailedROList() {
        return matchUpdateFailedROList;
    }

    public void addUpdateFailedRO(ReceiveOrderData roData) {
        this.matchUpdateFailedROList.add(roData);
    }
}
