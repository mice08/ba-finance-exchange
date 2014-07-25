package com.dianping.ba.finance.exchange.api;

import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData;

/**
 * Created by Administrator on 2014/7/24.
 */
public interface ReceiveNotifyService {
    /**
     * 保存收款通知
     *
     * @param receiveNotifyData
     * @return
     */
    int insertReceiveNotify(ReceiveNotifyData receiveNotifyData);
}
