package com.dianping.ba.finance.exchange.api;

import com.dianping.ba.finance.exchange.api.beans.RORNMatchingResultBean;
import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData;
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;

import java.util.List;

/**
 * Created by noahshen on 14-7-25.
 */
public interface RORNMatchService {

    /**
     *
     * @param receiveOrderDataList
     * @param receiveNotifyDataList
     */
    RORNMatchingResultBean matching(List<ReceiveOrderData> receiveOrderDataList, List<ReceiveNotifyData> receiveNotifyDataList);
}
