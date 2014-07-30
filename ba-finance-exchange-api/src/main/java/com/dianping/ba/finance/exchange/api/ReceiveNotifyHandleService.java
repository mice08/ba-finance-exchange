package com.dianping.ba.finance.exchange.api;

import com.dianping.ba.finance.exchange.api.dtos.ReceiveNotifyDTO;

/**
 * Created by Administrator on 2014/7/23.
 */
public interface ReceiveNotifyHandleService {
    public void handleReceiveNotify(ReceiveNotifyDTO receiveNotifyDTO);
}
